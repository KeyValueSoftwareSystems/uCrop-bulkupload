package com.yalantis.ucrop.view.ImageList;

import com.yalantis.ucrop.model.ImageTask;

import java.util.List;

/**
 * Set of callback methods to be implemented by {@link com.yalantis.ucrop.UCropActivity} or
 * {@link com.yalantis.ucrop.UCropFragment} and invoked by {@link ImageAdapter}
 */
public interface ImageTaskListOwner {
    /**
     * Invoked to update the new image task
     */
    void onSelectionChange(ImageTask currentTask);

    /**
     * Invoked if user clicks on any index < current position.
     * After completion of cropAndSave of current image,
     * owner activity responds by invoking {@link ImageTaskListener #onImageTaskFinish}
     */
    void onPrevTaskSelected();

    /**
     * Invoked if user clicks on any index > current position and sends
     *
     * @param copyTaskList the list of image tasks yet to be saved.
     * On completion, list owner activity responds by invoking {@link ImageTaskListener #onImageTaskFinish}
     */
    void onFutureTaskSelected(List<ImageTask> copyTaskList);

    /**
     * Invoked after Done button has been clicked to mark the end of crop.
     */
    void processPendingTasks(List<ImageTask> pendingTasks);
}
