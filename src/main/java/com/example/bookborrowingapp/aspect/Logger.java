package com.example.bookborrowingapp.aspect;

import com.example.bookborrowingapp.model.Book;
import com.example.bookborrowingapp.model.BookBorrowingCard;
import com.example.bookborrowingapp.model.VisitorCount;
import com.example.bookborrowingapp.service.IBookBorrowingCardService;
import com.example.bookborrowingapp.service.IVisitorCountService;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Aspect
@Component
public class Logger {
    @Autowired
    private IBookBorrowingCardService bookBorrowingCardService;

    @Autowired
    private IVisitorCountService visitorCountService;

    @AfterReturning(pointcut = "execution(* com.example.bookborrowingapp.controller.BookBorrowingController.borrowBook(..))")
    public void logAfterBookBorrowed(JoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();
        BookBorrowingCard card = (BookBorrowingCard) args[0];
        Book book = card.getBook();
        System.out.println(book.getTitle() + " borrowed");
        System.out.println("Remaining quantity: " + book.getQuantity());
        System.out.println("Card with code '" + card.getCode() + "' created");
    }

    @AfterReturning(pointcut = "execution(* com.example.bookborrowingapp.controller.BookBorrowingController.returnBook(..))")
    public void logAfterBookReturned(JoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();
        String cardCode = (String) args[0];
        Optional<BookBorrowingCard> bookBorrowingCard = bookBorrowingCardService.findByCode(cardCode);
        BookBorrowingCard card = bookBorrowingCard.get();
        Book book = card.getBook();
        System.out.println(book.getTitle() + " with borrowing card code '" + card.getCode() + "' returned");
        System.out.println("Remaining quantity: " + book.getQuantity());
        System.out.println("Card with code '" + card.getCode() + "' deactivated");
    }

    @Before("execution(* com.example.bookborrowingapp.controller.BookBorrowingController.*(..))")
    public void logVisitorCount() {
        Optional<VisitorCount> visitorCount = visitorCountService.findById(1);
        VisitorCount count = visitorCount.get();
        count.setCount(count.getCount() + 1);
        visitorCountService.save(count);
        System.out.println("Number of visitors: " + count.getCount());
    }
}
