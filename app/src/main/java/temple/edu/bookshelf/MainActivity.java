package temple.edu.bookshelf;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.TextView;

import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.List;

public class MainActivity extends AppCompatActivity implements BookListFragmentInterface {

    BookList BookList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setUp();
        if(savedInstanceState==null) {
            BookListFragment BLF = BookListFragment.newInstance(BookList);

            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.add(R.id.main_frame,BLF, "BLF")
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
    public void displaySelection() {

    }
}