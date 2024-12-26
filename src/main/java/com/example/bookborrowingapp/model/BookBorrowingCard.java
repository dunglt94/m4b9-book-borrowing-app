package com.example.bookborrowingapp.model;

import javax.persistence.*;
import java.util.Random;

@Entity
@Table(name = "borrowing_card")
public class BookBorrowingCard {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String code;

    @ManyToOne
    @JoinColumn(name = "book_id")
    private Book book;

    private boolean cardStatus;

    public BookBorrowingCard() {
        this.code = String.format("%05d", new Random().nextInt(100000));
        this.cardStatus = true;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public boolean isCardStatus() {
        return cardStatus;
    }

    public void setCardStatus(boolean cardStatus) {
        this.cardStatus = cardStatus;
    }
}
