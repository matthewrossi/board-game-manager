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
import java.util.HashMap;
import java.util.Map;

public class BoardgameDatabase {
	
	private static BoardgameDatabase instance = null;
	private Map<String, Boardgame> boardgames = new HashMap<String, Boardgame>();
	
	public static BoardgameDatabase getInstance() {
        if(instance == null) {
            instance = new BoardgameDatabase();
        }
        return instance;
    }
	
	private BoardgameDatabase(){

	}
	
	public void addGame(String id, String name, String designers, String imageUri){
		boardgames.put(id, new Boardgame(id, name, designers, new Image(imageUri)));
	}
	
	public Boardgame getBoardgameByName(String name){
		for (Map.Entry<String, Boardgame> game : boardgames.entrySet()){
			if(game.getValue().getName().equals(name))
				return game.getValue();
		}
		return null;
	}
	
	public ArrayList<Boardgame> filter(String filterType, String filter){
		ArrayList<Boardgame> boardgameList = new ArrayList<Boardgame>();
		for (Map.Entry<String, Boardgame> game : boardgames.entrySet()){
			if(filterType.equals("id") && game.getValue().getId().contains(filter))
				boardgameList.add(game.getValue());
			if(filterType.equals("name") && game.getValue().getName().contains(filter))
				boardgameList.add(game.getValue());
			if(filterType.equals("designers") && game.getValue().getDesigners().contains(filter))
				boardgameList.add(game.getValue());
		}
		return boardgameList;
	}
	
	public ArrayList<Boardgame> getIdInOrder(String filterType){
		ArrayList<Boardgame> boardgameList = new ArrayList<Boardgame>();
		Boardgame dummy;
		for (Map.Entry<String, Boardgame> game : boardgames.entrySet()){
			boardgameList.add(game.getValue());
		}
		//sort elements in ascending order by id
		if(filterType.equals("ascending"))
			for(int i=0; i<boardgameList.size();i++)
				for(int j=0; j<boardgameList.size();j++)
					if(boardgameList.get(i).getId().compareTo(boardgameList.get(j).getId()) < 0){
						dummy = boardgameList.get(j);
						boardgameList.set(j, boardgameList.get(i));
						boardgameList.set(i,dummy);
					}
		//sort elements in ascending order by id
		if(filterType.equals("descending"))
			for(int i=0; i<boardgameList.size();i++)
				for(int j=0; j<boardgameList.size();j++)
					if(boardgameList.get(i).getId().compareTo(boardgameList.get(j).getId()) > 0){
						dummy = boardgameList.get(j);
						boardgameList.set(j, boardgameList.get(i));
						boardgameList.set(i,dummy);
					}
		return boardgameList;
	} 
	
	public ArrayList<Boardgame> getNameInOrder(String filterType){
		ArrayList<Boardgame> boardgameList = new ArrayList<Boardgame>();
		Boardgame dummy;
		for (Map.Entry<String, Boardgame> game : boardgames.entrySet()){
			boardgameList.add(game.getValue());
		}
		//sort elements in ascending order by id
		if(filterType.equals("ascending"))
			for(int i=0; i<boardgameList.size();i++)
				for(int j=0; j<boardgameList.size();j++)
					if(boardgameList.get(i).getName().compareTo(boardgameList.get(j).getName()) < 0){
						dummy = boardgameList.get(j);
						boardgameList.set(j, boardgameList.get(i));
						boardgameList.set(i,dummy);
					}
		//sort elements in ascending order by id
		if(filterType.equals("descending"))
			for(int i=0; i<boardgameList.size();i++)
				for(int j=0; j<boardgameList.size();j++)
					if(boardgameList.get(i).getName().compareTo(boardgameList.get(j).getName()) > 0){
						dummy = boardgameList.get(j);
						boardgameList.set(j, boardgameList.get(i));
						boardgameList.set(i,dummy);
					}
		return boardgameList;
	} 
	
	public ArrayList<Boardgame> getDesignersInOrder(String filterType){
		ArrayList<Boardgame> boardgameList = new ArrayList<Boardgame>();
		Boardgame dummy;
		for (Map.Entry<String, Boardgame> game : boardgames.entrySet()){
			boardgameList.add(game.getValue());
		}
		//sort elements in ascending order by id
		if(filterType.equals("ascending"))
			for(int i=0; i<boardgameList.size();i++)
				for(int j=0; j<boardgameList.size();j++)
					if(boardgameList.get(i).getDesigners().compareTo(boardgameList.get(j).getDesigners()) < 0){
						dummy = boardgameList.get(j);
						boardgameList.set(j, boardgameList.get(i));
						boardgameList.set(i,dummy);
					}
		//sort elements in ascending order by id
		if(filterType.equals("descending"))
			for(int i=0; i<boardgameList.size();i++)
				for(int j=0; j<boardgameList.size();j++)
					if(boardgameList.get(i).getDesigners().compareTo(boardgameList.get(j).getDesigners()) > 0){
						dummy = boardgameList.get(j);
						boardgameList.set(j, boardgameList.get(i));
						boardgameList.set(i,dummy);
					}
		return boardgameList;
	} 
}
