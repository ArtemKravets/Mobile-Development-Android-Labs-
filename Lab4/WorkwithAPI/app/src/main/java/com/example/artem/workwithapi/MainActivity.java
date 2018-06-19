package com.example.artem.workwithapi;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.artem.workwithapi.net.MarvelApi;

import com.example.artem.workwithapi.net.request.characters.model.CharacterDataWrapper;
import com.example.artem.workwithapi.utils.CredentialsUtils;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Subscriber;
import rx.Subscription;
import rx.schedulers.Schedulers;

import static com.example.artem.workwithapi.utils.CredentialsUtils.public_key;
import static com.example.artem.workwithapi.utils.CredentialsUtils.ts;
import static rx.android.schedulers.AndroidSchedulers.mainThread;

public class MainActivity extends AppCompatActivity {

    // ui
    @BindView(R.id.avatarHero)
    ImageView avatarHero;

    @BindView(R.id.nameHero)
    TextView nameHero;

    private Subscription subscription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

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
                                           nameHero.setText(response.getData().getResults().get(0).getName());
                                           String imagePath = response.getData().getResults().get(0).getThumbnail().getPath()+ "/standard_xlarge" + ".";
                                           String imageExtension =  response.getData().getResults().get(0).getThumbnail().getExtension();
                                           String imageUrl = imagePath + imageExtension;
                                           Picasso.get().load(imageUrl).into(avatarHero);
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


}
