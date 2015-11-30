package com.example.sc.myexpenses;

import android.content.Intent;
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
 * Created by SC on 11/15/2015.
 */
public class ListIncome extends AppCompatActivity {

    private int iid;
    DatabaseHandler mydb;
    ListView listView;
    public static ArrayList<String> ArrayofIncome = new ArrayList<String>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_all);
        ArrayofIncome.clear();

        mydb = new DatabaseHandler(this);
        listView = (ListView)findViewById(R.id.list);

        Log.d("Reading: ", "Reading all expense..");
        List<Income> incomes = mydb.getAllIncome();
        for (Income in : incomes) {
            String log = "Id: " + in.getID() + " ,Date: " + in.getDate() + " ,Amount: " + in.getAmount() + " ,Description: " + in.getDesc();
            // Writing Income to log
            Log.d("Expense: ", log);

        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, ArrayofIncome);

        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
                iid=position+1;
                //Toast.makeText(getApplicationContext(), ((TextView) v).getText(), Toast.LENGTH_SHORT).show();
                Log.d("Log: ", id + " Is selected");
                Intent viewIncome = new Intent(ListIncome.this,UpdateIncome.class);
                final int result=1;
                viewIncome.putExtra("ExtraId", String.valueOf(iid));
                startActivityForResult(viewIncome, result);
            }
        });
    }
}
