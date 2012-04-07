package org.bluemagic.config.service.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.NoSuchElementException;
import java.util.Scanner;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;

public class TestClient {

    private static final String READ_PROPERTY = "Enter a property name with tags (ex: some/prop?tags=production)";
    private static final String READ_VALUE = "Enter the value of the property";
    private static final String SERVER_ADDRESS = "http://localhost:8080/property/";
    private static final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    
    public static void main(String[] args) throws ClientProtocolException, IOException {
        boolean running = true;
        
        while (running) {
            displayOptions();
            int selection = getSelection();
            
            try {
                switch (selection) {
                    case 1:
                        getAllProperties();
                        break;
                        
                    case 2:
                        getProperty();
                        break;
                        
                    case 3:
                        createProperty();
                        break;
                        
                    case 4:
                        updateProperty();
                        break;
                        
                    case 5:
                        deleteProperty();
                        break;
                        
                    case 6:
                        running = false;
                        break;            
                }
            } catch (Exception e) {}
        }
    }
    
    private static void displayOptions() {
        System.out.println("\n--------------------------");
        System.out.println("1 - Display All Properties");
        System.out.println("2 - Get Property");
        System.out.println("3 - Create Property");
        System.out.println("4 - Update Property");
        System.out.println("5 - Delete Property");
        System.out.println("6 - Quit");
        System.out.println("--------------------------");
        System.out.println("What would you like to do?");
    }
    
    private static int getSelection() {
        try {
            String line = br.readLine();
            int result = Integer.parseInt(line);
            
            if (result > 0 && result < 7) {
                return result;
            }
        } catch (Exception e) {}
        
        System.out.println("Invalid selection, what would you like to do?");
        
        return getSelection();
    }
    
    private static String readString(String prompt) {
        try {
            System.out.println(prompt);
            
            return br.readLine();
            
        } catch (Exception e) {}
        
        return readString(prompt);
    }
    
    private static void getAllProperties() {
        System.out.println("Not implemented");
    }
    
    private static void getProperty() throws ClientProtocolException, IOException {
        String property = readString(READ_PROPERTY);
        System.out.println("Getting property: " + property);
        
        HttpClient client = new DefaultHttpClient();
        HttpGet get = new HttpGet(SERVER_ADDRESS + property);
        HttpResponse response = client.execute(get);

        int statusCode = response.getStatusLine().getStatusCode();
        if (statusCode >= 200 && statusCode < 300) {
            System.out.println("Property value: " + streamToString(response.getEntity().getContent()));
        } else {
            System.out.println("Failure - " + statusCode + ": " + response.getStatusLine().getReasonPhrase());
        }
    }
    
    private static void createProperty() throws ClientProtocolException, IOException {
        String property = readString(READ_PROPERTY);
        String value = readString(READ_VALUE);
        System.out.println("Creating property: " + property + " with value: " + value);
        
        HttpClient client = new DefaultHttpClient();
        HttpPost post = new HttpPost(SERVER_ADDRESS + property);
        post.setEntity(new StringEntity(value));
        HttpResponse response = client.execute(post);
        
        int statusCode = response.getStatusLine().getStatusCode();
        if (statusCode >= 200 && statusCode < 300) {
            System.out.println("Property created");
        } else {
            System.out.println("Failure - " + statusCode + ": " + response.getStatusLine().getReasonPhrase());
        }
    }
    
    private static void updateProperty() throws ClientProtocolException, IOException {
        String property = readString(READ_PROPERTY);
        String value = readString(READ_VALUE);
        System.out.println("Updating property: " + property + " with value: " + value);
        
        HttpClient client = new DefaultHttpClient();
        HttpPut put = new HttpPut(SERVER_ADDRESS + property);
        put.setEntity(new StringEntity(value));
        HttpResponse response = client.execute(put);
        
        int statusCode = response.getStatusLine().getStatusCode();
        if (statusCode >= 200 && statusCode < 300) {
            System.out.println("Property updated");
        } else {
            System.out.println("Failure - " + statusCode + ": " + response.getStatusLine().getReasonPhrase());
        }
    }
    
    private static void deleteProperty() throws ClientProtocolException, IOException {
        String property = readString(READ_PROPERTY);
        System.out.println("Deleting property: " + property);
        
        HttpClient client = new DefaultHttpClient();
        HttpDelete delete = new HttpDelete(SERVER_ADDRESS + property);
        HttpResponse response = client.execute(delete);

        int statusCode = response.getStatusLine().getStatusCode();
        if (statusCode >= 200 && statusCode < 300) {
            System.out.println("Property deleted");
        } else {
            System.out.println("Failure - " + statusCode + ": " + response.getStatusLine().getReasonPhrase());
        }
    }
    
    private static String streamToString(InputStream is) {
        try {
            return new Scanner(is).useDelimiter("\\A").next();
        } catch (NoSuchElementException e) {
            return "";
        }
    }
}
