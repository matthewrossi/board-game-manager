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

package authorization;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.PreMatching;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.Provider;

import resources.User;
import resources.UserDatabase;

@Provider
@PreMatching
public class AuthFilter implements ContainerRequestFilter {
   
	/**
     * Apply the filter : check input request, validate or not with user auth
     * @param containerRequest The request from Tomcat server
     */
    @Override
    public void filter(ContainerRequestContext containerRequest) throws WebApplicationException {
        
        String method = containerRequest.getMethod();
        String path = containerRequest.getUriInfo().getPath(true);
        String auth = containerRequest.getHeaderString("authorization");
        
        if(auth == null) {
            throw new WebApplicationException(Status.UNAUTHORIZED);
        }
        String[] loginPassword = BasicAuth.decode(auth);
        if(loginPassword == null || loginPassword.length != 2) {
            throw new WebApplicationException(Status.UNAUTHORIZED);
        }
  
        UserDatabase userDatabase = UserDatabase.getInstance();
        User user;
        //create dummy user if the client is trying to create a new user
        //the 'then part' of this if is used to bypass the filter
        if(method.equals("POST") && path.equals("users"))
        	user = new User("0", "dummy", "dummy", "Superuser");
        else
        	user =  userDatabase.checkUsernameAndPassword(loginPassword[0], loginPassword[1]);
  
        //Our system refuse login and password
        if(user == null) {
            throw new WebApplicationException(Status.UNAUTHORIZED);
        }
  
        // We configure your Security Context here
        String scheme = containerRequest.getUriInfo().getRequestUri().getScheme();
        containerRequest.setSecurityContext(new BGMSecurityContext(user, scheme));

    }
}