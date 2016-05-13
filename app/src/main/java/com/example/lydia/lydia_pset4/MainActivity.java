package com.example.lydia.lydia_pset4;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ArrayList<TodoList> toDoLists = new ArrayList();
    ArrayListAdapter toDoListsAdapter;
    TodoManager myToDoManager;
    final DBHelper myDB = new DBHelper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myToDoManager= TodoManager.getInstance();

        final ListView todoListsLV = (ListView) findViewById(R.id.todoListsLV);
        EditText newToDoListET = (EditText) findViewById(R.id.newToDoListET);
        final Button addToDoListBT = (Button) findViewById(R.id.addListBT);
        addToDoListBT.setEnabled(false);
        todoListsLV.setAdapter(toDoListsAdapter);

        /*
        Do not allow user to send in an empty field
         */
        newToDoListET.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.toString().trim().length()==0){
                    addToDoListBT.setEnabled(false);
                } else {
                    addToDoListBT.setEnabled(true);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        // update toDoItems String [] with current to do items / load db
        toDoLists = myDB.readLists();

        // set Adapter
        todoListsLV.setAdapter(toDoListsAdapter);
        toDoListsAdapter.notifyDataSetChanged();

        assert todoListsLV != null;
        todoListsLV.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                // Get String of item for Toast
                String ToDoList = (String) todoListsLV.getItemAtPosition(position).toString();

                // Toast to let user know which item was deleted
                Toast.makeText(getApplicationContext(), "You deleted: " + ToDoList, Toast.LENGTH_SHORT).show();

                TodoList oldToDoList = (TodoList) todoListsLV.getItemAtPosition(position);
                myDB.deleteList(oldToDoList);

                // Update ListView
                toDoLists.clear();
                toDoLists.addAll(myDB.readLists());
                toDoListsAdapter.notifyDataSetChanged();
                return true;
            }
        });

    }

    /*
    Select the to do List where on clicked by user
     */
    Intent ToDoListActivity = new Intent(this, SecondActivity.class);
    public void onItemClicked (View view, int position, long id){
        ListView todoListsLV = (ListView) findViewById(R.id.todoListsLV);
        String ToDoList = (String) todoListsLV.getItemAtPosition(position).toString();
        Bundle giveTrough = new Bundle();
        giveTrough.putString("chosen ToDoList", ToDoList);
        ToDoListActivity.putExtras(giveTrough);
        startActivity(ToDoListActivity);
    }

    /*
    Adding new List to ListView and TodoManager
     */
    public void addNewList(){
        ListView toDoListsLV = (ListView) findViewById(R.id.todoListsLV);
        EditText addNewToDoList = (EditText) findViewById(R.id.newToDoListET);
        String newToDoList = addNewToDoList.getText().toString();
        myToDoManager.addList(newToDoList);

        assert toDoListsLV != null;
        toDoListsAdapter.notifyDataSetChanged();
        toDoListsLV.setAdapter(toDoListsAdapter);
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {  }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }
}
