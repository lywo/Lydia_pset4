package com.example.lydia.lydia_pset4;

import java.util.ArrayList;

/**
 * Created by Lydia on 9-5-2016.
 */
public class TodoList {
    static String title;
    private ArrayList<TodoItem> toDoItems;

    public static String getListTitle(TodoList currentToDoList){
        String toDoTitle = currentToDoList.title;
        return toDoTitle;
    }

    public static void setListTitle(String newItemTitle, TodoList  newToDoList){
        newToDoList.title = newItemTitle;
    }

    public void addItem(TodoItem newToDoItem){
        toDoItems.add(newToDoItem);
    }
}
