package com.example.sc.myexpenses;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.view.View;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.widget.Toast;


/**
 * Created by SC on 10/21/2015.
 */
public class ViewAll extends AppCompatActivity {

    DatabaseHandler mydb;
    Button btnViewIncome ,btnViewExpense,btnDeleteAll;


    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_all);

        mydb = new DatabaseHandler(this);
        btnViewIncome = (Button)findViewById(R.id.bvi);
        btnViewExpense = (Button)findViewById(R.id.bve);
        btnDeleteAll = (Button)findViewById(R.id.bda);


    }


    public void onViewExpenses(View view) {
        Intent viewListExpense = new Intent(this,ListExpense.class);
        final int result=1;
        startActivityForResult(viewListExpense, result);
    }
    public void onViewIncomes(View view) {
        Intent viewListIncome = new Intent(this,ListIncome.class);
        final int result=1;
        startActivityForResult(viewListIncome, result);
    }
    public void onDelete(View view) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(ViewAll.this);

        // Setting Dialog Title
        alertDialog.setTitle("Confirm Delete");

        // Setting Dialog Message
        alertDialog.setMessage("Are you sure you want to delete all data?");

        // Setting Icon to Dialog
        //alertDialog.setIcon(R.drawable.delete);

        // Setting Positive "Yes" Button
        alertDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                //delete all data
                mydb.deleteAll();
                mydb.close();
                Toast.makeText(getApplicationContext(), "All data deleted", Toast.LENGTH_SHORT).show();
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

}
