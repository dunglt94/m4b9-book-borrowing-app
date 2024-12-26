package com.example.bookborrowingapp.repository;

import com.example.bookborrowingapp.model.BookBorrowingCard;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IBookBorrowingCardRepository extends CrudRepository<BookBorrowingCard, Long> {
    Optional<BookBorrowingCard> findByCode(String code);
}
