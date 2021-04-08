package temple.edu.bookshelf;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import edu.temple.audiobookplayer.AudiobookService;

public class MainActivity extends AppCompatActivity implements BookListFragment.BookListFragmentInterface, BookDetailsFragment.BookDetailsFragmentInterface {

    BookList BookList_MainActivity;
    String restoreBookList = "RESTORE_LIST";

    BookListFragment BLF;
    BookDetailsFragment BDF;
    FragmentManager fragmentManager;

    boolean split; //what viewing mode are we in?

    int selectionOnResetForDetailFrag; //hold our selection in case of context switch
    String restoreSelection = "RESTORE_SELEC";
    int REQUEST_CODE = 1;

    String BookL_FragTag = "BLF"; String BookL_BackTag = "ListB";
    String BookD_FragTag = "BDF"; String BookD_BackTag = "DetailB";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        split = findViewById(R.id.detail_frame)!=null; //true when we can find the view(meaning we are in portrait) or false when we cant find the view(landscape or large)
        Button start_Search = findViewById(R.id.lookUpButton);


        fragmentManager = getSupportFragmentManager();

        if(savedInstanceState==null) { //This is to verify that the fragment is added only once when the activity is created
            BookList_MainActivity = new BookList(new ArrayList<Book>());
            BLF = BookListFragment.newInstance(BookList_MainActivity);
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction
                    .add(R.id.main_frame,BLF, BookL_FragTag)
                    .setReorderingAllowed(true)
                    .addToBackStack(BookL_BackTag)
                    .commit();
        } else{ //if we are operating on a savedInstance that isnt null (i.e. context switch) then pop the back stack for the name ands see if exists
            selectionOnResetForDetailFrag = savedInstanceState.getInt(restoreSelection,0);
            BookList_MainActivity = savedInstanceState.getParcelable(restoreBookList);
            boolean pop = fragmentManager.popBackStackImmediate(BookD_BackTag, FragmentManager.POP_BACK_STACK_INCLUSIVE);
            if(pop){ //if the frament type did exist then keep it but now load it into the main_fram frameview

                int frame_based_on_split = R.id.main_frame;//when we are in portrait single view DEFAULT VALUE
                if(split) //if we arent in the single screen then move the new selection to the other frame
                    frame_based_on_split = R.id.detail_frame;

                BDF = BookDetailsFragment.newInstance(BookList_MainActivity.getLibrary().get(selectionOnResetForDetailFrag));
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction
                        .add(frame_based_on_split, BDF, BookD_FragTag)
                        .setReorderingAllowed(true)
                        .addToBackStack(BookD_BackTag)
                        .commit();
            }
        }

        start_Search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, BookSearchActivity.class);
                startActivityForResult(intent, REQUEST_CODE);
                if(split){ //THIS IS TO MAKE SURE THE NEW LIST APPEARS WITHOUT ANY OLD SELECTIONS SHOWING FOR POSSIBLE OUT OF BOUNDS ARRAY
                    fragmentManager.beginTransaction()
                            .remove(BDF).commit();
                }
            }
        });
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {

        outState.putParcelable(restoreBookList, BookList_MainActivity); //save for the changing of activities so we can remember what to display
        outState.putInt(restoreSelection, selectionOnResetForDetailFrag);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void sendSelectionBack(int sel) {

        selectionOnResetForDetailFrag = sel;
        int frame_based_on_split = R.id.main_frame;//when we are in portrait single view DEFAULT VALUE
        if(split) //if we arent in the single screen then move the new selection to the other frame
            frame_based_on_split = R.id.detail_frame;

        BDF = BookDetailsFragment.newInstance(BookList_MainActivity.getLibrary().get(sel));
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction
                .replace(frame_based_on_split, BDF, BookD_FragTag)
                .setReorderingAllowed(true)
                .addToBackStack(BookD_BackTag)
                .commit();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE && resultCode == 12 && data != null) { //check we are getting the right info back to the main activity form the secondary search
            Bundle b = data.getExtras();
            if (b.getParcelable("BOOK_LIST") != null) { //make sure the key exists via key val pair
                BookList_MainActivity = data.getParcelableExtra("BOOK_LIST");
                BLF= BookListFragment.newInstance(BookList_MainActivity);

                fragmentManager.beginTransaction() //after we get the new selection make the fragment and replace the current one main_frame
                        .replace(R.id.main_frame, BLF, BookL_FragTag)
                        .setReorderingAllowed(true)
                        .addToBackStack(BookL_BackTag)
                        .commit();

            } else {
                System.out.println(data.toString());
            }
        }
    }
    @Override
    public void display(Book b) {

    }
}