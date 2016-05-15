package com.example.lydia.lydia_pset4;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Lydia on 13-5-2016.
 */
public class ArrayListAdapter  extends ArrayAdapter<TodoList> {

    public ArrayListAdapter(Context context, ArrayList<TodoList> values) {
        super(context,0, values);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView != null){
            LayoutInflater toDoInflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = toDoInflater.inflate(R.layout.activity_main, parent, false);
        }
        //        TodoList toDoItem = getItem(position);
//        ListView txtToDoItem = (ListView) theView.findViewById(R.id.todoListsLV);
        return convertView;
    }
}
