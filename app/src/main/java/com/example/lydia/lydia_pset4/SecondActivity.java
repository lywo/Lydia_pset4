package com.example.lydia.lydia_pset4;

import android.graphics.Color;
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
import java.util.List;

public class SecondActivity extends AppCompatActivity {
    ArrayList<TodoList> toDoLists;
    ToDoItemAdapter toDoListsAdapter;
    TodoManager myToDoManager;
    final DBHelper myDB = new DBHelper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        myToDoManager = TodoManager.getInstance();
        toDoLists = myToDoManager.getObject();
        String selectedList = getIntent().getExtras().getString("chosen ToDoList");
        int size  = toDoLists.size();
        ListView toDoItemsLV  = (ListView) findViewById(R.id.todoItemsLV);

        for (int i = 0; i < size; i++ ){
            if (TodoList.title == selectedList) {
                // Fill ListView with this selected list
                assert toDoItemsLV != null;
                toDoItemsLV.setAdapter(toDoListsAdapter);
            }
        }

        final ListView todoItemsLV = (ListView) findViewById(R.id.todoItemsLV);
        EditText newToDoItemET = (EditText) findViewById(R.id.newToDoItemET);
        final Button addToDoItemBT = (Button) findViewById(R.id.addToDoItemBT);
        addToDoItemBT.setEnabled(false);
        todoItemsLV.setAdapter(toDoListsAdapter);


        /*
        Do not allow user to send in an empty field
         */
        newToDoItemET.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.toString().trim().length()==0){
                    addToDoItemBT.setEnabled(false);
                } else {
                    addToDoItemBT.setEnabled(true);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        // update toDoItems String [] with current to do items / load db
        // toDoLists = myDB.readLists();

        // set Adapter
        todoItemsLV.setAdapter(toDoListsAdapter);
        toDoListsAdapter.notifyDataSetChanged();


        assert todoItemsLV != null;
        todoItemsLV.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                // Delete row of item at current position in sql
                String ToDoItem = (String) todoItemsLV.getItemAtPosition(position).toString();

                // Toast to let user know which item was deleted
                Toast.makeText(getApplicationContext(), "You deleted: " + ToDoItem, Toast.LENGTH_SHORT).show();

                // find selected item and delete from array list
                TodoItem oldToDoItem = (TodoItem) todoItemsLV.getItemAtPosition(position);
                String searchTitle = oldToDoItem.getToDoTitle(oldToDoItem);
                int size = toDoLists.size();
                for (int i = 0; i < size ; i ++ ){
                    if (searchTitle == TodoItem.title){
                        toDoLists.remove(oldToDoItem);
                    }
                }
//
//                myDB.deleteList(oldToDoList);
//
//                // Update ListView
//                toDoLists.clear();
//                toDoLists.addAll(myDB.readLists());
//                toDoListsAdapter.notifyDataSetChanged();
                return true;
            }
        });

        todoItemsLV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TodoItem currentToDoItem = (TodoItem) todoItemsLV.getItemAtPosition(position);
                if (currentToDoItem.getToDoBool(currentToDoItem)){
                    view.setBackgroundColor(Color.GREEN);
                    currentToDoItem.setToDoBool(currentToDoItem, false);
                }
                else {
                    view.setBackgroundColor(Color.TRANSPARENT);
                    currentToDoItem.setToDoBool(currentToDoItem, true);
                }
            }
        });
    }

    /*
    Adding new Item to ListView and TodoManager
     */
    public void addToDoItem(){
        ListView toDoItemLV = (ListView) findViewById(R.id.todoItemsLV);
        EditText addNewToDoItem = (EditText) findViewById(R.id.newToDoItemET);
        String newToDoTitle = addNewToDoItem.getText().toString();
        String selectedList = getIntent().getExtras().getString("chosen ToDoList");
        TodoList selectedToDoList = myToDoManager.readLists(selectedList);
        myToDoManager.writeToDos(newToDoTitle, selectedToDoList);

        assert toDoItemLV != null;
        toDoListsAdapter.notifyDataSetChanged();
        toDoItemLV.setAdapter(toDoListsAdapter);
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {  }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }
}
