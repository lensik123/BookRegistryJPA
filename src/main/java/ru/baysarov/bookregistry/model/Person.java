package ru.baysarov.bookregistry.model;


import java.time.Year;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.annotations.Cascade;


@Entity
@Table(name = "person")
public class Person {

  @Id
  @Column(name = "id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;

  @Column(name = "full_name")
  @NotEmpty(message = "Name should not be Empty")
  @Pattern(regexp = "^[А-ЯЁ][а-яё]+ [А-ЯЁ][а-яё]+ [А-ЯЁ][а-яё]+$", message = "Формат ФИО должен быть такой: Иван Иванов Иванович ")
  private String fullName;

  @Column(name = "year_of_birth")
  @NotNull(message = "Year of birth should not be empty")
  @Min(value = 1900, message = "Year should be no less than 1900")
  @Max(value = Year.MAX_VALUE, message = "Year should be in the valid range")
  private Integer yearOfBirth;

  @OneToMany(mappedBy = "owner")
  @Cascade(org.hibernate.annotations.CascadeType.ALL)
  List<Book> books;

  public Person() {
  }

  public Person(int id, String fullName, Integer yearOfBirth) {
    this.id = id;
    this.fullName = fullName;
    this.yearOfBirth = yearOfBirth;

  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public @NotEmpty(message = "Name should not be Empty") @Pattern(regexp = "^[А-ЯЁ][а-яё]+ [А-ЯЁ][а-яё]+ [А-ЯЁ][а-яё]+$", message = "Формат ФИО должен быть такой: Иван Иванов Иванович ") String getFullName() {
    return fullName;
  }

  public void setFullName(
      @NotEmpty(message = "Name should not be Empty") @Pattern(regexp = "^[А-ЯЁ][а-яё]+ [А-ЯЁ][а-яё]+ [А-ЯЁ][а-яё]+$", message = "Формат ФИО должен быть такой: Иван Иванов Иванович ") String fullName) {
    this.fullName = fullName;
  }

  public @NotNull(message = "Year of birth should not be empty") @Min(value = 1900, message = "Year should be no less than 1900") @Max(value = Year.MAX_VALUE, message = "Year should be in the valid range") Integer getYearOfBirth() {
    return yearOfBirth;
  }

  public void setYearOfBirth(
      @NotNull(message = "Year of birth should not be empty") @Min(value = 1900, message = "Year should be no less than 1900") @Max(value = Year.MAX_VALUE, message = "Year should be in the valid range") Integer yearOfBirth) {
    this.yearOfBirth = yearOfBirth;
  }

  public List<Book> getBooks() {
    return books;
  }

  public void setBooks(List<Book> books) {
    this.books = books;
  }

  @Override
  public String toString() {
    return "Person{" +
        "id=" + id +
        ", fullName='" + fullName + '\'' +
        ", yearOfBirth=" + yearOfBirth +
        '}';
  }

}
