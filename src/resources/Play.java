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

import java.util.Date;

public class Play {
	
	private User creator;
	private Boardgame game;
	private Date date;	
	private long timeToCompletion;
	private int numOfPlayers;
	private String winnerId;
	
	public Play(User creator, Boardgame game, Date date){
		this.creator = creator;
		this.game = game;
		this.date = date;
	}
	
	public Play(User creator, Boardgame game, Date date, long timeToCompletion, int numOfPlayers, String winnerId){
		this.creator = creator;
		this.game = game;
		this.date = date;
		this.timeToCompletion = timeToCompletion;
		this.numOfPlayers = numOfPlayers;
		this.winnerId = winnerId;
	}
	
	
	//------------Getters and setters-----------------------------------------------------
	public User getCreator(){
		return creator;
	}
	
	public Boardgame getGame(){
		return game;
	}
	
	public Date getDate(){
		return date;
	}
	
	public long getTimeToCompletion(){
		return timeToCompletion;
	}
	
	public int getNumOfPlayers(){
		return numOfPlayers;
	}
	
	public String getWinnerId(){
		return winnerId;
	}

}
