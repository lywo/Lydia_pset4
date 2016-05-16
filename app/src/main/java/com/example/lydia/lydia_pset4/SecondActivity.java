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
    public ToDoItemAdapter toDoItemsAdapter;
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
        final ListView toDoItemsLV  = (ListView) findViewById(R.id.todoItemsLV);
        TodoList searchList = null;

        for (int i = 0; i < size; i++ ){
            String title = toDoLists.get(i).title;
            if (title.equals(selectedList)) {
                // Fill ListView with this selected list
                searchList = toDoLists.get(i);
                break;
            }
        }
        final TodoList chosenList = searchList;
        assert toDoItemsLV != null;
        assert chosenList != null;
        toDoItemsAdapter = new ToDoItemAdapter(this, chosenList.getItems());
        toDoItemsLV.setAdapter(toDoItemsAdapter);
        EditText newToDoItemET = (EditText) findViewById(R.id.newToDoItemET);
        final Button addToDoItemBT = (Button) findViewById(R.id.addToDoItemBT);

        /*
        Do not allow user to send in an empty field
         */
        addToDoItemBT.setEnabled(false);
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
        toDoItemsLV.setAdapter(toDoItemsAdapter);
        toDoItemsAdapter.notifyDataSetChanged();

        toDoItemsLV.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                // find selected item and delete from array list
                TodoItem oldToDoItem = (TodoItem) toDoItemsLV.getItemAtPosition(position);
                String searchTitle = oldToDoItem.getToDoTitle();

                // Toast to let user know which item was deleted
                Toast.makeText(getApplicationContext(), "You deleted: " + searchTitle, Toast.LENGTH_SHORT).show();

                assert chosenList!= null;
                int size = chosenList.size();
                for (int i = 0; i < size ; i ++ ){
                    if (searchTitle.equals(chosenList.getItem(i).title)){
                        chosenList.removeItem(oldToDoItem);
                        break;
                    }
                }
//
//                myDB.deleteList(oldToDoList);
//
//                // Update ListView
//                toDoLists.clear();
//                toDoLists.addAll(myDB.readLists());

                toDoItemsAdapter.notifyDataSetChanged();
                return true;
            }
        });

        toDoItemsLV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TodoItem currentToDoItem = (TodoItem) toDoItemsLV.getItemAtPosition(position);
                currentToDoItem.setToDoBool (!currentToDoItem.getToDoBool());
                toDoItemsAdapter.notifyDataSetChanged();
            }
        });
    }

    /*
    Adding new Item to ListView and TodoManager
     */
    public void addToDoItem(View view) {
        myToDoManager = TodoManager.getInstance();
        EditText addNewToDoItem = (EditText) findViewById(R.id.newToDoItemET);
        assert addNewToDoItem != null;
        String newToDoTitle = addNewToDoItem.getText().toString();
        String selectedList = getIntent().getExtras().getString("chosen ToDoList");
        TodoList selectedToDoList = myToDoManager.readLists(selectedList);
        myToDoManager.writeToDos(newToDoTitle, selectedToDoList);
        addNewToDoItem.setText("");
        toDoItemsAdapter.notifyDataSetChanged();
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {  }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }


}
