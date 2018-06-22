package com.example.artem.roadtovacation.Fragments;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.artem.roadtovacation.AboutComic;
import com.example.artem.roadtovacation.AboutHero;
import com.example.artem.roadtovacation.Comics;
import com.example.artem.roadtovacation.Heroes;
import com.example.artem.roadtovacation.ListAdapter.AdapterListOfComics;
import com.example.artem.roadtovacation.ListAdapter.AdapterListView;
import com.example.artem.roadtovacation.R;
import com.example.artem.roadtovacation.net.MarvelApi;
import com.example.artem.roadtovacation.net.request.characters.model.CharacterDataContainer;
import com.example.artem.roadtovacation.net.request.characters.model.CharacterDataWrapper;
import com.example.artem.roadtovacation.net.request.comics.model.ComicDataWrapper;
import com.example.artem.roadtovacation.net.request.comics.model.ComicPrice;
import com.example.artem.roadtovacation.utils.CredentialsUtils;
import com.squareup.picasso.Picasso;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import rx.Subscriber;
import rx.Subscription;
import rx.schedulers.Schedulers;

import static com.example.artem.roadtovacation.utils.CredentialsUtils.public_key;
import static com.example.artem.roadtovacation.utils.CredentialsUtils.ts;
import static rx.android.schedulers.AndroidSchedulers.mainThread;

/**
 * Created by Artem on 20.06.2018.
 */

public class FragmentListOfComic extends Fragment implements View.OnClickListener{

    private Subscription subscription;

    private Button addFavoriteComic;

    ArrayList<Comics> comics = new ArrayList<Comics>();
    AdapterListOfComics adapterListOfComics;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragmen_list_of_comic, container, false);
        View viewList = inflater.inflate(R.layout.item_list_comic, container, false);

        adapterListOfComics = new AdapterListOfComics(getActivity().getApplicationContext(), comics);


        addFavoriteComic = viewList.findViewById(R.id.addFavoriteComic);
        addFavoriteComic.setOnClickListener(this);


        MarvelApi marvelApi = MarvelApi.getInstance();
        subscription = marvelApi.getMarvelComics(ts,public_key, CredentialsUtils.getHash())
                .subscribeOn(Schedulers.io())
                .observeOn(mainThread())
                .subscribe(new Subscriber<ComicDataWrapper>() {
                               @Override
                               public void onCompleted() {
                                   Log.d("MainActivity", "onCompleted");
                               }

                               @Override
                               public void onError(Throwable e) {
                                   Log.e("MainActivity", "onError => " + e.getMessage());
                               }

                               @Override
                               public void onNext(ComicDataWrapper response) {
                                   Log.d("ResponseComics", "onNext => " + response);
                                   try{

                                       if(response.getData().getResultsComic().size() > 0){


                                           for(int i = 0; i < response.getData().getResultsComic().size(); ++i){

                                               String currentTitleComic = response.getData().getResultsComic().get(i).getTitle();
                                               String currentDescriptionComic = response.getData().getResultsComic().get(i).getDescription();

                                               Float listOfPrice = response.getData().getResultsComic().get(i).getPrices().get(0).getPrice();
                                               String imagePath = response.getData().getResultsComic().get(i).getThumbnail().getPath()+ "/standard_xlarge" + ".";
                                               String imageExtension =  response.getData().getResultsComic().get(i).getThumbnail().getExtension();
                                               String imageUrl = imagePath + imageExtension;
                                               Picasso.get().load(imageUrl);

                                               Log.d("title-test", currentTitleComic);
                                               Log.d("get-get", String.valueOf(listOfPrice));

                                               fillData(currentTitleComic, currentDescriptionComic, listOfPrice, imageUrl);

                                           }

                                           final ListView listOfComics = view.findViewById(R.id.listOfComics);

                                           listOfComics.setAdapter(adapterListOfComics);

                                           // get the data from the current clicked item
                                           listOfComics.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                               @Override
                                               public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                                                   String currentTitleComic = comics.get(position).titleComic;
                                                   String currentDescriptionComic = comics.get(position).descriptionComic;
                                                   String currentPriceComic = comics.get(position).priceComic.toString();
                                                   String currentUrlPosterComic = comics.get(position).avatarComic;

//                                                  Toast.makeText(view.getContext(), "selected Item  " +  heroes.get(position), Toast.LENGTH_LONG).show();

                                                   Intent intent = new Intent(view.getContext(), AboutComic.class);
                                                   intent.putExtra("currentTitleComic",  currentTitleComic);
                                                   intent.putExtra("currentDescriptionComic",  currentDescriptionComic);
                                                   intent.putExtra("currentPriceComic",  currentPriceComic);
                                                   intent.putExtra("currentUrlPosterComic",  currentUrlPosterComic);

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

    void fillData(String currentTitleComic, String currentDescriptionComic, Float listOfPrice, String imageUrl){

        comics.add(new Comics(imageUrl, currentTitleComic, currentDescriptionComic, listOfPrice ));

    }

    @Override
    public void onClick(View v) {
        Log.d("testBtn", "testBtn");
        Toast.makeText(getContext(), "TEST!", Toast.LENGTH_LONG).show();
    }
}
