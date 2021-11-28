package com.keyin.httpclient;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.client.methods.HttpPost;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.Scanner;

public class SprintHttpClient {
    // Instance Variables
    private static HttpClient client;
    private static HttpRequest request, postRequest;
    private static HttpResponse response;
    private static Scanner scanner;
    private static int input, input2; // Integer user input
    private static String input3, firstName, lastName, email, phoneNumber, id, membershipStartDate;
    private static String membershipDuration, membershipType, currentTournamentDate, pastTournamentDate;
    private static String startDate, endDate, location, entryFee, cashPrizeAmount, futureTournamentDate, score;
    private static HashMap values;
    private static ObjectMapper objectMapper;
    private static String requestBody;

    public static void main(String[] args) throws UnsupportedEncodingException, JsonProcessingException {

        // Main Menu
        while (true) {
            client = HttpClient.newHttpClient();
            System.out.println("Welcome. What data would you like to interact with?");
            System.out.println("Choices: 1. People. 2. Members 3. Tournaments" +
                    " 4. Current Tournaments 5. Past Tournaments 6. Future Tournaments 7. Final Standings");
            scanner = new Scanner(System.in);
            input = scanner.nextInt();


            switch (input) {
                case 0:
                    System.out.println("Goodbye!");
                    System.exit(0);
                    // People Table
                case 1:
                    while (true) {
                        System.out.println("Please choose an option or enter 0 to exit");
                        System.out.println("Choices: 1. GET all People. 2. Get People By ID 3. GET all People by Last Name" +
                                "4.  POST new people 5. UPDATE People by id 6. DELETE People by Id");
                        input2 = scanner.nextInt();
                        if (input2 == 0) {
                            break;
                        }
                        // Get all people
                        switch (input2) {
                            case 1:
                                request = HttpRequest.newBuilder().uri(URI.create("http://localhost:8080/api/people")).build();
                                try {
                                    HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
                                    if (response.statusCode() == 200) {
                                        System.out.println(response.body());
                                        System.out.println();
                                    } else {
                                        System.out.print("*** Error Code *** : " + response.statusCode() + "\n \n");
                                    }

                                } catch (IOException | InterruptedException e) {
                                    e.printStackTrace();
                                }
                                break;
                            // Get all people by id
                            case 2:
                                System.out.println("Please enter the id of the Person");
                                input3 = scanner.next();
                                request = HttpRequest.newBuilder().uri(URI.create("http://localhost:8080/api/people/" + input3)).build();
                                try {
                                    HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
                                    if (response.statusCode() == 200) {
                                        System.out.println(response.body());
                                        System.out.println();
                                    } else {
                                        System.out.print("*** Error Code *** : " + response.statusCode() + "\n \n");
                                    }

                                } catch (IOException | InterruptedException e) {
                                    e.printStackTrace();
                                }
                                break;
                            // Get all people by last name
                            case 3:
                                System.out.println("Please enter the Last Name");
                                input3 = scanner.next();
                                request = HttpRequest.newBuilder().uri(URI.create("http://localhost:8080/api/people?lastName=" + input3)).build();
                                try {
                                    HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
                                    if (response.statusCode() == 200) {
                                        System.out.println(response.body());
                                        System.out.println();
                                    } else {
                                        System.out.print("*** Error Code *** : " + response.statusCode() + "\n \n");
                                    }

                                } catch (IOException | InterruptedException e) {
                                    e.printStackTrace();
                                }
                                break;
                            // Post people
                            case 4:
                                System.out.println("Please enter the Person's info you wish to POST, you can leave any field blank");
                                System.out.println("firstname:");
                                firstName = scanner.next();
                                System.out.println("lastname:");
                                lastName = scanner.next();
                                System.out.println("email:");
                                email = scanner.next();
                                System.out.println("phone number:");
                                phoneNumber = scanner.next();
                                values = new HashMap<String, String>() {{
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
                                    if (response.statusCode() == 200) {
                                        System.out.println(response.body());
                                        System.out.println();
                                    } else if (response.statusCode() == 201) {
                                        System.out.println("### Post Successful ###" + "\n \n");
                                    } else {
                                        System.out.print("*** Error Code *** : " + response.statusCode() + "\n \n");
                                    }

                                } catch (IOException | InterruptedException e) {
                                    e.printStackTrace();
                                }
                                break;
                            // Update people by id
                            case 5:
                                System.out.println("Please enter the ID for the record you wish to alter");
                                id = scanner.next();
                                System.out.println("Please enter the Person's info that you wish to update, you can leave any field blank");
                                System.out.println("firstname:");
                                firstName = scanner.next();
                                System.out.println("lastname:");
                                lastName = scanner.next();
                                System.out.println("email:");
                                email = scanner.next();
                                System.out.println("phone number:");
                                phoneNumber = scanner.next();
                                values = new HashMap<String, String>() {{
                                    put("firstName", firstName);
                                    put("lastName", lastName);
                                    put("email", email);
                                    put("phoneNumber", phoneNumber);
                                }};

                                objectMapper = new ObjectMapper();
                                requestBody = objectMapper.writeValueAsString(values);
                                postRequest = HttpRequest.newBuilder()
                                        .uri(URI.create("http://localhost:8080/api/people/" + id))
                                        .header("Content-Type", "application/json")
                                        .PUT(HttpRequest.BodyPublishers.ofString(requestBody))
                                        .build();
                                try {
                                    response = client.send(postRequest,
                                            HttpResponse.BodyHandlers.ofString());
                                    if (response.statusCode() == 200) {
                                        System.out.println("### UPDATE Successful ###" + "\n \n");
                                        System.out.println(response.body());
                                        System.out.println();
                                    } else {
                                        System.out.print("*** Error Code *** : " + response.statusCode() + "\n \n");
                                    }

                                } catch (IOException | InterruptedException e) {
                                    e.printStackTrace();
                                }
                                break;
                            // Delete people by id
                            case 6:
                                System.out.println("Please enter and ID for the record you wish to delete");
                                id = scanner.next();
                                request = HttpRequest.newBuilder().DELETE().uri(URI.create("http://localhost:8080/api/people/" + id)).build();
                                try {
                                    HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
                                    if (response.statusCode() == 204) {
                                        System.out.println("### Successfully deleted Person object with ID: " + id + " ###");
                                        System.out.println(response.body());
                                        System.out.println();
                                    } else {
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
                    break;
                    // Members Table
                case 2:
                    while (true) {
                        System.out.println("Please choose an option or enter 0 to exit");
                        System.out.println("Choices: 1. GET all Members. 2. GET Members By ID 3. POST new Members " +
                                "4. UPDATE Member by id 5. DELETE Member by Id");
                        input2 = scanner.nextInt();
                        if (input2 == 0) {
                            break;
                        }
                        switch (input2) {
                            // Get all members
                            case 1:
                                request = HttpRequest.newBuilder().uri(URI.create("http://localhost:8080/api/members")).build();
                                try {
                                    HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
                                    if (response.statusCode() == 200) {
                                        System.out.println(response.body());
                                        System.out.println();
                                    } else {
                                        System.out.print("*** Error Code *** : " + response.statusCode() + "\n \n");
                                    }

                                } catch (IOException | InterruptedException e) {
                                    e.printStackTrace();
                                }
                                break;
                            // Get members by id
                            case 2:
                                System.out.println("Please enter the id of the Member");
                                input3 = scanner.next();
                                request = HttpRequest.newBuilder().uri(URI.create("http://localhost:8080/api/members/" + input3)).build();
                                try {
                                    HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
                                    if (response.statusCode() == 200) {
                                        System.out.println(response.body());
                                        System.out.println();
                                    } else {
                                        System.out.print("*** Error Code *** : " + response.statusCode() + "\n \n");
                                    }

                                } catch (IOException | InterruptedException e) {
                                    e.printStackTrace();
                                }
                                break;
                            // Post members
                            case 3:
                                System.out.println("Please enter the Member's info you wish to POST, you can leave any field blank");
                                System.out.println("firstname:");
                                firstName = scanner.next();
                                System.out.println("lastname:");
                                lastName = scanner.next();
                                System.out.println("email:");
                                email = scanner.next();
                                System.out.println("phone number:");
                                phoneNumber = scanner.next();
                                System.out.println("membership start date (yyyy-mm-dd):");
                                membershipStartDate = scanner.next();
                                System.out.println("membership end date (yyyy-mm-dd):");
                                membershipDuration = scanner.next();
                                System.out.println("membership type:");
                                membershipType = scanner.next();
                                System.out.println("current tournament dates (yyyy-mm-dd):");
                                currentTournamentDate = scanner.next();
                                System.out.println("past tournament dates (yyyy-mm-dd):");
                                pastTournamentDate = scanner.next();
                                values = new HashMap<String, String>() {
                                    {
                                        put("firstName", firstName);
                                        put("lastName", lastName);
                                        put("email", email);
                                        put("phoneNumber", phoneNumber);
                                    }

                                    {
                                        put("membershipStartDate", membershipStartDate);
                                        put("membershipDuration", membershipDuration);
                                        put("membershipType", membershipType);
                                    }

                                    {
                                        put("currentTournamentDate", currentTournamentDate);
                                    }

                                    {
                                        put("pastTournamentDate", pastTournamentDate);
                                    }
                                };

                                objectMapper = new ObjectMapper();
                                requestBody = objectMapper.writeValueAsString(values);
                                postRequest = HttpRequest.newBuilder()
                                        .uri(URI.create("http://localhost:8080/api/members"))
                                        .header("Content-Type", "application/json")
                                        .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                                        .build();
                                try {
                                    response = client.send(postRequest,
                                            HttpResponse.BodyHandlers.ofString());
                                    if (response.statusCode() == 200) {
                                        System.out.println(response.body());
                                        System.out.println();
                                    } else if (response.statusCode() == 201) {
                                        System.out.println("### Post Successful ###" + "\n \n");
                                    } else {
                                        System.out.print("*** Error Code *** : " + response.statusCode() + "\n \n");
                                    }

                                } catch (IOException | InterruptedException e) {
                                    e.printStackTrace();
                                }
                                break;
                            // Update members by id
                            case 4:
                                System.out.println("Please enter the ID for the record you wish to alter");
                                id = scanner.next();
                                System.out.println("Please enter the Member's info that you wish to update, you can leave any field blank");
                                System.out.println("firstname:");
                                firstName = scanner.next();
                                System.out.println("lastname:");
                                lastName = scanner.next();
                                System.out.println("email:");
                                email = scanner.next();
                                System.out.println("phone number:");
                                phoneNumber = scanner.next();
                                System.out.println("membership start date (yyyy-mm-dd):");
                                membershipStartDate = scanner.next();
                                System.out.println("membership end date (yyyy-mm-dd):");
                                membershipDuration = scanner.next();
                                System.out.println("membership type (yyyy-mm-dd):");
                                membershipType = scanner.next();
                                System.out.println("current tournament dates (yyyy-mm-dd):");
                                currentTournamentDate = scanner.next();
                                System.out.println("past tournament dates (yyyy-mm-dd):");
                                pastTournamentDate = scanner.next();
                                values = new HashMap<String, String>() {
                                    {
                                        put("firstName", firstName);
                                        put("lastName", lastName);
                                        put("email", email);
                                        put("phoneNumber", phoneNumber);
                                    }

                                    {
                                        put("membershipStartDate", membershipStartDate);
                                        put("membershipDuration", membershipDuration);
                                        put("membershipType", membershipType);
                                    }

                                    {
                                        put("currentTournamentDate", currentTournamentDate);
                                    }

                                    {
                                        put("pastTournamentDate", pastTournamentDate);
                                    }
                                };

                                objectMapper = new ObjectMapper();
                                requestBody = objectMapper.writeValueAsString(values);
                                postRequest = HttpRequest.newBuilder()
                                        .uri(URI.create("http://localhost:8080/api/members/" + id))
                                        .header("Content-Type", "application/json")
                                        .PUT(HttpRequest.BodyPublishers.ofString(requestBody))
                                        .build();
                                try {
                                    response = client.send(postRequest,
                                            HttpResponse.BodyHandlers.ofString());
                                    if (response.statusCode() == 200) {
                                        System.out.println("### UPDATE Successful ###" + "\n \n");
                                        System.out.println(response.body());
                                        System.out.println();
                                    } else {
                                        System.out.print("*** Error Code *** : " + response.statusCode() + "\n \n");
                                    }

                                } catch (IOException | InterruptedException e) {
                                    e.printStackTrace();
                                }
                                break;
                            // Delete member by id
                            case 5:
                                System.out.println("Please enter and ID for the record you wish to delete");
                                id = scanner.next();
                                request = HttpRequest.newBuilder().DELETE().uri(URI.create("http://localhost:8080/api/members/" + id)).build();
                                try {
                                    HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
                                    if (response.statusCode() == 204) {
                                        System.out.println("### Successfully deleted Member object with ID: " + id + " ###");
                                        System.out.println(response.body());
                                        System.out.println();
                                    } else {
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
                    break;
                    // Tournaments Table
                case 3:
                    while (true) {
                        System.out.println("Please choose an option or enter 0 to exit");
                        System.out.println("Choices: 1. GET all Tournaments. 2. GET Tournament By ID 3. POST new Tournament " +
                                "4. UPDATE Tournament by id 5. DELETE Tournament by Id");
                        input2 = scanner.nextInt();
                        if (input2 == 0) {
                            break;
                        }
                        switch (input2) {
                            // Get all tournaments
                            case 1:
                                request = HttpRequest.newBuilder().uri(URI.create("http://localhost:8080/api/tournament")).build();
                                try {
                                    HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
                                    if (response.statusCode() == 200) {
                                        System.out.println(response.body());
                                        System.out.println();
                                    } else {
                                        System.out.print("*** Error Code *** : " + response.statusCode() + "\n \n");
                                    }

                                } catch (IOException | InterruptedException e) {
                                    e.printStackTrace();
                                }
                                break;
                            // Get tournament by id
                            case 2:
                                System.out.println("Please enter the id of the Tournament");
                                input3 = scanner.next();
                                request = HttpRequest.newBuilder().uri(URI.create("http://localhost:8080/api/tournament/" + input3)).build();
                                try {
                                    HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
                                    if (response.statusCode() == 200) {
                                        System.out.println(response.body());
                                        System.out.println();
                                    } else {
                                        System.out.print("*** Error Code *** : " + response.statusCode() + "\n \n");
                                    }

                                } catch (IOException | InterruptedException e) {
                                    e.printStackTrace();
                                }
                                break;
                            // Post tournament
                            case 3:
                                System.out.println("Please enter the Tournament info you wish to POST, you can leave any field blank");
                                System.out.println("startDate (yyyy-mm-dd):");
                                startDate = scanner.next();
                                System.out.println("end date (yyyy-mm-dd):");
                                endDate = scanner.next();
                                System.out.println("location:");
                                location = scanner.next();
                                System.out.println("entry fee:");
                                entryFee = scanner.next();
                                System.out.println("cash prize amount:");
                                cashPrizeAmount = scanner.next();
                                values = new HashMap<String, String>() {
                                    {
                                        put("startDate", startDate);
                                        put("endDate", endDate);
                                        put("location", location);
                                        put("entryFee", entryFee);
                                        put("cashPrizeAmount", cashPrizeAmount);
                                    }
                                };

                                objectMapper = new ObjectMapper();
                                requestBody = objectMapper.writeValueAsString(values);
                                postRequest = HttpRequest.newBuilder()
                                        .uri(URI.create("http://localhost:8080/api/tournament"))
                                        .header("Content-Type", "application/json")
                                        .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                                        .build();
                                try {
                                    response = client.send(postRequest,
                                            HttpResponse.BodyHandlers.ofString());
                                    if (response.statusCode() == 200) {
                                        System.out.println(response.body());
                                        System.out.println();
                                    } else if (response.statusCode() == 201) {
                                        System.out.println("### Post Successful ###" + "\n \n");
                                    } else {
                                        System.out.print("*** Error Code *** : " + response.statusCode() + "\n \n");
                                    }

                                } catch (IOException | InterruptedException e) {
                                    e.printStackTrace();
                                }
                                break;
                            // Update tournament by id
                            case 4:
                                System.out.println("Please enter the ID for the record you wish to alter");
                                id = scanner.next();
                                System.out.println("Please enter the Tournament's info that you wish to update, you can leave any field blank");
                                System.out.println("startDate (yyyy-mm-dd):");
                                startDate = scanner.next();
                                System.out.println("end date (yyyy-mm-dd):");
                                endDate = scanner.next();
                                System.out.println("location:");
                                location = scanner.next();
                                System.out.println("entry fee:");
                                entryFee = scanner.next();
                                System.out.println("cash prize amount:");
                                cashPrizeAmount = scanner.next();
                                values = new HashMap<String, String>() {
                                    {
                                        put("startDate", startDate);
                                        put("endDate", endDate);
                                        put("location", location);
                                        put("entryFee", entryFee);
                                        put("cashPrizeAmount", cashPrizeAmount);
                                    }

                                };

                                objectMapper = new ObjectMapper();
                                requestBody = objectMapper.writeValueAsString(values);
                                postRequest = HttpRequest.newBuilder()
                                        .uri(URI.create("http://localhost:8080/api/tournament/" + id))
                                        .header("Content-Type", "application/json")
                                        .PUT(HttpRequest.BodyPublishers.ofString(requestBody))
                                        .build();
                                try {
                                    response = client.send(postRequest,
                                            HttpResponse.BodyHandlers.ofString());
                                    if (response.statusCode() == 200) {
                                        System.out.println("### PUT Successful ###" + "\n \n");
                                        System.out.println(response.body());
                                        System.out.println();
                                    } else if (response.statusCode() == 201) {
                                        System.out.println("### Post Successful ###" + "\n \n");
                                    } else {
                                        System.out.print("*** Error Code *** : " + response.statusCode() + "\n \n");
                                    }

                                } catch (IOException | InterruptedException e) {
                                    e.printStackTrace();
                                }
                                break;
                            // Delete tournament by id
                            case 5:
                                System.out.println("Please enter and ID for the record you wish to delete");
                                id = scanner.next();
                                request = HttpRequest.newBuilder().DELETE().uri(URI.create("http://localhost:8080/api/tournament/" + id)).build();
                                try {
                                    HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
                                    if (response.statusCode() == 204) {
                                        System.out.println("### Successfully deleted Tournament object with ID: " + id + " ###");
                                        System.out.println(response.body());
                                        System.out.println();
                                    } else {
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
                    break;
                    // CurrentTournaments Table
                case 4:
                    while (true) {
                        System.out.println("Please choose an option or enter 0 to exit");
                        System.out.println("Choices: 1. GET all CurrentTournaments. 2. GET CurrentTournament By ID 3. POST new CurrentTournament " +
                                "4. UPDATE CurrentTournament by id 5. DELETE CurrentTournament by Id");
                        input2 = scanner.nextInt();
                        if (input2 == 0) {
                            break;
                        }
                        switch (input2) {
                            // Get all current tournaments
                            case 1:
                                request = HttpRequest.newBuilder().uri(URI.create("http://localhost:8080/api/currentTournaments")).build();
                                try {
                                    HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
                                    if (response.statusCode() == 200) {
                                        System.out.println(response.body());
                                        System.out.println();
                                    } else {
                                        System.out.print("*** Error Code *** : " + response.statusCode() + "\n \n");
                                    }

                                } catch (IOException | InterruptedException e) {
                                    e.printStackTrace();
                                }
                                break;
                            // Get current tournament by id
                            case 2:
                                System.out.println("Please enter the id of the CurrentTournament");
                                input3 = scanner.next();
                                request = HttpRequest.newBuilder().uri(URI.create("http://localhost:8080/api/currentTournaments/" + input3)).build();
                                try {
                                    HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
                                    if (response.statusCode() == 200) {
                                        System.out.println(response.body());
                                        System.out.println();
                                    } else {
                                        System.out.print("*** Error Code *** : " + response.statusCode() + "\n \n");
                                    }

                                } catch (IOException | InterruptedException e) {
                                    e.printStackTrace();
                                }
                                break;
                            // Post current tournament
                            case 3:
                                System.out.println("Please enter the CurrentTournament info you wish to POST, you can leave any field blank");
                                System.out.println("current tournament date (yyyy-mm-dd):");
                                currentTournamentDate = scanner.next();
                                System.out.println("startDate (yyyy-mm-dd):");
                                startDate = scanner.next();
                                System.out.println("end date (yyyy-mm-dd):");
                                endDate = scanner.next();
                                System.out.println("location:");
                                location = scanner.next();
                                System.out.println("entry fee:");
                                entryFee = scanner.next();
                                System.out.println("cash prize amount:");
                                cashPrizeAmount = scanner.next();
                                values = new HashMap<String, String>() {
                                    {
                                        put("currentTournamentDate", currentTournamentDate);
                                    }
                                    {
                                        put("startDate", startDate);
                                        put("endDate", endDate);
                                        put("location", location);
                                        put("entryFee", entryFee);
                                        put("cashPrizeAmount", cashPrizeAmount);
                                    }
                                };

                                objectMapper = new ObjectMapper();
                                requestBody = objectMapper.writeValueAsString(values);
                                postRequest = HttpRequest.newBuilder()
                                        .uri(URI.create("http://localhost:8080/api/currentTournaments"))
                                        .header("Content-Type", "application/json")
                                        .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                                        .build();
                                try {
                                    response = client.send(postRequest,
                                            HttpResponse.BodyHandlers.ofString());
                                    if (response.statusCode() == 200) {
                                        System.out.println(response.body());
                                        System.out.println();
                                    } else if (response.statusCode() == 201) {
                                        System.out.println("### Post Successful ###" + "\n \n");
                                    } else {
                                        System.out.print("*** Error Code *** : " + response.statusCode() + "\n \n");
                                    }

                                } catch (IOException | InterruptedException e) {
                                    e.printStackTrace();
                                }
                                break;
                            // Update current tournament by id
                            case 4:
                                System.out.println("Please enter the ID for the record you wish to alter");
                                id = scanner.next();
                                System.out.println("Please enter the CurrentTournament's info that you wish to update, you can leave any field blank");
                                System.out.println("current tournament date (yyyy-mm-dd):");
                                currentTournamentDate = scanner.next();
                                System.out.println("startDate (yyyy-mm-dd):");
                                startDate = scanner.next();
                                System.out.println("end date (yyyy-mm-dd):");
                                endDate = scanner.next();
                                System.out.println("location:");
                                location = scanner.next();
                                System.out.println("entry fee:");
                                entryFee = scanner.next();
                                System.out.println("cash prize amount:");
                                cashPrizeAmount = scanner.next();
                                values = new HashMap<String, String>() {
                                    {
                                        put("currentTournamentDate", currentTournamentDate);
                                    }
                                    {
                                        put("startDate", startDate);
                                        put("endDate", endDate);
                                        put("location", location);
                                        put("entryFee", entryFee);
                                        put("cashPrizeAmount", cashPrizeAmount);
                                    }

                                };

                                objectMapper = new ObjectMapper();
                                requestBody = objectMapper.writeValueAsString(values);
                                postRequest = HttpRequest.newBuilder()
                                        .uri(URI.create("http://localhost:8080/api/currentTournaments/" + id))
                                        .header("Content-Type", "application/json")
                                        .PUT(HttpRequest.BodyPublishers.ofString(requestBody))
                                        .build();
                                try {
                                    response = client.send(postRequest,
                                            HttpResponse.BodyHandlers.ofString());
                                    if (response.statusCode() == 200) {
                                        System.out.println("### UPDATE Successful ###" + "\n \n");
                                        System.out.println(response.body());
                                        System.out.println();
                                    } else if (response.statusCode() == 201) {
                                        System.out.println("### Post Successful ###" + "\n \n");
                                    } else {
                                        System.out.print("*** Error Code *** : " + response.statusCode() + "\n \n");
                                    }

                                } catch (IOException | InterruptedException e) {
                                    e.printStackTrace();
                                }
                                break;
                            // Delete current tournament by id
                            case 5:
                                System.out.println("Please enter and ID for the record you wish to delete");
                                id = scanner.next();
                                request = HttpRequest.newBuilder().DELETE().uri(URI.create("http://localhost:8080/api/currentTournaments/" + id)).build();
                                try {
                                    HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
                                    if (response.statusCode() == 204) {
                                        System.out.println("### Successfully deleted CurrentTournament object with ID: " + id + " ###");
                                        System.out.println(response.body());
                                        System.out.println();
                                    } else {
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
                    break;
                    // PastTournaments Table
                case 5:
                    while (true) {
                        System.out.println("Please choose an option or enter 0 to exit");
                        System.out.println("Choices: 1. GET all PastTournaments. 2. GET PastTournaments By ID 3. POST new PastTournaments " +
                                "4. UPDATE PastTournaments by id 5. DELETE PastTournaments by Id");
                        input2 = scanner.nextInt();
                        if (input2 == 0) {
                            break;
                        }
                        switch (input2) {
                            // Get all past tournaments
                            case 1:
                                request = HttpRequest.newBuilder().uri(URI.create("http://localhost:8080/api/pastTournaments")).build();
                                try {
                                    HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
                                    if (response.statusCode() == 200) {
                                        System.out.println(response.body());
                                        System.out.println();
                                    } else {
                                        System.out.print("*** Error Code *** : " + response.statusCode() + "\n \n");
                                    }

                                } catch (IOException | InterruptedException e) {
                                    e.printStackTrace();
                                }
                                break;
                                // Get past tournament by id
                            case 2:
                                System.out.println("Please enter the id of the PastTournament");
                                input3 = scanner.next();
                                request = HttpRequest.newBuilder().uri(URI.create("http://localhost:8080/api/pastTournaments/" + input3)).build();
                                try {
                                    HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
                                    if (response.statusCode() == 200) {
                                        System.out.println(response.body());
                                        System.out.println();
                                    } else {
                                        System.out.print("*** Error Code *** : " + response.statusCode() + "\n \n");
                                    }

                                } catch (IOException | InterruptedException e) {
                                    e.printStackTrace();
                                }
                                break;
                                // Post past tournament
                            case 3:
                                System.out.println("Please enter the PastTournament info you wish to POST, you can leave any field blank");
                                System.out.println("past tournament date (yyyy-mm-dd):");
                                pastTournamentDate = scanner.next();
                                System.out.println("startDate (yyyy-mm-dd):");
                                startDate = scanner.next();
                                System.out.println("end date (yyyy-mm-dd):");
                                endDate = scanner.next();
                                System.out.println("location:");
                                location = scanner.next();
                                System.out.println("entry fee:");
                                entryFee = scanner.next();
                                System.out.println("cash prize amount:");
                                cashPrizeAmount = scanner.next();
                                values = new HashMap<String, String>() {
                                    {
                                        put("pastTournamentDate", pastTournamentDate);
                                    }
                                    {
                                        put("startDate", startDate);
                                        put("endDate", endDate);
                                        put("location", location);
                                        put("entryFee", entryFee);
                                        put("cashPrizeAmount", cashPrizeAmount);
                                    }
                                };

                                objectMapper = new ObjectMapper();
                                requestBody = objectMapper.writeValueAsString(values);
                                postRequest = HttpRequest.newBuilder()
                                        .uri(URI.create("http://localhost:8080/api/pastTournaments"))
                                        .header("Content-Type", "application/json")
                                        .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                                        .build();
                                try {
                                    response = client.send(postRequest,
                                            HttpResponse.BodyHandlers.ofString());
                                    if (response.statusCode() == 200) {
                                        System.out.println(response.body());
                                        System.out.println();
                                    } else if (response.statusCode() == 201) {
                                        System.out.println("### Post Successful ###" + "\n \n");
                                    } else {
                                        System.out.print("*** Error Code *** : " + response.statusCode() + "\n \n");
                                    }

                                } catch (IOException | InterruptedException e) {
                                    e.printStackTrace();
                                }
                                break;
                                // Update past tournament by id
                            case 4:
                                System.out.println("Please enter the ID for the record you wish to alter");
                                id = scanner.next();
                                System.out.println("Please enter the PastTournament's info that you wish to update, you can leave any field blank");
                                System.out.println("past tournament date (yyyy-mm-dd):");
                                pastTournamentDate = scanner.next();
                                System.out.println("startDate (yyyy-mm-dd):");
                                startDate = scanner.next();
                                System.out.println("end date (yyyy-mm-dd):");
                                endDate = scanner.next();
                                System.out.println("location:");
                                location = scanner.next();
                                System.out.println("entry fee:");
                                entryFee = scanner.next();
                                System.out.println("cash prize amount:");
                                cashPrizeAmount = scanner.next();
                                values = new HashMap<String, String>() {
                                    {
                                        put("pastTournamentDate", pastTournamentDate);
                                    }
                                    {
                                        put("startDate", startDate);
                                        put("endDate", endDate);
                                        put("location", location);
                                        put("entryFee", entryFee);
                                        put("cashPrizeAmount", cashPrizeAmount);
                                    }

                                };

                                objectMapper = new ObjectMapper();
                                requestBody = objectMapper.writeValueAsString(values);
                                postRequest = HttpRequest.newBuilder()
                                        .uri(URI.create("http://localhost:8080/api/pastTournaments/" + id))
                                        .header("Content-Type", "application/json")
                                        .PUT(HttpRequest.BodyPublishers.ofString(requestBody))
                                        .build();
                                try {
                                    response = client.send(postRequest,
                                            HttpResponse.BodyHandlers.ofString());
                                    if (response.statusCode() == 200) {
                                        System.out.println("### UPDATE Successful ###" + "\n \n");
                                        System.out.println(response.body());
                                        System.out.println();
                                    } else if (response.statusCode() == 201) {
                                        System.out.println("### Post Successful ###" + "\n \n");
                                    } else {
                                        System.out.print("*** Error Code *** : " + response.statusCode() + "\n \n");
                                    }

                                } catch (IOException | InterruptedException e) {
                                    e.printStackTrace();
                                }
                                break;
                            // Delete past tournament by id
                            case 5:
                                System.out.println("Please enter and ID for the record you wish to delete");
                                id = scanner.next();
                                request = HttpRequest.newBuilder().DELETE().uri(URI.create("http://localhost:8080/api/pastTournaments/" + id)).build();
                                try {
                                    HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
                                    if (response.statusCode() == 204) {
                                        System.out.println("### Successfully deleted CurrentTournament object with ID: " + id + " ###");
                                        System.out.println(response.body());
                                        System.out.println();
                                    } else {
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
                    break;
                    // FutureTournaments Table
                case 6:
                    while (true) {
                        System.out.println("Please choose an option or enter 0 to exit");
                        System.out.println("Choices: 1. GET all FutureTournaments. 2. GET FutureTournaments By ID 3. POST new FutureTournaments " +
                                "4. UPDATE FutureTournaments by id 5. DELETE FutureTournaments by Id");
                        input2 = scanner.nextInt();
                        if (input2 == 0) {
                            break;
                        }
                        switch (input2) {
                            // Get all future tournaments
                            case 1:
                                request = HttpRequest.newBuilder().uri(URI.create("http://localhost:8080/api/futureTournaments")).build();
                                try {
                                    HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
                                    if (response.statusCode() == 200) {
                                        System.out.println(response.body());
                                        System.out.println();
                                    } else {
                                        System.out.print("*** Error Code *** : " + response.statusCode() + "\n \n");
                                    }

                                } catch (IOException | InterruptedException e) {
                                    e.printStackTrace();
                                }
                                break;
                                // Get future tournament by id
                            case 2:
                                System.out.println("Please enter the id of the FutureTournament");
                                input3 = scanner.next();
                                request = HttpRequest.newBuilder().uri(URI.create("http://localhost:8080/api/futureTournaments/" + input3)).build();
                                try {
                                    HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
                                    if (response.statusCode() == 200) {
                                        System.out.println(response.body());
                                        System.out.println();
                                    } else {
                                        System.out.print("*** Error Code *** : " + response.statusCode() + "\n \n");
                                    }

                                } catch (IOException | InterruptedException e) {
                                    e.printStackTrace();
                                }
                                break;
                                // Post future tournaments
                            case 3:
                                System.out.println("Please enter the FutureTournament info you wish to POST, you can leave any field blank");
                                System.out.println("future tournament date (yyyy-mm-dd):");
                                futureTournamentDate = scanner.next();
                                System.out.println("startDate (yyyy-mm-dd):");
                                startDate = scanner.next();
                                System.out.println("end date (yyyy-mm-dd):");
                                endDate = scanner.next();
                                System.out.println("location:");
                                location = scanner.next();
                                System.out.println("entry fee:");
                                entryFee = scanner.next();
                                System.out.println("cash prize amount:");
                                cashPrizeAmount = scanner.next();
                                values = new HashMap<String, String>() {
                                    {
                                        put("futureTournamentDate", futureTournamentDate);
                                    }
                                    {
                                        put("startDate", startDate);
                                        put("endDate", endDate);
                                        put("location", location);
                                        put("entryFee", entryFee);
                                        put("cashPrizeAmount", cashPrizeAmount);
                                    }
                                };

                                objectMapper = new ObjectMapper();
                                requestBody = objectMapper.writeValueAsString(values);
                                postRequest = HttpRequest.newBuilder()
                                        .uri(URI.create("http://localhost:8080/api/futureTournaments"))
                                        .header("Content-Type", "application/json")
                                        .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                                        .build();
                                try {
                                    response = client.send(postRequest,
                                            HttpResponse.BodyHandlers.ofString());
                                    if (response.statusCode() == 200) {
                                        System.out.println(response.body());
                                        System.out.println();
                                    } else if (response.statusCode() == 201) {
                                        System.out.println("### Post Successful ###" + "\n \n");
                                    } else {
                                        System.out.print("*** Error Code *** : " + response.statusCode() + "\n \n");
                                    }

                                } catch (IOException | InterruptedException e) {
                                    e.printStackTrace();
                                }
                                break;
                                // Update future tournament by id
                            case 4:
                                System.out.println("Please enter the ID for the record you wish to alter");
                                id = scanner.next();
                                System.out.println("Please enter the FutureTournament's info that you wish to update, you can leave any field blank");
                                System.out.println("future tournament date (yyyy-mm-dd):");
                                futureTournamentDate = scanner.next();
                                System.out.println("startDate (yyyy-mm-dd):");
                                startDate = scanner.next();
                                System.out.println("end date (yyyy-mm-dd):");
                                endDate = scanner.next();
                                System.out.println("location:");
                                location = scanner.next();
                                System.out.println("entry fee:");
                                entryFee = scanner.next();
                                System.out.println("cash prize amount:");
                                cashPrizeAmount = scanner.next();
                                values = new HashMap<String, String>() {
                                    {
                                        put("pastTournamentDate", futureTournamentDate);
                                    }
                                    {
                                        put("startDate", startDate);
                                        put("endDate", endDate);
                                        put("location", location);
                                        put("entryFee", entryFee);
                                        put("cashPrizeAmount", cashPrizeAmount);
                                    }

                                };

                                objectMapper = new ObjectMapper();
                                requestBody = objectMapper.writeValueAsString(values);
                                postRequest = HttpRequest.newBuilder()
                                        .uri(URI.create("http://localhost:8080/api/futureTournaments/" + id))
                                        .header("Content-Type", "application/json")
                                        .PUT(HttpRequest.BodyPublishers.ofString(requestBody))
                                        .build();
                                try {
                                    response = client.send(postRequest,
                                            HttpResponse.BodyHandlers.ofString());
                                    if (response.statusCode() == 200) {
                                        System.out.println("### UPDATE Successful ###" + "\n \n");
                                        System.out.println(response.body());
                                        System.out.println();
                                    } else if (response.statusCode() == 201) {
                                        System.out.println("### Post Successful ###" + "\n \n");
                                    } else {
                                        System.out.print("*** Error Code *** : " + response.statusCode() + "\n \n");
                                    }

                                } catch (IOException | InterruptedException e) {
                                    e.printStackTrace();
                                }
                                break;
                                // Delete future tournament by id
                            case 5:
                                System.out.println("Please enter and ID for the record you wish to delete");
                                id = scanner.next();
                                request = HttpRequest.newBuilder().DELETE().uri(URI.create("http://localhost:8080/api/futureTournaments/" + id)).build();
                                try {
                                    HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
                                    if (response.statusCode() == 204) {
                                        System.out.println("### Successfully deleted futureTournament object with ID: " + id + " ###");
                                        System.out.println(response.body());
                                        System.out.println();
                                    } else {
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
                    break;
                    // FinalStandings Table
                case 7:
                    while (true) {
                        System.out.println("Please choose an option or enter 0 to exit");
                        System.out.println("Choices: 1. GET all FinalStandings. 2. GET FinalStandings By ID 3. POST new FinalStandings " +
                                "4. UPDATE FinalStandings by id 5. DELETE FinalStandings by Id");
                        input2 = scanner.nextInt();
                        if (input2 == 0) {
                            break;
                        }
                        switch (input2) {
                            // Get all final standings
                            case 1:
                                request = HttpRequest.newBuilder().uri(URI.create("http://localhost:8080/api/finalStandings")).build();
                                try {
                                    HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
                                    if (response.statusCode() == 200) {
                                        System.out.println(response.body());
                                        System.out.println();
                                    } else {
                                        System.out.print("*** Error Code *** : " + response.statusCode() + "\n \n");
                                    }

                                } catch (IOException | InterruptedException e) {
                                    e.printStackTrace();
                                }
                                break;
                            // Get final standing by id
                            case 2:
                                System.out.println("Please enter the id of the finalStandings");
                                input3 = scanner.next();
                                request = HttpRequest.newBuilder().uri(URI.create("http://localhost:8080/api/finalStandings/" + input3)).build();
                                try {
                                    HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
                                    if (response.statusCode() == 200) {
                                        System.out.println(response.body());
                                        System.out.println();
                                    } else {
                                        System.out.print("*** Error Code *** : " + response.statusCode() + "\n \n");
                                    }

                                } catch (IOException | InterruptedException e) {
                                    e.printStackTrace();
                                }
                                break;
                            // Post final standings
                            case 3:
                                System.out.println("Please enter the FinalStandings info you wish to POST, you can leave any field blank");
                                System.out.println("startDate (yyyy-mm-dd):");
                                startDate = scanner.next();
                                System.out.println("end date (yyyy-mm-dd):");
                                endDate = scanner.next();
                                System.out.println("location:");
                                location = scanner.next();
                                System.out.println("entry fee:");
                                entryFee = scanner.next();
                                System.out.println("cash prize amount:");
                                cashPrizeAmount = scanner.next();
                                System.out.println("score");
                                score = scanner.next();
                                values = new HashMap<String, String>() {
                                    {
                                        put("startDate", startDate);
                                        put("endDate", endDate);
                                        put("location", location);
                                        put("entryFee", entryFee);
                                        put("cashPrizeAmount", cashPrizeAmount);
                                    }
                                    {
                                        put("score", score);
                                    }
                                };

                                objectMapper = new ObjectMapper();
                                requestBody = objectMapper.writeValueAsString(values);
                                postRequest = HttpRequest.newBuilder()
                                        .uri(URI.create("http://localhost:8080/api/futureTournaments"))
                                        .header("Content-Type", "application/json")
                                        .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                                        .build();
                                try {
                                    response = client.send(postRequest,
                                            HttpResponse.BodyHandlers.ofString());
                                    if (response.statusCode() == 200) {
                                        System.out.println(response.body());
                                        System.out.println();
                                    } else if (response.statusCode() == 201) {
                                        System.out.println("### Post Successful ###" + "\n \n");
                                    } else {
                                        System.out.print("*** Error Code *** : " + response.statusCode() + "\n \n");
                                    }

                                } catch (IOException | InterruptedException e) {
                                    e.printStackTrace();
                                }
                                break;
                            // Update final standings by id
                            case 4:
                                System.out.println("Please enter the ID for the record you wish to alter");
                                id = scanner.next();
                                System.out.println("Please enter the FinalStanding's info that you wish to update, you can leave any field blank");
                                System.out.println("startDate (yyyy-mm-dd):");
                                startDate = scanner.next();
                                System.out.println("end date (yyyy-mm-dd):");
                                endDate = scanner.next();
                                System.out.println("location:");
                                location = scanner.next();
                                System.out.println("entry fee:");
                                entryFee = scanner.next();
                                System.out.println("cash prize amount:");
                                cashPrizeAmount = scanner.next();
                                System.out.println("score");
                                score = scanner.next();
                                values = new HashMap<String, String>() {
                                    {
                                        put("startDate", startDate);
                                        put("endDate", endDate);
                                        put("location", location);
                                        put("entryFee", entryFee);
                                        put("cashPrizeAmount", cashPrizeAmount);
                                    }
                                    {
                                        put("score", score);
                                    }

                                };

                                objectMapper = new ObjectMapper();
                                requestBody = objectMapper.writeValueAsString(values);
                                postRequest = HttpRequest.newBuilder()
                                        .uri(URI.create("http://localhost:8080/api/finalStandings/" + id))
                                        .header("Content-Type", "application/json")
                                        .PUT(HttpRequest.BodyPublishers.ofString(requestBody))
                                        .build();
                                try {
                                    response = client.send(postRequest,
                                            HttpResponse.BodyHandlers.ofString());
                                    if (response.statusCode() == 200) {
                                        System.out.println("### UPDATE Successful ###" + "\n \n");
                                        System.out.println(response.body());
                                        System.out.println();
                                    } else if (response.statusCode() == 201) {
                                        System.out.println("### Post Successful ###" + "\n \n");
                                    } else {
                                        System.out.print("*** Error Code *** : " + response.statusCode() + "\n \n");
                                    }

                                } catch (IOException | InterruptedException e) {
                                    e.printStackTrace();
                                }
                                break;
                            // Delete final standings by id
                            case 5:
                                System.out.println("Please enter and ID for the record you wish to delete");
                                id = scanner.next();
                                request = HttpRequest.newBuilder().DELETE().uri(URI.create("http://localhost:8080/api/finalStandings/" + id)).build();
                                try {
                                    HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
                                    if (response.statusCode() == 204) {
                                        System.out.println("### Successfully deleted FinalStandings object with ID: " + id + " ###");
                                        System.out.println(response.body());
                                        System.out.println();
                                    } else {
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
    }
}