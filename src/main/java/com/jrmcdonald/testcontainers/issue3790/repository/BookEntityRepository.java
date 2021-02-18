package com.jrmcdonald.testcontainers.issue3790.repository;

import com.jrmcdonald.testcontainers.issue3790.model.BookEntity;

import org.springframework.data.jpa.repository.JpaRepository;

public interface BookEntityRepository extends JpaRepository<BookEntity, Long> {

}
