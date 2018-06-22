package com.example.artem.roadtovacation.Fragments;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.artem.roadtovacation.AboutHero;
import com.example.artem.roadtovacation.Heroes;
import com.example.artem.roadtovacation.ListAdapter.AdapterListView;
import com.example.artem.roadtovacation.R;
import com.example.artem.roadtovacation.net.MarvelApi;
import com.example.artem.roadtovacation.net.request.characters.model.CharacterDataWrapper;
import com.example.artem.roadtovacation.net.request.characters.model.ComicList;
import com.example.artem.roadtovacation.net.request.comics.model.ComicPrice;
import com.example.artem.roadtovacation.utils.CredentialsUtils;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import rx.Subscriber;
import rx.Subscription;
import rx.schedulers.Schedulers;

import static com.example.artem.roadtovacation.utils.CredentialsUtils.public_key;
import static com.example.artem.roadtovacation.utils.CredentialsUtils.ts;
import static rx.android.schedulers.AndroidSchedulers.mainThread;

/**
 * Created by Artem on 20.06.2018.
 */

public class FragmentCurrentListOfHeroes extends Fragment {

    ArrayList<Heroes> heroes = new ArrayList<Heroes>();
    AdapterListView adapterListView;

    private Subscription subscription;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_list_of_heroes, container, false);

        adapterListView = new AdapterListView(getActivity().getApplicationContext(), heroes);

        MarvelApi marvelApi = MarvelApi.getInstance();
        subscription = marvelApi.getMarvel(ts,public_key, CredentialsUtils.getHash())
                .subscribeOn(Schedulers.io())
                .observeOn(mainThread())
                .subscribe(new Subscriber<CharacterDataWrapper>() {
                               @Override
                               public void onCompleted() {
                                   Log.d("MainActivity", "onCompleted");
                               }

                               @Override
                               public void onError(Throwable e) {
                                   Log.e("MainActivity", "onError => " + e.getMessage());
                               }

                               @Override
                               public void onNext(CharacterDataWrapper response) {
                                   Log.d("MainActivity", "onNext => " + response);
                                   try{

                                       if(response.getData().getResultsCharacter().size() > 0){

                                           for(int i = 0; i < response.getData().getResultsCharacter().size(); ++i){

                                               String currentNameHero = response.getData().getResultsCharacter().get(i).getName();
                                               String currentDescriptionHero = response.getData().getResultsCharacter().get(i).getDescription();
//                                               Integer quantityComics = response.getData().getResultsCharacter().get(i).getComics().);
                                               String imagePath = response.getData().getResultsCharacter().get(i).getThumbnail().getPath()+ "/standard_xlarge" + ".";
                                               String imageExtension =  response.getData().getResultsCharacter().get(i).getThumbnail().getExtension();
                                               String imageUrl = imagePath + imageExtension;
                                               Picasso.get().load(imageUrl);

//                                               Log.d("comicListTest", String.valueOf(quantityComics));

                                               fillData(currentNameHero, currentDescriptionHero, imageUrl);

                                           }

                                           final ListView listOfHeroes = view.findViewById(R.id.listOfHeroes);

                                           listOfHeroes.setAdapter(adapterListView);

                                           // get the data from the current clicked item
                                           listOfHeroes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                               @Override
                                               public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                                                   String currentNameHero = heroes.get(position).nameHero;
                                                   String currentDescriptionHero = heroes.get(position).descriptionHero;
                                                   String currentUrlAvatarHero = heroes.get(position).avatarHero;

//                                                  Toast.makeText(view.getContext(), "selected Item  " +  heroes.get(position), Toast.LENGTH_LONG).show();

                                                   Intent intent = new Intent(view.getContext(), AboutHero.class);
                                                   intent.putExtra("currentNameHero",  currentNameHero);
                                                   intent.putExtra("currentDescriptionHero",  currentDescriptionHero);
                                                   intent.putExtra("currentUrlAvatarHero",  currentUrlAvatarHero);


                                                   startActivity(intent);

                                               }
                                           });


                                       }
                                   }catch (NullPointerException e){
                                       Log.e("MainActivity", "NullPointerException  => " + e.getMessage());
                                   }
                               }
                           }
                );



        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(subscription != null && !subscription.isUnsubscribed())
            subscription.unsubscribe();
    }

    void fillData(String currentNameHero, String currentDescriptionHero, String imageUrl){

        heroes.add(new Heroes(imageUrl, currentNameHero, currentDescriptionHero));

    }


}
