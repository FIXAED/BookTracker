package com.moc.booktracker.controller.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.moc.booktracker.R;
import com.moc.booktracker.controller.AuthorActivity;
import com.moc.booktracker.controller.AuthorProfileDialogFragment;
import com.moc.booktracker.controller.BookActivity;
import com.moc.booktracker.model.entity.Author;

import java.util.List;

public class AuthorAdapter extends ArrayAdapter<Author> {

    public AuthorAdapter(@NonNull Context context, @NonNull List<Author> authors) {
        super(context, R.layout.item_author, authors);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_author, null);
        }

        TextView fullNameTextView = convertView.findViewById(R.id.fullNameTextView);

        Author author = getItem(position);

        fullNameTextView.setText(author.getFull_name());

        ImageButton editGroupButton = convertView.findViewById(R.id.editGroupButton);
        editGroupButton.setOnClickListener((v) -> {
            new AuthorProfileDialogFragment(author).show(
                    ((AppCompatActivity) getContext()).getSupportFragmentManager(), AuthorProfileDialogFragment.TAG);
        });

        ImageButton deleteGroupButton = convertView.findViewById(R.id.deleteGroupButton);
        deleteGroupButton.setOnClickListener((v) -> {
            new AlertDialog.Builder(getContext())
                    .setTitle("Удалить автора")
                    .setMessage("Вы действительно хотите удалить этого автора?")
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setPositiveButton(android.R.string.yes, (dialog, whichButton) -> {
                        if (getContext() instanceof AuthorActivity) {
                            ((AuthorActivity) (getContext())).authorDeletionConfirmed(author);
                        }
                    })
                    .setNegativeButton(android.R.string.no, null).show();
        });


        convertView.setOnClickListener((v) -> {
            getContext().startActivity(
                    new Intent(getContext(), BookActivity.class).putExtra("id", author.getId())
            );
        });

        return convertView;
    }
}


