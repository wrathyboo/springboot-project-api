package com.wrathyboo.api.controllers;

import java.io.IOException;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wrathyboo.api.entities.AuthenticationRequest;
import com.wrathyboo.api.entities.AuthenticationResponse;
import com.wrathyboo.api.entities.RegisterRequest;
import com.wrathyboo.api.service.AuthenticationService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController{
	
	private final AuthenticationService service;

	 @PostMapping("/register")
	  public ResponseEntity<AuthenticationResponse> register(
	      @RequestBody RegisterRequest request
	  ) {
	    return ResponseEntity.ok(service.register(request));
	  }
	  @PostMapping("/authenticate")
	  public ResponseEntity<AuthenticationResponse> authenticate(
	      @RequestBody AuthenticationRequest request
	  ) {
	    return ResponseEntity.ok(service.authenticate(request));
	  }
	
	  @PostMapping("/refresh-token")
	  public void refreshToken(
	      HttpServletRequest request,
	      HttpServletResponse response
	  ) throws IOException {
	    service.refreshToken(request, response);
	  }

	
}

