package com.keyin.httpclient;

import java.io.IOException;
import java.net.Authenticator;
import java.net.PasswordAuthentication;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import java.util.Scanner;

public class SprintHttpClient {
    private static HttpClient client;
    private static HttpRequest request;
    private static Scanner scanner;
    private static Scanner scanner2;
    private static int input; //User input
    private static String input2; //User input
    public static void main(String[] args){
        while(true){
            client = HttpClient.newHttpClient();
            System.out.println("Welcome. Please choose an API call number or enter 0 to exit");
            System.out.println("Choices: 1. GET all People. 2. GET all People by Last Name");
            scanner = new Scanner(System.in);
            scanner2 = new Scanner(System.in);
            input = scanner.nextInt();

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

                    } catch (IOException | InterruptedException e) {
                        e.printStackTrace();
                    }
                    break;
                case 2:
                    System.out.println("Please enter the Last Name");
                    input2 = scanner2.next();
                    request = HttpRequest.newBuilder().uri(URI.create("http://localhost:8080/api/people?lastName=" + input2)).build();
                    try {
                        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
                        if (response.statusCode()==200) {
                            System.out.println(response.body());
                            System.out.println();
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
