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

package resources;

import java.util.ArrayList;

import javax.annotation.security.PermitAll;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Link;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.json.JSONException;
import org.json.JSONObject;

@Path("/users")
public class UserResource {
	
	UserDatabase userDatabase;
	private final String baseUrl = "/BoardGameManager/rest";
	
	public UserResource(){
		userDatabase = UserDatabase.getInstance();
	}
	
	@GET
	@Path("/{id}")
	@PermitAll
	@Produces(MediaType.APPLICATION_JSON)  
	public Response getUser(@PathParam("id") String id){
		String result = "Player not found";
		if(!userDatabase.idAlreadyInserted(id))
			return Response.status(404).entity(result).build(); 
		String role = (userDatabase.isSuperuser(id) == true) ? "Superuser" : "User";
		result = "User id: " + id + " Username: " + userDatabase.getUserName(id) + " Role: " + role;
		return Response.status(200).entity(result).build(); 
	}

	@POST
	@PermitAll
	@Consumes(MediaType.APPLICATION_JSON)
    public Response addUser(String userString) throws JSONException { 
		String result = "Id already used";
		JSONObject user = new JSONObject(userString);
		String userId = user.getString("id");
		String userName = user.getString("name");
		String userPassword = user.getString("password");
		String superUser = user.getString("superuser");
		String role;
		if(userDatabase.idAlreadyInserted(userId))
			return Response.status(409).entity(result).build(); 
		if(superUser.equals("n"))
			role = "User";
		else
			role = "Superuser";
		userDatabase.addUser(userId, userName, userPassword, role);
        result = "Created user with Id: " + userId + " Name: " + userName + " Role: " + role;
        return Response.status(200).entity(result).build(); 
    }
	
	@GET
	@Path("/login")
	@PermitAll
	@Produces(MediaType.APPLICATION_JSON)  
	public Response loginVerification() throws JSONException{
		String result = "Login successful";
		return Response.status(200).entity(result).build();
	}
	
	@GET
	@Path("/idOrder/{filterType}")
	@PermitAll
	@Produces(MediaType.APPLICATION_JSON)  
	public Response orderId(@PathParam("filterType") String filterType){
		String response = "The ordered users are:\n";
		ArrayList<User> userList = userDatabase.getIdInOrder(filterType);
		for(User user : userList){
			response = response + "User id: " + user.getId() + " Username: " + user.getName() + " Role: " + user.getRole() + "\n";
		}
		return Response.status(200).entity(response).build();
	}
	
	@GET
	@Path("/nameOrder/{filterType}")
	@PermitAll
	@Produces(MediaType.APPLICATION_JSON)  
	public Response orderName(@PathParam("filterType") String filterType){
		String response = "The ordered users are:\n";
		ArrayList<User> userList = userDatabase.getNameInOrder(filterType);
		for(User user : userList){
			response = response + "User id: " + user.getId() + " Username: " + user.getName() + " Role: " + user.getRole() + "\n";
		}
		return Response.status(200).entity(response).build();
	}
	
	@POST
	@Path("/filter/{type}")
	@PermitAll
	@Produces(MediaType.APPLICATION_JSON)  
	public Response filter(@PathParam("type") String filterType, String filter){
		ArrayList<User> userList = userDatabase.filter(filterType, filter);
		String response = "The filtered users are:";
		/*
		for(User user : userList){
			response = response + "Id: " + user.getId() + " Username: " + user.getName() + " Role: " + user.getRole() + "\n";
		}
		return Response.status(200).entity(response).build();
		*/
		String path;
		Link[] links = new Link[userList.size()];
		int cont = 0;
		for(User user : userList){
			path = baseUrl + "/users/" + user.getId();
			links[cont] = Link.fromUri(path).build();
			cont++;
		}
		return Response.status(200).entity(response).links(links).build();
	}
	
}
