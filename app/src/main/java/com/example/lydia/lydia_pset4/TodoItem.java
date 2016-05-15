package com.example.lydia.lydia_pset4;

/**
 * Created by Lydia on 9-5-2016.
 */
public class TodoItem {
    protected static String title;
    private Boolean completed;
    private int _id;

    public static String getToDoTitle(TodoItem currentToDoItem){
        String ToDoTitle = currentToDoItem.title;
        return ToDoTitle;
    }

    public static void setToDoTitle(String newItemTitle, TodoItem currentToDo){
        currentToDo.title= newItemTitle;
    }

    // getter bool
    public static Boolean getToDoBool (TodoItem currentToDo){
        return currentToDo.completed;
    }

    // setter bool
    public static void setToDoBool (TodoItem currentToDo, Boolean status){
        currentToDo.completed = status;
    }

}
