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
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Set;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Link;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.glassfish.jersey.client.authentication.HttpAuthenticationFeature;

public class TestClient2{

	private final String baseUrl = "http://localhost:8080/BoardGameManager/rest";
	private String user = "";
	private String password = "";
	private boolean loginSuccessful = false;
	private static InputStreamReader isr = new InputStreamReader(System.in);
	private static BufferedReader br=new BufferedReader(isr);
	
	public static void main(String[] args) throws IOException {
		TestClient2 client = new TestClient2();	
		client.preLoginMenu(client);
	}
	
	private void preLoginMenu(TestClient2 client) throws IOException{
		int choice;
		do{
			System.out.println("Choose what you want to do:");
			System.out.println("1 - Login");
			System.out.println("2 - Create user");
			System.out.println("0 - Exit ");
			choice = Integer.parseInt(br.readLine());
			switch(choice) {
			case 1:
				client.login(client);
				break;
			case 2:
				client.addNewUser(client);
				break;
			}
		}while(choice!=0 && choice!=1);
		if(choice == 1 && loginSuccessful == true)
			client.postLoginMenu(client);
		if(choice == 1 && loginSuccessful == false)
			client.preLoginMenu(client);
	}
	
	private void postLoginMenu(TestClient2 client) throws NumberFormatException, IOException{
		int choice;
		do{
			System.out.println("Choose what you want to do:");
			System.out.println("1 - View a user's details");
			System.out.println("2 - List users");
			System.out.println("3 - Create new boardgame");
			System.out.println("4 - View a boardgame's details");
			System.out.println("5 - List boardgames");
			System.out.println("6 - Create a new play");
			System.out.println("7 - View existing plays");
			System.out.println("9 - Logout");
			System.out.println("0 - Exit ");
			choice = Integer.parseInt(br.readLine());
			switch(choice) {
				case 1:
					client.viewUserDetails(client);
					break;
				case 2:
					client.listUsers(client);
					break;
				case 3:
					client.addNewBoardgame(client);
					break;
				case 4:
					client.viewBoardgameDetails(client);
					break;
				case 5:
					client.listBoardgames(client);
					break;
				case 6:
					client.addNewPlay(client);
					break;
				case 7:
					client.viewExistingPlays(client);
					break;
			}
		}while(choice!=0 && choice!=9);
		if(choice == 9)
			client.preLoginMenu(client);
	}
	
	private void login(TestClient2 client) throws IOException{
		System.out.println("Insert username: ");
		user = br.readLine();
		System.out.println("Insert password: ");
		password = br.readLine();
		client.sendGetLoginRequest("/users/login");
	}
	
	private void addNewUser(TestClient2 client) throws IOException{
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
	
	private void addNewBoardgame(TestClient2 client) throws IOException{
		String id, name, designers, image;
		System.out.println("Insert id: ");
		id = br.readLine();
		System.out.println("Insert name: ");
		name = br.readLine();
		System.out.println("Insert designers: ");
		designers = br.readLine();
		System.out.println("Insert image uri: ");
		image = br.readLine();
		String postData = "{\"id\": \"" + id + "\", \"name\": \"" + name +  "\", \"designers\": \"" + designers + "\", \"image\": \"" + image + "\"}";
		client.sendPostRequest("/boardgames", postData);
	}
	
	private void addNewPlay(TestClient2 client) throws IOException{
		String boardgameName, date, timeToCompletion, numOfPlayers, winnerId;
		System.out.println("Insert boardgame name: ");
		boardgameName = br.readLine();
		System.out.println("Insert date (dd/mm/yyyy): ");
		date = br.readLine();
		System.out.println("Insert time to completion (0 if not available): ");
		timeToCompletion = br.readLine();
		System.out.println("Insert number of players (0 if not available): ");
		numOfPlayers = br.readLine();
		System.out.println("Insert winner's id (null if not available): ");
		winnerId = br.readLine();
		String postData = "{\"boardgame\": \"" + boardgameName + "\", \"date\": \"" + date +  "\", \"timeToCompletion\": \"" + timeToCompletion + "\", \"players\": \"" + numOfPlayers + "\", \"winner\": \"" + winnerId + "\"}";
		client.sendPostRequest("/plays", postData);
	}
	
	private void viewUserDetails(TestClient2 client) throws IOException{
		String id;
		System.out.println("Insert user id: ");
		id = br.readLine();
		client.sendGetRequest("/users/"+id);
	}
	
	private void viewBoardgameDetails(TestClient2 client) throws IOException{
		String name;
		System.out.println("Insert boardgame's name: ");
		name = br.readLine();
		client.sendGetRequest("/boardgames/"+name);
	}
	
	private void listUsers(TestClient2 client) throws NumberFormatException, IOException{
		int choice, choice2;
		System.out.println("1 - Order users");
		System.out.println("2 - Filter users");
		choice = Integer.parseInt(br.readLine());
		if(choice == 1){
			System.out.println("1 - Order by id in ascending order");
			System.out.println("2 - Order by id in descending order");
			System.out.println("3 - Order by name in ascending order");
			System.out.println("4 - Order by name in descending order");
			choice2 = Integer.parseInt(br.readLine());
			switch(choice2){
				case 1:
					client.sendGetRequest("/users/idOrder/ascending");
					break;
				case 2:
					client.sendGetRequest("/users/idOrder/descending");
					break;
				case 3:
					client.sendGetRequest("/users/nameOrder/ascending");
					break;
				case 4:
					client.sendGetRequest("/users/nameOrder/descending");
					break;
			}
		}
		if(choice == 2){
			String postData;
			System.out.println("1 - Filter by id");
			System.out.println("2 - Filter by username");
			System.out.println("3 - Filter by role");
			choice2 = Integer.parseInt(br.readLine());
			System.out.println("Insert filter");
			postData = br.readLine();
			switch(choice2){
			case 1:
				client.sendPostRequest("/users/filter/id", postData);
				break;
			case 2:
				client.sendPostRequest("/users/filter/name", postData);
				break;
			case 3:
				client.sendPostRequest("/users/filter/role", postData);
				break;
			}
		}
	}
	
	private void listBoardgames(TestClient2 client) throws NumberFormatException, IOException{
		int choice, choice2;
		System.out.println("1 - Order boardgames");
		System.out.println("2 - Filter boardgames");
		choice = Integer.parseInt(br.readLine());
		if(choice == 1){
			System.out.println("1 - Order by id in ascending order");
			System.out.println("2 - Order by id in descending order");
			System.out.println("3 - Order by name in ascending order");
			System.out.println("4 - Order by name in descending order");
			System.out.println("5 - Order by designers in ascending order");
			System.out.println("6 - Order by desginers in descending order");
			choice2 = Integer.parseInt(br.readLine());
			switch(choice2){
				case 1:
					client.sendGetRequest("/boardgames/idOrder/ascending");
					break;
				case 2:
					client.sendGetRequest("/boardgames/idOrder/descending");
					break;
				case 3:
					client.sendGetRequest("/boardgames/nameOrder/ascending");
					break;
				case 4:
					client.sendGetRequest("/boardgames/nameOrder/descending");
					break;
				case 5:
					client.sendGetRequest("/boardgames/designersOrder/ascending");
					break;
				case 6:
					client.sendGetRequest("/boardgames/designersOrder/descending");
					break;
			}
		}
		if(choice == 2){
			String postData;
			System.out.println("1 - Filter by id");
			System.out.println("2 - Filter by name");
			System.out.println("3 - Filter by designers");
			choice2 = Integer.parseInt(br.readLine());
			System.out.println("Insert filter");
			postData = br.readLine();
			switch(choice2){
			case 1:
				client.sendPostRequest("/boardgames/filter/id", postData);
				break;
			case 2:
				client.sendPostRequest("/boardgames/filter/name", postData);
				break;
			case 3:
				client.sendPostRequest("/boardgames/filter/designers", postData);
				break;
			}
		}
	}
	
	private void viewExistingPlays(TestClient2 client) throws NumberFormatException, IOException{
		String userId;
		int choice, choice2;
		System.out.println("Insert user id");
		userId = br.readLine();
		System.out.println("1 - Order plays");
		System.out.println("2 - Filter plays");
		choice = Integer.parseInt(br.readLine());
		if(choice == 1){
			System.out.println("1 - Order by bordgame name in ascending order");
			System.out.println("2 - Order by boardgame name in descending order");
			System.out.println("3 - Order by date in ascending order");
			System.out.println("4 - Order by date in descending order");
			choice2 = Integer.parseInt(br.readLine());
			switch(choice2){
				case 1:
					client.sendGetRequest("/plays/boardgameOrder/ascending/" + userId);
					break;
				case 2:
					client.sendGetRequest("/plays/boardgameOrder/descending/" + userId);
					break;
				case 3:
					client.sendGetRequest("/plays/dateOrder/ascending/" + userId);
					break;
				case 4:
					client.sendGetRequest("/plays/dateOrder/descending/" + userId);
					break;
			}
		}
		if(choice == 2){
			String postData;
			System.out.println("1 - Filter by boardgame name");
			System.out.println("2 - Filter by date");
			choice2 = Integer.parseInt(br.readLine());
			System.out.println("Insert filter");
			postData = br.readLine();
			switch(choice2){
			case 1:
				client.sendPostRequest("/plays/filter/name/" + userId, postData);
				break;
			case 2:
				client.sendPostRequest("/plays/filter/date/" + userId, postData);
				break;
			}
		}
	}
	
	private void sendGetRequest(String urlString){
		HttpAuthenticationFeature feature = HttpAuthenticationFeature.basic(user, password);
		Client client = ClientBuilder.newClient();
		client.register(feature);
		WebTarget target = client.target(baseUrl).path(urlString);
		Invocation.Builder invocationBuilder = target.request(MediaType.APPLICATION_JSON);
		Response response = invocationBuilder.get();
		System.out.println(response.getStatus());
		System.out.println(response.readEntity(String.class));
		//Hypermedia part
		Set<Link> links = response.getLinks();
		String path;
		for(Link link : links){
			path = link.getUri().toString();
			path = path.replaceAll(baseUrl, "");
			this.sendGetRequest(path);
		}
	}
	
	private void sendPostRequest(String urlString, String postData){
		HttpAuthenticationFeature feature = HttpAuthenticationFeature.basic(user, password);
		Client client = ClientBuilder.newClient();
		client.register(feature);
		WebTarget target = client.target(baseUrl).path(urlString);
		Invocation.Builder invocationBuilder = target.request(MediaType.APPLICATION_JSON);
		Response response = invocationBuilder.post(Entity.entity(postData, MediaType.APPLICATION_JSON));
		System.out.println(response.getStatus());
		System.out.println(response.readEntity(String.class));
		//Hypermedia part
		Set<Link> links = response.getLinks();
		String path;
		for(Link link : links){
			path = link.getUri().toString();
			path = path.replaceAll(baseUrl, "");
			this.sendGetRequest(path);
		}		
	}
	
	private void sendGetLoginRequest(String urlString){
		HttpAuthenticationFeature feature = HttpAuthenticationFeature.basic(user, password);
		Client client = ClientBuilder.newClient();
		client.register(feature);
		WebTarget target = client.target(baseUrl).path(urlString);
		Invocation.Builder invocationBuilder = target.request(MediaType.APPLICATION_JSON);
		Response response = invocationBuilder.get();
		System.out.println(response.getStatus());
		if(response.getStatus() == 200){
			System.out.println(response.readEntity(String.class));
			loginSuccessful = true;
		}
		else{
			System.out.println("Login failed");
			loginSuccessful = false;
		}
	}
}
