package ru.baysarov.bookregistry.model;


import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotEmpty;


@Entity
@Table(name = "Book")
public class Book {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private int id;


  @Column(name = "title")
  @NotEmpty(message = "Title should not be empty")
  private String title;

  @Column(name = "author")
  @NotEmpty(message = "Author should not be empty")
  private String author;


  @ManyToOne
  @JoinColumn(name = "person_id", referencedColumnName = "id")
  private Person owner;

  @Column(name = "publish_year")
  private int year;

  @Transient
  private boolean isOverdue = false;

  @Column(name = "saved_at")
  private LocalDateTime savedAt;

  @Override
  public String toString() {
    return "Book{" +
        "id=" + id +
        ", title='" + title + '\'' +
        ", author='" + author + '\'' +
        ", year=" + year +
        '}';
  }

  public Book() {
  }

  public Book(int id, String title, String author, Person owner, int year) {
    this.id = id;
    this.title = title;
    this.author = author;
    this.owner = owner;
    this.year = year;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public @NotEmpty(message = "Title should not be empty") String getTitle() {
    return title;
  }

  public void setTitle(
      @NotEmpty(message = "Title should not be empty") String title) {
    this.title = title;
  }

  public @NotEmpty(message = "Author should not be empty") String getAuthor() {
    return author;
  }

  public void setAuthor(
      @NotEmpty(message = "Author should not be empty") String author) {
    this.author = author;
  }

  public Person getOwner() {
    return owner;
  }

  public void setOwner(Person owner) {
    this.owner = owner;
  }

  public int getYear() {
    return year;
  }

  public void setYear(int year) {
    this.year = year;
  }

  public LocalDateTime getSavedAt() {
    return savedAt;
  }

  public void setSavedAt(LocalDateTime savedAt) {
    this.savedAt = savedAt;
  }

  public boolean isOverdue() {
    return isOverdue;
  }

  public void setOverdue(boolean overdue) {
    isOverdue = overdue;
  }
}
