package com.netaphous.albumcodetest.View;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.netaphous.albumcodetest.Model.PhotoPojo;
import com.netaphous.albumcodetest.R;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PhotoPojoCustomAdapter extends RecyclerView.Adapter<PhotoPojoCustomAdapter.PhotoPojoViewHolder> {
    private List<PhotoPojo> items;

    private MainActivity activity;

    public PhotoPojoCustomAdapter(MainActivity activity) {
        this.activity = activity;
    }

    void setItems(List<PhotoPojo> items) {
        this.items = items;
        notifyDataSetChanged();
    }

    @Override
    @NonNull
    public PhotoPojoViewHolder onCreateViewHolder(@NonNull ViewGroup parent,
                                                  int viewType) {
        return new PhotoPojoViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull PhotoPojoViewHolder holder, int position) {
        PhotoPojo item = items.get(position);
        holder.setDisplay(item);
    }

    @Override
    public int getItemCount() {
        return items == null ? 0 : items.size();
    }

    class PhotoPojoViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.photo_image)

        ImageView ivImage;

        private PhotoPojo item;
        PhotoPojoViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void setDisplay(PhotoPojo item) {
            this.item = item;
            Picasso.get().load(Uri.parse(item.thumbnailUrl))
                    .placeholder(R.drawable.ic_launcher_background)
                    .error(R.drawable.ic_launcher_foreground)
                    .into(ivImage);
        }

        @OnClick(R.id.photo_image)
        void onClick() {
            if(activity.getSupportFragmentManager() != null) {
                PhotoDialogFragment photoFragment = new PhotoDialogFragment();
                Bundle bundle = new Bundle();
                bundle.putParcelable(PhotoDialogFragment.ARG_PHOTO, item);
                photoFragment.setArguments(bundle);
                photoFragment.showNow(activity.getSupportFragmentManager(), "photo");
            }
        }
    }
}