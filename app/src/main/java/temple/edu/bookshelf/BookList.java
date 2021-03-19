package temple.edu.bookshelf;

import java.util.Arrays;
import java.util.LinkedList;

public class BookList {

    private LinkedList<Book> Library;
    private int size;

    public BookList(Book[] Library) {

        this.Library = new LinkedList<Book>();
        this.Library.addAll(Arrays.asList(Library)); // fill linked list

        this.size = Library.length;
    }
    public int getSize() {
        return size;
    }
    public LinkedList<Book> getLibrary() {
        return Library;
    }
}
class Book{
    private String title;
    private String author;

    public Book(String title, String author){
        this.title = title;
        this.author = author;
    }
    public String getTitle() {
        return title;
    }
    public String getAuthor() {
        return author;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public void setAuthor(String author) {
        this.author = author;
    }
}