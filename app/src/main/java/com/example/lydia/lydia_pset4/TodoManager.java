package com.example.lydia.lydia_pset4;

import android.content.Context;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by Lydia on 9-5-2016.
 */
public class TodoManager {
    private static TodoManager myInstance;
    private ArrayList<TodoList> toDoLists;
    private TodoItem toDoItem;

    // constructor
    protected TodoManager(){
        toDoLists = new ArrayList<>();
    }

    // methods
    public static TodoManager getInstance(){
        if (myInstance == null){
            myInstance = new TodoManager();
        }
        return myInstance;
    }

    private static Context context;
    public static void init(Context currentContext){
        context = currentContext.getApplicationContext();
    }


    public TodoItem readTodos () {
        return toDoItem;
    }

    public TodoManager setObject (TodoManager myInstance){
        this.myInstance = myInstance;
        return myInstance;
    }

    public ArrayList<TodoList> getObject() {
        return this.toDoLists;
    }

    public void writeToDos(String newItem, TodoList currentList){
        TodoItem newToDoItem = new TodoItem();
        newToDoItem.setToDoTitle(newItem);
        newToDoItem.setToDoBool(false);
        currentList.addItem(newToDoItem);
    }

    public void addList(String newList){
        TodoList newTodoList = new TodoList();
        newTodoList.setListTitle(newList);
        toDoLists.add(newTodoList);
    }

    public TodoList readLists(String searchTitle){
        int size = toDoLists.size();
        for (int i = 0; i < size; i++ ) {
            if ((toDoLists.get(i).getListTitle()).equals(searchTitle)){
                return toDoLists.get(i);
            }
        }
        return null;
    }
}
