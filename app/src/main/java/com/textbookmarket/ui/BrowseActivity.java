package com.textbookmarket.ui;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.textbookmarket.R;
import com.textbookmarket.adapter.TextbookAdapter;
import com.textbookmarket.model.Textbook;
import com.textbookmarket.repository.TextbookRepository;

import java.util.List;

public class BrowseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_browse);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Browse Textbooks");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        List<Textbook> books = TextbookRepository.getInstance().getAllTextbooks();

        TextView tvSubtitle = findViewById(R.id.tv_browse_subtitle);
        tvSubtitle.setText(books.size() + " textbook(s) available");

        RecyclerView recyclerView = findViewById(R.id.rv_textbooks);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        TextbookAdapter adapter = new TextbookAdapter(books, book -> {
            Intent intent = new Intent(this, BookDetailActivity.class);
            intent.putExtra("book_id", book.getId());
            startActivity(intent);
        });
        recyclerView.setAdapter(adapter);
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}
