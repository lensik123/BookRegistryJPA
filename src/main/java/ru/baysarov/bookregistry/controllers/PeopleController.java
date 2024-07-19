package ru.baysarov.bookregistry.controllers;

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
import ru.baysarov.bookregistry.model.Book;
import ru.baysarov.bookregistry.model.Person;
import ru.baysarov.bookregistry.services.BooksService;
import ru.baysarov.bookregistry.services.PeopleService;
import ru.baysarov.bookregistry.util.PersonValidator;

@Controller
@RequestMapping("/people")
public class PeopleController {

  private final PersonValidator personValidatorvalidator;
  private final PeopleService peopleService;
  private final BooksService booksService;

  @Autowired
  public PeopleController(PersonValidator validator,
      PeopleService peopleService, BooksService booksService) {
    this.personValidatorvalidator = validator;
    this.peopleService = peopleService;
    this.booksService = booksService;
  }

  @GetMapping()
  public String index(Model model) {
    model.addAttribute("people", peopleService.findAll());
    return "people/index";
  }

  @GetMapping("/{id}")
  public String show(@PathVariable("id") int personId, Model model,
      @ModelAttribute("book") Book book) {
    model.addAttribute("person", peopleService.show(personId));

    Person person = peopleService.show(personId);

    if (person.getBooks() != null) {
      model.addAttribute("books", person.getBooks());
    } else {
      model.addAttribute("noBooks", "");
    }
    return "people/show";
  }

  @GetMapping("/new")
  public String newPerson(@ModelAttribute("person") Person person) {
    return "people/new";
  }

  @PostMapping
  public String create(@ModelAttribute("person") @Valid Person person,
      BindingResult bindingResult) {
    personValidatorvalidator.validate(person, bindingResult);
    if (bindingResult.hasErrors()) {
      return "people/new";
    }
    peopleService.save(person);
    return "redirect:/people";
  }

  @GetMapping("/{id}/edit")
  public String edit(@PathVariable("id") int id, Model model) {
    model.addAttribute("person", peopleService.show(id));
    return "people/edit";
  }

  @PatchMapping("/{id}")
  public String update(@ModelAttribute("person") @Valid Person person, BindingResult bindingResult,
      @PathVariable("id") int id) {
    if (bindingResult.hasErrors()) {
      return "people/edit";
    }
    personValidatorvalidator.validate(person, bindingResult);
    peopleService.update(id, person);
    return "redirect:/people";
  }

  @DeleteMapping("/{id}")
  public String delete(@PathVariable("id") int id) {
    peopleService.delete(id);
    System.out.println("deleted");
    return "redirect:/people";
  }

}
