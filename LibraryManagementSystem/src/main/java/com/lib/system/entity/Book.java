package com.lib.system.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "book")
public class Book {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	Integer id;
	@Column(name = "name")
	String name;
	@Column(name = "categoryId")
	Integer categoryId;
	@Column(name = "category")
	String category;
	@Column(name = "author")
	String author;
	@Column(name = "produceYear")
	Integer produceYear;
	@Column(name = "bookType")
	Integer bookType;
	
}