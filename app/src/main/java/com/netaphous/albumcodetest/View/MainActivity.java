package com.netaphous.albumcodetest.View;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.netaphous.albumcodetest.Model.PhotoPojo;
import com.netaphous.albumcodetest.R;
import com.netaphous.albumcodetest.ViewModel.AlbumViewModel;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();

    private Unbinder unbinder;

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;

    private AlbumViewModel viewModel;
    private PhotoPojoCustomAdapter customAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        unbinder = ButterKnife.bind(this);

        viewModel = ViewModelProviders.of(this).get(AlbumViewModel.class);
        viewModel.init(this);

        final Observer<List<PhotoPojo>> photoObserver =
                photoPojos -> customAdapter.setItems(photoPojos);

        viewModel.getPhotos().observe(this, photoObserver);

        customAdapter = new PhotoPojoCustomAdapter(this);
        recyclerView.setAdapter(customAdapter);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    public void updateFavorite(int photoId, boolean newValue) {
        viewModel.updateFavorite(photoId, newValue);
    }
}
