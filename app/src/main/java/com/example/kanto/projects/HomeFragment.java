package com.example.kanto.projects;


import android.app.Dialog;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.jaychang.srv.SimpleCell;
import com.jaychang.srv.SimpleRecyclerView;
import com.jaychang.srv.decoration.SectionHeaderProvider;
import com.jaychang.srv.decoration.SimpleSectionHeaderProvider;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;


public class HomeFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    private ArrayList<String> foodNameList = new ArrayList<String>();
    private ArrayList<String> KcalList = new ArrayList<String>();
    private ArrayList<Integer> Foodid = new ArrayList<>();
    private ArrayList<Double> Protein = new ArrayList<>();
    private ArrayList<Double> Fat = new ArrayList<>();
    private ArrayList<Double> Carb = new ArrayList<>();
    private ArrayList<Integer> MID = new ArrayList<>();

    public  SimpleRecyclerView simpleRecyclerView;

    private MyDBClass mDBClass;
    private SQLiteDatabase mDb;
    private Cursor Cursorfood;
    private Cursor List_id;

    SwipeRefreshLayout mSwipeRefreshLayout;






    public HomeFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View rootView = inflater.inflate(R.layout.fragment_home,container,false);
        simpleRecyclerView=rootView.findViewById(R.id.recyclerview);
        //header
        SectionHeaderProvider<Item> sh = new SimpleSectionHeaderProvider<Item>() {
            @NonNull
            @Override
            public View getSectionHeaderView(@NonNull Item item, int i) {
                View view = LayoutInflater.from(getContext()).inflate(R.layout.header_test, null, false);
                TextView textView =  view.findViewById(R.id.headerTxt);
                textView.setText(item.getCategoryName());
                return view;
            }

            @Override
            public boolean isSameSection(@NonNull Item food, @NonNull Item nextfood) {
                return food.getCategoryId() == nextfood.getCategoryId();
            }
            // Optional, whether the header is sticky, default false
            @Override
            public boolean isSticky() {
                return true;
            }
        };
        simpleRecyclerView.setSectionHeader(sh);

        mSwipeRefreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.swipe_container);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary,
                android.R.color.holo_green_dark,
                android.R.color.holo_orange_dark,
                android.R.color.holo_blue_dark);








        /**
         * Showing Swipe Refresh animation on activity create
         * As animation won't start on onCreate, post runnable is used
         */
        mSwipeRefreshLayout.post(new Runnable() {

            @Override
            public void run() {
                //
//                 Fetching data from server
                loadRecyclerViewData();
                mSwipeRefreshLayout.setRefreshing(false);
            }
        });




        return rootView;
    }
    @Override
    public void onRefresh() {
        mSwipeRefreshLayout.setRefreshing(false);
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.detach(this).attach(this).commit();
    }

    private void loadRecyclerViewData() {


        mSwipeRefreshLayout.setRefreshing(false);
        //bindData
        List<Item> items = null;
        if(items!=null){
            items.clear();
        }

        items = getData();
        if(items.size()<=0){
            Collections.sort(items, new Comparator<Item>(){
                public int compare(Item item, Item nextitem) {
                    return item.getCategoryId() - nextitem.getCategoryId();
                }
            });


        }
        //CUSTOM SORT ACCORDING TO CATEGORIES
        List<ItemCell> cells = new ArrayList<>();
        final FragmentManager manager = getFragmentManager();
        final DialogEdit dialog = new DialogEdit();
        final Bundle args = new Bundle();



//
//        LOOP THROUGH GALAXIES INSTANTIATING THEIR CELLS AND ADDING TO CELLS COLLECTION
        for (Item item : items) {
            ItemCell cell = new ItemCell(item);
//            Toast.makeText(getContext(),"Cell "+String.valueOf(cell), Toast.LENGTH_SHORT).show();
//             There are two default cell listeners: OnCellClickListener<CELL, VH, T> and OnCellLongClickListener<CELL, VH, T>
            cell.setOnCellClickListener(new SimpleCell.OnCellClickListener<Item>() {
                @Override
                public void onCellClicked(@NonNull Item item) {
                    args.putInt("MID",item.getMID());
                    args.putDouble("Protien",item.getProtien());
                    args.putDouble("Fat",item.getFat());
                    args.putDouble("Carb",item.getCarbohydate());
                    args.putInt("FID",item.getFoodID());
                    args.putString("KCAL", item.getKcal());
                    dialog.setArguments(args);
                    dialog.show(manager,"DialogEdit");
//
                }
            });
            cells.add(cell);
        }
        simpleRecyclerView.addCells(cells);
    }


    private ArrayList<Item> getData()
    {
        ArrayList<Item> items=new ArrayList<>();

        //CREATE CATEGORIES
        mDBClass = new MyDBClass(getContext());
        mDb = mDBClass.getWritableDatabase();
        List_id = mDb.rawQuery("SELECT * FROM List ORDER BY LID DESC LIMIT 1 ",null);
        List_id.moveToFirst();
        if (List_id==null){

        }else{
            int Lid = List_id.getInt(List_id.getColumnIndex("LID"));
            Cursorfood = mDb.rawQuery("SELECT F.FID,F.FName,F.KCAL,F.PROTEIN,F.FAT,F.CARBOHYDRATE,M.MID FROM Management M,Food F,List L Where F.FID = M.FID AND L.LID = "+Lid+" AND L.LID = M.LID ",null);
            Cursorfood.moveToFirst();
        }
        Item f;
        if(Cursorfood==null){

        }else {

            while (!Cursorfood.isAfterLast()) {
                foodNameList.add(Cursorfood.getString(Cursorfood.getColumnIndex("FName")));
                KcalList.add(Cursorfood.getString(Cursorfood.getColumnIndex("KCAL")));
                Foodid.add(Cursorfood.getInt(Cursorfood.getColumnIndex("FID")));
                Protein.add(Cursorfood.getDouble(Cursorfood.getColumnIndex("PROTEIN")));
                Fat.add(Cursorfood.getDouble(Cursorfood.getColumnIndex("FAT")));
                Carb.add(Cursorfood.getDouble(Cursorfood.getColumnIndex("CARBOHYDRATE")));
                MID.add(Cursorfood.getInt(Cursorfood.getColumnIndex("MID")));
                Cursorfood.moveToNext();
            }
        }
        if(foodNameList.size()<=0){

        }else {
            Category day1 = new Category(0, "วันที่ 1");
            Category day2 = new Category(1, "วันที่ 2");
            Category day3 = new Category(2, "วันที่ 3");
            Category day4 = new Category(3, "วันที่ 4");
            Category day5 = new Category(4, "วันที่ 5");
            Category day6 = new Category(5, "วันที่ 6");
            Category day7 = new Category(6, "วันที่ 7");
            String[] titlefood = {
                    "เช้า        : ",
                    "กลางวัน : ",
                    "เย็น        : ",
                    "มื้อเพิ่มเติม1  : ",
                    "มื้อเพิ่มเติม2  : "
            };
            String[] titlefood2 = {
                    "เช้า        : ",
                    "กลางวัน : ",
                    "เย็น        : ",
                    "ผลไม้  : "
            };
            Double total = 0.0;
            int j = 1;
            if (foodNameList.size() > 28) {
                for (int i = 0; i < foodNameList.size(); i++) {
                    if (i == 0) {
                        j = 0;
                    } else if (i % 5 == 0) {
                        j++;
                    }
                    if (j == 0) {
                        f = new Item(Foodid.get(i), titlefood[i % 5] + String.valueOf(foodNameList.get(i)), String.valueOf(KcalList.get(i)), Protein.get(i), Fat.get(i), Carb.get(i), MID.get(i), day1);
                        total += Double.valueOf(KcalList.get(i));
                        items.add(f);
                    } else if (j == 1) {
                        f = new Item(Foodid.get(i), titlefood[i % 5] + String.valueOf(foodNameList.get(i)), String.valueOf(KcalList.get(i)), Protein.get(i), Fat.get(i), Carb.get(i), MID.get(i), day2);
                        total += Double.valueOf(KcalList.get(i));
                        items.add(f);
                    } else if (j == 2) {
                        f = new Item(Foodid.get(i), titlefood[i % 5] + String.valueOf(foodNameList.get(i)), String.valueOf(KcalList.get(i)), Protein.get(i), Fat.get(i), Carb.get(i), MID.get(i), day3);
                        total += Double.valueOf(KcalList.get(i));
                        items.add(f);
                    } else if (j == 3) {
                        f = new Item(Foodid.get(i), titlefood[i % 5] + String.valueOf(foodNameList.get(i)), String.valueOf(KcalList.get(i)), Protein.get(i), Fat.get(i), Carb.get(i), MID.get(i), day4);
                        total += Double.valueOf(KcalList.get(i));
                        items.add(f);
                    } else if (j == 4) {
                        f = new Item(Foodid.get(i), titlefood[i % 5] + String.valueOf(foodNameList.get(i)), String.valueOf(KcalList.get(i)), Protein.get(i), Fat.get(i), Carb.get(i), MID.get(i), day5);
                        total += Double.valueOf(KcalList.get(i));
                        items.add(f);
                    } else if (j == 5) {
                        f = new Item(Foodid.get(i), titlefood[i % 5] + String.valueOf(foodNameList.get(i)), String.valueOf(KcalList.get(i)), Protein.get(i), Fat.get(i), Carb.get(i), MID.get(i), day6);
                        total += Double.valueOf(KcalList.get(i));
                        items.add(f);
                    } else if (j == 6) {
                        f = new Item(Foodid.get(i), titlefood[i % 5] + String.valueOf(foodNameList.get(i)), String.valueOf(KcalList.get(i)), Protein.get(i), Fat.get(i), Carb.get(i), MID.get(i), day7);
                        total += Double.valueOf(KcalList.get(i));
                        items.add(f);
                    }
                }
            }else if(foodNameList.size()<=28){
                for (int i = 0; i < foodNameList.size(); i++) {
                    if (i == 0) {
                        j = 0;
                    } else if (i % 4 == 0) {
                        j++;
                    }
                    if (j == 0) {
                        f = new Item(Foodid.get(i), titlefood2[i % 4] + String.valueOf(foodNameList.get(i)), String.valueOf(KcalList.get(i)), Protein.get(i), Fat.get(i), Carb.get(i), MID.get(i), day1);
                        total += Double.valueOf(KcalList.get(i));
                        items.add(f);
                    } else if (j == 1) {
                        f = new Item(Foodid.get(i), titlefood2[i % 4] + String.valueOf(foodNameList.get(i)), String.valueOf(KcalList.get(i)), Protein.get(i), Fat.get(i), Carb.get(i), MID.get(i), day2);
                        total += Double.valueOf(KcalList.get(i));
                        items.add(f);
                    } else if (j == 2) {
                        f = new Item(Foodid.get(i), titlefood2[i % 4] + String.valueOf(foodNameList.get(i)), String.valueOf(KcalList.get(i)), Protein.get(i), Fat.get(i), Carb.get(i), MID.get(i), day3);
                        total += Double.valueOf(KcalList.get(i));
                        items.add(f);
                    } else if (j == 3) {
                        f = new Item(Foodid.get(i), titlefood2[i % 4] + String.valueOf(foodNameList.get(i)), String.valueOf(KcalList.get(i)), Protein.get(i), Fat.get(i), Carb.get(i), MID.get(i), day4);
                        total += Double.valueOf(KcalList.get(i));
                        items.add(f);
                    } else if (j == 4) {
                        f = new Item(Foodid.get(i), titlefood2[i % 4] + String.valueOf(foodNameList.get(i)), String.valueOf(KcalList.get(i)), Protein.get(i), Fat.get(i), Carb.get(i), MID.get(i), day5);
                        total += Double.valueOf(KcalList.get(i));
                        items.add(f);
                    } else if (j == 5) {
                        f = new Item(Foodid.get(i), titlefood2[i % 4] + String.valueOf(foodNameList.get(i)), String.valueOf(KcalList.get(i)), Protein.get(i), Fat.get(i), Carb.get(i), MID.get(i), day6);
                        total += Double.valueOf(KcalList.get(i));
                        items.add(f);
                    } else if (j == 6) {
                        f = new Item(Foodid.get(i), titlefood2[i % 4] + String.valueOf(foodNameList.get(i)), String.valueOf(KcalList.get(i)), Protein.get(i), Fat.get(i), Carb.get(i), MID.get(i), day7);
                        total += Double.valueOf(KcalList.get(i));
                        items.add(f);
                    }
                }
            }
        }
        foodNameList.clear();
        KcalList.clear();
        Foodid.clear();
        Protein.clear();
        Fat.clear();
        Carb.clear();
        return items;
    }



}
