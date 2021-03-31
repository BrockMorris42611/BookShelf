package temple.edu.bookshelf;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link BookListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BookListFragment extends Fragment {

    private BookList BookListF; //the list we need to display
    private String key = "BookInfo";
    private BookListFragmentInterface tester;
    bookListViewAdapter adapter;
    ListView lv;

    public BookListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            tester = (BookListFragmentInterface) context; //make sure we are operating on the context of a correct interface activity
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement BookListFragmentInterface");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        tester = null; //null the allocated tester from onAttach()
    }

    // TODO: Rename and change types and number of parameters
    public static BookListFragment newInstance(BookList BookListF) {
        BookListFragment fragment = new BookListFragment();
        Bundle args = new Bundle();
        args.putParcelable("BookInfo", BookListF); //instantiate with new BookList so we can display
        fragment.setArguments(args);
        System.out.println("NEWINSTANCE HERE: " + BookListF.toString());
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            this.BookListF = getArguments().getParcelable(key); //retrieve instantiate data
        }
    }

    public void updateBLFrag(BookList updatedBookList){
        BookListF = updatedBookList;
        adapter.bookArrayList = BookListF.getLibrary();
        adapter.notifyDataSetChanged();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_book_list, container, false);

        lv = v.findViewById(R.id.bkListView);

        adapter = new bookListViewAdapter(getActivity(), BookListF.getLibrary());
        lv.setAdapter(adapter);

        lv.setOnItemClickListener(new ListView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                tester.sendSelectionBack(position); //when the button is pressed call the activity implemented function sending
                                                    // the poition clicked back
            }
        });


        return v;
    }

    public interface BookListFragmentInterface {
        void sendSelectionBack(int sel); //this is the interface to be used by the function in main
    }
}