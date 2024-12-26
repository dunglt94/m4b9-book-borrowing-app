package com.example.bookborrowingapp.service;

import com.example.bookborrowingapp.exception.CardNotFoundException;
import com.example.bookborrowingapp.model.BookBorrowingCard;
import com.example.bookborrowingapp.repository.IBookBorrowingCardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class BookBorrowingCardService implements IBookBorrowingCardService {
    @Autowired
    private IBookBorrowingCardRepository bookBorrowingCardRepository;

    @Override
    public void updateCardStatus(BookBorrowingCard card) {
        card.setCardStatus(false);
        bookBorrowingCardRepository.save(card);
    }

    @Override
    public Iterable<BookBorrowingCard> findAll() {
        return bookBorrowingCardRepository.findAll();
    }

    @Override
    public Optional<BookBorrowingCard> findById(long id) {
        return bookBorrowingCardRepository.findById(id);
    }

    @Override
    public void save(BookBorrowingCard card) {
        bookBorrowingCardRepository.save(card);
    }

    @Override
    public void deleteById(long id) {
        bookBorrowingCardRepository.deleteById(id);
    }

    @Override
    public Optional<BookBorrowingCard> findByCode(String code) throws CardNotFoundException {
        try {
            return bookBorrowingCardRepository.findByCode(code);
        } catch (CardNotFoundException e) {
            throw new CardNotFoundException();
        }
    }
}
