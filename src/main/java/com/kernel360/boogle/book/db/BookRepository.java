package com.kernel360.boogle.book.db;



import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookRepository extends JpaRepository<BookEntity, Long> {

    BookEntity save(BookEntity book);

    List<BookEntity> findAll();
}

