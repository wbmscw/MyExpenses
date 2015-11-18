package com.example.sc.myexpenses;

/**
 * Created by SC on 10/24/2015.
 */
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.webkit.ConsoleMessage;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.logging.Logger;


public class DatabaseHandler extends SQLiteOpenHelper{

    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "myExpense";

    // Income table name
    private static final String TABLE_INCOME = "income";
    private static final String TABLE_EXPENSE = "expense";

    // Income Table Columns names
    public static final String I_ID = "id";
    public static final String I_DATE = "idate";
    public static final String I_AMOUNT = "amount";
    public static final String I_DESCRIPTION = "description";

    //Expense Table Column names
    private static final String E_ID = "id";
    private static final String E_DATE = "idate";
    private static final String E_AMOUNT = "amount";
    private static final String E_CATEGORY = "category";
    private static final String E_ITEM = "iName";
    private static final String E_METHOD = "pMethod";
    private static final String E_DESCRIPTION = "description";

    String CREATE_INCOME = "CREATE TABLE " + TABLE_INCOME + "("
            + I_ID + " INTEGER PRIMARY KEY," + I_DATE + " DATE,"
            + I_AMOUNT + " DOUBLE," + I_DESCRIPTION + " TEXT" + ")";

    String CREATE_EXPENSE = "CREATE TABLE " + TABLE_EXPENSE + "("
            + E_ID + " INTEGER PRIMARY KEY," + E_DATE + " DATE,"
            + E_AMOUNT + " DOUBLE," + E_CATEGORY + " TEXT,"+ E_ITEM + " TEXT," + E_METHOD + " TEXT,"
            + E_DESCRIPTION + " TEXT" + ")";
    //private static final String CREATE_EXPENSE = "create table if not exists expense(id integer auto_increment,idate date,amount double(10,2) not null,iName varchar(30),pMethod varchar(30),description varchar(200),constraint e_pk primary key(id));";
    private static final String SELECT_ALL="SELECT * FROM ";
    private static final String TOTAL_INCOME="SELECT SUM(amount) FROM "+TABLE_INCOME;
    private static final String TOTAL_EXPENSE="SELECT SUM(amount) FROM "+TABLE_EXPENSE;

    private int year_x,month_x;

    private final Context context;

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(CREATE_INCOME);
        db.execSQL(CREATE_EXPENSE);

    }

    //Upgrade database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        Toast.makeText(context,"ON Upgrade",Toast.LENGTH_LONG).show();
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_INCOME);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_EXPENSE);

        // Create tables again
        onCreate(db);
    }

    /**
     ***All CRUD***
     **/

    //add data
    void addIncome(Income income) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(I_DATE, income.getDate());
        values.put(I_AMOUNT, income.getAmount());
        values.put(I_DESCRIPTION, income.getDesc());

        // Inserting Row
        db.insert(TABLE_INCOME, null, values);
        db.close(); // Closing database connection
    }
    void addExpense(Expense expense) {
        SQLiteDatabase db = this.getWritableDatabase();


        ContentValues contentValues = new ContentValues();
        contentValues.put(E_DATE, expense.getDate());
        contentValues.put(E_AMOUNT, expense.getAmount());
        contentValues.put(E_CATEGORY, expense.getCategory());
        contentValues.put(E_ITEM, expense.getname());
        contentValues.put(E_METHOD, expense.getMethod());
        contentValues.put(E_DESCRIPTION, expense.getDesc());

        // Inserting Row
        db.insert(TABLE_EXPENSE, null, contentValues);
        db.close(); // Closing database connection
    }
    /*public boolean insertDataExpense(String date, double amount,String iName, String pMethod, String description){
//idate date,amount double(6,2) not null, iName varchar, pMethod varchar, description varchar(200)
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(E_AMOUNT, amount);
        contentValues.put(E_DESCRIPTION, description);
        contentValues.put(E_DATE, date);
        contentValues.put(E_ITEM, iName);
        contentValues.put(E_METHOD, pMethod);

        long result = db.insert(TABLE_EXPENSE, null, contentValues);

        if(result==-1)
            return false;
        else
            return true;

    }*/


    public Cursor getAllIncomeData(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery(SELECT_ALL + TABLE_INCOME, null);
        return res;
    }
    public Cursor getAllExpenseData(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery(SELECT_ALL + TABLE_EXPENSE, null);
        return res;
    }


    //get single data
    Income getIncome(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_INCOME, new String[]{I_ID,
                        I_DATE, I_AMOUNT, I_DESCRIPTION}, I_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        Income income = new Income(Integer.parseInt(cursor.getString(0)),
                cursor.getString(1),
                Double.parseDouble(cursor.getString(2)), cursor.getString(3));
        // return income
        return income;
    }
    Expense getExpense(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_EXPENSE, new String[]{E_ID,
                        E_DATE, E_AMOUNT,E_ITEM,E_METHOD,E_CATEGORY, E_DESCRIPTION}, E_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        Expense expense  = new Expense(Integer.parseInt(cursor.getString(0)),
                cursor.getString(1),
                Double.parseDouble(cursor.getString(2)),cursor.getString(3),cursor.getString(4), cursor.getString(5),cursor.getString(6));
        // return income
        return expense;
    }

    //get all data
    public List<Income> getAllIncome() {
        List<Income> incomeList = new ArrayList<Income>();
        // Select All Query
        String selectQuery = SELECT_ALL + TABLE_INCOME;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Income income = new Income();
                income.setID(Integer.parseInt(cursor.getString(0)));
                income.setDate(cursor.getString(1));
                income.setAmount(Double.parseDouble(cursor.getString(2)));
                income.setDesc(cursor.getString(3));
                //Log.d("Reading: ", "Reading all income.."+cursor.getString(3));
                String name = "\nDate: "+cursor.getString(1) +"\n"+ "Amount: "+cursor.getString(2)+"\n";
                ListIncome.ArrayofIncome.add(name);

                // Adding income to list
                incomeList.add(income);
            } while (cursor.moveToNext());
        }

        // return contact list
        return incomeList;
    }
    public List<Expense> getAllExpense() {
        List<Expense> expenseList = new ArrayList<Expense>();
        // Select All Query
        String selectQuery = SELECT_ALL + TABLE_EXPENSE;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Expense expense = new Expense();
                expense.setID(Integer.parseInt(cursor.getString(0)));
                expense.setDate(cursor.getString(1));
                expense.setAmount(Double.parseDouble(cursor.getString(2)));
                expense.setCategory(cursor.getString(3));
                expense.setname(cursor.getString(4));
                expense.setMethod(cursor.getString(5));
                expense.setDesc(cursor.getString(6));
                //Log.d("Reading: ", "Reading all expense.....ID.." + cursor.getString(0));
                String name = "\nDate: "+cursor.getString(1) +"\n"+ "Amount: "+cursor.getString(2)
                        +"\n"+ "Category: "+cursor.getString(3)+"\n";
                ListExpense.ArrayofExpense.add(name);

                // Adding expense to list
                expenseList.add(expense);
            } while (cursor.moveToNext());
        }

        // return expense list
        return expenseList;
    }

    //get all total,monthly income expense
    public String getTIncome() {
        String tincome="";
        SQLiteDatabase database = this.getReadableDatabase();
        //String selectQuery = "SELECT sum(amount) FROM income";
        Cursor cursor = database.rawQuery(TOTAL_INCOME, null);
        if(cursor!=null && cursor.getCount()>0)
        {
            cursor.moveToFirst();
            do {
                tincome = cursor.getString(0);
            } while (cursor.moveToNext());
        }

        return tincome;
    }
    public String getTExpense() {
        String texpense="";
        SQLiteDatabase database = this.getReadableDatabase();
        //String selectQuery = "SELECT sum(amount) FROM expense";
        Cursor cursor = database.rawQuery(TOTAL_EXPENSE, null);
        if(cursor!=null && cursor.getCount()>0)
        {
            cursor.moveToFirst();
            do {
                texpense = cursor.getString(0);
            } while (cursor.moveToNext());
        }

        return texpense;
    }
    public String getMIncome() {
        final Calendar cal = Calendar.getInstance();
        year_x= cal.get(Calendar.YEAR);
        month_x= cal.get(Calendar.MONTH)+1;
        String date = year_x+"-"+month_x+"-";

        String mIncome="";
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery ="SELECT sum(amount) FROM income where idate like '"+date+"%'";
        //Log.d("ADebugTag", "Value of monthly income###########################################################: " + date);
        Cursor cursor = db.rawQuery(selectQuery, null);
        if(cursor!=null && cursor.getCount()>0)
        {
            cursor.moveToFirst();
            do {
                mIncome = cursor.getString(0);
            } while (cursor.moveToNext());
        }
        db.close();

        return mIncome;
    }
    public String getMExpense() {
        final Calendar cal = Calendar.getInstance();
        year_x= cal.get(Calendar.YEAR);
        month_x= cal.get(Calendar.MONTH)+1;
        String date = year_x+"-"+month_x+"-";

        String mExpense="";
        SQLiteDatabase database = this.getReadableDatabase();
        String selectQuery = "SELECT sum(amount) FROM expense where idate like '" + date + "%'";
        Cursor cursor = database.rawQuery(selectQuery, null);
        if(cursor!=null && cursor.getCount()>0)
        {
            cursor.moveToFirst();
            do {
                mExpense = cursor.getString(0);
            } while (cursor.moveToNext());
        }

        return mExpense;
    }

    //update data
    public long updateIncome(int rowId, String date,double amount, String desc) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(I_DATE, date);
        contentValues.put(I_AMOUNT, amount);
        contentValues.put(I_DESCRIPTION, desc);
        SQLiteDatabase db = this.getWritableDatabase();
        long val = db.update(TABLE_INCOME, contentValues,
                I_ID + "=" + rowId, null);
        db.close();
        return val;
    }

    //delete single data
    public int deleteOneIncome(int rowId) {
        SQLiteDatabase db = this.getWritableDatabase();
        int val = db.delete(TABLE_INCOME,
                I_ID + "=" + rowId, null);
        db.close();
        return val;
    }
    public int deleteOneExpense(int rowId) {
        SQLiteDatabase db = this.getWritableDatabase();
        int val = db.delete(TABLE_EXPENSE,
                I_ID + "=" + rowId, null);
        db.close();
        return val;
    }

    //delete all data
    public void deleteAll(){

        SQLiteDatabase database = this.getWritableDatabase();
        database.delete(TABLE_INCOME,null,null);
        database.delete(TABLE_EXPENSE, null, null);
    }
}
