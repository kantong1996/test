package com.example.kanto.projects;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.Toast;

import java.util.ArrayList;


public class InformationFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    // TODO: Rename and change types of parameters

    private String mParam1;
    private String mParam2;
    private MyDBClass mDBClass;
    private SQLiteDatabase mDb;
    private Cursor mCursor;
    private Cursor mCurupdate;
    private ListView listView;
    private EditText scoretext;
    double scores;

    public InformationFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment InformationFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static InformationFragment newInstance(String param1, String param2) {
        InformationFragment fragment = new InformationFragment();
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
        View rootview = inflater.inflate(R.layout.fragment_information, container, false);
        listView = (ListView) rootview.findViewById(R.id.listinfomation);
        scoretext = (EditText)rootview.findViewById(R.id.textscore);
//        scores = Double.parseDouble(scoretext.getText().toString());
//
        mDBClass = new MyDBClass(getContext());
        mDb = mDBClass.getWritableDatabase();
        mCursor = mDb.rawQuery("SELECT * FROM User ORDER BY USER_ID DESC",null);
        ArrayList<String> dirArray = new ArrayList<String>();
        mCursor.moveToFirst();
        while (!mCursor.isAfterLast()) {
            String check = mCursor.getString(mCursor.getColumnIndex("SEX"));
            if(check.equals("0")){
                dirArray.add(mCursor.getString(mCursor.getColumnIndex("DATE"))+"\t\t"+"ชื่อ: "+ mCursor.getString(mCursor.getColumnIndex("Name")) + "\t\t" + "อายุ: " + mCursor.getString(mCursor.getColumnIndex("Age")) + "\n"
                + "น้ำหนัก: " + mCursor.getString(mCursor.getColumnIndex("Weight")) + "\t\t"
                + "ส่วนสูง: " + mCursor.getString(mCursor.getColumnIndex("HEIGHT")) + "\n" + "รอบเอว: " + mCursor.getString(mCursor.getColumnIndex("waistline"))+"\t\t\t\t\t\t"+"รอบข้อมือ: "+mCursor.getString(mCursor.getColumnIndex("wrist"))
                +"\t\t\t"+"รอบต้นแขน: "+mCursor.getString(mCursor.getColumnIndex("arm"))+"\n"+"รอบสะโพก: "+mCursor.getString(mCursor.getColumnIndex("hips"))+"\t\t"+"BMR: "+mCursor.getString(mCursor.getColumnIndex("BMR"))+"\t\t"+"TDEE: "+mCursor.getString(mCursor.getColumnIndex("TDEE"))+"\n"+"เปอร์เซ็นต์ไขมัน: "+String.format("%.2f",Double.valueOf(mCursor.getString(mCursor.getColumnIndex("BODY_FAT"))))+"%\t\t"+"คะแนนประเมินตนเอง: "+mCursor.getString(mCursor.getColumnIndex("score")));
                mCursor.moveToNext();
            }else if(check.equals("1")) {
                dirArray.add(mCursor.getString(mCursor.getColumnIndex("DATE"))+"\t\t"+"ชื่อ: "+mCursor.getString(mCursor.getColumnIndex("Name")) + "\t\t" + "อายุ: " + mCursor.getString(mCursor.getColumnIndex("Age")) + "\n"
                + "น้ำหนัก: " + mCursor.getString(mCursor.getColumnIndex("Weight")) + "\t\t"
                + "ส่วนสูง: " + mCursor.getString(mCursor.getColumnIndex("HEIGHT")) + "\n" + "รอบเอว: " + mCursor.getString(mCursor.getColumnIndex("waistline"))
                +"\t\t"+"BMR: "+mCursor.getString(mCursor.getColumnIndex("BMR"))+"\t\t"+"TDEE: "+mCursor.getString(mCursor.getColumnIndex("TDEE"))+"\n"+"เปอร์เซ็นต์ไขมัน: "+String.format("%.2f",Double.valueOf(mCursor.getString(mCursor.getColumnIndex("BODY_FAT"))))+"%\t\t"+"คะแนนประเมินตนเอง: "+mCursor.getString(mCursor.getColumnIndex("score")));
                mCursor.moveToNext();
            }
                ArrayAdapter<String> adapterDir = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, dirArray);
                listView.setAdapter(adapterDir);

            }
        final Button save = (Button)rootview.findViewById(R.id.buttonscore);
        save.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
                mDBClass = new MyDBClass(getContext());
                mDb = mDBClass.getWritableDatabase();
                mCursor = mDb.rawQuery("SELECT * FROM User ORDER BY USER_ID DESC LIMIT 1",null);

                if(mCursor.moveToFirst()){
                    String check = mCursor.getString(mCursor.getColumnIndex("USER_ID"));
                    mCurupdate = mDb.rawQuery("update User set score = "+scoretext.getText().toString()+" where USER_ID = "+check+"",null);
                    mCurupdate.moveToFirst();
                    scoretext.getText().clear();
                    onRefresh();
                }else{
                    Toast.makeText(getActivity(),"ไม่มีข้อมูลผู้ใช้งาน",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });

        return rootview;
    }
    public void onRefresh() {
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.detach(this).attach(this).commit();
    }
}
