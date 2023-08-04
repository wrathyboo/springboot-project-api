package com.wrathyboo.api.controllers;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.wrathyboo.api.entities.User;
import com.wrathyboo.api.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;


@RestController
@RequestMapping("api/v1/users")
@RequiredArgsConstructor
public class UserController {
	
	private final UserRepository userRepository;
	
	@GetMapping()
	public List<User> getList() {
		return new ResponseEntity<>(userRepository.findAll(), HttpStatus.OK).getBody();
    }
	
	
    
	@PostMapping(value = "/create", consumes = MediaType.APPLICATION_JSON)
    public ResponseEntity<String> create(@RequestBody User user) {
    	Gson son = new Gson();
		User objeb = user;
		var bl = userRepository.save(objeb);
		String data = son.toJson(bl);
 		return ResponseEntity.ok(son.toJson(data));
    }
    
  
}