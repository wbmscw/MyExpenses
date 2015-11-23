package com.example.sc.myexpenses;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.content.Intent;

import java.util.Calendar;

/**
 * Created by SC on 09/17/2015.
 */
public class Expense extends AppCompatActivity {

    //variables
    private int eid;
    private String edate;
    private double eamount;
    private String category;
    private String ename;//item name
    private String emethod;//payment method
    private String edesc;

    public Expense(){}
    public Expense(int id,String date,double amount,String category,String name,String method,String desc){
        this.eid=id;
        this.edate=date;
        this.eamount=amount;
        this.category=category;
        this.ename=name;
        this.emethod=method;
        this.edesc=desc;
    }
    public Expense(String date,double amount,String category,String name,String method,String desc){
        this.edate=date;
        this.eamount=amount;
        this.category=category;
        this.ename=name;
        this.emethod=method;
        this.edesc=desc;
    }

    public int getID(){return eid;}
    public void setID(int id){this.eid=id;}

    public String getDate(){return edate;}
    public void setDate(String date){this.edate=date;}

    public double getAmount(){return eamount;}
    public void setAmount(double amount){this.eamount=amount;}

    public String getCategory(){return category;}
    public void setCategory(String category){this.category=category;}

    public String getname(){return ename;}
    public void setname(String name){this.ename=name;}

    public String getMethod(){return emethod;}
    public void setMethod(String method){this.emethod=method;}

    public String getDesc(){return edesc;}
    public void setDesc(String desc){this.edesc=desc;}


    private TextView date;
    private EditText amount;
    private TextView itemName;
    private String paymentMethod;
    private EditText description;
    private Button bOk;

    private Button bOke;
    DatabaseHandler mydb;

    //set Calender variables
    private Button btn;
    private int year_x,month_x,date_x;
    static final int DIALOG_ID=0;
    private TextView currentD;

    //set ArrayAdapter
    ArrayAdapter<CharSequence> adapter_pm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_expense);

        Intent intent = getIntent();
        String category = intent.getExtras().getString("ExtraCategory");
        setCategory(category);

        mydb = new DatabaseHandler(this);
        date = (TextView)findViewById(R.id.EDate);
        itemName = (TextView)findViewById(R.id.editTextItemName);
        amount=(EditText)findViewById(R.id.editTextAmount);
        description = (EditText)findViewById(R.id.editTextDescription);
        bOke = (Button)findViewById(R.id.bOke);
        addExpense();

        //Get current date
        final Calendar cal = Calendar.getInstance();
        year_x= cal.get(Calendar.YEAR);
        month_x= cal.get(Calendar.MONTH)+1;
        date_x= cal.get(Calendar.DAY_OF_MONTH);

        //set current date
        currentD=(TextView) findViewById(R.id.EDate);
        currentD.setText(year_x + "-" + month_x + "-" + date_x);

        //set Spinner for payment method
        Spinner spinnerPaymentMethod = (Spinner)findViewById(R.id.spinner_payment_method);
        adapter_pm = ArrayAdapter.createFromResource(this, R.array.payment_method_array, android.R.layout.simple_spinner_item);
        adapter_pm.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerPaymentMethod.setAdapter(adapter_pm);

        spinnerPaymentMethod.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                paymentMethod = parent.getSelectedItem().toString();
                Toast.makeText(getBaseContext(), parent.getSelectedItem() + " selected", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                //paymentMethod="null";
            }
        });

    }
    //select date
    public void onCalender(View view) {

        btn=(Button)findViewById(R.id.bCalender);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showDialog(DIALOG_ID);
            }
        });
    }

    @Override
    protected Dialog onCreateDialog(int id){

        if(id==DIALOG_ID)
            return new DatePickerDialog(this,dpickerListner,year_x,month_x-1,date_x);
        return null;
    }

    private DatePickerDialog.OnDateSetListener dpickerListner
            = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            year_x=year;
            month_x=monthOfYear+1;
            date_x=dayOfMonth;

            currentD.setText(year_x + "-" + month_x + "-" + date_x);
        }
    };

    //Add Expense to database
    public void addExpense(){

        bOke.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                try {
                    String setDate = date.getText().toString();
                    Double setAmount = Double.parseDouble(amount.getText().toString());
                    String setCategory = getCategory();
                    String name = itemName.getText().toString();
                    String method = paymentMethod;
                    String setDesc = description.getText().toString();

                    Log.d("Inserting: ", "Inserting..");
                    mydb.addExpense(new Expense(setDate, setAmount,setCategory , name, method, setDesc));

                    Toast.makeText(Expense.this, "Data inserted", Toast.LENGTH_LONG).show();
                    //restart app
                    Intent i = getBaseContext().getPackageManager().getLaunchIntentForPackage(getBaseContext().getPackageName());
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(i);
                }catch(Exception e){

                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(Expense.this);
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

    public void onCancel(View view) {
        Intent i = getBaseContext().getPackageManager().getLaunchIntentForPackage( getBaseContext().getPackageName() );
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(i);
    }
}
