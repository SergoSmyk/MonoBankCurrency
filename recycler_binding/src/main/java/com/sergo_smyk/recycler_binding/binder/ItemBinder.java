package com.sergo_smyk.recycler_binding.binder;

import androidx.annotation.NonNull;

public interface ItemBinder<VB, I> {
    void onBind(@NonNull VB binding, @NonNull I item, int position);

    void onBindListeners(@NonNull VB binding);

    void onPostBind(@NonNull VB binding, @NonNull I item, int position);
}
