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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PlayDatabase {

	private static PlayDatabase instance = null;
	private List<Play> plays = new ArrayList<Play>();
	
	public static PlayDatabase getInstance() {
        if(instance == null) {
            instance = new PlayDatabase();
        }
        return instance;
    }
	
	public PlayDatabase(){
		
	}
	
	public void addPlay(User creator, Boardgame game, Date date, long timeToCompletion, int numOfPlayers, String winnerId){
		plays.add(new Play(creator, game, date, timeToCompletion, numOfPlayers, winnerId));
	}
	
	public List<Play> getPlayByUser(User user){
		List<Play> result = new ArrayList<Play>();
		for(Play play : plays)
			if(play.getCreator().getId().equals(user.getId()))
				result.add(play);
		return result;
	}
	
	public ArrayList<Play> filter(String filterType, String filter, String userId){
		DateFormat dateFormat = new SimpleDateFormat("dd/mm/yyyy");
		ArrayList<Play> playList = new ArrayList<Play>();
		ArrayList<Play> userPlayList = new ArrayList<Play>(); //list of plays created by the user which id is equal to userId
		for(Play play : plays)
			if(play.getCreator().getId().equals(userId))
				userPlayList.add(play);
		for(Play play : userPlayList) {
			if(filterType.equals("name") && play.getGame().getName().contains(filter))
				playList.add(play);
			if(filterType.equals("date") && dateFormat.format(play.getDate()).contains(filter))
				playList.add(play);
		}
		return playList;
	}
	
	public ArrayList<Play> getNameInOrder(String filterType, String userId){
		Play dummy;
		ArrayList<Play> userPlayList = new ArrayList<Play>(); //list of plays created by the user which id is equal to userId
		for(Play play : plays)
			if(play.getCreator().getId().equals(userId))
				userPlayList.add(play);
		//sort elements in ascending order by boardgame name
		if(filterType.equals("ascending"))
			for(int i=0; i<userPlayList.size(); i++)
				for(int j=0; j<userPlayList.size(); j++)
					if(userPlayList.get(i).getGame().getName().compareTo(userPlayList.get(j).getGame().getName()) < 0){
						dummy = userPlayList.get(j);
						userPlayList.set(j, userPlayList.get(i));
						userPlayList.set(i,dummy);
					}
		//sort elements in descending order by boardgame name
		if(filterType.equals("descending"))
			for(int i=0; i<userPlayList.size(); i++)
				for(int j=0; j<userPlayList.size(); j++)
					if(userPlayList.get(i).getGame().getName().compareTo(userPlayList.get(j).getGame().getName()) > 0){
						dummy = userPlayList.get(j);
						userPlayList.set(j, userPlayList.get(i));
						userPlayList.set(i,dummy);
					}
		return userPlayList;
	} 
	
	public ArrayList<Play> getDateInOrder(String filterType, String userId){
		Play dummy;
		ArrayList<Play> userPlayList = new ArrayList<Play>(); //list of plays created by the user which id is equal to userId
		for(Play play : plays)
			if(play.getCreator().getId().equals(userId))
				userPlayList.add(play);
		//sort elements in ascending order by date
		if(filterType.equals("ascending"))
			for(int i=0; i<userPlayList.size(); i++)
				for(int j=0; j<userPlayList.size(); j++)
					if(userPlayList.get(i).getDate().before((userPlayList.get(j).getDate()))){
						dummy = userPlayList.get(j);
						userPlayList.set(j, userPlayList.get(i));
						userPlayList.set(i,dummy);
					}
		//sort elements in descending order by date
		if(filterType.equals("descending"))
			for(int i=0; i<userPlayList.size(); i++)
				for(int j=0; j<userPlayList.size(); j++)
					if(userPlayList.get(i).getDate().after((userPlayList.get(j).getDate()))){
						dummy = userPlayList.get(j);
						userPlayList.set(j, userPlayList.get(i));
						userPlayList.set(i,dummy);
					}
		return userPlayList;
	} 
	
}
