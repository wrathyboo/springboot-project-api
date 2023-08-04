package com.wrathyboo.api.service;

import java.util.Collections;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.wrathyboo.api.entities.Category;
import com.wrathyboo.api.entities.Product;
import com.wrathyboo.api.repository.CategoryRepository;
import com.wrathyboo.api.repository.ProductRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductService {

	private final ProductRepository productRepository;
	private final CategoryRepository categoryRepository;
	
	public Category categoryResponse(Category request) {
		var category = Category.builder()
				.name(request.getName())
				.build();
		var savedcate = categoryRepository.save(category);
		return savedcate;
	}
	
	
}
