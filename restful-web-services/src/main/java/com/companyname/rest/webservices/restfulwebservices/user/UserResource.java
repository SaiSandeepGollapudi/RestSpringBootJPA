package com.companyname.rest.webservices.restfulwebservices.user;


import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.net.URI;
import java.util.List;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import jakarta.validation.Valid;

@RestController
public class UserResource {

	private UserDaoService service;

	public UserResource(UserDaoService service) {
		this.service = service;
	}

	// GET /users
	@GetMapping("/users")
	public List<User> retrieveAllUsers() {
		return service.findAll();
	}
	
	// GET /user/{id}
	@GetMapping("/users/{id}")//http://localhost:8080/users/1
	public EntityModel<User> retrieveUser(@PathVariable int id) {//you're are basically encapsulating an the user class	
		User user = service.findOne(id);
		
		if(user==null) 
			throw new UserNotFoundException("id: "+ id);// if we test with user id as 101 in link http://localhost:8080/users/101, 
			//we will get in that link UserNotFoundException id:101
		
			EntityModel<User> entityModel= EntityModel.of(user);
			
			WebMvcLinkBuilder link = linkTo(methodOn(this.getClass()).retrieveAllUsers());//using the web MVC link builder link.to() to create a link
			//pointing to the controller method. The controller method we are pointing to is retrieve all users and now once we have the link, we can
//			add the link to the entity, modal entity, 
		
			entityModel.add(link.withRel("all-users"));
			
				
		return entityModel;
	}
	
	
	@DeleteMapping("/users/{id}")
	public void deleteUser(@PathVariable int id) {
		service.deleteById(id);
		
	}
	
	@PostMapping("/users")
	public ResponseEntity<User> createUser(@Valid @RequestBody User user) {
		User savedUser = service.save(user);
		// /users/4 -> /users, user.getID
		URI location = ServletUriComponentsBuilder.fromCurrentRequest()//So to the path of the current request,
				.path("/{id}")// we are appending a path /id variable 
				.buildAndExpand(savedUser.getId())//and  we are replacing it with the newly created ID, which is four.
				.toUri();// converting it to a URI
			
				return ResponseEntity.created(location).build();// we are able to return location of created resource as part of the response headers

	}
	


	
	


}
