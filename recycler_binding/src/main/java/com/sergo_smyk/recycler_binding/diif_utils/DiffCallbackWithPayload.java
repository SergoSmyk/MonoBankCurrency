package com.sergo_smyk.recycler_binding.diif_utils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public abstract class DiffCallbackWithPayload<I, P> extends DiffCallback<I> {

    public abstract P getChangePayload(@NonNull I oldItem, @NonNull I newItem);

    @Nullable
    @Override
    public Object getChangePayload(int oldItemPosition, int newItemPosition) {
        return getChangePayload(oldList.get(oldItemPosition), newList.get(newItemPosition));
    }
}
