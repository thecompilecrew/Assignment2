package com.textbookmarket.ui;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.textbookmarket.R;
import com.textbookmarket.adapter.TextbookAdapter;
import com.textbookmarket.model.Textbook;
import com.textbookmarket.repository.TextbookRepository;

import java.util.List;

public class SearchActivity extends AppCompatActivity {

    private EditText etSearch;
    private RecyclerView recyclerView;
    private TextView tvResultCount, tvEmpty;
    private TextbookAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Search Textbooks");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        etSearch = findViewById(R.id.et_search);
        recyclerView = findViewById(R.id.rv_search_results);
        tvResultCount = findViewById(R.id.tv_result_count);
        tvEmpty = findViewById(R.id.tv_empty);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Show all on launch
        performSearch("");

        etSearch.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void onTextChanged(CharSequence s, int start, int before, int count) {
                performSearch(s.toString());
            }
            @Override public void afterTextChanged(Editable s) {}
        });
    }

    private void performSearch(String query) {
        List<Textbook> results = TextbookRepository.getInstance().search(query);

        if (results.isEmpty()) {
            recyclerView.setVisibility(View.GONE);
            tvEmpty.setVisibility(View.VISIBLE);
            tvResultCount.setText("No results found");
        } else {
            recyclerView.setVisibility(View.VISIBLE);
            tvEmpty.setVisibility(View.GONE);
            tvResultCount.setText(results.size() + " result(s)");
        }

        adapter = new TextbookAdapter(results, book -> {
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
