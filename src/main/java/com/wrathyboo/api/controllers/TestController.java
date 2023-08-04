package com.wrathyboo.api.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.wrathyboo.api.entities.User;
import com.wrathyboo.api.repository.UserRepository;

import lombok.RequiredArgsConstructor;


@RestController
@RequestMapping("/api/v1/demo-controller")
@RequiredArgsConstructor
public class TestController {
	private final UserRepository userRepository;
	
	  @GetMapping
	  public ResponseEntity<List<User>> sayHello() {
		  List<User> users = userRepository.findAll();
	
			return ResponseEntity.ok(users);
	  }
}
