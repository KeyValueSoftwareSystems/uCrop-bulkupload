package com.yalantis.ucrop.sample;

import android.content.Context;
import android.net.Uri;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ImageViewHolder> {
    private final ArrayList<Uri> mImgArray;

    public ImageAdapter(ArrayList<Uri> array) {
        mImgArray = array;
    }

    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return ImageViewHolder.createView(parent.getContext());
    }

    @Override
    public void onBindViewHolder(@NonNull ImageViewHolder holder, int position) {
        holder.bind(mImgArray.get(position));
    }

    @Override
    public int getItemCount() {
        return mImgArray.size();
    }

    public static class ImageViewHolder extends RecyclerView.ViewHolder {
        public final ImageView mImageView;

        public ImageViewHolder(ImageView iv) {
            super(iv);
            mImageView = iv;
        }

        public void bind(Uri data) {
            mImageView.setImageURI(data);
        }

        public static ImageViewHolder createView(Context context) {
            ImageView iv = new ImageView(context);
            iv.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            return new ImageViewHolder(iv);
        }
    }
}