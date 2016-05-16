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
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ArrayList<TodoList> toDoLists = new ArrayList();
    ToDoListAdapter toDoListsAdapter;
    TodoManager myToDoManager;
    // final DBHelper myDB = new DBHelper(this);

//    @Override
//    protected void onStart() {
//        super.onStart();
//
//        TodoManager.init(this);
//        myToDoManager = TodoManager.getInstance();
//        toDoLists = myToDoManager.getObject();
//        toDoLists = myDB.loadAll();
//    }
//
//    @Override
//    protected void onStop() {
//        super.onStop();
//        myDB.saveAll();
//    }
//
//    @Override
//    protected void onPause() {
//        super.onPause();
//        myDB.saveAll();
//    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //deleteDatabase("todo.db");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TodoManager.init(this);
        myToDoManager = TodoManager.getInstance();
        toDoLists = myToDoManager.getObject();

        //myToDoManager = myToDoManager.getInstance().setObject(new TodoManager());

//        myToDoManager = new TodoManager();
//        myToDoManager = TodoManager.getInstance();
        // Object object = SingletonObject.getInstance().getObject();

        toDoListsAdapter = new ToDoListAdapter(this, toDoLists);
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
                String searchTitle = oldToDoList.getListTitle();
                int size = toDoLists.size();
                for (int i = 0; i < size; i++) {
                    if (searchTitle == toDoLists.get(i).title) {
                        toDoLists.remove(oldToDoList);
                    }
                }

                // Update ListView
                toDoListsAdapter.notifyDataSetChanged();
                return true;
            }
        });

        toDoListsLV.setOnItemClickListener(new ListView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent ToDoListActivity = new Intent(MainActivity.this, SecondActivity.class);
                ListView todoListsLV = (ListView) findViewById(R.id.todoListsLV);
                assert todoListsLV != null;
                TextView chosenItem = (TextView) findViewById(R.id.toDoListTV) ;
                String ToDoList= chosenItem.getText().toString();
                Bundle giveThrough = new Bundle();
                giveThrough.putString("chosen ToDoList", ToDoList);
                ToDoListActivity.putExtras(giveThrough);
                startActivity(ToDoListActivity);
            }

//            @Override
//            public void onClick(View view) {
//                Intent ToDoListActivity = new Intent(MainActivity.this, SecondActivity.class);
//                ListView todoListsLV = (ListView) findViewById(R.id.todoListsLV);
//                assert todoListsLV != null;
//                String ToDoList = view.toString();
//                Bundle giveTrough = new Bundle();
//                giveTrough.putString("chosen ToDoList", ToDoList);
//                ToDoListActivity.putExtras(giveTrough);
//                startActivity(ToDoListActivity);
//            }
        });
    }

    /*
    Select the to do List where on clicked by user
     */
//    public void onItemClicked (View view){
//        Intent ToDoListActivity = new Intent(MainActivity.this, SecondActivity.class);
//        ListView todoListsLV = (ListView) findViewById(R.id.todoListsLV);
//        assert todoListsLV != null;
//        String ToDoList = view.toString();
//        Bundle giveTrough = new Bundle();
//        giveTrough.putString("chosen ToDoList", ToDoList);
//        ToDoListActivity.putExtras(giveTrough);
//        startActivity(ToDoListActivity);
//    }

    /*
    Adding new List to ListView and TodoManager
     */
    public void addNewList(View view){
        myToDoManager = TodoManager.getInstance();
        toDoLists = myToDoManager.getObject();
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
