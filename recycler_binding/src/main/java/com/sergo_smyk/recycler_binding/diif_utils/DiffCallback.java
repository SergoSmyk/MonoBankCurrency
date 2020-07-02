package com.sergo_smyk.recycler_binding.diif_utils;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;

import java.util.Collections;
import java.util.List;

public abstract class DiffCallback<I> extends DiffUtil.Callback {

    protected List<I> oldList = Collections.emptyList();
    protected List<I> newList = Collections.emptyList();

    public void setNewList(List<I> list) {
        this.newList = list;
    }

    public void setOldList(List<I> list) {
        this.oldList = list;
    }

    @Override
    public int getOldListSize() {
        return oldList.size();
    }

    @Override
    public int getNewListSize() {
        return newList.size();
    }

    public abstract boolean areItemsTheSame(@NonNull I oldItem, @NonNull I newItem);

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        return areItemsTheSame(oldList.get(oldItemPosition), newList.get(newItemPosition));
    }

    public abstract boolean areContentsTheSame(@NonNull I oldItem, @NonNull I newItem);

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        return areContentsTheSame(oldList.get(oldItemPosition), newList.get(newItemPosition));
    }
}
