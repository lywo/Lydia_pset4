package com.example.lydia.lydia_pset4;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import java.util.ArrayList;

/**
 * Created by Lydia on 13-5-2016.
 */
public class ToDoItemAdapter extends ArrayAdapter {

        public ToDoItemAdapter(Context context, ArrayList<TodoItem> values) {
            super(context,0, values);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater toDoInflater = LayoutInflater.from(getContext());
            View theView = toDoInflater.inflate(R.layout.activity_second, parent, false);
//        TodoList toDoItem = getItem(position);
//        ListView txtToDoItem = (ListView) theView.findViewById(R.id.todoListsLV);
            return theView;
        }
}
