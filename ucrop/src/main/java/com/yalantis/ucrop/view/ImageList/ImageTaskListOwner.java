package com.yalantis.ucrop.view.ImageList;

import com.yalantis.ucrop.model.ImageTask;

public interface ImageTaskListOwner {
    void onSelectionChange(ImageTask currentTask); // Invoked by this adapter to update the new image task
}
