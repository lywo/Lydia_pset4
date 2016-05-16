package com.example.lydia.lydia_pset4;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

/**
 * Created by Lydia on 13-5-2016.
 */
public class ToDoListAdapter extends ArrayAdapter<TodoList> {

    public ToDoListAdapter(Context context, ArrayList<TodoList> values) {
        super(context,0, values);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater toDoInflater = LayoutInflater.from(getContext());
        View theView = toDoInflater.inflate(R.layout.row_layout, parent, false);

        TodoList currentToDoList = getItem(position);
        String toDoListTitle = currentToDoList.getListTitle();

        TextView toDoListTV = (TextView) theView.findViewById(R.id.toDoListTV);
        toDoListTV.setText(toDoListTitle);
        return theView;
    }
}
