package com.wrathyboo.api.controllers;

import java.util.List;

import javax.ws.rs.core.MediaType;

import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.wrathyboo.api.entities.Category;
import com.wrathyboo.api.entities.Product;
import com.wrathyboo.api.entities.Type;
import com.wrathyboo.api.repository.CategoryRepository;
import com.wrathyboo.api.repository.ProductRepository;
import com.wrathyboo.api.repository.UserRepository;
import com.wrathyboo.api.service.ProductService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("api/v1/product")
@RequiredArgsConstructor
public class ProductController {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
	
	@GetMapping()
	public List<Product> getProducts(@RequestParam(name="category", defaultValue = "", required = false) List<Integer> category, 
			                         @RequestParam(name="order", required = false) String order, 
			                         @RequestParam(name="name", required = false) Type name,
			                         @RequestParam(name="search", required = false) String keywords,
			                         @RequestParam(name="min", required = false) Integer min,
			                         @RequestParam(name="max", required = false) Integer max,
			                         @RequestParam(name="sale", required = false) Integer sale,
			                         @RequestParam(name="page", defaultValue = "0", required = false) Integer page,
			                         @RequestParam(name="type", defaultValue = "", required = false) Type type,
			                         @RequestParam(name="popular", defaultValue = "", required = false) String popular,
	                                 @RequestParam(name="customSearch", defaultValue = "false" , required = false) Boolean flag){
		Pageable pageRequest = PageRequest.of(page, 9);
		if (category.isEmpty()) {
			List<Category> temp = categoryRepository.findAll();
			for (Category x : temp) {
				category.add(x.getId());
			}
		}
		
		if (!popular.isEmpty()) {
			Page<Product> popularList = productRepository.findAll(PageRequest.of(0, 10, Sort.by(Sort.Direction.DESC, "rating")));
			return new ResponseEntity<>(popularList.getContent(), HttpStatus.OK).getBody();
		}
		
		if (type == Type.MALE || type == Type.FEMALE || type == Type.UNISEX) {
			List<Product> temp = productRepository.findAllByTypeOrderByCreatedAtDesc(type);
			return new ResponseEntity<>(temp, HttpStatus.OK).getBody();
		}
		
		Page<Product> list = productRepository.findAllByStatusTrueOrderByCreatedAtDesc(pageRequest);
		
		if (keywords != null) {
			 list = productRepository.search(keywords, pageRequest);
		}
		
		if (flag) {
			list = productRepository.findAllByCategoryInAndPriceBetweenAndSaleGreaterThanEqualAndStatusTrue(category, min, max, sale,pageRequest);
		}
        
		System.out.println("Total pages: " + list.getTotalPages());
		System.out.println("Current page: " + (page + 1));
		System.out.println("Items: " + list.getTotalElements());
		
		

		
		return new ResponseEntity<>(list.getContent(), HttpStatus.OK).getBody();
		
		
	}
	
	@GetMapping(value="/getPages")
	public Integer getTotalPages(@RequestParam(name="customSearch", defaultValue = "false" , required = false) Boolean flag){
		Pageable pageRequest = PageRequest.of(0, 9);
		Page<Product> list= productRepository.findAllByStatusTrueOrderByCreatedAtDesc(pageRequest);
	
		
		 
		System.out.println("Total pages: " + list.getTotalPages());
		return new ResponseEntity<>(list.getTotalPages(), HttpStatus.OK).getBody();
		
		
	}
	
	@GetMapping(value = "/manager")
	public List<Product> productManager() {
		return new ResponseEntity<>(productRepository.findAll(), HttpStatus.OK).getBody();
	}
	
	@GetMapping(value = "/{id}",produces = MediaType.APPLICATION_JSON)
	public ResponseEntity<String> getItemById(@PathVariable("id") Integer id) {
		Product s = productRepository.findById(id).orElseThrow();
		Gson son = new Gson();
		String data = son.toJson(s);
		return ResponseEntity.ok(son.toJson(s));
	}
	
	@PostMapping(value = "/create")
    public ResponseEntity<String> createItem(@RequestBody Product s) {
    	Gson son = new Gson();
    	Product objeb = s;
		var bl = productRepository.save(objeb);
		String data = son.toJson(objeb);
 		return ResponseEntity.ok(son.toJson(data));
    }
	
	@DeleteMapping(value = "/delete/{id}")
    public ResponseEntity<Product> delete(@PathVariable("id") Integer id) {
        productRepository.deleteById(id);
         return new ResponseEntity<>(HttpStatus.OK);
    }
	
	@PutMapping(value = "/update/{id}")
    public ResponseEntity<Product> updateItem(@PathVariable("id") Integer id, @RequestBody Product request) {
    	Product persistedObj = productRepository.findById(id).orElseThrow();
    	
    	if (request.getImage() != null) {
    	   	persistedObj.setImage(request.getImage());
    	}
    	if (request.getStatus() != null) {
    	   	persistedObj.setStatus(request.getStatus());
    	}
    	if (request.getType() != null) {
    	   	persistedObj.setType(request.getType());
    	}
    	persistedObj.setName(request.getName());
    	persistedObj.setDescription(request.getDescription());
    	persistedObj.setPrice(request.getPrice()*100);
    	persistedObj.setSale(request.getSale());
    	persistedObj.setReviews(request.getReviews());
    	persistedObj.setRating(request.getRating() != null ? request.getRating() : 0);
    	persistedObj.setCategory(request.getCategory());
    	
		productRepository.save(persistedObj);
		
 		return new ResponseEntity<>(HttpStatus.OK);
    }
}
