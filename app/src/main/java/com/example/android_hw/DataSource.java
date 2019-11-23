package com.example.android_hw;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class DataSource {
    private static final int INITIAL_ITEM_COUNT = 100;

    private static DataSource sInstance;

    public synchronized static DataSource getInstance() {
        if (sInstance == null) {
            sInstance = new DataSource();
        }

        return sInstance;
    }

    public enum Color {
        RED,
        BLUE,
    }

    public static class DataItem {
        private int mNumber;
        private DataSource.Color mColor;

        private DataItem(int number, DataSource.Color color) {
            mNumber = number;
            mColor = color;
        }

        public int getNumber() {
            return mNumber;
        }

        public Color getColor() {
            return mColor;
        }
    }

    private final List<DataItem> mItems;

    private DataSource() {
        mItems = new ArrayList<>();
        init();
    }

    private void init() {
        for (int i = 0; i < INITIAL_ITEM_COUNT; i++) {
            grow();
        }
    }

    @Nullable
    public DataItem get(int pos) {
        if (pos < 0 || pos >= mItems.size()) {
            return null;
        }

        return mItems.get(pos);
    }

    public int getSize() {
        return mItems.size();
    }

    public void grow() {
        DataItem dataItem = new DataItem(
                mItems.size() + 1,
                mItems.size() % 2 == 0 ? Color.RED : Color.BLUE
        );

        mItems.add(dataItem);
    }
}
