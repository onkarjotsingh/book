package com.example.android.book;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private String api_url="https://www.googleapis.com/books/v1/volumes?q=";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Button searchbutton=(Button) findViewById(R.id.search_button);
        searchbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String temp=api_url;
                EditText editText=(EditText) findViewById(R.id.type);
                String url=editText.getText().toString();
                temp=temp+url.toLowerCase()+"&maxResults=10";
                new BookAsyncTask().execute(temp);
            }
        });
    }
    private class BookAsyncTask extends AsyncTask<String,Void, ArrayList<book>>{

        @Override
        protected ArrayList<book> doInBackground(String... urls) {
            if(urls.length<1||urls[0]==null){
                return null;
            }
            ArrayList<book> books=Utils.fetchbooksdata(urls[0]);
            return books;
        }

        protected void onPostExecute(ArrayList<book> books){
            ListView bookListView=(ListView) findViewById(R.id.booklist);
            final bookadapter bookadapter=new bookadapter(MainActivity.this,books);
            bookListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    book b=bookadapter.getItem(position);
                    String url=b.getLoc();
                    Intent intent=new Intent(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse(url));
                    startActivity(intent);
                }
            });
            bookListView.setAdapter(bookadapter);
        }
    }
}