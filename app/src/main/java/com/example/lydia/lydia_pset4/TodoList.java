package com.example.lydia.lydia_pset4;

import java.util.ArrayList;

/**
 * Created by Lydia on 9-5-2016.
 */
public class TodoList {
    protected String title;
    private ArrayList<TodoItem> toDoItems;

    // constructor
    protected TodoList(){
        toDoItems = new ArrayList<>();
    }

    public String getListTitle(){
        String toDoTitle = title;
        return toDoTitle;
    }

    public void setListTitle(String newItemTitle){
        title = newItemTitle;
    }

    public void addItem(TodoItem newToDoItem){
        toDoItems.add(newToDoItem);
    }

    public int size (){
        return toDoItems.size();
    }

    public TodoItem getItem (int i){
        return toDoItems.get(i);
    }

    public void removeItem (TodoItem itemToRemove){
        toDoItems.remove(itemToRemove);
    }

    public ArrayList<TodoItem> getItems (){
        return toDoItems;
    }

}
