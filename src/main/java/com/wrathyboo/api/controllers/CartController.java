package com.wrathyboo.api.controllers;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.core.MediaType;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.wrathyboo.api.entities.Cart;
import com.wrathyboo.api.entities.CartItem;
import com.wrathyboo.api.entities.CartRequest;
import com.wrathyboo.api.entities.Category;
import com.wrathyboo.api.entities.Product;
import com.wrathyboo.api.repository.CartRepository;
import com.wrathyboo.api.repository.CategoryRepository;
import com.wrathyboo.api.repository.ProductRepository;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("api/v1/cart")
@RequiredArgsConstructor
public class CartController {
	private final CartRepository cartRepository;
	private final ProductRepository productRepository;
	
//	@GetMapping(produces = MediaType.APPLICATION_JSON)
//	public ResponseEntity<String> getCart(@RequestParam Integer id) {
//		List<Cart> s = cartRepository.findAllItemsByOwner(id);
//		Gson son = new Gson();
//		String data = son.toJson(s);
//		return ResponseEntity.ok(son.toJson(s));
//	}
	
	@GetMapping(produces = MediaType.APPLICATION_JSON)
	public ResponseEntity<String> getCartItems(@RequestParam Integer id) {
		List<Cart> s = cartRepository.findAllItemsByOwner(id);
		List<Integer> ids = new ArrayList<Integer>();
		for (Cart x : s) {
			ids.add(x.getItem());
		}
		List<Product> items = productRepository.findByIds(ids);
		
		List<CartItem> result = new ArrayList<CartItem>();
		for (Cart x : s) {
			CartItem temp = new CartItem();
			temp.setImage(productRepository.getById(x.getItem()).getImage());
			temp.setName(productRepository.getById(x.getItem()).getName());
			temp.setPrice(productRepository.getById(x.getItem()).getPrice());
			temp.setQuantity(x.getQuantity());
			temp.setId(x.getItem());
			temp.setSale(productRepository.getById(x.getItem()).getSale());
			temp.setCartId(x.getId());
			
			if(temp.getSale() > 0) {
				Integer discountedPrice = temp.getPrice() - temp.getPrice() * temp.getSale() / 100;
				temp.setTotal(discountedPrice * temp.getQuantity());
			}
			else {
				temp.setTotal(temp.getPrice() * temp.getQuantity());
			}
			
			result.add(temp);
		}
		Gson son = new Gson();
		return ResponseEntity.ok(son.toJson(result));
	}
	
	@PostMapping(value="/add", consumes = MediaType.APPLICATION_JSON)
	public ResponseEntity<String> addCartItems(@RequestBody Cart cart) {
		
		
		
		if (cartRepository.existsCartByItemAndOwner(cart.getItem(),cart.getOwner())) {
			Cart c = cartRepository.findByItem(cart.getItem());
			c.setQuantity(c.getQuantity() + 1);
			cartRepository.save(c);
		}
		else {
			cartRepository.save(cart);
		}
		
		Gson son = new Gson();
		return ResponseEntity.ok(null);
	}
	
	@GetMapping(value="/remove")
	public ResponseEntity<String> requestRemove(@RequestParam(name="cartId") Integer id) {
		
		System.out.println("---------------------------"+id);
		
		cartRepository.deleteById(id);
			
		
		Gson son = new Gson();
		return ResponseEntity.ok("Deleted");
	}
	
}
