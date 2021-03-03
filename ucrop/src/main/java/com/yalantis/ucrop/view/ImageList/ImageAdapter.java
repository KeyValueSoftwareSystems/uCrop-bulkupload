package com.yalantis.ucrop.view.ImageList;

import android.os.Build;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.yalantis.ucrop.R;
import com.yalantis.ucrop.model.ImageTask;

import java.util.ArrayList;
import java.util.List;

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ImageViewHolder> implements ImageTaskListener {
    private final ImageTaskListOwner mListOwner;
    private final List<ImageTask> mImageList;
    private int mCurrentSelectedIndex;
    private int mLastCroppedIndex;
    private int mClickedIndex;

    public ImageAdapter(List<ImageTask> itemList, @NonNull ImageTaskListOwner owner) {
        mImageList = itemList;
        mListOwner = owner;
        mCurrentSelectedIndex = 0;
        mLastCroppedIndex = -1;
        mListOwner.onSelectionChange(mImageList.get(mCurrentSelectedIndex));
    }

    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return ImageViewHolder.createView(parent);
    }

    @Override
    public void onBindViewHolder(@NonNull final ImageViewHolder holder, final int position) {
        holder.bind(mImageList.get(position), position == mCurrentSelectedIndex);
        holder.mItemImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mClickedIndex = position;
                if (position == mCurrentSelectedIndex) return;
                else if (position <= mLastCroppedIndex) {
                    mListOwner.onPrevTaskSelected();
                } else {
                    List<ImageTask> sublist = mImageList.subList(mCurrentSelectedIndex + 1, position);
                    mListOwner.onFutureTaskSelected(sublist);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mImageList.size();
    }

    @Override
    public void onImageTaskFinish() {
        if (mClickedIndex <= mLastCroppedIndex)
            mLastCroppedIndex = Math.max(mLastCroppedIndex, mCurrentSelectedIndex);
        else
            mLastCroppedIndex = mClickedIndex - 1;

        int prevSelection = mCurrentSelectedIndex;
        ImageTask task;

        if (mClickedIndex == -1) {
            mClickedIndex = (mCurrentSelectedIndex + 1) % mImageList.size();
            task = mImageList.get(mClickedIndex);
        } else if (mClickedIndex <= mLastCroppedIndex) { // prev clicked
            task = new ImageTask(mImageList.get(mClickedIndex).getDestination(), mImageList.get(mClickedIndex).getDestination());
        } else { // future clicked
            task = mImageList.get(mClickedIndex);
        }

        mCurrentSelectedIndex = mClickedIndex;
        mClickedIndex = -1;

        notifyItemChanged(prevSelection);
        notifyItemChanged(mCurrentSelectedIndex);
        if (mListOwner != null) {
            mListOwner.onSelectionChange(task);
        }
    }

    @Override
    public void onDoneClicked() {
        List<ImageTask> pendingTaskList;
        try {
            pendingTaskList = mImageList.subList(mLastCroppedIndex + 1, mImageList.size());
        } catch (ArrayIndexOutOfBoundsException e) {
            pendingTaskList = new ArrayList<ImageTask>();
        }
        mListOwner.processPendingTasks(pendingTaskList);
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
