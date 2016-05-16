package com.example.lydia.lydia_pset4;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lydia on 13-5-2016.
 */
public class ToDoItemAdapter extends ArrayAdapter<TodoItem> {

        public ToDoItemAdapter(Context context, ArrayList<TodoItem> values) {
            super(context,0, values);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater toDoInflater = LayoutInflater.from(getContext());
            View theView = toDoInflater.inflate(R.layout.row_layout_item, parent, false);

            TextView toDoItemTV = (TextView) theView.findViewById(R.id.toDoItemTV);
            TodoItem currentToDoItem = getItem(position);
            String itemTitle = currentToDoItem.getToDoTitle();
            toDoItemTV.setText(itemTitle);

            if (currentToDoItem.getToDoBool()){
                theView.setBackgroundColor(Color.GREEN);
            }
            else {
                theView.setBackgroundColor(Color.TRANSPARENT);
            }

            return theView;
        }
}
