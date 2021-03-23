package temple.edu.bookshelf;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity implements BookListFragment.BookListFragmentInterface, BookDetailsFragment.BookDetailsFragmentInterface {

    BookList BookList;
    FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fragmentManager = getSupportFragmentManager();

        setUp();
        if(savedInstanceState==null) { //This is to verify that the fragment is added only once when the activity is created

            BookListFragment BLF = BookListFragment.newInstance(BookList);
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction
                    .add(R.id.main_frame,BLF, "BLF")
                    .setReorderingAllowed(true)
                    .addToBackStack("ListB")
                    .commit();
        }
    }
    public BookList setUp(){
        String[] bookTitleArr = getResources().getStringArray(R.array.BookTitles);
        String[] bookAuthArr = getResources().getStringArray(R.array.BookAuthors); //these two should be same size
        Book[] books = new Book[bookTitleArr.length];

        for (int i = 0; i < bookTitleArr.length; i++)
            books[i] = new Book(bookTitleArr[i], bookAuthArr[i]);
        BookList = new BookList(books);
        return BookList;
    }

    @Override
    public void sendSelectionBack(int sel) {
        BookDetailsFragment BDF = BookDetailsFragment.newInstance(BookList.getLibrary().get(sel));
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction
                .replace(R.id.main_frame, BDF, "BDF")
                .setReorderingAllowed(true)
                .addToBackStack("DetailB")
                .commit();
    }

    @Override
    public void display(Book b) {

    }
}