package com.example.lydia.lydia_pset4;

/*
Lydia Wolfs
First Activity of an app that stories multiple TO DO lists with mulitple TO DO items
Connected to listview with all TO DO lists, lists can be added via a button. Controlled by addNewList Function.
onItemClicked function selects the correct list and gives string to next activity via intent.
 */

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
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

        myToDoManager.getInstance().setObject(new TodoManager());
        toDoLists = myToDoManager.getInstance().getObject();
//        myToDoManager = new TodoManager();
//        myToDoManager = TodoManager.getInstance();
        // Object object = SingletonObject.getInstance().getObject();

        toDoListsAdapter = new ArrayListAdapter(this, toDoLists);
        final ListView toDoListsLV = (ListView) findViewById(R.id.todoListsLV);
        EditText newToDoListET = (EditText) findViewById(R.id.newToDoListET);
        final Button addToDoListBT = (Button) findViewById(R.id.addListBT);
        addToDoListBT.setEnabled(false);
        toDoListsLV.setAdapter(toDoListsAdapter);

        /*
        Do not allow user to send in an empty field
         */
        newToDoListET.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().trim().length() == 0){
                    addToDoListBT.setEnabled(false);
                }
                else{
                    addToDoListBT.setEnabled(true);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        // update toDoItems String [] with current to do items / load db
        // toDoLists = myDB.readLists();

        // set Adapter
        toDoListsLV.setAdapter(toDoListsAdapter);
        toDoListsAdapter.notifyDataSetChanged();

        toDoListsLV.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                // Get String of item for Toast
                String ToDoList = toDoListsLV.getItemAtPosition(position).toString();

                // Toast to let user know which item was deleted
                Toast.makeText(getApplicationContext(), "You deleted: " + ToDoList, Toast.LENGTH_SHORT).show();
                TodoList oldToDoList = (TodoList) toDoListsLV.getItemAtPosition(position);

                // Delete correct toDoList
                String searchTitle = oldToDoList.getListTitle(oldToDoList);
                int size = toDoLists.size();
                for (int i = 0; i < size; i++) {
                    if (searchTitle == TodoList.title) {
                        toDoLists.remove(oldToDoList);
                    }
                }

                // Update ListView
                toDoListsAdapter.notifyDataSetChanged();
                return true;
            }
        });
    }

    /*
    Select the to do List where on clicked by user
     */
    public void onItemClicked (View view, int position, long id){
        Intent ToDoListActivity = new Intent(MainActivity.this, SecondActivity.class);
        ListView todoListsLV = (ListView) findViewById(R.id.todoListsLV);
        assert todoListsLV != null;
        String ToDoList = todoListsLV.getItemAtPosition(position).toString();
        Bundle giveTrough = new Bundle();
        giveTrough.putString("chosen ToDoList", ToDoList);
        ToDoListActivity.putExtras(giveTrough);
        startActivity(ToDoListActivity);
    }

    /*
    Adding new List to ListView and TodoManager
     */
    protected void addNewList(View view){
        TodoManager.getInstance().setObject(myToDoManager);
        toDoLists = myToDoManager.getInstance().getObject();
        EditText addNewToDoList = (EditText) findViewById(R.id.newToDoListET);
        String newToDoList = addNewToDoList.getText().toString();
        myToDoManager.addList(newToDoList);
        addNewToDoList.setText("");
        toDoListsAdapter.notifyDataSetChanged();
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }
}
