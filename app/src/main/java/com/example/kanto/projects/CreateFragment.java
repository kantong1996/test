package com.example.kanto.projects;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.Fragment;

import android.support.v4.app.FragmentTransaction;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;


public class CreateFragment extends Fragment implements View.OnClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private MyDBClass mDBClass;
    private SQLiteDatabase mDb;
    private Cursor mCursor_user;



    public CreateFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CreateFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CreateFragment newInstance(String param1, String param2) {
        CreateFragment fragment = new CreateFragment();
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
        View rootView = inflater.inflate(R.layout.fragment_create,container,false);
        Button upBtn = (Button) rootView.findViewById(R.id.button_up);
        Button downBtn = (Button) rootView.findViewById(R.id.button_down);
//        upBtn.setOnClickListener(new View.OnClickListener(){
//            @Override
//            public void onClick(View v){
//                FragmentManager fragmentManager = getFragmentManager();
//                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//                WeightUpFragment up = new WeightUpFragment();
//                fragmentTransaction.replace(R.id.fragment_container,up);
//            }
//        });
        upBtn.setOnClickListener(this);
        downBtn.setOnClickListener(this);
        return rootView;
    }

    @Override
    public void onClick(View v) {
        Fragment fragment = null;
        mDBClass = new MyDBClass(getContext());
        mDb = mDBClass.getWritableDatabase();
        mCursor_user = mDb.rawQuery("SELECT * FROM User ORDER BY USER_ID DESC LIMIT 1",null);
        if(mCursor_user.moveToFirst()){
        switch (v.getId()) {
            case R.id.button_up:
                fragment = new WeightUpFragment();
                replaceFragment(fragment);
                break;

            case R.id.button_down:
                fragment = new WeightDownFragment();
                replaceFragment(fragment);
                break;
            }
        }else{
            Toast.makeText(getActivity(),"ไม่มีข้อมูลผู้ใช้งาน",
                Toast.LENGTH_SHORT).show();
        }

    }
    public void replaceFragment(Fragment newFragment){
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container,newFragment);
//        transaction.addToBackStack(null);
        transaction.commit();
    }
}
