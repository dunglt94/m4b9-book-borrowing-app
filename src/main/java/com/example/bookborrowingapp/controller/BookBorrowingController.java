package com.example.bookborrowingapp.controller;

import com.example.bookborrowingapp.exception.BookUnavailableException;
import com.example.bookborrowingapp.exception.CardNotFoundException;
import com.example.bookborrowingapp.model.Book;
import com.example.bookborrowingapp.model.BookBorrowingCard;
import com.example.bookborrowingapp.service.IBookBorrowingCardService;
import com.example.bookborrowingapp.service.IBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

@Controller
@RequestMapping("/books")
public class BookBorrowingController {
    @Autowired
    private IBookService bookService;

    @Autowired
    private IBookBorrowingCardService bookBorrowingCardService;

    @ExceptionHandler(CardNotFoundException.class)
    public ModelAndView cardNotFound() {
        return new ModelAndView("error/wrong-card-code");
    }

    @ExceptionHandler(BookUnavailableException.class)
    public ModelAndView bookUnavailable() {
        return new ModelAndView("error/book-unavailable");
    }

    @GetMapping
    public String showBookList(ModelMap model) {
        model.addAttribute("books", bookService.findAll());
        return "index";
    }

    @GetMapping("/create")
    public String showCreateForm(ModelMap model) {
        model.addAttribute("book", new Book());
        return "create";
    }

    @PostMapping("/save")
    public String create(Book book, ModelMap model) {
        bookService.save(book);
        return "redirect:/books";
    }

    @GetMapping("/{id}/view")
    public String showView(@PathVariable long id, ModelMap model) {
        Optional<Book> book = bookService.findById(id);
        if (book.isPresent()) {
            model.addAttribute("book", book.get());
            BookBorrowingCard card = new BookBorrowingCard();
            card.setBook(book.get());
            model.addAttribute("card", card);
            return "view";
        } else {
            return "error/error-404";
        }
    }

    @GetMapping("/{id}/edit")
    public String showEditForm(@PathVariable long id, ModelMap model) {
        Optional<Book> book = bookService.findById(id);
        if (book.isPresent()) {
            model.addAttribute("book", book.get());
            return "update";
        } else {
            return "error/error-404";
        }
    }

    @PostMapping("/update")
    public String update(Book book, ModelMap model) {
        bookService.save(book);
        return "redirect:/books";
    }

    @GetMapping("/{id}/delete")
    public String delete(@PathVariable long id, ModelMap model) {
        bookService.deleteById(id);
        return "redirect:/books";
    }

    @PostMapping("/borrow")
    public String borrowBook(BookBorrowingCard card, RedirectAttributes redirectAttributes) throws BookUnavailableException {
        Book book = card.getBook();
        if (book.getQuantity() == 0) {
            throw new BookUnavailableException();
        }
        book.setQuantity(book.getQuantity() - 1);
        bookService.save(book);
        bookBorrowingCardService.save(card);
        redirectAttributes.addFlashAttribute("cardCode", card.getCode());
        return "redirect:/books";
    }

    @GetMapping("/return")
    public String returnBookForm(ModelMap model) {
        return "return-book";
    }

    @PostMapping("/return")
    public String returnBook(@RequestParam("cardCode") String cardCode) throws CardNotFoundException {
        Optional<BookBorrowingCard> card = bookBorrowingCardService.findByCode(cardCode);
        if (card.isPresent()) {
            Book book = card.get().getBook();
            book.setQuantity(book.getQuantity() + 1);
            bookService.save(book);
            bookBorrowingCardService.updateCardStatus(card.get());
        }
        return "redirect:/books";
    }
}
