package com.sergo_smyk.recycler_binding.recycler_adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;

import com.sergo_smyk.recycler_binding.recycler_adapters.view_holder.BindViewHolder;

import java.util.Collections;
import java.util.List;

public abstract class AbstractBindAdapter<VB extends ViewDataBinding, I>
        extends RecyclerView.Adapter<BindViewHolder<VB>>
        implements AdapterWithItems<I> {

    @LayoutRes
    protected int itemLayout;

    protected List<I> items = Collections.emptyList();

    public AbstractBindAdapter(@LayoutRes int itemLayout) {
        this.itemLayout = itemLayout;
    }

    @NonNull
    @Override
    public BindViewHolder<VB> onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        VB binding = DataBindingUtil.inflate(inflater, itemLayout, parent, false);
        return new BindViewHolder<>(binding);
    }

    abstract void bindViewHolder(@NonNull VB binding, I item, int position);

    @Override
    public void onBindViewHolder(@NonNull BindViewHolder<VB> holder, int position) {
        I item = items.get(position);
        bindViewHolder(holder.getViewBinding(), item, position);
    }

    boolean bindViewHolder(@NonNull VB binding, int position, @NonNull I item, @NonNull List<Object> payloads) {
        return false;
    }

    @Override
    public void onBindViewHolder(@NonNull BindViewHolder<VB> holder, int position, @NonNull List<Object> payloads) {
        if (!payloads.isEmpty()) {
            I item = items.get(position);
            if (bindViewHolder(holder.getViewBinding(), position, item, payloads)) {
                return;
            }
        }
        super.onBindViewHolder(holder, position, payloads);
    }

    @Override
    public void setItems(List<I> items) {
        this.items = items;
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    @Override
    public void notifyInfoChanged() {
        notifyDataSetChanged();
    }
}
