package com.example.artem.catalogoffilms;


import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Artem on 10.04.2018.
 */

public class AdapterListView extends BaseAdapter {
    Context ctx;
    LayoutInflater lInflater;

    ArrayList<Films> films;

    public AdapterListView(Context context, ArrayList<Films> _films) {
        ctx = context;
        films = _films;
        lInflater = (LayoutInflater)ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }


    @Override
    public int getCount() {
        return films.size();
    }

    @Override
    public Object getItem(int position) {
        return films.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = convertView;
        if (view == null) {
            view = lInflater.inflate(R.layout.item_list, parent, false);
        }

        Films films = getFilm(position);


        ((ImageView)view.findViewById(R.id.poster)).setImageDrawable(films.poster);
        ((TextView)view.findViewById(R.id.title)).setText(films.title);
        ((TextView)view.findViewById(R.id.country)).setText(films.country);
        ((TextView)view.findViewById(R.id.rating)).setText(films.rating);
        ((TextView)view.findViewById(R.id.description)).setText(films.description);


        return view;
    }

    Films getFilm(int position){
        return((Films)getItem(position));
    }
}
