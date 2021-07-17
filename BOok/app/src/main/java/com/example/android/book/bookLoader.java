package com.example.android.book;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.loader.content.AsyncTaskLoader;

import java.util.ArrayList;

public class bookLoader extends AsyncTaskLoader<ArrayList<book>> {
    private String request_url;
    public bookLoader(@NonNull Context context,String url) {
        super(context);
        request_url=url;
    }

    @Nullable
    @Override
    public void onStartLoading(){
        forceLoad();
    }
    public ArrayList<book> loadInBackground() {
        final ArrayList<book> books=Utils.fetchbooksdata(request_url);
        return books;
    }
}
