package com.cowrycode.publisherhouse.book;

import com.cowrycode.publisherhouse.userbooks.UserBooks;
import com.cowrycode.publisherhouse.userbooks.UserBooksPrimaryKey;
import com.cowrycode.publisherhouse.userbooks.UserBooksRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Optional;

@Controller
public class BookController {
    private final String COVER_IMAGE_ROOT = "http://covers.openlibrary.org/b/id/";

    @Autowired
    BookRepository bookRepository;

    @Autowired
    UserBooksRepository userBooksRepository;


    @GetMapping(value = "/books/{bookId}")
    public String getBook(@PathVariable String bookId, Model model, @AuthenticationPrincipal OAuth2User principal){
        Optional<BookObject> optionalBook =  bookRepository.findById(bookId);
            if(optionalBook.isPresent()){
                BookObject book = optionalBook.get();
                String coverImageUrl = "/images/no-image.png";
                if(book.getCoverIds() != null && book.getCoverIds().size() > 0){
                    coverImageUrl = COVER_IMAGE_ROOT + book.getCoverIds().get(0) +"-L.jpg";
                }
                model.addAttribute("coverImage", coverImageUrl);
                model.addAttribute("book", book);


                if(principal != null && principal.getAttribute("login") != null){
                    model.addAttribute("loginId", principal.getAttribute("login"));
                    String userId = principal.getAttribute("login");
                    model.addAttribute("loginId", userId);
                    UserBooksPrimaryKey key = new UserBooksPrimaryKey();
                    key.setBookId(bookId);
                    key.setUserId(userId);
                    Optional<UserBooks> userBooks = userBooksRepository.findById(key);
                    if(userBooks.isPresent()){
                        model.addAttribute("userBooks", userBooks.get());
                    }else {
                        model.addAttribute("userBooks", new UserBooks());
                    }
                }
                System.out.println("THE BOOK IS PRESENT ::::::::::::::::::");
                return "book";
            }

        System.out.println("THE BOOK IS NOT PRESENT ::::::::::::::::::");
        return "book-not-found";
    }
}
