package com.example.kanto.projects;

/**
 * Created by kanto on 8/2/2018.
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


public class MyDBClass extends SQLiteOpenHelper {
    private  static  final int DATABASE_VERSION = 1;
    private  static  final String DATABASE_NAME = "mydatabase.sqlite";
    public final static String DATABASE_PATH = "/data/data/com.example.kanto.projects/databases/";
    private MyDBClass mHelper;
    private Context mContext;
    private SQLiteDatabase mDatabase;
    public MyDBClass(Context context) {
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
        this.mContext = context;
        boolean dbexist = checkDataBase();
        if(dbexist)
        {
            //System.out.println("Database exists");
            openDataBase();
        }
        else
        {
            System.out.println("Database doesn't exist");
            try {
                createDataBase();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void createDataBase() throws IOException {
        boolean mDataBaseExist = checkDataBase();
        if (!mDataBaseExist) {
            this.getReadableDatabase();
            try {
                copyDataBase();
            } catch (IOException mIOException) {
                mIOException.printStackTrace();
                throw new Error("Error copying database");
            } finally {
                this.close();
            }
        }else{
            openDataBase();
        }
    }

    /** This method checks whether database is exists or not **/
    private boolean checkDataBase() {
        try {
            final String mPath = DATABASE_PATH + DATABASE_NAME;
            final File file = new File(mPath);
            if (file.exists())
                return true;
            else
                return false;
        } catch (SQLiteException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * This method will copy database from /assets directory to application
     * package /databases directory
     **/
    private void copyDataBase() throws IOException {
        try {

            InputStream mInputStream = mContext.getAssets().open(DATABASE_NAME);
            String outFileName = DATABASE_PATH + DATABASE_NAME;
            OutputStream mOutputStream = new FileOutputStream(outFileName);
            byte[] buffer = new byte[1024];
            int length;
            while ((length = mInputStream.read(buffer)) > 0) {
                mOutputStream.write(buffer, 0, length);
            }
            mOutputStream.flush();
            mOutputStream.close();
            mInputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /** This method open database for operations **/
    public boolean openDataBase() throws SQLException {
        String mPath = DATABASE_PATH + DATABASE_NAME;
        mDatabase = SQLiteDatabase.openDatabase(mPath, null,
                SQLiteDatabase.OPEN_READWRITE);
        return mDatabase.isOpen();
    }

    /** This method close database connection and released occupied memory **/
    @Override
    public synchronized void close() {
        if (mDatabase != null)
            mDatabase.close();
        SQLiteDatabase.releaseMemory();
        super.close();
    }



    @Override
    public void onCreate(SQLiteDatabase db) {
//        db.execSQL("CREATE TABLE Food (FID INTEGER PRIMARY KEY AUTOINCREMENT,"+"FName TEXT,"+"KCAL DOUBLE,"+"PROTEIN DOUBLE,"+"FAT DOUBLE,"+"CARBOHYDRATE DOUBLE,"+"FIBRE DOUBLE,"+"TYPE DOUBLE,"+"INGREDIENT_SEA INT,INGREDIENT_NUT INT,INGREDIENT_MILK INT,WEIGHT DOUBLE);");
//        db.execSQL("CREATE TABLE User "+"(USER_ID INTEGER PRIMARY KEY AUTOINCREMENT,"+
//                "Name TEXT,"+"Age INTEGER,"+"SEX INTEGER,"+"Weight DOUBLE,"+
//                "HEIGHT DOUBLE,"+"waistline DOUBLE,"+"wrist DOUBLE,"+
//                "arm DOUBLE,"+"hips DOUBLE,score DOUBLE,INGREDIENTS_SEA INT,INGREDIENTS_NUT INT,INGREDIENTS_MILK INT,EXERCISE INT," +
//                "BMR DOUBLE,TDEE DOUBLE,DATE DATETIME,BODY_FAT INT);");
//        db.execSQL("CREATE TABLE Management (MID INTEGER PRIMARY KEY AUTOINCREMENT,LID INTEGER,FID INTEGER);");
//        db.execSQL("CREATE TABLE List (LID INTEGER PRIMARY KEY AUTOINCREMENT,LDATE TEXT,USER_ID INT);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

//    public MyDBClass open() throws SQLException {
//        Set up the helper with the context
//        mHelper = new MyDBClass (mContext);
//        Open the database with our helper
//        mDatabase = mHelper.getWritableDatabase();
//        return this;
//    }
    public long InsertData(String strName,String strAge,String strSex,String strWeight,
                           String strHEIGHT,String strwaistline,String strwrist,String strarm,String strhips,int exercise,double bmr,double tdee,int sea,int nut,int milk,String score,double percenfat){

        try{
            SQLiteDatabase db = this.getWritableDatabase();

            ContentValues Val = new ContentValues();
            Val.put("Name",strName);
            Val.put("Age",strAge);
            Val.put("SEX",strSex);
            Val.put("Weight",strWeight);
            Val.put("HEIGHT",strHEIGHT);
            Val.put("waistline",strwaistline);
            Val.put("wrist",strwrist);
            Val.put("arm",strarm);
            Val.put("hips",strhips);
            Val.put("EXERCISE",exercise);
            Val.put("BMR",bmr);
            Val.put("TDEE",tdee);
            Val.put("INGREDIENTS_SEA",sea);
            Val.put("INGREDIENTS_NUT",nut);
            Val.put("INGREDIENTS_MILK",milk);
            Val.put("DATE",new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
            Val.put("score",score);
            Val.put("BODY_FAT",percenfat);

            long rows = db.insert("User",null,Val);
            db.close();
            return rows;
        } catch(Exception e){
            return -1;
        }
    }
    public long InsertList(){
        try{
            SQLiteDatabase db = this.getWritableDatabase();

            ContentValues Val = new ContentValues();
            Val.put("LDATE",new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
            long rows = db.insert("List",null,Val);
            db.close();
            return rows;
        } catch(Exception e){
            return -1;
        }
    }
    public long InsertManagement(int list,int fid){
        try{
            SQLiteDatabase db = this.getWritableDatabase();

            ContentValues Val = new ContentValues();

//            for(int i=0;i<AFID.size();i++){
                Val.put("LID",list);
                Val.put("FID",fid);
//                Val.put("TOTAL",total);
                long rows = db.insert("Management",null,Val);
//            }
            db.close();
            return rows;
        } catch(Exception e){
            return -1;
        }


    }

}

