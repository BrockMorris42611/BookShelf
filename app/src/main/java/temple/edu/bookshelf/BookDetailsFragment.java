package temple.edu.bookshelf;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import static android.graphics.Color.RED;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link BookDetailsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BookDetailsFragment extends Fragment {


    private static final String key = "Book";
    private Book book; //the book we selected in the other fragment
    private BookDetailsFragmentInterface tester; //here for better memory reference

    public BookDetailsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            tester = (BookDetailsFragmentInterface) context; //are we working with an activity that implements our interface?
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement BookListFragmentInterface");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        tester = null; //null what we set earlier
    }

    // TODO: Rename and change types and number of parameters
    public static BookDetailsFragment newInstance(Book book) {
        BookDetailsFragment fragment = new BookDetailsFragment();
        Bundle args = new Bundle();
        args.putParcelable(key, book);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            book = getArguments().getParcelable(key); //get the arguments we set in the newInstance()
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_book_details, container, false);

        v.setBackgroundColor(RED);   // I want to show the difference between the two frames

        TextView tvTitle = v.findViewById(R.id.BookTitleTextView);
        TextView tvAuthor = v.findViewById(R.id.BookAuthorTextView);

        tvTitle.setText(book.getTitle()); //given the book we selected
        tvTitle.setTextSize(50);

        tvAuthor.setText(book.getAuthor());
        tvAuthor.setTextSize(20);

        return v;
    }
    public interface BookDetailsFragmentInterface {
        public void display(Book b);
    }
}