package com.netaphous.albumcodetest.Model;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class Repository {
    private static final String TAG = Repository.class.getSimpleName();
    private static final String BASE_URL = "https://jsonplaceholder.typicode.com/";
    private static volatile Repository instance;

    private PhotoDao dao;

    private Repository(Context context) {
        AlbumAPI api = new Retrofit.Builder().baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build().create(AlbumAPI.class);
        dao = PhotoDatabase.getInstance(context).dao();

        api.getPhotos()
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .subscribe(new SingleObserver<List<PhotoPojo>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onSuccess(List<PhotoPojo> photoPojos) {
                        dao.storePhotos(photoPojos);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "onError: " + e.getMessage());
                    }
                });
    }

    public static Repository getInstance(Context context) {
        if (instance == null)
            synchronized (Repository.class) {
                if (instance == null)
                    instance = new Repository(context);
            }
        return instance;
    }

    public Observable<List<PhotoPojo>> subscribeToPhotos() {
        return dao.subscribeToPhotos()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public void updateFavorite(int photoId, boolean newValue) {
        dao.updateFavorites(photoId, newValue)
                .subscribeOn(Schedulers.io())
                .subscribe();
    }
}
