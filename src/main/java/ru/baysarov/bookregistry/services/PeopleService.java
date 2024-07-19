package ru.baysarov.bookregistry.services;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.baysarov.bookregistry.model.Book;
import ru.baysarov.bookregistry.model.Person;
import ru.baysarov.bookregistry.repositories.PeopleRepository;

@Service
@Transactional(readOnly = true)
public class PeopleService {

  private final PeopleRepository peopleRepository;

  @Autowired
  public PeopleService(PeopleRepository peopleRepository) {
    this.peopleRepository = peopleRepository;
  }

  public List<Person> findAll() {
    List<Person> personList = peopleRepository.findAll();
    return personList;
  }

  public Person show(int id) {
    Optional<Person> person = peopleRepository.findById(id);
    Hibernate.initialize(person.get().getBooks());

    LocalDateTime currentDate = LocalDateTime.now();
    for (Book book : person.get().getBooks()) {
      long daysBetween = ChronoUnit.DAYS.between(book.getSavedAt(), currentDate);
      if (daysBetween > 10){
        book.setOverdue(true);
      }
    }

    return person.orElse(null);
  }

  public Optional<Person> findByName(String name) {
    return peopleRepository.findByFullName(name);
  }

  public Optional<Person> isBookOwner(int personId) {
    return peopleRepository.findBookOwnerByBookId(personId);
  }

  @Transactional
  public void save(Person person) {
    peopleRepository.save(person);
  }

  @Transactional
  public void update(int id, Person updatedPerson) {
    updatedPerson.setId(id);
    peopleRepository.save(updatedPerson);
  }

  @Transactional
  public void delete(int id) {
    System.out.println("deleted2 " + id);
    peopleRepository.deleteById(id);
  }
}
