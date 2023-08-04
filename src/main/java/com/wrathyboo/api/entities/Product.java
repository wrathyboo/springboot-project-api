package com.wrathyboo.api.entities;

import java.sql.Timestamp;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
@Table(name = "product")
@EntityListeners(AuditingEntityListener.class)
public class Product {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	private String name;
	
	private String image;
	
	@Enumerated(EnumType.STRING)
	private Type type;
	
	@Column(name="category_id")
	public Integer category;
	
	private Integer price;
	
	private String description;
	
	@Column(columnDefinition = "boolean default true")
	private Boolean status;
	
	@Column(name="on_sale",columnDefinition = "integer default 0")
	private Integer sale;
	
	private Integer rating;
	
	private Integer reviews;
	
	@LastModifiedDate
	@Column(name="updated_at")
	private Timestamp updatedAt;

	@CreatedDate
	@Column(name="created_at")
	private Timestamp createdAt;

}
