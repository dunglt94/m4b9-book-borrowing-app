package com.example.bookborrowingapp.service;

import com.example.bookborrowingapp.exception.CardNotFoundException;
import com.example.bookborrowingapp.model.BookBorrowingCard;

import java.util.Optional;

public interface IBookBorrowingCardService extends IGenerateService<BookBorrowingCard> {
    void updateCardStatus (BookBorrowingCard card);

    Optional<BookBorrowingCard> findByCode(String code) throws CardNotFoundException;
}
