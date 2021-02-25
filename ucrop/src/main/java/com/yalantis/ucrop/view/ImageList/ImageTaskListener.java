package com.yalantis.ucrop.view.ImageList;

/**
 * Set of callback methods to be implemented by {@link ImageAdapter}
 * and invoked by {@link com.yalantis.ucrop.UCropActivity}
 */
public interface ImageTaskListener {
    /**
     * Invoked to indicate that the current task of saving bitmaps are finished
     * and that the next one to be loaded for preview.
     */
    void onImageTaskFinish();

    /*
     * Invoked to indicate that it's going to finish.
     * */
    void onDoneClicked();
}
