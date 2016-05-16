package com.example.lydia.lydia_pset4;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

/**
 * Created by Lydia on 11-5-2016.
 */
public class DBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "todo.db";
    private static final int DATABASE_VERSION = 2;
    private static final String TODO_LISTS = "todolist";
    public static final String LIST_ID = "_id";
    public static final String ITEM_ID = "_id";
    public static final String ITEM_TITLE = "title";
    public static final String LIST_TITLE = "title";
    public static final String TODO_ITEMS = "todoitem";
    public static final String ITEM_LIST_ID = "list_id";
    public static final String ITEM_COMPLETED = "completed";

    public DBHelper(Context context){ // DIT IS DE CONSTRUCTOR
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // tabel met alle ToDoLists
        String CREATE_LISTS_TABLE = "CREATE TABLE " + TODO_LISTS + " ( " + LIST_ID +
                " INTEGER PRIMARY KEY, " + LIST_TITLE + " TEXT "+ ")";
        db.execSQL(CREATE_LISTS_TABLE);

        // tabel met alle ToDoitems
        String CREATE_ITEMS_TABLE = "CREATE TABLE " + TODO_ITEMS + "( " + ITEM_ID +
                " INTEGER PRIMARY KEY, " +ITEM_TITLE + " TEXT, " + ITEM_LIST_ID + " INTEGER, " + ITEM_COMPLETED + " INTEGER" + " ) ";
        db.execSQL(CREATE_ITEMS_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // tabel met alle ToDoLists
        String CREATE_LISTS_TABLE = "CREATE TABLE " + TODO_LISTS + "(" + LIST_ID +
                " INTEGER PRIMARY KEY, " + LIST_TITLE + " TEXT"+ ")";
        db.execSQL(CREATE_LISTS_TABLE);

        // tabel met alle ToDoitems
        String CREATE_ITEMS_TABLE = "CREATE TABLE " + TODO_ITEMS + " ( " + ITEM_ID +
                " INTEGER PRIMARY KEY, " + ITEM_TITLE + " TEXT, " + ITEM_LIST_ID + " INTEGER " + " ) ";
        db.execSQL(CREATE_ITEMS_TABLE);
    }


    public void saveAll (){
        TodoManager myTodoManager = TodoManager.getInstance();
        ArrayList<TodoList> toDoLists = myTodoManager.getObject();
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        int listCount = toDoLists.size();

        for (int i = 0; i < listCount; i++){
            TodoList currentToDoList = toDoLists.get(i);
            values.put(LIST_TITLE, currentToDoList.getListTitle());
            Long listID = db.insert(TODO_LISTS, null, values);

            int itemCount = currentToDoList.size();
            for (int j = 0; j < itemCount; i++){
                values.put(ITEM_TITLE, currentToDoList.getItem(j).getToDoTitle());
                int itemCompleted;
                if (currentToDoList.getItem(j).getToDoBool()){
                    itemCompleted = 1;
                }
                else{
                    itemCompleted = 0;
                }
                values.put(ITEM_COMPLETED, itemCompleted);
                values.put(ITEM_LIST_ID, listID);
                db.insert(TODO_ITEMS, null, values);
            }
        }
        db.close();
    }

    public ArrayList<TodoList> loadAll (){
        TodoManager myTodoManager = TodoManager.getInstance();
        ArrayList<TodoList> toDoLists = myTodoManager.getObject();
        SQLiteDatabase db = getReadableDatabase();
        String query = "SELECT " + LIST_ID + " , " + LIST_TITLE + " FROM " + TODO_LISTS;
        Cursor listCursor = db.rawQuery( query, null );
        TodoList currentToDoList = new TodoList();

        if (listCursor.moveToFirst()) {
            while (!listCursor.isAfterLast()) {
                int listId = listCursor.getColumnIndex(LIST_ID);
                currentToDoList.setListTitle(listCursor.getString(listId));
                loadTodoItems(listId, currentToDoList);
                listCursor.moveToNext();
            }
        }
        listCursor.close();
        return toDoLists;
    }

    private void loadTodoItems(int index, TodoList currentList){
        String indexString = Integer.toString(index);
        SQLiteDatabase db = getReadableDatabase();
        String query = "SELECT " + ITEM_ID + " , " + ITEM_TITLE + " , " + ITEM_LIST_ID + " FROM " + TODO_ITEMS + " WHERE " + ITEM_LIST_ID + " = " + indexString;
        Cursor itemCursor = db.rawQuery( query, null);
        TodoItem currentToDoItem = new TodoItem();
        TodoManager myTodoManager = TodoManager.getInstance();
        ArrayList<TodoList> toDoLists = myTodoManager.getObject();

        if (itemCursor.moveToFirst()){
            while(!itemCursor.isAfterLast()){
                int itemBool = itemCursor.getInt(itemCursor.getColumnIndex(ITEM_COMPLETED));
                String itemTitle = itemCursor.getString(itemCursor.getColumnIndex(ITEM_TITLE));
                currentToDoItem.setToDoTitle(itemTitle);
                currentToDoItem.setToDoBool(itemBool == 1);
                currentList.addItem(currentToDoItem);
            }
        }
        itemCursor.close();
        toDoLists.add(currentList);
    }

}
