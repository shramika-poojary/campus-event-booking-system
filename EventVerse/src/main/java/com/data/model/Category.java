package com.data.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Entity
@Data

@RequiredArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Table(name="Categories")
public class Category {


	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int categoryId;
	
	@Column(nullable=false,unique=true)
	private String categoryName;
	
	@CreatedDate
	private LocalDateTime createdAt;
	
	@OneToMany(mappedBy = "category",cascade=CascadeType.ALL)
	private List<Event> events= new ArrayList<>();
	

}
