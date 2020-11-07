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

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import org.json.JSONException;
import org.json.JSONObject;

@Path("/plays")
public class PlayResource {
	
	PlayDatabase playDatabase;
	BoardgameDatabase boardgameDatabase;
	
	public PlayResource(){
		playDatabase = PlayDatabase.getInstance();
		boardgameDatabase = BoardgameDatabase.getInstance();
	}
	
	@POST
	@RolesAllowed("Superuser")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response addPlay(@Context SecurityContext sc, String playString) throws JSONException, ParseException{
		String response;
		JSONObject play = new JSONObject(playString);
		
		//get play creator
		User creator = (User) sc.getUserPrincipal();
		
		//get boardgame name
		String boardgameName = play.getString("boardgame");
		Boardgame game = boardgameDatabase.getBoardgameByName(boardgameName);
		
		//get date
		String dateString = play.getString("date");
		DateFormat dateFormat = new SimpleDateFormat("dd/mm/yyyy");
		Date date = dateFormat.parse(dateString);
		
		//get time to completion
		int timeToCompletion = Integer.parseInt(play.getString("timeToCompletion"));
		
		//get number of players
		int numOfPlayers = Integer.parseInt(play.getString("players"));
		
		//get winner's id
		String winnerId = play.getString("winner");
		
		playDatabase.addPlay(creator, game, date, timeToCompletion, numOfPlayers, winnerId);
		response = "Added play with creator: " + creator.getName() + " Boardgame: " + game.getName() + " Date: " + date.toString() + " Time to completion: " + timeToCompletion + " Number of players: " + numOfPlayers + " Winner's id: " + winnerId;
		return Response.status(200).entity(response).build();
	}
	
	@POST
	@Path("/filter/{type}/{userId}")
	@PermitAll
	@Produces(MediaType.APPLICATION_JSON)  
	public Response filter(@PathParam("type") String filterType, @PathParam("userId") String userId, String filter){
		ArrayList<Play> playList = playDatabase.filter(filterType, filter, userId);
		String response = "The filtered plays are:\n";
		for(Play play : playList){
			response = response + "Creator: " + play.getCreator().getName() + " Boardgame: " + play.getGame().getName() + " Date: " + play.getDate().toString() + " Time to completion: " + play.getTimeToCompletion() + " Number of players: " + play.getNumOfPlayers() + " Winner's id: " + play.getWinnerId() + "\n";
		}
		return Response.status(200).entity(response).build();
	}
	
	@GET
	@Path("/boardgameOrder/{orderType}/{userId}")
	@PermitAll
	@Produces(MediaType.APPLICATION_JSON)  
	public Response orderBoardgame(@PathParam("orderType") String filterType, @PathParam("userId") String userId){
		String response = "";
		ArrayList<Play> playList = playDatabase.getNameInOrder(filterType, userId);
		for(Play play : playList){
			response = response + "Creator: " + play.getCreator().getName() + " Boardgame: " + play.getGame().getName() + " Date: " + play.getDate().toString() + " Time to completion: " + play.getTimeToCompletion() + " Number of players: " + play.getNumOfPlayers() + " Winner's id: " + play.getWinnerId() + "\n";
		}
		return Response.status(200).entity(response).build();
	}
	
	@GET
	@Path("/dateOrder/{orderType}/{userId}")
	@PermitAll
	@Produces(MediaType.APPLICATION_JSON)  
	public Response orderDate(@PathParam("orderType") String filterType, @PathParam("userId") String userId){
		String response = "";
		ArrayList<Play> playList = playDatabase.getDateInOrder(filterType, userId);
		for(Play play : playList){
			response = response + "Creator: " + play.getCreator().getName() + " Boardgame: " + play.getGame().getName() + " Date: " + play.getDate().toString() + " Time to completion: " + play.getTimeToCompletion() + " Number of players: " + play.getNumOfPlayers() + " Winner's id: " + play.getWinnerId() + "\n";
		}
		return Response.status(200).entity(response).build();
	}
	
}
