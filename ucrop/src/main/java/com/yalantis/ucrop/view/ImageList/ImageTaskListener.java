package com.yalantis.ucrop.view.ImageList;

import com.yalantis.ucrop.model.ImageTask;

import java.util.List;

public interface ImageTaskListener {
    /*
    * Invoked by the owner activity to indicate that the current task of saving bitmaps are finished
    * and that the next one to be loaded for preview.
    * */
    void onImageTaskFinish();

    /*
    * Invoked by the owner activity to indicate that it's going to finish.
    * */
    void onDoneClicked();
}
