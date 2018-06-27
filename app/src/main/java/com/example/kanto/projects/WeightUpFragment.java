package com.example.kanto.projects;


import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.round;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link WeightUpFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class WeightUpFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    ListView listView;
    ArrayAdapter<String> adapter;
    private MyDBClass mDBClass;
    private SQLiteDatabase mDb;
    private Cursor breakfast;
    private Cursor ex;
    private Cursor specail;
    private Cursor User_id;
    private Cursor lunch;
    private Cursor dinner;
    private Cursor mCursor_user;
    private Cursor List_id;
    private Cursor mCurupdate;



    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    public WeightUpFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment WeightUpFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static WeightUpFragment newInstance(String param1, String param2) {
        WeightUpFragment fragment = new WeightUpFragment();
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
        View rootView = inflater.inflate(R.layout.fragment_weight_up,container,false);
        listView = (ListView) rootView.findViewById(R.id.listup);
//
//        scores = Double.parseDouble(scoretext.getText().toString());
//
        mDBClass = new MyDBClass(getContext());
        mDb = mDBClass.getWritableDatabase();
        mCursor_user = mDb.rawQuery("SELECT * FROM User ORDER BY USER_ID DESC LIMIT 1",null);
        mCursor_user.moveToFirst();
        ArrayList<String> dirArray_breakfast = new ArrayList<String>();
        ArrayList<String> dirArray_lunch = new ArrayList<String>();
        ArrayList<String> dirArray_dinner = new ArrayList<String>();
        ArrayList<String> dirArray_ex = new ArrayList<String>();
        ArrayList<String> dirArray_specail = new ArrayList<String>();
        ArrayList<String> Meal_Plan = new ArrayList<String>();

        Double tdee = mCursor_user.getDouble(mCursor_user.getColumnIndex("TDEE"));
        Double w = mCursor_user.getDouble(mCursor_user.getColumnIndex("Weight"));
        Double bmr = mCursor_user.getDouble(mCursor_user.getColumnIndex("BMR"));
        int milk = mCursor_user.getInt(mCursor_user.getColumnIndex("INGREDIENTS_MILK"));
        int nuts = mCursor_user.getInt(mCursor_user.getColumnIndex("INGREDIENTS_NUT"));
        int sea  = mCursor_user.getInt(mCursor_user.getColumnIndex("INGREDIENTS_SEA"));
//        if(tdee+500 >= 3000){
//            tdee = tdee+300;
//        }else if(tdee+500 <= 3000){
//            tdee = tdee+500;
//        }
        tdee = tdee+500;//tdee+500


//        Toast.makeText(getActivity(),String.valueOf(nuts),
//                Toast.LENGTH_LONG).show();
        Double protien = 2.2*w;
        Double fat = 0.3*tdee;
        Double carbohydate =  0.45*tdee;
        String ingredient = "";
//        tdee = tdee-50;
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
//        Toast.makeText(getActivity(),ingredient,
//                Toast.LENGTH_LONG).show();

        breakfast = mDb.rawQuery("SELECT * FROM Food WHERE KCAL >="+bmr/4+" AND KCAL <="+tdee/4+" AND (TYPE = 1 OR TYPE = 4 OR TYPE = 7) AND PROTEIN < "+protien/4+" AND CARBOHYDRATE < "+carbohydate/4+" AND FAT < "+fat/4+""+ingredient+" ORDER BY RANDOM()LIMIT 7",null);
        breakfast.moveToFirst();
        lunch = mDb.rawQuery("SELECT * FROM Food WHERE KCAL <= "+tdee/4+" AND KCAL >="+bmr/4+" AND (TYPE = 2 OR TYPE = 5 OR TYPE = 7) AND PROTEIN < "+protien/4 +" AND CARBOHYDRATE < "+carbohydate/4+" AND FAT < "+fat/4+""+ingredient+" ORDER BY RANDOM()LIMIT 7",null);
        lunch.moveToFirst();
        dinner = mDb.rawQuery("SELECT * FROM Food WHERE KCAL <= "+tdee/4+" AND KCAL >="+bmr/4+" AND (TYPE = 3 OR TYPE = 5 OR TYPE = 7) AND PROTEIN < "+protien/4+"  AND CARBOHYDRATE < "+carbohydate/4+" AND FAT < "+fat/4+""+ingredient+" ORDER BY RANDOM()LIMIT 7",null);
        dinner.moveToFirst();
        ex = mDb.rawQuery("SELECT * FROM Food WHERE KCAL <= "+tdee/4+" AND KCAL >="+bmr/4+" AND (TYPE = 1 OR TYPE = 2 OR TYPE = 3 OR TYPE = 4 OR TYPE = 5 OR TYPE = 7) AND PROTEIN < "+protien/4+"  AND CARBOHYDRATE < "+carbohydate/4+" AND FAT < "+fat/4+""+ingredient+" ORDER BY RANDOM()LIMIT 7",null);
        ex.moveToFirst();

        ArrayList<Double> Totalb = new ArrayList<Double>();
        ArrayList<Double> Totall = new ArrayList<Double>();
        ArrayList<Double> Totald = new ArrayList<Double>();
        ArrayList<Double> Totale = new ArrayList<Double>();
        ArrayList<Double> Totals = new ArrayList<Double>();
        final ArrayList<Integer> Food_idm = new ArrayList<Integer>();
        final ArrayList<Integer> Food_idl = new ArrayList<Integer>();
        final ArrayList<Integer> Food_idd = new ArrayList<Integer>();
        final ArrayList<Integer> Food_ide = new ArrayList<Integer>();
        final ArrayList<Integer> Food_ids = new ArrayList<Integer>();
        final ArrayList<Integer> Food_id = new ArrayList<>();


        while (!breakfast.isAfterLast()) {
            dirArray_breakfast.add("เช้า        : "+breakfast.getString(breakfast.getColumnIndex("FName")) + "\n" + "พลังงาน: " + breakfast.getString(breakfast.getColumnIndex("KCAL")) + "\n");

//            ArrayAdapter<String> adapterDir = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, dirArray);
//            listView.setAdapter(adapterDir);
            Totalb.add(breakfast.getDouble(breakfast.getColumnIndex("KCAL")));
            Food_idm.add(breakfast.getInt(breakfast.getColumnIndex("FID")));
            breakfast.moveToNext();

        }

        while (!lunch.isAfterLast()){
            dirArray_lunch.add("กลางวัน: "+lunch.getString(lunch.getColumnIndex("FName")) + "\n" + "พลังงาน: " + lunch.getString(lunch.getColumnIndex("KCAL")) + "\n");
            Totall.add(lunch.getDouble(lunch.getColumnIndex("KCAL")));
            Food_idl.add(lunch.getInt(lunch.getColumnIndex("FID")));
            lunch.moveToNext();
        }

        while (!dinner.isAfterLast()){
            dirArray_dinner.add("เย็น       : "+dinner.getString(dinner.getColumnIndex("FName")) + "\n" + "พลังงาน: " + dinner.getString(dinner.getColumnIndex("KCAL")) + "\n");
            Totald.add(dinner.getDouble(dinner.getColumnIndex("KCAL")));
            Food_idd.add(dinner.getInt(dinner.getColumnIndex("FID")));
            dinner.moveToNext();
        }
        while (!ex.isAfterLast()){
            dirArray_ex.add("มื้อเพิ่มเติม1    : "+ex.getString(ex.getColumnIndex("FName")) + "\n" + "พลังงาน: " + ex.getString(ex.getColumnIndex("KCAL")) + "\n");
            Totale.add(ex.getDouble(ex.getColumnIndex("KCAL")));
            Food_ide.add(ex.getInt(ex.getColumnIndex("FID")));
            ex.moveToNext();
        }



        int i;

        final int[] fids = {0};
        Double sums = 0.0;
        final Double total;
        Double checks;
        for(i=0;i<7;i++){
            checks = (tdee)-(Totalb.get(i%dirArray_breakfast.size())+Totall.get(i%dirArray_lunch.size())+Totald.get(i%dirArray_dinner.size())+Totale.get(i%dirArray_ex.size()));
            specail = mDb.rawQuery("SELECT * FROM Food WHERE KCAL <= "+checks+" AND KCAL >= "+checks/5+" AND PROTEIN < "+protien/5+"  AND CARBOHYDRATE < "+carbohydate/5+" AND FAT < "+fat/5+""+ingredient+" ORDER BY RANDOM()LIMIT 1",null);
            specail.moveToFirst();
            dirArray_specail.add("มื้อเพิ่มเติม2     : "+specail.getString(specail.getColumnIndex("FName")) + "\n" + "พลังงาน: " + specail.getString(specail.getColumnIndex("KCAL")) + "\n");
            Totals.add(specail.getDouble(specail.getColumnIndex("KCAL")));
            Food_ids.add(specail.getInt(specail.getColumnIndex("FID")));
        }


//        Toast.makeText(getActivity(),String.valueOf(Food_id.size()),
//                       Toast.LENGTH_LONG).show();

        for(i=0;i<7;i++){
            Meal_Plan.add(dirArray_breakfast.get(i%dirArray_breakfast.size())); //ลอง mod ดู
            Meal_Plan.add(dirArray_lunch.get(i%dirArray_lunch.size()));
            Meal_Plan.add(dirArray_dinner.get(i%dirArray_dinner.size()));
            Meal_Plan.add(dirArray_ex.get(i%dirArray_ex.size()));
            Meal_Plan.add(dirArray_specail.get(i%dirArray_specail.size()));
            Meal_Plan.add("รวม: "+String.valueOf((Totalb.get(i%dirArray_breakfast.size())+Totall.get(i%dirArray_lunch.size())+Totald.get(i%dirArray_dinner.size())+Totale.get(i%dirArray_ex.size())+Totals.get(i%dirArray_specail.size())))+"\t\tอื่นๆ: "+String.format("%.2f",(tdee)-(Totalb.get(i%dirArray_breakfast.size())+Totall.get(i%dirArray_lunch.size())+Totald.get(i%dirArray_dinner.size())+Totale.get(i%dirArray_ex.size())+Totals.get(i%dirArray_specail.size()))));
            sums = sums + (Totalb.get(i%dirArray_breakfast.size())+Totall.get(i%dirArray_lunch.size())+Totald.get(i%dirArray_dinner.size())+Totale.get(i%dirArray_ex.size()));
        }
        total = sums/7;
        for(i=0;i<7;i++){
            Food_id.add(Food_idm.get(i%Food_idm.size())); //ลอง mod ดู
            Food_id.add(Food_idl.get(i%Food_idl.size()));
            Food_id.add(Food_idd.get(i%Food_idd.size()));
            Food_id.add(Food_ide.get(i%Food_ide.size()));
            Food_id.add(Food_ids.get(i%Food_ids.size()));
        }
//        Toast.makeText(getActivity(),String.valueOf(Food_id.size()),
//                       Toast.LENGTH_LONG).show();

        ArrayAdapter<String> adapterDir = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, Meal_Plan);
        listView.setAdapter(adapterDir);
        final long[] check = {0};
        final Button savelistup = (Button) rootView.findViewById(R.id.selectup);
        savelistup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mDBClass.InsertList();
                mDBClass = new MyDBClass(getContext());
                mDb = mDBClass.getWritableDatabase();
                List_id = mDb.rawQuery("SELECT * FROM List ORDER BY LID DESC LIMIT 1 ",null);
                List_id.moveToFirst();
                User_id = mDb.rawQuery("SELECT * FROM User ORDER BY USER_ID DESC LIMIT 1 ",null);
                User_id.moveToFirst();
                int uid = User_id.getInt(User_id.getColumnIndex("USER_ID"));
                int Lid = List_id.getInt(List_id.getColumnIndex("LID"));
                mCurupdate = mDb.rawQuery("update List set Total = "+total+" ,USER_ID = "+uid+" where LID = "+Lid+"",null);
                mCurupdate.moveToFirst();
                for (int i=0;i<Food_id.size();i++){
                    fids[0] = Food_id.get(i);
                    check[0] = mDBClass.InsertManagement(Lid, fids[0]);
                }
                if(check[0] >0){
                    Toast.makeText(getActivity(),"บันทึกรายการอาหารสำเร็จ",
                            Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(getActivity(),"บันทึกรายการอาหารไม่สำเร็จ",
                            Toast.LENGTH_SHORT).show();
                }

            }
        });

//        while (!mCursor_user.isAfterLast()){
//            dirArray.add(mCursor_user.getString(mCursor_user.getColumnIndex("Name")));
//            mCursor_user.moveToNext();
//            adapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1,dirArray);
//            listView.setAdapter(adapter);
//        }

        return rootView;
    }

}
