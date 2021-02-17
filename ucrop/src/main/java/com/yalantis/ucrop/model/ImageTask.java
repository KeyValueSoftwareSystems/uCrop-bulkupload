package com.yalantis.ucrop.model;

import android.net.Uri;

public class ImageTask {
    private Uri source;
    private Uri destination;

    public Uri getSource() {
        return source;
    }

    public Uri getDestination() {
        return destination;
    }

    public ImageTask(Uri src, Uri des) {
        source = src;
        destination = des;
    }
}
