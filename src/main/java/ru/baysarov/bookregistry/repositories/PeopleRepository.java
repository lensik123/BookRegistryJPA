package ru.baysarov.bookregistry.repositories;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.baysarov.bookregistry.model.Person;

@Repository
public interface PeopleRepository extends JpaRepository<Person,Integer> {
  Optional<Person> findByFullName(String fullName);


  @Query("SELECT p FROM Person p JOIN p.books b WHERE b.owner.id = :personId")
  Optional<Person> findBookOwnerByBookId(@Param("personId") Integer personId);



}
