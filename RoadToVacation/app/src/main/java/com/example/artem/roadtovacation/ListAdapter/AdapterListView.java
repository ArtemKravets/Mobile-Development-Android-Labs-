package com.example.artem.roadtovacation.ListAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.artem.roadtovacation.Heroes;
import com.example.artem.roadtovacation.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Artem on 20.06.2018.
 */

public class AdapterListView extends BaseAdapter {

    Context ctx;
    LayoutInflater lInflater;

    ArrayList<Heroes> heroes;

    public AdapterListView(Context context, ArrayList<Heroes> _heroes) {
        ctx = context;
        heroes = _heroes;
        lInflater = (LayoutInflater)ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return heroes.size();
    }

    @Override
    public Object getItem(int position) {
        return heroes.get(position);
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

        Heroes heroes = getHero(position);

//        Log.d("myTag", heroes.toString());


        Picasso.get().load(heroes.avatarHero).into((ImageView)view.findViewById(R.id.avatarHero));
        ((TextView)view.findViewById(R.id.nameHero)).setText(heroes.nameHero);


        return view;
    }

    Heroes getHero(int position){
        return((Heroes)getItem(position));
    }

}
