package com.example.artem.workwithapi;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.artem.workwithapi.net.MarvelApi;

import com.example.artem.workwithapi.net.request.characters.model.CharacterDataWrapper;
import com.example.artem.workwithapi.utils.CredentialsUtils;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.io.Serializable;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Subscriber;
import rx.Subscription;
import rx.schedulers.Schedulers;

import static com.example.artem.workwithapi.utils.CredentialsUtils.public_key;
import static com.example.artem.workwithapi.utils.CredentialsUtils.ts;
import static rx.android.schedulers.AndroidSchedulers.mainThread;

public class MainActivity extends AppCompatActivity {


    private Subscription subscription;

    ArrayList<Heroes> heroes = new ArrayList<Heroes>();
    AdapterListView adapterListView;

    Drawable testAvatarHero;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);


        testAvatarHero = getResources().getDrawable(R.drawable.hero);

        adapterListView = new AdapterListView(this, heroes);


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

                                       if(response.getData().getResults().size() > 0){



                                           for(int i = 0; i < response.getData().getResults().size(); ++i){

                                               String currentNameHero = response.getData().getResults().get(i).getName();
                                               String currentDescriptionHero = response.getData().getResults().get(i).getDescription();
                                               String imagePath = response.getData().getResults().get(i).getThumbnail().getPath()+ "/standard_xlarge" + ".";
                                               String imageExtension =  response.getData().getResults().get(i).getThumbnail().getExtension();
                                               String imageUrl = imagePath + imageExtension;
                                               Picasso.get().load(imageUrl);

                                               fillData(currentNameHero, currentDescriptionHero, imageUrl);

                                           }

                                           final ListView listOfHeroes = findViewById(R.id.listOfHeroes);

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


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(subscription != null && !subscription.isUnsubscribed())
            subscription.unsubscribe();
    }

    void fillData(String currentNameHero, String currentDescriptionHero, String imageUrl){

        heroes.add(new Heroes(imageUrl, currentNameHero, currentDescriptionHero));

    }


}
