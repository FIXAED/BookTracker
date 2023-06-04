package com.moc.booktracker.controller;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.fragment.app.DialogFragment;

import com.moc.booktracker.MyApplication;
import com.moc.booktracker.R;
import com.moc.booktracker.model.entity.Author;
import com.moc.booktracker.service.AuthorService;

public class AuthorProfileDialogFragment extends DialogFragment {

    public static String TAG = "CreateAuthorDialogFragment";

    private AuthorService authorService;

    private Author author = null;
    private Boolean isForEditing = false;

    public AuthorProfileDialogFragment() {
        super();
    }

    public AuthorProfileDialogFragment(Author author) {
        super();
        this.author = author;
        this.isForEditing = true;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        authorService = ((MyApplication) (requireActivity().getApplication())).authorService;

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LinearLayout view = new LinearLayout(getActivity());
        view.setOrientation(LinearLayout.VERTICAL);

        final EditText authorEditText = new EditText(getActivity());
        authorEditText.setHint(R.string.full_name);

        view.addView(authorEditText);
        builder.setView(view);

        if (isForEditing) {
            authorEditText.setText(author.getFull_name());
        }

        builder.setMessage(R.string.addAuthor)
                .setPositiveButton(R.string.action_ok, (dialog, id) -> {

                    if (!isForEditing) {
                        author = new Author();
                    }

                    author.setFull_name(authorEditText.getText().toString());

                    if (author.getFull_name() == null || author.getFull_name().length() == 0) {
                        return;
                    }


                    new Thread(() -> {

                        if (!isForEditing) {
                            authorService.createAuthor(author);
                        } else {
                            authorService.editAuthor(author);
                        }

                        getActivity().runOnUiThread(() -> {
                            dialog.dismiss();
                            Toast.makeText(getActivity(), isForEditing ? "Писатель изменён" : "Писатель добавлен", Toast.LENGTH_SHORT).show();
                            if (getActivity() instanceof AuthorActivity) {
                                ((AuthorActivity) getActivity()).updateAuthors();
                            }
                        });
                    }).start();

                })
                .setNegativeButton(R.string.action_cancel, (dialog, id) -> {
                    dialog.cancel();
                });

        return builder.create();
    }
}


