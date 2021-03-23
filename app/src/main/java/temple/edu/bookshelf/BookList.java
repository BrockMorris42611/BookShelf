package temple.edu.bookshelf;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;

public class BookList implements Parcelable {

    private ArrayList<Book> Library;
    private int size;

    public BookList(Book[] Library) {

        this.Library = new ArrayList<Book>();
        this.Library.addAll(Arrays.asList(Library)); // fill linked list

        this.size = Library.length;
    }

    protected BookList(Parcel in) {
        size = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(size);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<BookList> CREATOR = new Creator<BookList>() {
        @Override
        public BookList createFromParcel(Parcel in) {
            return new BookList(in);
        }

        @Override
        public BookList[] newArray(int size) {
            return new BookList[size];
        }
    };

    public int getSize() { return size; }
    public ArrayList<Book> getLibrary() { return Library; }
    public void setLibrary(ArrayList<Book> library) { Library = library; }
    public void setSize(int size) { this.size = size; }

    @Override
    public String toString() {
        return "BookList{" +
                "Library=" + Library +
                ", size=" + size +
                '}';
    }
}
class Book implements Parcelable{
    private String title;
    private String author;

    public Book(String title, String author){
        this.title = title;
        this.author = author;
    }

    protected Book(Parcel in) {
        title = in.readString();
        author = in.readString();
    }

    public static final Creator<Book> CREATOR = new Creator<Book>() {
        @Override
        public Book createFromParcel(Parcel in) {
            return new Book(in);
        }

        @Override
        public Book[] newArray(int size) {
            return new Book[size];
        }
    };

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(author);
    }

    @Override
    public String toString() {
        return "Book{" +
                "title='" + title + '\'' +
                ", author='" + author + '\'' +
                '}';
    }
}