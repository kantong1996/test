package com.example.kanto.projects;


import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.app.FragmentManager;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SearchView;

import java.util.ArrayList;
import java.util.List;
import android.widget.SearchView.OnQueryTextListener;
import android.app.DialogFragment;




/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Search#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Search extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    MyDBClass mDBClass = new MyDBClass(getContext());
    SQLiteDatabase mDb;
    Cursor mCursor;

    ListView lstView;
    SearchView sv;

    public Search() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Search.
     */
    // TODO: Rename and change types and number of parameters
    public static Search newInstance(String param1, String param2) {
        Search fragment = new Search();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_search,container,false);
        lstView = (ListView) rootView.findViewById(R.id.list);
        sv = (SearchView) rootView.findViewById(R.id.searchView1);
        mDBClass = new MyDBClass(getContext());
        mDb = mDBClass.getWritableDatabase();
        mCursor = mDb.rawQuery("SELECT * FROM Food",null);
        mCursor.moveToFirst();
        final ArrayList<String> adapter = new ArrayList<String>();
        mCursor.moveToFirst();
        while (!mCursor.isAfterLast()) {
            adapter.add(mCursor.getString(mCursor.getColumnIndex("FName")) + "\t\t" + "พลังงาน: " + mCursor.getString(mCursor.getColumnIndex("KCAL")) + "\t\t");
                mCursor.moveToNext();
        }
        final ArrayAdapter<String> adapterDir = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, adapter);
        lstView.setAdapter(adapterDir);
        sv.setQueryHint("Search..");
        sv.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String text) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String txt) {
                adapterDir.getFilter().filter(txt);
                return false;
            }
        }
        );

        return rootView;
    }

}
