package com.abhijit.restfulmicroservice.user;

import java.net.URI;
import java.util.List;

import org.apache.coyote.http11.Http11AprProtocol;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
public class UserController {
	@Autowired
	private UserDaoService service;

	@GetMapping("/users")
	private List<User> retrieveAllUsers() {
		return service.findAll();
	}

	@GetMapping("/users/{id}")
	public HttpStatus retriveUser(@PathVariable int id) {
		User user = service.findOne(id);
		if(user == null) {
			throw new UserNotFoundException("id " + id);
		}else {
			return HttpStatus.OK;
		}
	}

	@PostMapping("/users")
	public ResponseEntity<Object> createUser(@RequestBody User user) {
		User savedUser = service.save(user);
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(savedUser.getId())
				.toUri();
		return ResponseEntity.created(location).build();
	}
}
