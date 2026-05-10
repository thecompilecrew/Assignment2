package com.textbookmarket.ui;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.textbookmarket.R;
import com.textbookmarket.repository.TextbookRepository;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        updateCounter();

        CardView cardBrowse = findViewById(R.id.card_browse);
        CardView cardList = findViewById(R.id.card_list);
        CardView cardSearch = findViewById(R.id.card_search);

        cardBrowse.setOnClickListener(v ->
                startActivity(new Intent(this, BrowseActivity.class)));

        cardList.setOnClickListener(v ->
                startActivity(new Intent(this, ListBookActivity.class)));

        cardSearch.setOnClickListener(v ->
                startActivity(new Intent(this, SearchActivity.class)));
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateCounter();
    }

    private void updateCounter() {
        TextView tvCount = findViewById(R.id.tv_book_count);
        int count = TextbookRepository.getInstance().getTotalCount();
        tvCount.setText(count + " textbooks available");
    }
}
