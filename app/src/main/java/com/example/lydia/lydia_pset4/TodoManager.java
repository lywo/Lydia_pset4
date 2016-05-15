package com.example.lydia.lydia_pset4;

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

    public TodoItem readTodos () {
        return toDoItem;
    }

    public void setObject (TodoManager myInstance){
        this.myInstance = myInstance;
    }

    public ArrayList<TodoList> getObject() {
        return toDoLists;
    }

    public void writeToDos(String newItem, TodoList currentList){
        TodoItem newToDoItem = new TodoItem();
        newToDoItem.setToDoTitle(newItem,newToDoItem);
        currentList.addItem(newToDoItem);
    }

    public void addList(String newList){
        TodoList newTodoList = new TodoList();
        newTodoList.setListTitle(newList, newTodoList);
        toDoLists.add(newTodoList);
    }

    public TodoList readLists(String searchTitle){
        int size = toDoLists.size();
        for (int i = 0; i < size; i++ ) {
            if (TodoList.getListTitle(toDoLists.get(i)) == searchTitle) {
                TodoList correctList = toDoLists.get(i);
                return correctList;
            }
        }
        return null;
    }
}
