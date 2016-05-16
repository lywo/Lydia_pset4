package com.example.lydia.lydia_pset4;

/**
 * Created by Lydia on 9-5-2016.
 */
public class TodoItem {
    protected String title;
    private Boolean completed;
    private int _id;

    public String getToDoTitle(){
        String ToDoTitle = title;
        return ToDoTitle;
    }

    public void setToDoTitle(String newItemTitle){
        title= newItemTitle;
    }

    // getter bool
    public Boolean getToDoBool (){
        return completed;
    }

    // setter bool
    public void setToDoBool (Boolean status){completed = status;
    }

}
