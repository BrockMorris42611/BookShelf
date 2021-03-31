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

public class MainActivity extends AppCompatActivity implements BookListFragment.BookListFragmentInterface, BookDetailsFragment.BookDetailsFragmentInterface {

    BookList BookList_MainActivity; //init to size zero
    String restore_BookList = "RESTORE_LIST";

    BookListFragment BLF;
    BookDetailsFragment BDF;

    String BookL_FragTag = "BLF"; String BookL_BackTag = "ListB";
    String BookD_FragTag = "BDF"; String BookD_BackTag = "DetailB";



    FragmentManager fragmentManager;

    boolean split; //what viewing mode are we in?
    int selectionOnResetForDetailFrag; //hold our selection in case of context switch
    int REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        split = findViewById(R.id.detail_frame)!=null; //true when we can find the view(meaning we are in portrait) or false when we cant find the view(landscape or large)
        fragmentManager = getSupportFragmentManager();
        Button b = findViewById(R.id.lookUpButton);

        if(savedInstanceState==null) { //This is to verify that the fragment is added only once when the activity is created
            BookList_MainActivity = new BookList(new ArrayList<>());
            BLF = BookListFragment.newInstance(BookList_MainActivity);
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction
                    .add(R.id.main_frame,BLF, BookL_FragTag)
                    .setReorderingAllowed(true)
                    .addToBackStack(BookL_BackTag)
                    .commit();
        } else{ //if we are operating on a savedInstance that isnt null (i.e. context switch) then pop the back stack for the name ands see if exists
            boolean pop = fragmentManager.popBackStackImmediate(BookD_BackTag, FragmentManager.POP_BACK_STACK_INCLUSIVE);
            if(pop){ //if the frament type did exist then keep it but now load it into the main_fram frameview
                System.out.println("SWITCH "+ BookList_MainActivity.getLibrary().toString());
                BDF = BookDetailsFragment.newInstance(BookList_MainActivity.getLibrary().get(selectionOnResetForDetailFrag));
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction
                        .add(R.id.detail_frame,BDF, BookD_FragTag)
                        .setReorderingAllowed(true)
                        .addToBackStack(BookD_BackTag)
                        .commit();
            }
        }


        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, BookSearchActivity.class);

                startActivityForResult(intent, REQUEST_CODE);

            }
        });
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState, @NonNull PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);

    }

    @Override
    public void sendSelectionBack(int sel) {
        selectionOnResetForDetailFrag = sel;
        if(!split) { //when we are in portrait single view
            BDF = BookDetailsFragment.newInstance(BookList_MainActivity.getLibrary().get(sel));
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction
                    .replace(R.id.main_frame, BDF, BookD_FragTag)
                    .setReorderingAllowed(true)
                    .addToBackStack(BookD_BackTag)
                    .commit();
        }else{ //if we arent in the single screen then move the new selection to the other frame
            System.out.println(";dsflkghnstr;ngnr");
            BDF = BookDetailsFragment.newInstance(BookList_MainActivity.getLibrary().get(sel));
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction
                    .replace(R.id.detail_frame, BDF, BookD_FragTag)
                    .setReorderingAllowed(true)
                    .addToBackStack(BookD_BackTag)
                    .commit();
        }
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE && resultCode == 12 && data != null) {
            Bundle b = data.getExtras();
            if (b.getParcelable("BOOK_LIST") != null) {
                BookList_MainActivity = data.getParcelableExtra("BOOK_LIST");
                BLF.updateBLFrag(BookList_MainActivity);

                if(!split){
                    fragmentManager.beginTransaction()
                            .replace(R.id.main_frame, BLF, BookL_FragTag)
                            .setReorderingAllowed(true)
                            .addToBackStack(BookL_BackTag)
                            .commit();
                }
            } else {
                System.out.println(data.toString());
            }
        }
    }

    @Override
    public void display(Book b) {

    }
}