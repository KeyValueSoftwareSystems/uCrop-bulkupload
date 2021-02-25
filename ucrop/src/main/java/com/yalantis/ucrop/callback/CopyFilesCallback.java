package com.yalantis.ucrop.callback;

public interface CopyFilesCallback {
    void onCopyTaskFailed(Throwable exception);
    void onCopyTaskComplete();
}
