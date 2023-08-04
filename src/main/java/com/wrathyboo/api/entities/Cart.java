package com.wrathyboo.api.entities;

import java.sql.Timestamp;
import java.util.Set;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "cart")
@EntityListeners(AuditingEntityListener.class)
public class Cart {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	private Integer owner;

    private Integer item;
    
    private Integer sale;
    
    @Column(name="quantity", columnDefinition="integer default 1")
    private Integer quantity;
}