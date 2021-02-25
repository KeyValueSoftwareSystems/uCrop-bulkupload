package com.yalantis.ucrop.task;

import android.os.AsyncTask;

import androidx.annotation.NonNull;

import com.yalantis.ucrop.callback.CopyFilesCallback;
import com.yalantis.ucrop.model.ImageTask;
import com.yalantis.ucrop.util.FileUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CopyFilesTask extends AsyncTask<Void, Void, Throwable> {
    private final CopyFilesCallback mCopyCallback;
    private final List<ImageTask> mTaskList;

    public CopyFilesTask(CopyFilesCallback copyCallback, @NonNull ArrayList<ImageTask> imageTasks) {
        mCopyCallback = copyCallback;
        mTaskList = imageTasks;
    }

    @Override
    protected Throwable doInBackground(Void... voids) {
        for (ImageTask task : mTaskList) {
            try {
                FileUtils.copyFile(task.getSource().toString(), task.getDestination().toString());
            } catch (IOException e) {
                return e;
            }
        }
        return null;
    }

    @Override
    protected void onPostExecute(Throwable exception) {
        if (mCopyCallback != null) {
            if (exception == null)
                mCopyCallback.onCopyTaskComplete();
            else
                mCopyCallback.onCopyTaskFailed(exception);
        }
    }
}
