package ru.baysarov.bookregistry.util;

import java.time.Year;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.baysarov.bookregistry.model.Person;
import ru.baysarov.bookregistry.services.PeopleService;

@Component
public class PersonValidator implements Validator {

  private final PeopleService peopleService;

  @Autowired
  public PersonValidator( PeopleService peopleService) {
    this.peopleService = peopleService;
  }

  @Override
  public boolean supports(Class<?> aClass) {
    return Person.class.equals(aClass);
  }

  @Override
  public void validate(Object o, Errors errors) {
    Person person = (Person) o;

    if (peopleService.findByName(person.getFullName()).isPresent()) {
      errors.rejectValue("fullName", "error.person", "A person with this name already exists");
    }

    if (person.getYearOfBirth() != null) {
      int currentYear = Year.now().getValue();
      int personAge = currentYear - person.getYearOfBirth();
      if (personAge < 18) {
        errors.rejectValue("yearOfBirth", "", "You have to be 18 or older to get a book");
      }
    }

  }
}
