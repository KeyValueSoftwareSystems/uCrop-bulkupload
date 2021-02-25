package com.yalantis.ucrop.task;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.yalantis.ucrop.callback.CopyFilesCallback;
import com.yalantis.ucrop.model.ImageTask;
import com.yalantis.ucrop.util.BitmapLoadUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

public class CopyFilesTask extends AsyncTask<Void, Void, Throwable> {
    private static final String TAG = "CopyFilesTask";

    private final CopyFilesCallback mCopyCallback;
    private final List<ImageTask> mTaskList;
    private final Context mContext;

    public CopyFilesTask(@NonNull Context context, CopyFilesCallback copyCallback, @NonNull ArrayList<ImageTask> imageTasks) {
        mContext = context;
        mCopyCallback = copyCallback;
        mTaskList = imageTasks;
    }

    @Override
    protected Throwable doInBackground(Void... voids) {
        for (ImageTask task : mTaskList) {
            try {
                copyFile(task.getSource(), task.getDestination());
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

    private void copyFile(@NonNull Uri inputUri, @Nullable Uri outputUri) throws NullPointerException, IOException {
        Log.d(TAG, "copyFile");

        if (outputUri == null) {
            throw new NullPointerException("Output Uri is null - cannot copy image");
        }

        InputStream inputStream = null;
        OutputStream outputStream = null;
        try {
            inputStream = mContext.getContentResolver().openInputStream(inputUri);
            outputStream = new FileOutputStream(new File(outputUri.getPath()));
            if (inputStream == null) {
                throw new NullPointerException("InputStream for given input Uri is null");
            }

            byte buffer[] = new byte[1024];
            int length;
            while ((length = inputStream.read(buffer)) > 0) {
                outputStream.write(buffer, 0, length);
            }
        } finally {
            BitmapLoadUtils.close(outputStream);
            BitmapLoadUtils.close(inputStream);
        }
    }
}
