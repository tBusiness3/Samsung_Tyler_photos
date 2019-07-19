package com.netaphous.albumcodetest.ViewModel;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.netaphous.albumcodetest.Model.PhotoPojo;
import com.netaphous.albumcodetest.Model.Repository;

import java.util.List;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class AlbumViewModel extends ViewModel {
    private static final String TAG = AlbumViewModel.class.getSimpleName();

    private MutableLiveData<List<PhotoPojo>> photos = new MutableLiveData<>();
    private Repository repository;

    public void init(Context context) {
        repository = Repository.getInstance(context);
        repository.subscribeToPhotos().subscribe(new Observer<List<PhotoPojo>>() {
            @Override
            public void onSubscribe(Disposable d) {
            }

            @Override
            public void onNext(List<PhotoPojo> photoPojos) {
                photos.setValue(photoPojos);
            }

            @Override
            public void onError(Throwable e) {
                Log.d(TAG, "onError: " + e.getMessage());
            }

            @Override
            public void onComplete() {
            }
        });
    }

    public LiveData<List<PhotoPojo>> getPhotos() {
        return photos;
    }

    public void updateFavorite(int photoId, boolean newValue) {
        repository.updateFavorite(photoId, newValue);
    }
}
