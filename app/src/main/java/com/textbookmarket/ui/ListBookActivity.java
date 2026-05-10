package com.textbookmarket.ui;

import android.app.AlertDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.textbookmarket.R;
import com.textbookmarket.model.Textbook;
import com.textbookmarket.model.TextbookExceptions;
import com.textbookmarket.repository.TextbookRepository;

import java.util.UUID;

public class ListBookActivity extends AppCompatActivity {

    private EditText etTitle, etAuthor, etIsbn, etSubject, etEdition,
            etCondition, etPrice, etCopies, etSellerName,
            etBankName, etAccountNumber, etAccountHolder, etDescription;
    private Button btnSubmit, btnClear;
    private TextView tvListingCount;
    private int sessionListingCount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_book);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("List a Textbook");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        initViews();
        setupButtons();
    }

    private void initViews() {
        etTitle = findViewById(R.id.et_title);
        etAuthor = findViewById(R.id.et_author);
        etIsbn = findViewById(R.id.et_isbn);
        etSubject = findViewById(R.id.et_subject);
        etEdition = findViewById(R.id.et_edition);
        etCondition = findViewById(R.id.et_condition);
        etPrice = findViewById(R.id.et_price);
        etCopies = findViewById(R.id.et_copies);
        etSellerName = findViewById(R.id.et_seller_name);
        etBankName = findViewById(R.id.et_bank_name);
        etAccountNumber = findViewById(R.id.et_account_number);
        etAccountHolder = findViewById(R.id.et_account_holder);
        etDescription = findViewById(R.id.et_description);
        btnSubmit = findViewById(R.id.btn_submit);
        btnClear = findViewById(R.id.btn_clear);
        tvListingCount = findViewById(R.id.tv_listing_count);

        updateListingCountText();
    }

    private void setupButtons() {
        btnSubmit.setOnClickListener(v -> handleSubmit());
        btnClear.setOnClickListener(v -> clearForm());
    }

    private void handleSubmit() {
        // Gather inputs
        String title = etTitle.getText().toString().trim();
        String author = etAuthor.getText().toString().trim();
        String isbn = etIsbn.getText().toString().trim();
        String subject = etSubject.getText().toString().trim();
        String edition = etEdition.getText().toString().trim();
        String condition = etCondition.getText().toString().trim();
        String priceStr = etPrice.getText().toString().trim();
        String copiesStr = etCopies.getText().toString().trim();
        String sellerName = etSellerName.getText().toString().trim();
        String bankName = etBankName.getText().toString().trim();
        String accountNumber = etAccountNumber.getText().toString().trim();
        String accountHolder = etAccountHolder.getText().toString().trim();
        String description = etDescription.getText().toString().trim();

        // Basic UI validation before constructing the object
        if (TextUtils.isEmpty(title)) { etTitle.setError("Required"); etTitle.requestFocus(); return; }
        if (TextUtils.isEmpty(author)) { etAuthor.setError("Required"); etAuthor.requestFocus(); return; }
        if (TextUtils.isEmpty(sellerName)) { etSellerName.setError("Required"); etSellerName.requestFocus(); return; }
        if (TextUtils.isEmpty(priceStr)) { etPrice.setError("Required"); etPrice.requestFocus(); return; }
        if (TextUtils.isEmpty(copiesStr)) { etCopies.setError("Required"); etCopies.requestFocus(); return; }
        if (TextUtils.isEmpty(bankName)) { etBankName.setError("Required"); etBankName.requestFocus(); return; }
        if (TextUtils.isEmpty(accountNumber)) { etAccountNumber.setError("Required"); etAccountNumber.requestFocus(); return; }
        if (TextUtils.isEmpty(accountHolder)) { etAccountHolder.setError("Required"); etAccountHolder.requestFocus(); return; }

        double price;
        int copies;
        try {
            price = Double.parseDouble(priceStr);
        } catch (NumberFormatException e) {
            etPrice.setError("Enter a valid number");
            etPrice.requestFocus();
            return;
        }
        try {
            copies = Integer.parseInt(copiesStr);
        } catch (NumberFormatException e) {
            etCopies.setError("Enter a valid number");
            etCopies.requestFocus();
            return;
        }

        // Build textbook object
        String id = "usr_" + UUID.randomUUID().toString().substring(0, 8);
        Textbook newBook = new Textbook(id, title, author,
                TextUtils.isEmpty(isbn) ? "N/A" : isbn,
                TextUtils.isEmpty(subject) ? "General" : subject,
                TextUtils.isEmpty(edition) ? "N/A" : edition,
                TextUtils.isEmpty(condition) ? "Not Specified" : condition,
                price, copies, sellerName,
                bankName, accountNumber, accountHolder, description);

        // Attempt to add via repository (handles duplicate + validation exceptions)
        try {
            TextbookRepository.getInstance().addTextbook(newBook);
            sessionListingCount++;
            updateListingCountText();
            showConfirmationDialog(newBook);

        } catch (TextbookExceptions.DuplicateTextbookException e) {
            showErrorDialog("Duplicate Listing", e.getMessage());
        } catch (TextbookExceptions.InvalidTextbookDataException e) {
            showErrorDialog("Invalid Data", e.getMessage());
        }
    }

    private void showConfirmationDialog(Textbook book) {
        String message = "✅ Your listing has been submitted!\n\n"
                + "📚 " + book.getTitle() + "\n"
                + "👤 Seller: " + book.getSellerName() + "\n"
                + "💰 Price: R" + String.format("%.2f", book.getPrice()) + "\n"
                + "📦 Copies: " + book.getNumberOfCopies() + "\n\n"
                + "Would you like to list another textbook?";

        new AlertDialog.Builder(this)
                .setTitle("Listing Confirmed!")
                .setMessage(message)
                .setPositiveButton("List Another", (d, w) -> clearForm())
                .setNegativeButton("Done", (d, w) -> finish())
                .setCancelable(false)
                .show();
    }

    private void showErrorDialog(String title, String message) {
        new AlertDialog.Builder(this)
                .setTitle("⚠️ " + title)
                .setMessage(message)
                .setPositiveButton("OK", null)
                .show();
    }

    private void clearForm() {
        etTitle.setText(""); etAuthor.setText(""); etIsbn.setText("");
        etSubject.setText(""); etEdition.setText(""); etCondition.setText("");
        etPrice.setText(""); etCopies.setText(""); etSellerName.setText("");
        etBankName.setText(""); etAccountNumber.setText("");
        etAccountHolder.setText(""); etDescription.setText("");
        etTitle.requestFocus();
        Toast.makeText(this, "Form cleared", Toast.LENGTH_SHORT).show();
    }

    private void updateListingCountText() {
        tvListingCount.setText("Books listed this session: " + sessionListingCount);
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}
