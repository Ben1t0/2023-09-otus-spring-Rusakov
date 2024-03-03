package ru.otus.spring.page;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequiredArgsConstructor
@Controller
public class BookPagesController {

    @GetMapping("/")
    public String listPage() {
        return "list";
    }

    @GetMapping("/books/create")
    public String createPage() {
        return "create_book";
    }

    @GetMapping("/books/edit")
    public String editPage(@RequestParam("id") long id, Model model) {
        model.addAttribute("bookId", id);
        return "edit_book";
    }
}
