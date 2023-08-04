package com.wrathyboo.api.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.wrathyboo.api.entities.Category;

public interface CategoryRepository extends JpaRepository<Category, Integer> {

	Optional<Category> findByName (String name);
}
