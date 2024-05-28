package com.enset.bookconsulting;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.enset.bookconsulting.Activity.BookDetailActivity;
import com.enset.bookconsulting.Adapters.BookAdapter;
import com.enset.bookconsulting.models.Book;
import com.enset.bookconsulting.models.GoogleBooksResponse;
import com.enset.bookconsulting.services.GoogleBooksService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    ListView listViewBooks;
    Button buttonSearch;
    EditText editTextQuery;
    List<Book> books;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_1);

        books = new ArrayList<>();
        BookAdapter bookAdapter = new BookAdapter(this, R.layout.book_item, books);
        listViewBooks = findViewById(R.id.listItem);
        buttonSearch = findViewById(R.id.buttom);
        editTextQuery = findViewById(R.id.edittextQuery);
        listViewBooks.setAdapter(bookAdapter);

        Gson gson = new GsonBuilder().create();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://www.googleapis.com/books/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        GoogleBooksService booksApi = retrofit.create(GoogleBooksService.class);

        buttonSearch.setOnClickListener(view -> {
            String query = editTextQuery.getText().toString();
            Call<GoogleBooksResponse> call = booksApi.searchBooks(query);

            call.enqueue(new Callback<GoogleBooksResponse>() {
                @Override
                public void onResponse(Call<GoogleBooksResponse> call, Response<GoogleBooksResponse> response) {
                    GoogleBooksResponse booksResponse = response.body();
                    // Log.i("Info", booksResponse.getKind() + " " + booksResponse.getTotalItems());
                    books.clear();
                    books.addAll(booksResponse.getItems());
                    bookAdapter.notifyDataSetChanged();
                }

                @Override
                public void onFailure(Call<GoogleBooksResponse> call, Throwable throwable) {
                    throwable.printStackTrace();
                    Log.i("Info", "nop");
                    Log.e("Error", "API Call Failed: " + throwable.getMessage());
                    Toast.makeText(MainActivity.this, "Connection Error", Toast.LENGTH_SHORT);
                }
            });
        });
        listViewBooks.setOnItemClickListener((adapterView, view, i, l) -> {
            Intent intent = new Intent(getApplicationContext(), BookDetailActivity.class);
            intent.putExtra("book", books.get(i));
            startActivity(intent);
        });
    }





}