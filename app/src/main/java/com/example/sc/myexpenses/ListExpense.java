package com.example.sc.myexpenses;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by SC on 11/16/2015.
 */
public class ListExpense extends AppCompatActivity {
    DatabaseHandler mydb;
    ListView listView;
    public static ArrayList<String> ArrayofExpense = new ArrayList<String>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_all);
        ArrayofExpense.clear();

        mydb = new DatabaseHandler(this);
        listView = (ListView)findViewById(R.id.list);

         //mydb.addExpense(new Expense("2015-09-1", 100, "test","test","test"));
        Log.d("Reading: ", "Reading all expense..");
        List<Expense> expense = mydb.getAllExpense();
        for (Expense ex : expense) {
            String log = "Id: " + ex.getID() + " ,Date: " + ex.getDate() + " ,Amount: " + ex.getAmount()
                    + " ,ItemName: " + ex.getname()+ " ,Method: " + ex.getMethod()+ " ,Description: " + ex.getDesc();
            // Writing Income to log
            Log.d("Expense: ", log);

        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, ArrayofExpense);

        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
                Toast.makeText(getApplicationContext(),
                        ((TextView) v).getText(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
