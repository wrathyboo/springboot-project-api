package com.wrathyboo.api.controllers;

import java.util.List;

import javax.ws.rs.core.MediaType;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.wrathyboo.api.entities.Category;
import com.wrathyboo.api.entities.Product;
import com.wrathyboo.api.repository.CategoryRepository;
import com.wrathyboo.api.repository.ProductRepository;
import com.wrathyboo.api.repository.UserRepository;
import com.wrathyboo.api.service.ProductService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("api/v1/category")
@RequiredArgsConstructor
public class CategoryController {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
	
	@GetMapping(produces = MediaType.APPLICATION_JSON)
	public ResponseEntity<String> getCatList() {
		List<Category> s = categoryRepository.findAll();
		Gson son = new Gson();
		String data = son.toJson(s);
		return ResponseEntity.ok(son.toJson(s));
	}
	
	@GetMapping(value = "/{id}",produces = MediaType.APPLICATION_JSON)
	public ResponseEntity<String> getCategory(@PathVariable("id") String id) {
		Category s = categoryRepository.findById(Integer.parseInt(id)).orElseThrow();
		Gson son = new Gson();
		String data = son.toJson(s);
		return ResponseEntity.ok(son.toJson(s));
	}
	
	@PostMapping(value = "/create", consumes = MediaType.APPLICATION_JSON)
    public ResponseEntity<Category> createItem(@RequestBody Category s) {
		Category newItem = categoryRepository.save(s);
 		return new ResponseEntity<>(newItem, HttpStatus.CREATED);
    }
	
	
//	    @PostMapping(value = "/edit")
//	    public ResponseEntity<AdminModel> edit(@ModelAttribute AdminModel adminModel) {
//	        adminModel.setPassword(encoder.encode(adminModel.getPassword()));
//	        AdminModel adminModel1 = userService.edit(adminModel);
//	        return new ResponseEntity<>(adminModel1, HttpStatus.CREATED);
//	    }
	    @PostMapping(value = "/delete/{id}")
	    public ResponseEntity<Category> delete(@PathVariable("id") String id) {
	        categoryRepository.deleteById(Integer.parseInt(id));
	         return new ResponseEntity<>(HttpStatus.OK);
	        
	    }
}
