package com.example.artem.roadtovacation.ListAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.artem.roadtovacation.Comics;
import com.example.artem.roadtovacation.Heroes;
import com.example.artem.roadtovacation.R;
import com.example.artem.roadtovacation.net.request.comics.model.Comic;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Artem on 21.06.2018.
 */

public class AdapterListOfComics extends BaseAdapter {

    Context ctx;
    LayoutInflater lInflater;

    ArrayList<Comics> comics;

    public AdapterListOfComics(Context context, ArrayList<Comics> _comics) {
        ctx = context;
        comics = _comics;
        lInflater = (LayoutInflater)ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return comics.size();
    }

    @Override
    public Object getItem(int position) {
        return comics.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = convertView;
        if (view == null) {
            view = lInflater.inflate(R.layout.item_list_comic, parent, false);
        }

        Comics comics = getComic(position);

//        Log.d("myTag", heroes.toString());


        Picasso.get().load(comics.avatarComic).into((ImageView)view.findViewById(R.id.posterComic));
        ((TextView)view.findViewById(R.id.titleComic)).setText(comics.titleComic);
        ((TextView)view.findViewById(R.id.priceComic)).setText(comics.priceComic.toString() + "$");


        return view;
    }

    Comics getComic(int position){
        return((Comics)getItem(position));
    }

}
