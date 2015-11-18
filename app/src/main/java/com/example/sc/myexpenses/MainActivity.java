package com.example.sc.myexpenses;

import android.annotation.TargetApi;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import android.util.Log;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {



    @TargetApi(Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DatabaseHandler mydb = new DatabaseHandler(this);
        TextView mIncome=(TextView)findViewById(R.id.mIncome);
        TextView mExpense=(TextView)findViewById(R.id.mExpense);
        TextView mBalance=(TextView)findViewById(R.id.mBalance);
        TextView tIncome = (TextView)findViewById(R.id.tIncome);
        TextView tExpense=(TextView)findViewById(R.id.tExpense);
        TextView tBalance=(TextView)findViewById(R.id.tBalance);


        String monthlyIncome = mydb.getMIncome();
        String monthlyExpense = mydb.getMExpense();
        String totalIncome = mydb.getTIncome();
        String totalExpense = mydb.getTExpense();

        Log.d("ADebugTag", "Value of monthly income: " + monthlyIncome);
        Log.d("ADebugTag", "Value of monthly expense: " + monthlyExpense);
        Log.d("ADebugTag", "Value of total income: " + totalIncome);
        Log.d("ADebugTag", "Value of total expense: " + totalExpense);


        //set monthly income expense & balance from the database
        if(Objects.equals(monthlyIncome, null) && Objects.equals(monthlyExpense, null)){
            mIncome.setText("0");
            mExpense.setText("0");
            mBalance.setText("0");
        }else{
            if(Objects.equals(monthlyIncome, null)){
                mIncome.setText("0");
                mExpense.setText(monthlyExpense);
                double bal = 0-Double.parseDouble(monthlyExpense);
                mBalance.setText(String.valueOf(bal));
            }else{
                if(Objects.equals(monthlyExpense, null)){
                    mIncome.setText(monthlyIncome);
                    mExpense.setText("0");
                    double bal = Double.parseDouble(monthlyIncome);
                    mBalance.setText(String.valueOf(bal));
                }else{
                    mIncome.setText(monthlyIncome);
                    mExpense.setText(monthlyExpense);
                    double bal = Double.parseDouble(monthlyIncome)-Double.parseDouble(monthlyExpense);
                    mBalance.setText(String.valueOf(bal));
                }
            }
        }

        //set total income expense & balance from the database
        if(Objects.equals(totalIncome, null) && Objects.equals(totalExpense, null)){
            tIncome.setText("0");
            tExpense.setText("0");
            tBalance.setText("0");
        }else{
            if(Objects.equals(totalIncome, null)){
                tIncome.setText("0");
                tExpense.setText(totalExpense);
                double bal = 0-Double.parseDouble(totalExpense);
                tBalance.setText(String.valueOf(bal));
            }else{
                if(Objects.equals(totalExpense, null)){
                    tIncome.setText(totalIncome);
                    tExpense.setText("0");
                    double bal = Double.parseDouble(totalIncome)-0;
                    tBalance.setText(String.valueOf(bal));
                }else{
                    tIncome.setText(totalIncome);
                    tExpense.setText(totalExpense);
                    double bal = Double.parseDouble(totalIncome)-Double.parseDouble(totalExpense);
                    tBalance.setText(String.valueOf(bal));
                }
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void onIncome(View view) {
        Intent viewIncome = new Intent(this,Income.class);
        final int result=1;
        startActivityForResult(viewIncome, result);
    }

    public void onAbout(View view) {
        Intent viewAbout = new Intent(this,About.class);
        final int result=1;
        startActivityForResult(viewAbout, result);
    }

    public void onSettings(View view) {
        Intent viewSettings = new Intent(this,Settings.class);
        final int result=1;
        startActivityForResult(viewSettings, result);
    }

    public void onViewAll(View view) {
        Intent viewAll = new Intent(this,ViewAll.class);
        final int result=1;
        startActivityForResult(viewAll, result);
    }


    public void onFood(View view) {
        Intent viewExpense = new Intent(this,Expense.class);
        final int result=1;
        viewExpense.putExtra("ExtraCategory","Food");
        startActivityForResult(viewExpense, result);
    }

    public void onMedical(View view) {
        Intent viewExpense = new Intent(this,Expense.class);
        final int result=1;
        viewExpense.putExtra("ExtraCategory","Medical");
        startActivityForResult(viewExpense, result);
    }

    public void onTravel(View view) {
        Intent viewExpense = new Intent(this,Expense.class);
        final int result=1;
        viewExpense.putExtra("ExtraCategory","Travel");
        startActivityForResult(viewExpense, result);
    }

    public void onShopping(View view) {
        Intent viewExpense = new Intent(this,Expense.class);
        final int result=1;
        viewExpense.putExtra("ExtraCategory","Shopping");
        startActivityForResult(viewExpense, result);
    }

    public void onStationary(View view) {
        Intent viewExpense = new Intent(this,Expense.class);
        final int result=1;
        viewExpense.putExtra("ExtraCategory","Stationary");
        startActivityForResult(viewExpense, result);
    }

    public void onEntertain(View view) {
        Intent viewExpense = new Intent(this,Expense.class);
        final int result=1;
        viewExpense.putExtra("ExtraCategory","Entertainment");
        startActivityForResult(viewExpense, result);
    }
}
