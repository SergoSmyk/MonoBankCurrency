package com.sergo_smyk.recycler_binding.binder;

import androidx.annotation.NonNull;

import java.util.List;

public interface ItemPayloadBinder<VB, I, P> extends ItemBinder<VB, I> {

    void onBindPayload(@NonNull VB binding, @NonNull List<P> payload, I item, int position);

    void onPostBindPayload(@NonNull VB binding, @NonNull List<P> payload, I item, int position);
}
