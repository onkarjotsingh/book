package com.example.android.book;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.GradientDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;

public class bookadapter extends ArrayAdapter<book> {

    public bookadapter(@NonNull Context context,@NonNull ArrayList<book> object){
        super(context,0,object);
    }
    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View listItemView = convertView;
        if(listItemView==null){
            listItemView=LayoutInflater.from(getContext()).inflate(R.layout.list_item,parent,false);
        }
        book curr=getItem(position);

        TextView title=(TextView) listItemView.findViewById(R.id.title);
        title.setText(curr.getTitle());

        TextView auth=(TextView) listItemView.findViewById(R.id.author);
        auth.setText(curr.getAuthor());

        ImageView img=(ImageView) listItemView.findViewById(R.id.image);
        String url=curr.getImg();
        return listItemView;
    }

}
