package temple.edu.bookshelf;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    BookList BookList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String[] bookTitleArr = getResources().getStringArray(R.array.BookTitles);
        String[] bookAuthArr = getResources().getStringArray(R.array.BookAuthors); //these two should be same size
        Book[] books = new Book[bookTitleArr.length];

        for(int i = 0; i < bookTitleArr.length; i++)
            books[i] = new Book(bookTitleArr[i], bookAuthArr[i]);

    }
}