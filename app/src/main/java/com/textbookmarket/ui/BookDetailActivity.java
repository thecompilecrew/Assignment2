package com.textbookmarket.ui;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.textbookmarket.R;
import com.textbookmarket.model.Textbook;
import com.textbookmarket.repository.TextbookRepository;

public class BookDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_detail);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Book Details");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        String bookId = getIntent().getStringExtra("book_id");
        Textbook book = TextbookRepository.getInstance().getById(bookId);

        if (book == null) {
            Toast.makeText(this, "Book not found", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        // Book info
        setText(R.id.tv_detail_title, book.getTitle());
        setText(R.id.tv_detail_author, "by " + book.getAuthor());
        setText(R.id.tv_detail_subject, book.getSubject());
        setText(R.id.tv_detail_edition, "Edition: " + book.getEdition());
        setText(R.id.tv_detail_isbn, "ISBN: " + book.getIsbn());
        setText(R.id.tv_detail_condition, "Condition: " + book.getCondition());
        setText(R.id.tv_detail_copies, "Copies Available: " + book.getNumberOfCopies());
        setText(R.id.tv_detail_price, "R" + String.format("%.2f", book.getPrice()));

        // Description
        String desc = book.getDescription();
        setText(R.id.tv_detail_description,
                (desc != null && !desc.isEmpty()) ? desc : "No description provided.");

        // Seller info
        setText(R.id.tv_detail_seller, book.getSellerName());
        setText(R.id.tv_detail_bank, book.getBankName());
        setText(R.id.tv_detail_account_holder, book.getAccountHolder());
        setText(R.id.tv_detail_account_number, book.getAccountNumber());

        // Badge
        TextView tvBadge = findViewById(R.id.tv_preloaded_badge);
        if (book.isPreloaded()) {
            tvBadge.setText("📚 Official Listing");
            tvBadge.setVisibility(android.view.View.VISIBLE);
        } else {
            tvBadge.setText("👤 Student Listing");
            tvBadge.setVisibility(android.view.View.VISIBLE);
        }
    }

    private void setText(int viewId, String text) {
        TextView tv = findViewById(viewId);
        if (tv != null) tv.setText(text);
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}
