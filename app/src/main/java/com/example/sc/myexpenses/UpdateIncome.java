package com.example.sc.myexpenses;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.*;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by SC on 11/23/2015.
 */
public class UpdateIncome extends AppCompatActivity {

    String id;
    String date;
    double amount;
    String desc;
    DatabaseHandler mydb;

    Button deleteBtn;
    Button updateBtn;
    TextView idate;
    EditText iamount;
    EditText description;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.update_income);

        Intent intent = getIntent();
        id=intent.getExtras().getString("ExtraId");
        mydb = new DatabaseHandler(this);
        Log.d("Log::::::::::::::::: ", "Id=" + id);
        Income in = mydb.getIncome(Integer.parseInt(id));

        idate = (TextView)findViewById(R.id.EditDate);
        iamount = (EditText)findViewById(R.id.editTextAmount);
        description = (EditText)findViewById(R.id.editTextDescription);
        deleteBtn =(Button)findViewById(R.id.bDeleteIn);
        updateBtn =(Button)findViewById(R.id.bUpdateIn);

        amount=in.getAmount();
        iamount.setText(String.valueOf(amount));
        date=in.getDate();
        idate.setText(date);
        desc=in.getDesc();
        description.setText(desc);
        Log.d("Log::::::::::::::::: ", "amount=" + amount);
        Log.d("Log::::::::::::::::: ", "date=" + date);
        Log.d("Log::::::::::::::::: ", "desc=" + desc);

        updateIncome();
        deleteIncome();
    }

    public void updateIncome(){

        updateBtn.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {

                    date = idate.getText().toString();
                    amount = Double.parseDouble(iamount.getText().toString());
                    desc = description.getText().toString();

                    Income income = new Income(date, amount,desc);

                    Log.d("Inserting: ", "Inserting..");
                    mydb.updateIncome(Integer.parseInt(id), income);

                    Toast.makeText(UpdateIncome.this, "Data updated", Toast.LENGTH_LONG).show();
                    //restart app
                    Intent i = getBaseContext().getPackageManager().getLaunchIntentForPackage(getBaseContext().getPackageName());
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(i);
                } catch (Exception e) {

                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(UpdateIncome.this);
                    alertDialog.setTitle("Alert");
                    alertDialog.setMessage("Please enter a valid Amount!");
                    alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
                    alertDialog.show();
                }

            }
        });
    }
    public void deleteIncome(){

        deleteBtn.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(android.view.View v) {

                AlertDialog.Builder alertDialog = new AlertDialog.Builder(UpdateIncome.this);
                alertDialog.setTitle("Confirm Delete");
                alertDialog.setMessage("Are you sure you want to delete this expense?");
                alertDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        //delete data
                        mydb.deleteOneIncome(Integer.parseInt(id));
                        mydb.close();
                        Toast.makeText(getApplicationContext(), "deleted", Toast.LENGTH_SHORT).show();
                        dialog.cancel();
                        //restart app
                        Intent i = getBaseContext().getPackageManager().getLaunchIntentForPackage( getBaseContext().getPackageName() );
                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(i);
                    }
                });

                // Setting Negative "NO" Button
                alertDialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                // Showing Alert Message
                alertDialog.show();
            }
        });
    }


    public void onCancel(android.view.View view) {
        Intent i = getBaseContext().getPackageManager().getLaunchIntentForPackage( getBaseContext().getPackageName() );
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(i);
    }
}
