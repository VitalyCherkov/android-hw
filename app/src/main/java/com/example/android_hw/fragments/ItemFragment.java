package com.example.android_hw.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.android_hw.DataSource;
import com.example.android_hw.R;

public class ItemFragment extends Fragment {
    private static final String EXTRA_ITEM_POS = "item_pos";

    public static ItemFragment newInstance(int itemPos) {
        Bundle args = new Bundle();
        args.putInt(EXTRA_ITEM_POS, itemPos);

        ItemFragment fragment = new ItemFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.item_fragment, container, false);

        Bundle args = getArguments();

        if (args == null) {
            return view;
        }

        int pos = args.getInt(EXTRA_ITEM_POS);
        DataSource.DataItem item = DataSource.getInstance().get(pos);
        if (item == null) {
            return view;
        }

        TextView text = view.findViewById(R.id.item_fragment_text);
        text.setText(String.valueOf(item.getNumber()));

        int color = item.getColor() == DataSource.Color.RED
                ? R.color.colorAccent
                : R.color.colorPrimary;
        text.setBackgroundResource(color);

        return view;
    }
}
