package com.yalantis.ucrop.view.ImageList;

import com.yalantis.ucrop.model.ImageTask;

import java.util.List;

public interface ImageTaskListOwner {
    void onSelectionChange(ImageTask currentTask); // Invoked by this adapter to update the new image task

    /*
     * Invoked by the [ImageAdapter] if user clicks on any index < current position.
     * After completion of cropAndSave of current image, owner activity responds by invoking [ImageTaskListener.onImageTaskFinish]
     * */
    void onPrevTaskSelected();

    /*
     *  Invoked by the [ImageAdapter] if user clicks on any index > current position and
     * @param sends the list of image tasks yet to be saved.
     * On completion, list owner activity responds by invoking [ImageTaskListener.onImageTaskFinish]
     * */
    void onFutureTaskSelected(List<ImageTask> copyTaskList);

    /*
    * Invoked by the [ImageAdapter] after Done button has been clicked
    * */
    void processPendingtasks(List<ImageTask> pendingTasks);
}
