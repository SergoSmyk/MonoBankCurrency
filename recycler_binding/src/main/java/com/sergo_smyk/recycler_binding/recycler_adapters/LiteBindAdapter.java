package com.sergo_smyk.recycler_binding.recycler_adapters;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.ViewDataBinding;

import com.sergo_smyk.recycler_binding.binder.ItemBinder;
import com.sergo_smyk.recycler_binding.recycler_adapters.view_holder.BindViewHolder;

public class LiteBindAdapter<VB extends ViewDataBinding, I> extends AbstractBindAdapter<VB, I> {

    private ItemBinder<VB, I> itemBinder;

    public LiteBindAdapter(int itemLayout) {
        super(itemLayout);
    }

    public LiteBindAdapter(int itemLayout, ItemBinder<VB, I> itemBinder) {
        super(itemLayout);
        this.itemBinder = itemBinder;
    }

    @NonNull
    @Override
    public BindViewHolder<VB> onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        BindViewHolder<VB> vh = super.onCreateViewHolder(parent, viewType);
        itemBinder.onBindListeners(vh.getViewBinding());
        return vh;
    }

    @Override
    void bindViewHolder(@NonNull VB binding, I item, int position) {
        itemBinder.onBind(binding, item, position);
        binding.getRoot().post(() ->
                itemBinder.onPostBind(binding, item, position)
        );
    }

    public void setItemBinder(@NonNull ItemBinder<VB, I> itemBinder) {
        this.itemBinder = itemBinder;
    }
}
