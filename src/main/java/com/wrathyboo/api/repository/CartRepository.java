package com.wrathyboo.api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.wrathyboo.api.entities.Cart;


public interface CartRepository extends JpaRepository<Cart, Integer> {
	@Query(value = """
		      select t from Cart t inner join User u\s
		      on t.owner = u.id\s
		      where u.id = :id\s
		      """)
		  List<Cart> findAllItemsByOwner(Integer id);
	
	Cart findByItem(Integer id);
	
	Boolean existsCartByItemAndOwner(Integer item, Integer owner);
	
}
