package temple.edu.bookshelf;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.SQLOutput;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link BookListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BookListFragment extends Fragment {

    private BookList BookListF;
    private String key = "BookInfo";
    private BookListFragmentInterface tester;

    public BookListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            tester = (BookListFragmentInterface) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement BookListFragmentInterface");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        tester = null;
    }

    // TODO: Rename and change types and number of parameters
    public static BookListFragment newInstance(BookList BookListF) {
        BookListFragment fragment = new BookListFragment();
        Bundle args = new Bundle();
        args.putParcelable("BookInfo", BookListF);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            this.BookListF = getArguments().getParcelable(key);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_book_list, container, false);

        ListView lv = v.findViewById(R.id.bkListView);

        bookListViewAdapter adapter = new bookListViewAdapter(getActivity(), BookListF.getLibrary());
        lv.setAdapter(adapter);

        getActivity().setTitle(BookListF.getLibrary().get(0).getTitle());

        lv.setOnItemClickListener(new ListView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getActivity(), BookListF.getLibrary().get(position).getAuthor() + BookListF.getLibrary().get(position).getTitle(), Toast.LENGTH_LONG).show();
            }
        });

        return v;
    }
}
interface BookListFragmentInterface{
    public void displaySelection();
}