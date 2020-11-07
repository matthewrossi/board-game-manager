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
import javax.annotation.security.RolesAllowed;
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

@Path("/boardgames")
public class BoardgameResource {
	
	BoardgameDatabase boardgameDatabase;
	private final String baseUrl = "/BoardGameManager/rest";
	
	public BoardgameResource(){
		boardgameDatabase = BoardgameDatabase.getInstance();
	}
	
	@POST
	@RolesAllowed("Superuser")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response addBoardgame(String gameString) throws JSONException{
		String response = "Id already used";
		JSONObject game = new JSONObject(gameString);
		String id = game.getString("id");
		String name = game.getString("name");
		String designers = game.getString("designers");
		String image = game.getString("image");
		boardgameDatabase.addGame(id, name, designers, image);
		response = "Created boardgame with Id: " + id + " Name: " + name + " Designers: " + designers;
		response = response + " Image uri: " + image;
		return Response.status(200).entity(response).build();
	}
	
	@GET
	@Path("/{name}")
	@PermitAll
	@Produces(MediaType.APPLICATION_JSON)  
	public Response getBoardgame(@PathParam("name") String name){
		String response = "Boardgame not found";
		Boardgame game = boardgameDatabase.getBoardgameByName(name);
		if(game == null)
			return Response.status(404).entity(response).build(); 
		response = "Boardgame id: " + game.getId() + " Name: " + game.getName() + " Designers: " + game.getDesigners() + " Cover art: " + game.getCoverArt().getUri();
		return Response.status(200).entity(response).build();
	}
	
	@POST
	@Path("/filter/{type}")
	@PermitAll
	@Produces(MediaType.APPLICATION_JSON)  
	public Response filter(@PathParam("type") String filterType, String filter){
		ArrayList<Boardgame> boardgameList = boardgameDatabase.filter(filterType, filter);
		String response = "The filtered boardgames are: ";
		/*
		for(Boardgame game : boardgameList){
			response = response + "Boardgame id: " + game.getId() + " Name: " + game.getName() + " Designers: " + game.getDesigners() + " Cover art: " + game.getCoverArt().getUri() + "\n";
		}
		return Response.status(200).entity(response).build();
		*/
		
		String path;
		Link[] links = new Link[boardgameList.size()];
		int cont = 0;
		for(Boardgame game : boardgameList){
			path = baseUrl + "/boardgames/" + game.getName();
			links[cont] = Link.fromUri(path).build();
			cont++;
		}
		return Response.status(200).entity(response).links(links).build();
		
	}
	
	@GET
	@Path("/idOrder/{filterType}")
	@PermitAll
	@Produces(MediaType.APPLICATION_JSON)  
	public Response orderId(@PathParam("filterType") String filterType){
		String response = "The ordered boardgames are: 	\n";
		ArrayList<Boardgame> boardgameList = boardgameDatabase.getIdInOrder(filterType);
		for(Boardgame game : boardgameList){
			response = response + "Boardgame id: " + game.getId() + " Name: " + game.getName() + " Designers: " + game.getDesigners() + " Cover art: " + game.getCoverArt().getUri() + "\n";
		}
		return Response.status(200).entity(response).build();
	}
	
	@GET
	@Path("/nameOrder/{filterType}")
	@PermitAll
	@Produces(MediaType.APPLICATION_JSON)  
	public Response orderName(@PathParam("filterType") String filterType){
		String response = "The ordered boardgames are: \n";
		ArrayList<Boardgame> boardgameList = boardgameDatabase.getNameInOrder(filterType);
		for(Boardgame game : boardgameList){
			response = response + "Boardgame id: " + game.getId() + " Name: " + game.getName() + " Designers: " + game.getDesigners() + " Cover art: " + game.getCoverArt().getUri() + "\n";
		}
		return Response.status(200).entity(response).build();
	}
	
	@GET
	@Path("/designersOrder/{filterType}")
	@PermitAll
	@Produces(MediaType.APPLICATION_JSON)  
	public Response orderDesigners(@PathParam("filterType") String filterType){
		String response = "The ordered boardgames are: \n";
		ArrayList<Boardgame> boardgameList = boardgameDatabase.getDesignersInOrder(filterType);
		for(Boardgame game : boardgameList){
			response = response + "Boardgame id: " + game.getId() + " Name: " + game.getName() + " Designers: " + game.getDesigners() + " Cover art: " + game.getCoverArt().getUri() + "\n";
		}
		return Response.status(200).entity(response).build();
	}

}
