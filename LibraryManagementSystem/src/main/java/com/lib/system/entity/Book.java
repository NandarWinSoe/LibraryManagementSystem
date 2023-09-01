package com.lib.system.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "book")
public class Book {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Integer id;
	String name;
	Integer categoryId;
	String category;
	String author;
	Integer produceYear;
	Integer bookType;
	Integer lendUser;
	String file;
	byte[] size;

	@Transient
	public String getPdfFilePath() {
		if (name == null || id == null)
			return null;

		return "/pdf-files/" + id + "/" + file;

	}
}