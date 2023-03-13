package com.example.qrazy;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;

import java.util.List;

/**
 * CustomAdapter is a custom ArrayAdapter, which allows for filtering items
 * This is mostly used for search functionality
 * Code partially adapted fro https://stackoverflow.com/a/59312629
 */
public class CustomAdapter extends BaseAdapter implements Filterable {

    private List items;
    private List filteredList;
    private Context context;

    public CustomAdapter(Context context, List items) {
        this.items = items;
        this.context = context;
    }

    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        return null;
    }

    @Override
    public Filter getFilter() {
        return null;
    }
}
