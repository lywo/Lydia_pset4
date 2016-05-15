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
    private static final int DATABASE_VERSION = 1;
    private static final String TODO_LISTS = "todolist";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_ITEM = "item";
    public static final String COLUMN_LIST = "list";
    public static final String TODO_ITEMS = "todoitem";

    public DBHelper(Context context){ // DIT IS DE CONSTRUCTOR
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        // tabel met alle ToDoLists
        String CREATE_LISTS_TABLE = "CREATE TABLE " + TODO_LISTS + "(" + COLUMN_ID +
                " INTEGER PRIMARY KEY," + COLUMN_LIST + " TEXT"+ ")";
        db.execSQL(CREATE_LISTS_TABLE);

        // tabel met alle ToDoitems
        String CREATE_ITEMS_TABLE = "CREATE TABLE" + TODO_ITEMS + "(" + COLUMN_ID +
                " INTEGER PRIMARY KEY" + COLUMN_ITEM + "TEXT" + ")";

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        // tabel met alle ToDoLists
        String CREATE_LISTS_TABLE = "CREATE TABLE " + TODO_LISTS + "(" + COLUMN_ID +
                " INTEGER PRIMARY KEY," + COLUMN_LIST + " TEXT"+ ")";
        db.execSQL(CREATE_LISTS_TABLE);

        // tabel met alle ToDoitems
        String CREATE_ITEMS_TABLE = "CREATE TABLE" + TODO_ITEMS + "(" + COLUMN_ID +
                " INTEGER PRIMARY KEY" + COLUMN_ITEM + "TEXT" + ")";
    }

//    /*
//    CRUD method
//    Add one item.
//    */
//    public void addItem(String listItem) {
//        ContentValues values = new ContentValues();
//        values.put(COLUMN_ITEM, listItem);
//        SQLiteDatabase db = this.getWritableDatabase();
//        db.insert(TODO_LISTS, null, values);
//        db.close();
//    }
//
//    /*
//    CRUD method
//    Delete one item from the list.
//     */
//    // TODO delete items by id not String item value
//    public void deleteItem(String item) {
//        String query = "DELETE FROM " + TODO_LISTS + " WHERE " + COLUMN_ITEM + " =  \"" + item + "\"";
//        SQLiteDatabase db = this.getWritableDatabase();
//        db.execSQL(query);
//        db.close();
//    }
//
//    /*
//    CRUD method
//    Delete one list from the lists.
//     */
//    public void deleteList(TodoList list){
//        String query = "DELETE FROM " + TODO_LISTS + " WHERE " + COLUMN_ITEM + " =  \"" + list + "\"";
//        SQLiteDatabase db = this.getWritableDatabase();
//        db.execSQL(query);
//        db.close();
//    }
//
//    /*
//    CRUD method
//    Loading all items
//     */
//    public ArrayList<String> readList() {
//        SQLiteDatabase db = getReadableDatabase();
//        String query = "SELECT _id, item FROM " + TODO_LISTS;
//        Cursor cursor = db.rawQuery( query, null );
//        ArrayList<String> ToDoItems = new ArrayList<String>();
//
//        // adding a String listItem to the ArrayList of ToDoItems.
//        String listItem = "";
//        if (cursor.moveToFirst()) {
//            while (cursor.isAfterLast() == false) {
//                listItem = cursor.getString(cursor.getColumnIndex(COLUMN_ITEM));
//                ToDoItems.add(listItem);
//                cursor.moveToNext();
//            }
//        }
//        cursor.close();
//        db.close();
//        return ToDoItems;
//    }
//
//    /*
//    CRUD method
//    Loading complete database
//     */
//    public ArrayList<TodoList> readLists() {
//        SQLiteDatabase db = getReadableDatabase();
//        String query = "SELECT _id, item FROM " + TODO_LISTS;
//        Cursor cursor = db.rawQuery( query, null );
//        ArrayList<TodoList> ToDoItems = new ArrayList<>();
//
//        // adding TodoLists to Arraylist with all TodoLists
//        TodoList listBuffer;
//        if (cursor.moveToFirst()) {
//            while (cursor.isAfterLast() == false) {
//                listBuffer =
//                ToDoItems.add(listBuffer);
//                cursor.moveToNext();
//            }
//        }
//        cursor.close();
//        db.close();
//        return ToDoItems;
//    }


    /*
    Function to clean up whole database.
     */
    public void DeleteAll(){
        SQLiteDatabase db = getWritableDatabase();
        String query = "DELETE FROM " + TODO_LISTS;
        db.execSQL(query);
    }
}
