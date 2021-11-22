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
    private static Scanner scanner = new Scanner(System.in);
    private static String input;
    public static void main(String[] args){
        while(true){
            System.out.println("Welcome. Please choose an API call");
            input = scanner.next();

            client = HttpClient.newHttpClient();
            request = HttpRequest.newBuilder().uri(URI.create("http://localhost:8080/api/people")).build();
            try {
                HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
                if (response.statusCode()==200) {
                    System.out.println(response.body());
                }

            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
