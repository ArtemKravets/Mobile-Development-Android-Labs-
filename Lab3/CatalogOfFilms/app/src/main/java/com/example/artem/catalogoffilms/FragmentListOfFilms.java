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
import android.widget.Toast;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Artem on 10.04.2018.
 */

public class FragmentListOfFilms extends Fragment {

    ArrayList<Films> films = new ArrayList<Films>();
    AdapterListView adapterListView;

    Drawable poster1, poster2, poster3;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_list_of_films, container,  false);

        // get all img from folder drawable
        poster1  = getResources().getDrawable( R.drawable.interstellar);
        poster2  = getResources().getDrawable( R.drawable.catch_me_if_you_can);
        poster3  = getResources().getDrawable( R.drawable.shutter_island);

        adapterListView = new AdapterListView(getActivity().getApplicationContext(), films);

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

//                Toast.makeText(view.getContext(), "selected Item  " + currentTitle.getText(), Toast.LENGTH_LONG).show();

            }
        });

        return view;
    }

    void fillData(){
        films.add(new Films(poster1, "Интерстеллар",
                "США", "Рейтинг: 5",
                "Классный фильм. Научная фантастика"));
        films.add(new Films(poster2,
                "Поймай меня, если сможешь",
                "США", "Рейтинг: 5",
                "Фрэнк Эбегнейл успел поработать врачом, адвокатом и пилотом на пассажирской авиалинии — и все это до достижения полного совершеннолетия в 21 год. Мастер в обмане и жульничестве, он также обладал искусством подделки документов, что в конечном счете принесло ему миллионы долларов, которые он получил по фальшивым чекам."));
        films.add(new Films(poster3, "Остров проклятых",
                "США",
                "Рейтинг: 5",
                "Два американских судебных пристава отправляются на один из островов в штате Массачусетс, чтобы расследовать исчезновение пациентки клиники для умалишенных преступников. При проведении расследования им придется столкнуться с паутиной лжи, обрушившимся ураганом и смертельным бунтом обитателей клиники."));
    }
}


