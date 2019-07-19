package com.netaphous.albumcodetest.Model;

import java.util.List;

import io.reactivex.Single;
import retrofit2.http.GET;

public interface AlbumAPI {
    @GET("photos")
    Single<List<PhotoPojo>> getPhotos();
}
