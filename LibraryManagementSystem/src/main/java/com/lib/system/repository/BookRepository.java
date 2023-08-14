package com.lib.system.repository;

import org.apache.ibatis.annotations.Mapper;

import com.lib.system.entity.Book;

@Mapper
public interface BookRepository {
	void save(Book book);
}
