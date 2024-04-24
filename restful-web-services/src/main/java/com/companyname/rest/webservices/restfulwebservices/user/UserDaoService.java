package com.companyname.rest.webservices.restfulwebservices.user;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import org.springframework.stereotype.Component;

@Component
public class UserDaoService {	
	// JPA/Hibernate > Database
	// UserDaoService > Static List
	
	private static List<User> users = new ArrayList<>();

private static int usersCount = 0;
	//This is a static initializer block, This block allows you to perform any initialization tasks that need to be done when the class is first accessed 
//or instantiated. In this case, it's used to initialize the users list with some initial data. 
//One of the important things for you to remember is that we are making use of a static list whenever We restart the server the entire list gets reset.
//So whenever this server restarts, you'll only have three users present.
	static {
		users.add(new User(++usersCount,"Adam",LocalDate.now().minusYears(30)));
		users.add(new User(++usersCount,"Eve",LocalDate.now().minusYears(25)));
		users.add(new User(++usersCount,"Jim",LocalDate.now().minusYears(20)));
	}
	
	public User save(User user) { // we are temporarily adding the user which is inserted from API to users list
		user.setId(++usersCount);
		users.add(user);
		return user;
	}

	public List<User> findAll() {
		return users;
	}
	
	public User findOne(int id) {
	    // Create a predicate to check if a User's ID matches the specified id
	    Predicate<? super User> predicate = user -> user.getId().equals(id);
	    
	    // Convert the list of users into a stream, filter by the predicate, and find the first matching user
	    // If no user matches the predicate, throw a NoSuchElementException
	    return users.stream()
	                .filter(predicate)
	                .findFirst()
	                .orElse(null); // Throws NoSuchElementException if no user is found
	}

	public void deleteById(int id) {
	    // Create a predicate to check if a User's ID matches the specified id
	    Predicate<? super User> predicate = user -> user.getId().equals(id);
	    
	     users.removeIf(predicate);//If a predicate matches. So if the ID matches, we are going to remove the user.
	                
	}
	
	
	
	}
