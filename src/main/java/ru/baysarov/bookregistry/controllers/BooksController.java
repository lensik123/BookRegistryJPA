package ru.baysarov.bookregistry.controllers;

import java.util.Optional;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.baysarov.bookregistry.model.Book;
import ru.baysarov.bookregistry.model.Person;
import ru.baysarov.bookregistry.services.BooksService;
import ru.baysarov.bookregistry.services.PeopleService;
import ru.baysarov.bookregistry.util.BookValidator;

@Controller
@RequestMapping("/books")
public class BooksController {


  private final BookValidator bookValidator;
  private final BooksService booksService;
  private final PeopleService peopleService;


  @Autowired
  public BooksController(BookValidator bookValidator,
      BooksService booksService, PeopleService peopleService) {
    this.bookValidator = bookValidator;
    this.booksService = booksService;
    this.peopleService = peopleService;
  }

  @GetMapping()
  public String index(Model model, @RequestParam(value = "page", required = false) Integer page,
      @RequestParam(value = "books_per_page", required = false) Integer booksPerPage,
      @RequestParam(value = "sort_by_year", required = false) boolean sortByYear) {

    if (page == null || booksPerPage == null) {
      model.addAttribute("books", booksService.findAll(sortByYear));
    } else {
      model.addAttribute("books", booksService.findWithPagination(page,booksPerPage,sortByYear));
    }
    return "books/index";
  }


  @GetMapping("/{id}")
  public String show(@PathVariable("id") int id, Model model,
      @ModelAttribute("person") Person person) {
    model.addAttribute("book", booksService.show(id));

    Optional<Person> bookOwner = booksService.getOwner(id);
    if (bookOwner.isPresent()) {
      model.addAttribute("bookOwner", bookOwner);
    } else {
      model.addAttribute("people", peopleService.findAll());
    }

    return "books/show";
  }

  @GetMapping("/new")
  public String addBook(@ModelAttribute("book") Book book) {
    return "books/new";
  }

  @PostMapping()
  public String create(@ModelAttribute("book") @Valid Book book, BindingResult bindingResult) {
    bookValidator.validate(book, bindingResult);
    if (bindingResult.hasErrors()) {
      return "books/new";
    }

    booksService.save(book);
    return "redirect:/books";
  }

  @GetMapping("/{id}/edit")
  public String edit(@PathVariable("id") int id, Model model) {
    model.addAttribute("book", booksService.show(id));
    return "books/edit";
  }

  @PatchMapping("/{id}")
  public String update(@ModelAttribute("book") @Valid Book book, BindingResult bindingResult,
      @PathVariable("id") int id) {
    if (bindingResult.hasErrors()) {
      return "books/edit";
    }
    bookValidator.validate(book, bindingResult);
    booksService.update(id, book);
    return "redirect:/books";
  }

  @DeleteMapping("/{id}")
  public String delete(@PathVariable("id") int id) {
    booksService.delete(id);
    return "redirect:/books";
  }

  @PatchMapping("/{id}/assign")
  public String assignBook(@PathVariable("id") int bookId,
      @ModelAttribute("person") Person person) {
    booksService.assign(person.getId(), bookId);
    return "redirect:/books";
  }

  @PatchMapping("/{id}/release")
  public String releaseBook(@PathVariable("id") int bookId) {
    booksService.release(bookId);
    return "redirect:/books";
  }

  @GetMapping("/search")
  public String searchPage() {
    return "books/search";
  }

  @PostMapping("/search")
  public String makeSearch(Model model, @RequestParam("query") String query) {
    model.addAttribute("books", booksService.searchByTitle(query));
    return "books/search";
  }

}
