// Copyright (c) 2020 Matthew Rossi
//
// Permission is hereby granted, free of charge, to any person obtaining a copy of
// this software and associated documentation files (the "Software"), to deal in
// the Software without restriction, including without limitation the rights to
// use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of
// the Software, and to permit persons to whom the Software is furnished to do so,
// subject to the following conditions:
//
// The above copyright notice and this permission notice shall be included in all
// copies or substantial portions of the Software.
//
// THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
// IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS
// FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
// COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER
// IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN
// CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.

package client;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class TestClient {
	
	private final String baseUrl = "http://localhost:8080/BoardGameManager/rest";
	private static InputStreamReader isr = new InputStreamReader(System.in);
	private static BufferedReader br=new BufferedReader(isr);

	public static void main(String[] args) throws IOException{ 
		
		TestClient client = new TestClient();		
		int choice;
		do{
			System.out.println("Choose what you want to do:");
			System.out.println("1 - Create user ");
			System.out.println("3 - View a user's details ");
			System.out.println("0 - Exit ");
			choice = Integer.parseInt(br.readLine());
			switch(choice) {
				case 1:
					client.addNewUser(client);
					break;
				case 3:
					client.viewUserDetails(client);
					break;
			}
		}while(choice!=0);
	}
	
	private void addNewUser(TestClient client) throws IOException{
		String id, userName, password, superUser;
		System.out.println("Insert id: ");
		id = br.readLine();
		System.out.println("Insert username: ");
		userName = br.readLine();
		System.out.println("Insert password: ");
		password = br.readLine();
		System.out.println("Are you a SuperUser? [y/n]: ");
		superUser = br.readLine();
		
		String postData = "{\"id\": \""+id+"\", \"name\": \""+userName+"\", \"password\": \""+password+"\", \"superuser\": \""+superUser+"\"}";
		client.sendPostRequest("/users",postData);
	}
	
	private void viewUserDetails(TestClient client) throws IOException{
		String id;
		System.out.println("Insert user id: ");
		id = br.readLine();
		client.sendGetRequest("/users/"+id);
	}
	
	private void sendGetRequest(String urlString) throws IOException{
		StringBuffer response = new StringBuffer();
		try{
			//Building and sending GET request
			URL url = new URL(baseUrl+ urlString);
			HttpURLConnection connection = (HttpURLConnection)url.openConnection();
			connection.setRequestMethod("GET");
			int responseCode = connection.getResponseCode();  
			System.out.println("Sending get request : "+ url);  
			System.out.println("Response code : "+ responseCode);
			
			//Reading response from input Stream
			BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));  
			String output;  
			while ((output = in.readLine()) != null){  
				response.append(output);  
			}  
			in.close();  
		} catch (IOException e) {
				System.out.println(e.getMessage());
		}
		//printing result from response  
		System.out.println(response.toString());
	}
	
	private void sendPostRequest(String urlString, String postData) {  
		StringBuffer response = new StringBuffer();
		try {
			//Building POST request
			URL url = new URL(baseUrl + urlString);
			HttpURLConnection connection = (HttpURLConnection)url.openConnection();
			connection.setRequestMethod("POST");
			connection.setRequestProperty("Content-Type","application/json");
			//String postData = "{\"id\": \"1\",\"name\": \"User1\"}";
			
			//Sending POST request
			connection.setDoOutput(true);  
			DataOutputStream outputStream = new DataOutputStream(connection.getOutputStream());
			
			outputStream.writeBytes(postData);
			outputStream.flush();  
			outputStream.close();
			
			//Receiving response
			int responseCode = connection.getResponseCode();  
			System.out.println("\nSending 'POST' request to URL : " + url);  
			System.out.println("Post Data : " + postData);  
			System.out.println("Response Code : " + responseCode);
			
			BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			String output;
			
			while ((output = in.readLine()) != null) {
				response.append(output);
			}
			in.close();	
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
		
		//printing result from response
		System.out.println(response.toString());
		
	}

}
