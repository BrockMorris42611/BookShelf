package temple.edu.bookshelf;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements BookListFragment.BookListFragmentInterface, BookDetailsFragment.BookDetailsFragmentInterface {

    BookList BookList_MainActivity;
    BookListFragment BLF;
    BookDetailsFragment BDF;
    FragmentManager fragmentManager;
    boolean split; //what viewing mode are we in?
    int selectionOnResetForDetailFrag; //hold our selection in case of context switch

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        split = findViewById(R.id.detail_frame)!=null; //true when we can find the view(meaning we are in portrait) or false when we cant find the view(landscape or large)

        BookList_MainActivity = new BookList(new ArrayList<>()); //init to size zero

        fragmentManager = getSupportFragmentManager();

        if(savedInstanceState==null) { //This is to verify that the fragment is added only once when the activity is created

            BLF = BookListFragment.newInstance(BookList_MainActivity);
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction
                    .add(R.id.main_frame,BLF, "BLF")
                    .setReorderingAllowed(true)
                    .addToBackStack("ListB")
                    .commit();
        } else{ //if we are operating on a savedInstance that isnt null (i.e. context switch) then pop the back stack for the name ands see if exists
            boolean pop = fragmentManager.popBackStackImmediate("BDF", FragmentManager.POP_BACK_STACK_INCLUSIVE);
            if(pop){ //if the frament type did exist then keep it but now load it into the main_fram frameview
                BDF = BookDetailsFragment.newInstance(BookList_MainActivity.getLibrary().get(selectionOnResetForDetailFrag));
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction
                        .add(R.id.main_frame,BDF, "BDF")
                        .setReorderingAllowed(true)
                        .addToBackStack("DetailB")
                        .commit();
            }
        }
    }

    @Override
    public void sendSelectionBack(BookList BookList, int sel) {
        BookList_MainActivity = BookList;
        selectionOnResetForDetailFrag = sel;
        if(!split) { //when we are in portrait single view
            BDF = BookDetailsFragment.newInstance(BookList.getLibrary().get(sel));
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction
                    .replace(R.id.main_frame, BDF, "BDF")
                    .setReorderingAllowed(true)
                    .addToBackStack("DetailB")
                    .commit();
        }else{ //if we arent in the single screen then move the new selection to the other frame
            BDF = BookDetailsFragment.newInstance(BookList.getLibrary().get(sel));
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction
                    .replace(R.id.detail_frame, BDF, "BDF")
                    .setReorderingAllowed(true)
                    .addToBackStack("BDF")
                    .commit();
        }
    }

    @Override
    public void display(Book b) {

    }
}