package com.example.kanto.projects;

import android.app.Dialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import java.util.ArrayList;

public class DialogEdit extends DialogFragment {
    private MyDBClass mDBClass = new MyDBClass(getContext());
    private SQLiteDatabase mDb;
    private Cursor mCursor;
    private Cursor mCursor_user;
    private Cursor mCurupdate;
    public int edit = 0;
    ArrayList <String> Foodedit = new ArrayList<String>();
    ArrayList <String> clear = new ArrayList<String>();
    ArrayList <Integer> Foodid = new ArrayList<Integer>();
    ListView listView;
    Bundle mArgs;
    String data;
    Double Protien;
    Double Fat;
    Double Carb;
    int fid;
    int mid;

    ArrayAdapter<String> adapterDir;
    SwipeRefreshLayout mSwipeRefreshLayout;

    public DialogEdit(){

    }


    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.dialog_edit,container,false);

        listView = (ListView) rootView.findViewById(R.id.listedit);
        mArgs = getArguments();
        data = mArgs.getString("KCAL");
        fid = mArgs.getInt("FID");
        mid = mArgs.getInt("MID");
        Protien = mArgs.getDouble("Protien");
        Fat = mArgs.getDouble("Fat");
        Carb = mArgs.getDouble("Carb");
        mDBClass = new MyDBClass(getContext());
        mDb = mDBClass.getWritableDatabase();
        mCursor_user = mDb.rawQuery("SELECT * FROM User ORDER BY USER_ID DESC LIMIT 1", null);
        mCursor_user.moveToFirst();
        int milk = mCursor_user.getInt(mCursor_user.getColumnIndex("INGREDIENTS_MILK"));
        int nuts = mCursor_user.getInt(mCursor_user.getColumnIndex("INGREDIENTS_NUT"));
        int sea  = mCursor_user.getInt(mCursor_user.getColumnIndex("INGREDIENTS_SEA"));
        String ingredient = "";
        if(milk == 1){
            ingredient += " AND INGREDIENT_MILK != 1";
        }else{
            ingredient += "";
        }
        if(nuts == 1){
            ingredient += " AND INGREDIENT_NUT != 1";
        }else{
            ingredient += "";
        }
        if(sea == 1){
            ingredient += " AND INGREDIENT_SEA != 1";
        }else{
            ingredient += "";
        }
        mCursor = mDb.rawQuery("SELECT * FROM Food Where KCAL <= "+(Double.parseDouble(data)+100)+" AND KCAL >="+(Double.parseDouble(data)-150)+" AND PROTEIN <= "+(Protien+3)+" AND FAT <="+(Fat+3)+" AND CARBOHYDRATE <="+(Carb+4)+""+ingredient+"",null);
        mCursor.moveToFirst();
        if(adapterDir!=null){
            adapterDir.clear();
        }
        while (!mCursor.isAfterLast()){
            Foodedit.add(mCursor.getString(mCursor.getColumnIndex("FName"))+"\t\t"+mCursor.getString(mCursor.getColumnIndex("KCAL")));
            Foodid.add(mCursor.getInt(mCursor.getColumnIndex("FID")));
            mCursor.moveToNext();
        }
        final Bundle args = new Bundle();
        adapterDir = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, Foodedit);
        listView.setAdapter(adapterDir);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getContext(), "แก้ไขสำเร็จ", Toast.LENGTH_SHORT).show();
                getDialog().dismiss();
                mCurupdate = mDb.rawQuery("update Management set FID = "+Foodid.get(position)+" where FID = "+fid+" AND MID = "+mid+"",null);
                mCurupdate.moveToFirst();
                adapterDir.clear();
//                HomeFragment nextFrag= new HomeFragment();
//                getActivity().getSupportFragmentManager().beginTransaction()
//                        .replace(R.id.container, nextFrag,"Home")
//                        .commit();
            }
        });


        return rootView;
    }


}

