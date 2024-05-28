package com.enset.bookconsulting.Activity;


import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.squareup.picasso.Picasso;
import com.enset.bookconsulting.R;
import  com.enset.bookconsulting.models.Book;

public class BookDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_per_book);
        Intent intent = getIntent();
        Book book = (Book) intent.getSerializableExtra("book");

        TextView textViewTitle = findViewById(R.id.textViewTitle1);
        TextView textViewAuthors = findViewById(R.id.textViewAuthors1);
        TextView textViewDescription = findViewById(R.id.textViewDescription1);
        ImageView imageViewBook = findViewById(R.id.imageViewBook1);

        Button buttonShare = findViewById(R.id.buttonShare);

        textViewTitle.setText(book.getVolumeinfo().getTitle());
        textViewAuthors.setText(book.getVolumeinfo().getAuthors().toString());
        textViewDescription.setText(book.getVolumeinfo().getDescription());
        Picasso.get().load(book.getVolumeinfo().getImagelink().getThumbnail().replace("http://", "https://")).into(imageViewBook);

        buttonShare.setOnClickListener(view -> {
            String shareMsg = "Details book : \nTitle : " + book.getVolumeinfo().getTitle() + "\n"
                    + "Authors : " + book.getVolumeinfo().getAuthors().toString() + "\n"
                    + "Description : " + book.getVolumeinfo().getDescription();
            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setAction(Intent.ACTION_SEND);
            shareIntent.setType("text/plain");
            shareIntent.putExtra(Intent.EXTRA_TEXT, shareMsg);
            startActivity(Intent.createChooser(shareIntent, "Share via"));
        });

    }
}



