package com.sergo_smyk.recycler_binding;

import androidx.annotation.NonNull;
import androidx.databinding.BindingAdapter;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.Adapter;

import com.sergo_smyk.recycler_binding.listener.OnScrollStopListener;
import com.sergo_smyk.recycler_binding.recycler_adapters.AdapterWithItems;
import com.sergo_smyk.recycler_binding.recycler_adapters.BindAdapter;

import java.util.List;

import static androidx.recyclerview.widget.RecyclerView.SCROLL_STATE_IDLE;

public class DataBindingsAdapters {

    @SuppressWarnings("unchecked")
    @BindingAdapter("adapter_items")
    public static <T> void setRecyclerItems(RecyclerView view, List<T> items) {
        if (items == null) {
            return;
        }

        Adapter<?> adapter = ((RecyclerView) view).getAdapter();
        if (adapter instanceof AdapterWithItems) {
            ((AdapterWithItems<T>) adapter).setItems(items);
            ((AdapterWithItems<?>) adapter).notifyInfoChanged();
        }
    }

    @BindingAdapter("adapter")
    public static void setAdapterToRecycler(RecyclerView view, BindAdapter<?, ?> adapter) {
        view.setAdapter(adapter);
    }

    @BindingAdapter("onScrollStop")
    public static void setOnScrollStopListener(RecyclerView view, OnScrollStopListener listener) {
        ((RecyclerView) view).addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                if (newState == SCROLL_STATE_IDLE) {
                    listener.onScrollStopped(recyclerView);
                }
            }
        });
    }
}
