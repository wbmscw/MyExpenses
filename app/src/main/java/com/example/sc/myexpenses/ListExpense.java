package com.example.sc.myexpenses;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by SC on 11/16/2015.
 */
public class ListExpense extends AppCompatActivity {
    private int eid;
    DatabaseHandler mydb;
    ListView listView;
    public static ArrayList<String> arrayOfExpense = new ArrayList<String>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_all);
        arrayOfExpense.clear();

        mydb = new DatabaseHandler(this);
        listView = (ListView)findViewById(R.id.list);

        Log.d("Reading: ", "Reading all expense..");
        List<Expense> expense = mydb.getAllExpense();
        for (Expense ex : expense) {
            String log = "Id: " + ex.getID() + " ,Date: " + ex.getDate() + " ,Amount: " + ex.getAmount()
                    + " ,ItemName: " + ex.getname()+ " ,Method: " + ex.getMethod()+ " ,Description: " + ex.getDesc();
            // Writing Expense to log
            Log.d("Expense: ", log);

        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, arrayOfExpense);


        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                eid=position+1;
                //Toast.makeText(getApplicationContext(), ((TextView) v).getText(), Toast.LENGTH_SHORT).show();
                Log.d("Log: ", eid + " Is selected");
                Intent viewExpense = new Intent(ListExpense.this,UpdateExpense.class);
                final int result=1;
                viewExpense.putExtra("ExtraId", String.valueOf(eid));
                startActivityForResult(viewExpense, result);
            }

        });

    }
}
