package com.example.sc.myexpenses;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

/**
 * Created by SC on 11/23/2015.
 */
public class UpdateExpense extends AppCompatActivity {
    String id;
    String date;
    double amount;
    String name;
    String method;
    String desc;
    DatabaseHandler mydb;
    int p=0;

    TextView edate;
    EditText eamount;
    TextView itemName;
    EditText description;
    Button deleteBtn;
    Button updateBtn;

    //set ArrayAdapter
    ArrayAdapter<CharSequence> adapter_pm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.update_expense);

        Intent intent = getIntent();
        id=intent.getExtras().getString("ExtraId");
        mydb = new DatabaseHandler(this);
        Log.d("Log::::::::::::::::: ", "Id=" + id);
        Expense ex = mydb.getExpense(Integer.parseInt(id));

        edate = (TextView)findViewById(R.id.UDate);
        eamount = (EditText)findViewById(R.id.editTextAmount);
        itemName = (TextView)findViewById(R.id.editTextItemName);
        description = (EditText)findViewById(R.id.editTextDescription);
        deleteBtn =(Button)findViewById(R.id.bDeleteEx);
        updateBtn =(Button)findViewById(R.id.bUpdateEx);


        amount=ex.getAmount();
        date=ex.getDate();
        name=ex.getname();
        method=ex.getMethod();
        desc=ex.getDesc();
        Log.d("Log::::::::::::::::: ", "name=" + name);
        Log.d("Log::::::::::::::::: ", "method=" + method);
        Log.d("Log::::::::::::::::: ", "desc=" + desc);

        eamount.setText(String.valueOf(amount));
        edate.setText(date);
        itemName.setText(name);
        description.setText(desc);

        //set Spinner for payment method
        Spinner spinnerPaymentMethod = (Spinner)findViewById(R.id.spinner_payment_method);
        adapter_pm = ArrayAdapter.createFromResource(this, R.array.payment_method_array, android.R.layout.simple_spinner_item);
        adapter_pm.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerPaymentMethod.setAdapter(adapter_pm);

        if(method.equals("Cash"))
            p=0;
        if(method.equals("Cheque"))
            p=1;
        if(method.equals("Credit Card"))
            p=2;
        if(method.equals("Debit"))
            p=3;
        if(method.equals("Electronic Transfer"))
            p=4;

        spinnerPaymentMethod.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, android.view.View view, int position, long id) {
                parent.setSelection(p);
                method = parent.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        updateExpense();
        deleteExpense();
    }


    public void updateExpense(){

        updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {

                    date = edate.getText().toString();
                    amount = Double.parseDouble(eamount.getText().toString());
                    name = itemName.getText().toString();
                    desc = description.getText().toString();

                    Expense expense = new Expense(date, amount, name, method, desc);

                    Log.d("Inserting: ", "Inserting..");
                    mydb.updateExpense(Integer.parseInt(id),expense);

                    Toast.makeText(UpdateExpense.this, "Updated", Toast.LENGTH_LONG).show();
                    //restart app
                    Intent i = getBaseContext().getPackageManager().getLaunchIntentForPackage(getBaseContext().getPackageName());
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(i);
                }catch(Exception e){

                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(UpdateExpense.this);
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
    public void deleteExpense(){

        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder alertDialog = new AlertDialog.Builder(UpdateExpense.this);
                alertDialog.setTitle("Confirm Delete");
                alertDialog.setMessage("Are you sure you want to delete this expense?");
                alertDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        //delete data
                        mydb.deleteOneExpense(Integer.parseInt(id));
                        mydb.close();
                        Toast.makeText(getApplicationContext(), "Deleted", Toast.LENGTH_SHORT).show();
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


    public void onCancel(View view) {
        Intent i = getBaseContext().getPackageManager().getLaunchIntentForPackage( getBaseContext().getPackageName() );
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(i);
    }
}
