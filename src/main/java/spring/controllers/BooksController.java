package spring.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import spring.dao.BookDAO;
import spring.dao.PersonDao;
import spring.models.Book;
import spring.models.Person;

import javax.validation.Valid;
import java.util.Optional;

@Controller
@RequestMapping("/books")
public class BooksController {

    private final BookDAO bookDAO;
    private final PersonDao personDao;

    @Autowired
    public BooksController(BookDAO bookDAO, PersonDao personDao) {
        this.bookDAO = bookDAO;
        this.personDao = personDao;
    }

    @GetMapping
    public String showBooks(Model model){
        model.addAttribute("books", bookDAO.showAll());
        return "books/show";
    }

    @GetMapping("/{id}")
    public String indexBook(@PathVariable("id") int id, Model model, @ModelAttribute("person") Person person){
        model.addAttribute("book", bookDAO.indexBook(id));

       Optional <Person> bookOwner = bookDAO.getBookOwner(id);

        if (bookOwner.isPresent())
            model.addAttribute("owner", bookOwner.get());
        else
            model.addAttribute("people", personDao.showAll());

        return "books/index";
    }

    @GetMapping("/new")
    public String newBook(@ModelAttribute("book") Book book){
        return "books/new";
    }

    @PostMapping
    public String createBook(@ModelAttribute("book") @Valid Book book,
                             BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return "books/new";
        }

        bookDAO.saveBook(book);
        return "redirect:/books";
    }

    @GetMapping("/{id}/edit")
    public String editBook(@PathVariable("id") int id, Model model) {
        model.addAttribute("book", bookDAO.indexBook(id));
        return "books/edit";
    }

    @PatchMapping("/{id}")
    public String updateBook(@PathVariable("id") int id, @ModelAttribute("book") @Valid Book book,
                             BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            return "books/edit";
        }

        bookDAO.updateBook(id, book);
        return "redirect:/books";
    }

    @DeleteMapping("{id}")
    public String deleteBook(@PathVariable("id") int id){
        bookDAO.deleteBook(id);
        return "redirect:/books";
    }
    @PatchMapping("/{id}/release")
    public String release(@PathVariable("id") int id){
        bookDAO.release(id);
        return "redirect:/books/" + id;

    }

    @PatchMapping("/{id}/assign")
    public String assign(@PathVariable("id") int id, @ModelAttribute("person")Person person){
    bookDAO.assign(id, person);
    return "redirect:/books/" + id;
    }
}
