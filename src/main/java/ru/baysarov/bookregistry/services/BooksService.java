package ru.baysarov.bookregistry.services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.baysarov.bookregistry.model.Book;
import ru.baysarov.bookregistry.model.Person;
import ru.baysarov.bookregistry.repositories.BooksRepository;
import ru.baysarov.bookregistry.repositories.PeopleRepository;

@Service
@Transactional
public class BooksService {

  private final BooksRepository booksRepository;
  private final PeopleRepository peopleRepository;

  @Autowired
  public BooksService(BooksRepository booksRepository, PeopleRepository peopleRepository) {
    this.booksRepository = booksRepository;
    this.peopleRepository = peopleRepository;
  }


  public List<Book> findAll(boolean sortByYear) {
    System.out.println(sortByYear);
    if (sortByYear) {
      return booksRepository.findAll(Sort.by("year"));
    } else {
      return booksRepository.findAll();
    }
  }

  public List<Book> findWithPagination(Integer page, Integer booksPerPage, boolean sortByYear) {
    if(sortByYear) {
      return booksRepository.findAll(PageRequest.of(page,booksPerPage,Sort.by("year"))).getContent();
    } else {
      return booksRepository.findAll(PageRequest.of(page,booksPerPage)).getContent();
    }
  }

  public List<Book> findBooksByOwnerId(int id) {
    return booksRepository.findBooksByOwnerId(id);
  }


  public Book show(int id) {
    Optional<Book> book = booksRepository.findById(id);
    return book.orElse(null);
  }

  public Optional<Person> getOwner(int id) {
    return booksRepository.findBookOwnerByBookId(id);
  }

  @Transactional
  public void save(Book book) {
    booksRepository.save(book);
  }

  @Transactional
  public void update(int id, Book book) {
    book.setId(id);
    booksRepository.save(book);
  }


  @Transactional
  public void delete(int id) {
    booksRepository.deleteById(id);
  }

  @Transactional
  public void assign(int personId, int bookId) {
    Book book = booksRepository.getOne(bookId);
    book.setOwner(peopleRepository.getOne(personId));
    book.setSavedAt(LocalDateTime.now());
    booksRepository.save(book);
  }

  @Transactional
  public void release(int bookId) {
    booksRepository.removeBookFromPerson(bookId);
  }

  public List<Book> searchByTitle(String query) {
    return booksRepository.findByTitleContaining(query);
  }

}
