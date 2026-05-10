package com.textbookmarket.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.textbookmarket.R;
import com.textbookmarket.model.Textbook;

import java.util.List;

public class TextbookAdapter extends RecyclerView.Adapter<TextbookAdapter.ViewHolder> {

    public interface OnBookClickListener {
        void onBookClick(Textbook book);
    }

    private final List<Textbook> books;
    private final OnBookClickListener listener;

    public TextbookAdapter(List<Textbook> books, OnBookClickListener listener) {
        this.books = books;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_textbook, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Textbook book = books.get(position);

        holder.tvTitle.setText(book.getTitle());
        holder.tvAuthor.setText(book.getAuthor());
        holder.tvSubject.setText(book.getSubject());
        holder.tvPrice.setText("R" + String.format("%.2f", book.getPrice()));
        holder.tvSeller.setText("Seller: " + book.getSellerName());
        holder.tvCopies.setText("Copies: " + book.getNumberOfCopies());
        holder.tvCondition.setText(book.getCondition());

        // Badge color difference
        if (book.isPreloaded()) {
            holder.tvCondition.setBackgroundResource(R.drawable.bg_badge_official);
        } else {
            holder.tvCondition.setBackgroundResource(R.drawable.bg_badge_student);
        }

        holder.itemView.setOnClickListener(v -> listener.onBookClick(book));
    }

    @Override
    public int getItemCount() { return books.size(); }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitle, tvAuthor, tvSubject, tvPrice, tvSeller, tvCopies, tvCondition;

        ViewHolder(View view) {
            super(view);
            tvTitle = view.findViewById(R.id.tv_item_title);
            tvAuthor = view.findViewById(R.id.tv_item_author);
            tvSubject = view.findViewById(R.id.tv_item_subject);
            tvPrice = view.findViewById(R.id.tv_item_price);
            tvSeller = view.findViewById(R.id.tv_item_seller);
            tvCopies = view.findViewById(R.id.tv_item_copies);
            tvCondition = view.findViewById(R.id.tv_item_condition);
        }
    }
}
