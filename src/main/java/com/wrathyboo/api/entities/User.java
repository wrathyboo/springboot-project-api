package com.wrathyboo.api.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.sql.Timestamp;
import java.util.Collection;
import java.util.List;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "user")
@EntityListeners(AuditingEntityListener.class)
public class User implements UserDetails{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
    
	private String username;
	
	private String email;
	
	private String password;
	
	private String phone;
	
	@Column(columnDefinition = "boolean default true")
    private Boolean status;
	
	@Enumerated(EnumType.STRING)
	private Role role;
	
	@LastModifiedDate
	private Timestamp updated_at;

	@CreatedDate
	private Timestamp created_at;
	
//	@OneToMany(mappedBy = "user")
//	 private List<Token> tokens;

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO Auto-generated method stub
		return List.of(new SimpleGrantedAuthority(role.name()));
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true;
	}


	
}
