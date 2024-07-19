package ru.baysarov.bookregistry.repositories;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.baysarov.bookregistry.model.Book;
import ru.baysarov.bookregistry.model.Person;

@Repository
public interface BooksRepository extends JpaRepository<Book, Integer> {

  List<Book> findBooksByOwnerId(int id);

  @Query("SELECT p FROM Person p JOIN p.books b WHERE b.id = :bookId")
  Optional<Person> findBookOwnerByBookId(@Param("bookId") int bookId);

  @Modifying
  @Query("update Book b set b.owner.id = ?1 where b.id = ?2")
  void assignBookToPerson(int personId, int bookId);

  @Modifying
  @Query("update Book b set b.owner.id = null where b.id = ?1")
  void removeBookFromPerson(int bookId);

  List<Book> findByTitleContaining(String query);
}
