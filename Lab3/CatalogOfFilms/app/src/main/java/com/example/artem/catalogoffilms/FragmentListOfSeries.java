package com.example.artem.catalogoffilms;


import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Artem on 10.04.2018.
 */

public class FragmentListOfSeries extends Fragment {


    ArrayList<Films> series = new ArrayList<Films>();
    AdapterListView adapterListView;

    Drawable poster1, poster2, poster3;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragmet_list_of_series, container,  false);

        // get all img from folder drawable
        poster1  = getResources().getDrawable( R.drawable.game_of_the_trone);
        poster2  = getResources().getDrawable( R.drawable.breaking_bad);
        poster3  = getResources().getDrawable( R.drawable.cherlok);

        adapterListView = new AdapterListView(getActivity().getApplicationContext(), series);

        // fill adapter array of objects
        fillData();
        final ListView listOfFilms = (ListView)view.findViewById(R.id.listOfFilms);
        listOfFilms.setAdapter(adapterListView);

        // get the data from the current clicked item
        listOfFilms.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                // find current img
                ImageView findPoster = (ImageView) view.findViewById(R.id.poster);

                findPoster.buildDrawingCache();

                // get current obj img
                Bitmap currentPoster = findPoster.getDrawingCache();

                TextView currentTitle = (TextView) view.findViewById(R.id.title);
                TextView currentCountry = (TextView) view.findViewById(R.id.country);
                TextView currentRating = (TextView) view.findViewById(R.id.rating);
                TextView currentDescription = (TextView) view.findViewById(R.id.description);


                Intent intent = new Intent(view.getContext(), FullInfoEssence.class);

                Bundle extras = new Bundle();

                extras.putParcelable("currentPoster", currentPoster);
                intent.putExtras(extras);

                // transmit the data in fullInfoActivity
                intent.putExtra("currentTitle", currentTitle.getText());
                intent.putExtra("currentCountry", currentCountry.getText());
                intent.putExtra("currentRating", currentRating.getText());
                intent.putExtra("currentDescription", currentDescription.getText());
                startActivity(intent);

            }
        });


        return view;
    }

    void fillData(){
        series.add(new Films(poster1, "Игра престолов",
                "США", "Рейтинг: 5",
                "К концу подходит время благоденствия, и лето, длившееся почти десятилетие, угасает. Вокруг средоточия власти Семи королевств, Железного трона, зреет заговор, и в это непростое время король решает искать поддержки у друга юности Эддарда Старка. В мире, где все — от короля до наемника — рвутся к власти, плетут интриги и готовы вонзить нож в спину, есть место и благородству, состраданию и любви"));
        series.add(new Films(poster2,
                "Во все тяжкие",
                "США", "Рейтинг: 5",
                "Школьный учитель химии Уолтер Уайт узнаёт, что болен раком лёгких. Учитывая сложное финансовое состояние дел семьи, а также перспективы, Уолтер решает заняться изготовлением метамфетамина. Для этого он привлекает своего бывшего ученика Джесси Пинкмана, когда-то исключённого из школы при активном содействии Уайта"));
        series.add(new Films(poster3, "Шерлок",
                "США",
                "Рейтинг: 5",
                "События разворачиваются в наши дни. Он прошел Афганистан, остался инвалидом. По возвращении в родные края встречается с загадочным, но своеобразным гениальным человеком. Тот в поиске соседа по квартире. Лондон, 2010 год. Происходят необъяснимые убийства. Скотланд-Ярд без понятия, за что хвататься"));
    }
}
