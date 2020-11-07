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

public class UserDatabase {

	private static UserDatabase instance = null;
	private Map<String, User> users = new HashMap<String, User>();
	
	public static UserDatabase getInstance() {
        if(instance == null) {
            instance = new UserDatabase();
        }
        return instance;
    }
	
	private UserDatabase(){
		users.put("1", new User("1", "1", "1", "User"));
		users.put("2", new User("2", "2", "2", "User"));
	}
	
	public String getUserName(String id){
		return users.get(id).getName();
	}
	
	public void addUser(String id, String name, String password, String role){
		users.put(id, new User(id, name, password, role));
	}
	
	
	public boolean isSuperuser(String id){
		if(users.get(id).getRole().equals("Superuser"))
			return true;
		else
			return false;
	}
	
	public boolean idAlreadyInserted(String id){
		return users.containsKey(id);
	}

	public User checkUsernameAndPassword(String username, String password){
		for (Map.Entry<String, User> user : users.entrySet()){
			if(user.getValue().getName().equals(username) && user.getValue().getPassword().equals(password))
				return user.getValue();
		}
		return null;
	}
	
	public ArrayList<User> getIdInOrder(String filterType){
		ArrayList<User> userList = new ArrayList<User>();
		User dummy;
		for (Map.Entry<String, User> user : users.entrySet()){
			userList.add(user.getValue());
		}
		//sort elements in ascending order by id
		if(filterType.equals("ascending"))
			for(int i=0; i<userList.size();i++)
				for(int j=0; j<userList.size();j++)
					if(userList.get(i).getId().compareTo(userList.get(j).getId()) < 0){
						dummy = userList.get(j);
						userList.set(j, userList.get(i));
						userList.set(i,dummy);
					}
		//sort elements in ascending order by id
		if(filterType.equals("descending"))
			for(int i=0; i<userList.size();i++)
				for(int j=0; j<userList.size();j++)
					if(userList.get(i).getId().compareTo(userList.get(j).getId()) > 0){
						dummy = userList.get(j);
						userList.set(j, userList.get(i));
						userList.set(i,dummy);
					}
		return userList;
	} 
	
	public ArrayList<User> getNameInOrder(String filterType){
		ArrayList<User> userList = new ArrayList<User>();
		User dummy;
		for (Map.Entry<String, User> user : users.entrySet()){
			userList.add(user.getValue());
		}
		//sort elements in ascending order by id
		if(filterType.equals("ascending"))
			for(int i=0; i<userList.size();i++)
				for(int j=0; j<userList.size();j++)
					if(userList.get(i).getName().compareTo(userList.get(j).getName()) < 0){
						dummy = userList.get(j);
						userList.set(j, userList.get(i));
						userList.set(i,dummy);
					}
		//sort elements in ascending order by id
		if(filterType.equals("descending"))
			for(int i=0; i<userList.size();i++)
				for(int j=0; j<userList.size();j++)
					if(userList.get(i).getName().compareTo(userList.get(j).getName()) > 0){
						dummy = userList.get(j);
						userList.set(j, userList.get(i));
						userList.set(i,dummy);
					}
		return userList;
	} 
	
	public ArrayList<User> filter(String filterType, String filter){
		ArrayList<User> userList = new ArrayList<User>();
		for (Map.Entry<String, User> user : users.entrySet()){
			if(filterType.equals("id") && user.getValue().getId().contains(filter))
				userList.add(user.getValue());
			if(filterType.equals("name") && user.getValue().getName().contains(filter))
				userList.add(user.getValue());
			if(filterType.equals("role") && user.getValue().getRole().contains(filter))
				userList.add(user.getValue());
		}
		return userList;
	}

}
