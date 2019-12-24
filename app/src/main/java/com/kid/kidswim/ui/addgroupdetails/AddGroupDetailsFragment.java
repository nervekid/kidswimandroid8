package com.kid.kidswim.ui.addgroupdetails;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.kid.kidswim.R;
import com.kid.kidswim.ui.addgroup.AddGroupViewModel;

public class AddGroupDetailsFragment extends Fragment {
    private AddGroupDetailsViewModel addGroaupDetailsViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        addGroaupDetailsViewModel = ViewModelProviders.of(this).get(AddGroupDetailsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_add_group_details, container, false);
        final TextView textView = root.findViewById(R.id.text_gallery_details);

        addGroaupDetailsViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }
}
