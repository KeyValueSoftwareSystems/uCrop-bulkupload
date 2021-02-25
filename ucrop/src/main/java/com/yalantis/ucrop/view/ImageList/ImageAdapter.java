package com.yalantis.ucrop.view.ImageList;

import android.os.Build;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.yalantis.ucrop.R;
import com.yalantis.ucrop.model.ImageTask;

import java.util.List;

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ImageViewHolder> implements ImageTaskListener {
    private final ImageTaskListOwner listOwner;
    private final List<ImageTask> mImageList;
    private int mCurrentSelectedIndex;
    private int mLastCroppedIndex;
    private int mClickedIndex;

    public ImageAdapter(List<ImageTask> itemList, @NonNull ImageTaskListOwner owner) {
        mImageList = itemList;
        listOwner = owner;
        mCurrentSelectedIndex = 0;
        mLastCroppedIndex = -1;
        listOwner.onSelectionChange(mImageList.get(mCurrentSelectedIndex));
    }

    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return ImageViewHolder.createView(parent);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageViewHolder holder, final int position) {
        holder.bind(mImageList.get(position), position == mCurrentSelectedIndex);
        holder.mItemImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (position == mCurrentSelectedIndex) return;
                else if (position <= mLastCroppedIndex) {
                    listOwner.onPrevTaskSelected();
                } else {
                    List<ImageTask> sublist = mImageList.subList(mLastCroppedIndex + 1, position);
                    listOwner.onFutureTaskSelected(sublist);
                }
                mClickedIndex = position;
            }
        });
    }

    @Override
    public int getItemCount() {
        return mImageList.size();
    }

    @Override
    public void onImageTaskFinish() {
        if (mCurrentSelectedIndex > mLastCroppedIndex)
            mLastCroppedIndex = mCurrentSelectedIndex;

        int prevSelection = mCurrentSelectedIndex;
        ImageTask task;

        if (mClickedIndex < mLastCroppedIndex) { // prev clicked
            task = new ImageTask(mImageList.get(mClickedIndex).getDestination(), mImageList.get(mClickedIndex).getDestination());
        } else { // future clicked
            task = mImageList.get(mClickedIndex);
        }

        mCurrentSelectedIndex = mClickedIndex;
        mClickedIndex = -1;

        notifyItemChanged(prevSelection);
        notifyItemChanged(mCurrentSelectedIndex);
        if (listOwner != null) {
            listOwner.onSelectionChange(task);
        }
    }

    @Override
    public void onDoneClicked() {
        List<ImageTask> pendingTaskList = mImageList.subList(mLastCroppedIndex + 1, mImageList.size());
        listOwner.processPendingtasks(pendingTaskList);
    }

    public static class ImageViewHolder extends RecyclerView.ViewHolder {
        private final ImageView mItemImage;

        public ImageViewHolder(ImageView mItem) {
            super(mItem);
            mItemImage = mItem;
        }

        public void bind(ImageTask task, boolean isSelected) {
            mItemImage.setBackgroundResource(0);
            mItemImage.setImageURI(task.getSource());
            if (isSelected) mItemImage.setBackgroundResource(R.drawable.selected_item_border);
        }

        public static ImageViewHolder createView(ViewGroup parent) {
            ImageView image = new ImageView(parent.getContext());
            ViewGroup.MarginLayoutParams layoutParams = new ViewGroup.MarginLayoutParams(120, 120);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                layoutParams.setMarginEnd(15);
            }
            image.setLayoutParams(layoutParams);
            return new ImageViewHolder(image);
        }
    }
}
