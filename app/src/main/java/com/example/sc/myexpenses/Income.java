package com.example.sc.myexpenses;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.EditText;
import android.widget.Toast;
import android.content.Intent;
import java.util.Calendar;
import java.util.Objects;


/**
 * Created by SC on 09/17/2015.
 */
public class Income extends AppCompatActivity{

    private int iid;
    private String idate;
    private Double iamount;
    private String idesc;

    public Income(){}
    public Income(int id,String date,double amount,String desc){
        this.iid=id;
        this.idate=date;
        this.iamount=amount;
        this.idesc=desc;
    }
    public Income(String date,double amount,String desc){
        this.idate=date;
        this.iamount=amount;
        this.idesc=desc;
    }
    public int getID(){return iid;}
    public void setID(int id){this.iid=id;}

    public double getAmount(){return iamount;}
    public void setAmount(double amount){this.iamount=amount;}

    public String getDate(){return idate;}
    public void setDate(String date){this.idate=date;}

    public String getDesc(){return idesc;}
    public void setDesc(String desc){this.idesc=desc;}


//
    private TextView date;
    private EditText amount;
    private EditText description;
    private Button bOk;
    DatabaseHandler mydb;

    //variables for Calender
    private Button btnCal;
    private int year_x,month_x,date_x;
    private static final int DIALOG_ID=0;
    TextView currentD;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_income);

        mydb = new DatabaseHandler(this);
        date = (TextView)findViewById(R.id.EditDate);
        amount=(EditText)findViewById(R.id.editTextAmount);
        description = (EditText)findViewById(R.id.editTextDescription);
        bOk = (Button)findViewById(R.id.bOk);
        addData();


        //Get current date
        final Calendar cal = Calendar.getInstance();
        year_x= cal.get(Calendar.YEAR);
        month_x= cal.get(Calendar.MONTH)+1;
        date_x= cal.get(Calendar.DAY_OF_MONTH);

        //set current date
        currentD=(TextView) findViewById(R.id.EditDate);
        currentD.setText(year_x + "-" + month_x + "-" + date_x);
    }
    //select date
    public void onCalender(View view) {

        btnCal=(Button)findViewById(R.id.bCalender);
        btnCal.setOnClickListener(new View.OnClickListener() {
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


    public void addData(){

        bOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try{

                    String setDate= date.getText().toString();
                    Double setAmount=Double.parseDouble(amount.getText().toString());
                    String setDesc = description.getText().toString();
                    Log.d("Inserting: ", "Inserting..");
                    mydb.addIncome(new Income(setDate, setAmount, setDesc));

                    Toast.makeText(Income.this, "Data inserted", Toast.LENGTH_LONG).show();
                    //restart app
                    Intent i = getBaseContext().getPackageManager().getLaunchIntentForPackage(getBaseContext().getPackageName());
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(i);

                }catch(Exception e){
                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(Income.this);
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


    //reset to main interface
    public void onCancel(View view) {
        Intent i = getBaseContext().getPackageManager().getLaunchIntentForPackage( getBaseContext().getPackageName() );
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(i);
    }
}
