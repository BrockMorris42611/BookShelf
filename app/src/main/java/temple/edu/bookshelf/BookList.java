package temple.edu.bookshelf;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class BookList implements Parcelable {

    private ArrayList<Book> Library;

    public BookList(ArrayList<Book> Library) {

        this.Library = Library;
    }

    protected BookList(Parcel in) {
        Library = in.createTypedArrayList(Book.CREATOR);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(Library);
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

    public int getSize() { return Library.size(); }
    public ArrayList<Book> getLibrary() { return Library; }
    public void setLibrary(ArrayList<Book> library) { Library = library; }

    @Override
    public String toString() {
        return "BookList{" +
                "Library=" + Library;
    }
}
class Book implements Parcelable{

    private int id;
    private String title;
    private String author;
    private String coverURL;

    public Book(int id, String title, String author, String coverURL){
        this.id = id;
        this.title = title;
        this.author = author;
        this.coverURL = coverURL;
    }

    protected Book(Parcel in) {
        title = in.readString();
        author = in.readString();
        coverURL = in.readString();
        id = in.readInt();
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

    public int getId() { return id; }
    public String getCoverURL() { return coverURL; }
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
    public void setId(int id) { this.id = id; }
    public void setCoverURL(String coverURL) { this.coverURL = coverURL; }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(author);
        dest.writeString(coverURL);
        dest.writeInt(id);
    }

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", coverURL='" + coverURL + '\'' +
                '}';
    }
}