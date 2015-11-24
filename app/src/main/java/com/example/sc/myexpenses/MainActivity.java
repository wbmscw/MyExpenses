package com.example.sc.myexpenses;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
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
            mIncome.setText("0.0");
            mExpense.setText("0.0");
            mBalance.setText("0.0");
        }else{
            if(Objects.equals(monthlyIncome, null)){
                mIncome.setText("0.0");
                mExpense.setText(monthlyExpense);
                double bal = 0-Double.parseDouble(monthlyExpense);
                mBalance.setText(String.valueOf(bal));
            }else{
                if(Objects.equals(monthlyExpense, null)){
                    double inc=Double.parseDouble(monthlyIncome);
                    mIncome.setText(String.valueOf(inc));
                    mExpense.setText("0.0");
                    mBalance.setText(String.valueOf(inc));
                }else{
                    double inc=Double.parseDouble(monthlyIncome);
                    double ex=Double.parseDouble(monthlyExpense);
                    mIncome.setText(String.valueOf(inc));
                    mExpense.setText(String.valueOf(ex));
                    mBalance.setText(String.valueOf(inc-ex));
                }
            }
        }

        //set total income expense & balance from the database
        if(Objects.equals(totalIncome, null) && Objects.equals(totalExpense, null)){
            tIncome.setText("0.0");
            tExpense.setText("0.0");
            tBalance.setText("0.0");
        }else{
            if(Objects.equals(totalIncome, null)){
                tIncome.setText("0.0");
                tExpense.setText(totalExpense);
                double bal = 0-Double.parseDouble(totalExpense);
                tBalance.setText(String.valueOf(bal));
            }else{
                if(Objects.equals(totalExpense, null)){
                    double inc=Double.parseDouble(totalIncome);
                    tIncome.setText(String.valueOf(inc));
                    tExpense.setText("0.0");
                    tBalance.setText(String.valueOf(inc));
                }else{
                    double inc=Double.parseDouble(totalIncome);
                    double ex=Double.parseDouble(totalExpense);
                    tIncome.setText(String.valueOf(inc));
                    tExpense.setText(String.valueOf(ex));
                    tBalance.setText(String.valueOf(inc-ex));
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

    public void onIncome(android.view.View view) {
        Intent viewIncome = new Intent(this,Income.class);
        final int result=1;
        startActivityForResult(viewIncome, result);
    }

    public void onAbout(android.view.View view) {
        Intent viewAbout = new Intent(this,About.class);
        final int result=1;
        startActivityForResult(viewAbout, result);
    }

    public void onSettings(android.view.View view) {
        Intent viewSettings = new Intent(this,Settings.class);
        final int result=1;
        startActivityForResult(viewSettings, result);
    }

    public void onViewAll(android.view.View view) {
        Intent viewAll = new Intent(this,View.class);
        final int result=1;
        startActivityForResult(viewAll, result);
    }


    public void onFood(android.view.View view) {
        Intent viewExpense = new Intent(this,Expense.class);
        final int result=1;
        viewExpense.putExtra("ExtraCategory","Food");
        startActivityForResult(viewExpense, result);
    }

    public void onMedical(android.view.View view) {
        Intent viewExpense = new Intent(this,Expense.class);
        final int result=1;
        viewExpense.putExtra("ExtraCategory","Medical");
        startActivityForResult(viewExpense, result);
    }

    public void onTravel(android.view.View view) {
        Intent viewExpense = new Intent(this,Expense.class);
        final int result=1;
        viewExpense.putExtra("ExtraCategory","Travel");
        startActivityForResult(viewExpense, result);
    }

    public void onShopping(android.view.View view) {
        Intent viewExpense = new Intent(this,Expense.class);
        final int result=1;
        viewExpense.putExtra("ExtraCategory","Shopping");
        startActivityForResult(viewExpense, result);
    }

    public void onStationary(android.view.View view) {
        Intent viewExpense = new Intent(this,Expense.class);
        final int result=1;
        viewExpense.putExtra("ExtraCategory","Stationary");
        startActivityForResult(viewExpense, result);
    }

    public void onEntertain(android.view.View view) {
        Intent viewExpense = new Intent(this,Expense.class);
        final int result=1;
        viewExpense.putExtra("ExtraCategory","Entertainment");
        startActivityForResult(viewExpense, result);
    }
}
