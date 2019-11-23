package com.example.android_hw;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.android_hw.fragments.ItemFragment;
import com.example.android_hw.fragments.ListFragment;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity implements ListFragment.IListFragmentHolder {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            initState();
        }
    }

    private void initState() {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        Fragment fragmentList = getSupportFragmentManager().findFragmentById(R.id.fragment_container);

        if (fragmentList == null || !fragmentList.isAdded()) {
            transaction.replace(R.id.fragment_container, new ListFragment());
        }

        transaction.commit();
    }

    @Override
    public void selectElement(int itemPos) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, ItemFragment.newInstance(itemPos))
                .addToBackStack(null)
                .commit();
    }
}
