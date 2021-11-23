package com.keyin.httpclient;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.message.BasicNameValuePair;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.Authenticator;
import java.net.PasswordAuthentication;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

public class SprintHttpClient {
    private static HttpClient client;
    private static HttpRequest request, postRequest;
    private static HttpPost httpPost;
    private static HttpResponse response;
    private static Scanner scanner;
    private static int input; // Integer user input
    private static String input2, firstName, lastName, email, phoneNumber; // String user input
    private static HashMap values;
    private static ObjectMapper objectMapper;
    private static String requestBody;
    public static void main(String[] args) throws UnsupportedEncodingException, JsonProcessingException {
        while(true){
            client = HttpClient.newHttpClient();
            System.out.println("Welcome. Please choose an API call number or enter 0 to exit");
            System.out.println("Choices: 1. GET all People. 2. GET all People by Last Name 3. POST new people");
            scanner = new Scanner(System.in);
            input = scanner.nextInt();

            // List by class's first ("What data would you like to access?")
            switch(input){
                case 0:
                    System.out.println("Goodbye!");
                    System.exit(0);
                case 1:
                    request = HttpRequest.newBuilder().uri(URI.create("http://localhost:8080/api/people")).build();
                    try {
                        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
                        if (response.statusCode()==200) {
                            System.out.println(response.body());
                            System.out.println();
                        }
                        else{
                            System.out.print("*** Error Code *** : " + response.statusCode() + "\n \n");
                        }

                    } catch (IOException | InterruptedException e) {
                        e.printStackTrace();
                    }
                    break;
                case 2:
                    System.out.println("Please enter the Last Name");
                    input2 = scanner.next();
                    request = HttpRequest.newBuilder().uri(URI.create("http://localhost:8080/api/people?lastName=" + input2)).build();
                    try {
                        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
                        if (response.statusCode()==200) {
                            System.out.println(response.body());
                            System.out.println();
                        }
                        else{
                            System.out.print("*** Error Code *** : " + response.statusCode() + "\n \n");
                        }

                    } catch (IOException | InterruptedException e) {
                        e.printStackTrace();
                    }
                    break;
                case 3:
                    System.out.println("Please enter the Person's info, you can leave any field blank");
                    firstName = scanner.next();
                    lastName = scanner.next();
                    email = scanner.next();
                    phoneNumber = scanner.next();
                    values = new HashMap<String, String>(){{
                        put("firstName", firstName);
                        put("lastName", lastName);
                        put("email", email);
                        put("phoneNumber", phoneNumber);
                    }};

                    objectMapper = new ObjectMapper();
                    requestBody = objectMapper.writeValueAsString(values);
                    postRequest = HttpRequest.newBuilder()
                            .uri(URI.create("http://localhost:8080/api/people"))
                            .header("Content-Type", "application/json")
                            .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                            .build();
                    try {
                        response = client.send(postRequest,
                                HttpResponse.BodyHandlers.ofString());
                        if (response.statusCode()==200) {
                            System.out.println(response.body());
                            System.out.println();
                        }
                        else if(response.statusCode()==201){
                            System.out.println("### Post Successful ###" + "\n \n");
                        }
                        else{
                            System.out.print("*** Error Code *** : " + response.statusCode() + "\n \n");
                        }

                    } catch (IOException | InterruptedException e) {
                        e.printStackTrace();
                    }
                    break;


                default:
                    System.out.println("Oops! Something went wrong :( Please try again or enter in 0 to exit.");



            }
        }
    }
}
