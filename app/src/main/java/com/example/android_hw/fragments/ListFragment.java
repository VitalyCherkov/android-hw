package com.example.android_hw.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android_hw.DataSource;
import com.example.android_hw.R;

public class ListFragment extends Fragment {
    public interface IListFragmentHolder {
        void selectElement(int itemPos);
    }

    private MyDataAdapter mAdapter;
    private RecyclerView mRecyclerView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.recycler_fragment, container, false);

        mAdapter = new MyDataAdapter(DataSource.getInstance());
        configureRecyclerView(view);
        configureButton(view);

        return view;
    }

    private void configureRecyclerView(View container) {
        final int columnCount = getResources().getInteger(R.integer.columnCount);
        GridLayoutManager layoutManager = new GridLayoutManager(
                getActivity(),
                columnCount,
                RecyclerView.VERTICAL,
                false
        );

        mRecyclerView = container.findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(mAdapter);
    }

    private void configureButton(View container) {
        Button button = container.findViewById(R.id.add_button);
        button.setOnClickListener(v -> {
            mAdapter.mDataSource.grow();
            final int next = mAdapter.mDataSource.getSize() - 1;
            mAdapter.notifyItemInserted(next);
            mRecyclerView.smoothScrollToPosition(next);
        });
    }

    class MyDataAdapter extends RecyclerView.Adapter<MyViewHolder> {
        private DataSource mDataSource;

        private MyDataAdapter(DataSource dataSource) {
            mDataSource = dataSource;
        }

        @NonNull
        @Override
        public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater
                    .from(parent.getContext())
                    .inflate(R.layout.list_item, parent, false);
            return new MyViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
            DataSource.DataItem item = mDataSource.get(position);
            if (item == null) {
                return;
            }

            holder.mText.setText(String.valueOf(item.getNumber()));
            int color = item.getColor() == DataSource.Color.RED
                    ? R.color.colorAccent
                    : R.color.colorPrimary;
            holder.mText.setBackgroundResource(color);
        }

        @Override
        public int getItemCount() {
            return mDataSource.getSize();
        }
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView mText;

        private MyViewHolder(@NonNull View itemView) {
            super(itemView);

            mText = itemView.findViewById(R.id.list_item_text);
            mText.setOnClickListener(view -> {
                IListFragmentHolder holder = (IListFragmentHolder) getActivity();
                if (holder == null) {
                    return;
                }

                int pos = getAdapterPosition();
                holder.selectElement(pos);
            });
        }
    }
}
