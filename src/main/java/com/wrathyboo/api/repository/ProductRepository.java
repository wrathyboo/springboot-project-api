package com.wrathyboo.api.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.wrathyboo.api.entities.Product;
import com.wrathyboo.api.entities.Type;

public interface ProductRepository extends JpaRepository<Product, Integer> {

	List<Product> findByName (String name);
	
	List<Product> findAllByOrderByPriceAsc();
	
	List<Product> findAllByOrderByPriceDesc();
	
	List<Product> findAllByOrderByCreatedAtAsc();
	
	List<Product> findAllByOrderByCreatedAtDesc();
	
	List<Product> findAllByStatusFalse ();
	
	Page<Product> findAllByOrderByRatingDesc (Pageable pageable);
	
	Page<Product> findAllByStatusTrueOrderByCreatedAtDesc (Pageable pageable);
	
	List<Product> findAllByTypeOrderByCreatedAtDesc (Type type);
	
	Page<Product> findAllByCategoryInAndPriceBetweenAndSaleGreaterThanEqualAndStatusTrue (List<Integer> list, Integer startPrice, Integer endPrice, Integer sale, Pageable pageable );
	
	List<Product> findAllByPriceBetween(Integer startPrice, Integer endPrice);
	
	@Query("SELECT p FROM Product p WHERE CONCAT(p.name, p.type, p.description) LIKE %?1%")
    public Page<Product> search(String keyword, Pageable pageable );
	
	@Query("select p from Product p where p.id in :ids" )
	 List<Product> findByIds(@Param("ids") List<Integer> ids);
	
	Product getById(Integer id);
	
	
//	List<Product> findAllByOrderByNameAsc(String name);
}
