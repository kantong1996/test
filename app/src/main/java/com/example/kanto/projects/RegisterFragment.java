package com.example.kanto.projects;


import android.app.Dialog;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RegisterFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RegisterFragment extends DialogFragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private CheckBox sea_food;
    private CheckBox nut_food;
    private CheckBox milks;
    private double TDEE;
    private double BMR;
    private double LBM;
    private double Weightfat;
    double percen;
    int sea;
    int nut;
    int milk;
    private EditText edit_name,edit_age,edit_weight,edit_high,edit_waistline,edit_wrist,edit_arm,edit_hips;
    private RadioGroup radiosexGroup;
    private RadioGroup radioexercise;
    private RadioButton radiosexbutton;
    private RadioButton man;
    private RadioButton exercise;
    private RadioButton radioexercisebutton;
    private String valuesex;
    int value2;



    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment RegisterFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static RegisterFragment newInstance(String param1, String param2) {
        RegisterFragment fragment = new RegisterFragment();
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
        final View rootview = inflater.inflate(R.layout.fragment_register, container, false);
        final MyDBClass myDBClass = new MyDBClass(getActivity());

        edit_name = (EditText)rootview.findViewById(R.id.edit_name);
        edit_age = (EditText) rootview.findViewById(R.id.edit_age);
        edit_weight = (EditText) rootview.findViewById(R.id.edit_weight);
        edit_high = (EditText) rootview.findViewById(R.id.edit_high);
        edit_waistline = (EditText) rootview.findViewById(R.id.edit_waistline);
        edit_wrist = (EditText) rootview.findViewById(R.id.edit_wrist);
        edit_arm = (EditText) rootview.findViewById(R.id.edit_arm);
        edit_hips = (EditText) rootview.findViewById(R.id.edit_hips);
        radiosexGroup = (RadioGroup) rootview.findViewById(R.id.radioGroup);
        radioexercise = (RadioGroup) rootview.findViewById(R.id.radioexercise);
        sea_food = (CheckBox) rootview.findViewById(R.id.sea_food);
        nut_food = (CheckBox) rootview.findViewById(R.id.nut);
        milks = (CheckBox) rootview.findViewById(R.id.milk);
        man = (RadioButton) rootview.findViewById(R.id.male);
        exercise = (RadioButton) rootview.findViewById(R.id.checkBox10);

        final int[] checkdata = new int[1];


        final Button save = (Button)rootview.findViewById(R.id.insert);
        save.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
                checkdata[0] = 0;
                int selectedId = radiosexGroup.getCheckedRadioButtonId();
                radiosexbutton = (RadioButton)rootview.findViewById(selectedId);
                if(radiosexbutton.getText().toString().equals("เพศชาย")){
                    valuesex = "1";

                }else if(radiosexbutton.getText().toString().equals("เพศหญิง")){
                    valuesex = "0";

                }

                if(valuesex.equals("1")){
                    if(edit_name.getText().toString().equals("") || edit_age.getText().toString().equals("") || edit_weight.getText().toString().equals("") || edit_high.getText().toString().equals("") || edit_waistline.getText().toString().equals("")){
                        Toast.makeText(getActivity(),"กรอกข้อมูลไม่ครบ",
                                Toast.LENGTH_LONG).show();
                    }else{
                        checkdata[0] = 1;
                        BMR = 66+(13.7* (Double.parseDouble(edit_weight.getText().toString())))+(5*(Double.parseDouble(edit_high.getText().toString())))-(6.8*(Double.parseDouble(edit_age.getText().toString())));
                        LBM = ((((Double.parseDouble(edit_weight.getText().toString()))*2.2)*1.082)+94.42)-((Double.parseDouble(edit_waistline.getText().toString()))*4.15);
                        Weightfat = ((Double.parseDouble(edit_weight.getText().toString()))*2.2)-LBM;
                        percen = (Weightfat*100)/((Double.parseDouble(edit_weight.getText().toString()))*2.2);
                    }
                }else if(valuesex.equals("0")){
                    if(edit_name.getText().toString().equals("") || edit_age.getText().toString().equals("") || edit_weight.getText().toString().equals("") || edit_high.getText().toString().equals("") || edit_waistline.getText().toString().equals("") || edit_wrist.getText().toString().equals("")|| edit_arm.getText().toString().equals("")|| edit_hips.getText().toString().equals("")){
                        Toast.makeText(getActivity(),"กรอกข้อมูลไม่ครบ",
                                Toast.LENGTH_LONG).show();
                    }else{
                        BMR = 655+(9.6* (Double.parseDouble(edit_weight.getText().toString())))+(1.8*(Double.parseDouble(edit_high.getText().toString())))-(4.7*(Double.parseDouble(edit_age.getText().toString())));
                        checkdata[0] = 1;
                        LBM = ((((Double.parseDouble(edit_weight.getText().toString()))*2.2)*0.732)+8.987)+((Double.parseDouble(edit_wrist.getText().toString()))/3.140)-((Double.parseDouble(edit_waistline.getText().toString()))*0.157)-((Double.parseDouble(edit_hips.getText().toString()))*0.249)+((Double.parseDouble(edit_arm.getText().toString()))*0.434);
                        Weightfat = ((Double.parseDouble(edit_weight.getText().toString()))*2.2)-LBM;
                        percen = (Weightfat*100)/((Double.parseDouble(edit_weight.getText().toString()))*2.2);
                    }
                }


                int selectedId2 = radioexercise.getCheckedRadioButtonId();
                radioexercisebutton = (RadioButton) rootview.findViewById(selectedId2);
                if (radioexercisebutton.getText().toString().equals("ออกกำลังกายน้อยมากหรือไม่ออกเลย")){
                    value2 = 0;
                    TDEE = 1.2*BMR;
                }
                else if (radioexercisebutton.getText().toString().equals("ออกกำลังกายเบา 1-3 ครั้งต่อสัปดาห์")){
                    value2 = 1;
                    TDEE = 1.375*BMR;
                }else if(radioexercisebutton.getText().toString().equals("ออกกำลังกายปานกลาง 3-5 ครั้งต่อสัปดาห์")){
                    value2 = 2;
                    TDEE = 1.55*BMR;
                }else if(radioexercisebutton.getText().toString().equals("ออกกำลังกายหนัก 5-7 ครั้งต่อสัปดาห์")){
                    value2 = 3;
                    TDEE = 1.79*BMR;
                }else if(radioexercisebutton.getText().toString().equals("ออกกำลังกายหนัก 2 ครั้งต่อวัน")){
                    value2 = 4;
                    TDEE = 1.9*BMR;
                }
               if(sea_food.isChecked()){
                    sea = 1;
                }
                else{
                    sea = 0;
                }
                if(nut_food.isChecked()){
                    nut = 1;
                }else{
                    nut = 0;
                }
                if (milks.isChecked()){
                    milk = 1;
                }else{
                    milk = 0;
                }

                if(checkdata[0] == 1){
                    long check = myDBClass.InsertData(edit_name.getText().toString().trim(),edit_age.getText().toString().trim(),
                            valuesex,edit_weight.getText().toString().trim(),edit_high.getText().toString().trim(),
                            edit_waistline.getText().toString().trim(),edit_wrist.getText().toString().trim(),
                            edit_arm.getText().toString().trim(),edit_hips.getText().toString().trim(),value2,BMR,TDEE,sea,nut,milk,"-",percen);
                    if(check > 0)
                    {
                        Toast.makeText(getActivity(),"กรอกข้อมูลสำเร็จ",
                                Toast.LENGTH_SHORT).show();
                        refreshf();
                    }else{
                        Toast.makeText(getActivity(),"กรอกข้อมูลไม่สำเร็จ",
                                Toast.LENGTH_SHORT).show();
                    }

                }
            }
        });


        return rootview;
    }
    public void refreshf() {
        man.setChecked(true);
        exercise.setChecked(true);
        edit_name.getText().clear();
        edit_weight.getText().clear();
        edit_age.getText().clear();
        edit_arm.getText().clear();
        edit_high.getText().clear();
        edit_hips.getText().clear();
        edit_waistline.getText().clear();
        edit_wrist.getText().clear();
        sea_food.setChecked(false);
        milks.setChecked(false);
        nut_food.setChecked(false);

    }

}
