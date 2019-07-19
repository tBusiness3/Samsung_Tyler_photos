package com.netaphous.albumcodetest.View;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.netaphous.albumcodetest.Model.PhotoPojo;
import com.netaphous.albumcodetest.R;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PhotoDialogFragment extends DialogFragment {
    protected static final String ARG_PHOTO = "PhotoPojo";
    @BindView(R.id.iv_dialog_image)
    ImageView ivImage;
    @BindView(R.id.tv_dialog_title)
    TextView tvTitle;
    @BindView(R.id.ib_favorite)
    ImageButton ibFavorite;

    private PhotoPojo photo;
    private boolean favorited = false;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_fragment_photo, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Bundle bundle = getArguments();
        if(bundle != null) {
            photo = bundle.getParcelable(ARG_PHOTO);
            if (photo != null) {
                Picasso.get().load(photo.url)
                        .placeholder(R.drawable.ic_launcher_background)
                        .error(R.drawable.ic_launcher_foreground)
                        .into(ivImage);
                tvTitle.setText(photo.title);
                favorited = photo.favorited;
                if(favorited)
                    ibFavorite.setImageResource(R.drawable.ic_favorite_black_24dp);
                else
                    ibFavorite.setImageResource(R.drawable.ic_favorite_border_black_24dp);
            }
        }
    }

    @OnClick(R.id.ib_favorite)
    void onFavoriteClick() {
        if(photo != null) {
            favorited = !favorited;
            if(getActivity() instanceof MainActivity)
                ((MainActivity) getActivity()).updateFavorite(photo.id, favorited);

            if(favorited)
                ibFavorite.setImageResource(R.drawable.ic_favorite_black_24dp);
            else
                ibFavorite.setImageResource(R.drawable.ic_favorite_border_black_24dp);
        }
    }
}
