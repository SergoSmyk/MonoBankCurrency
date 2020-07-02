package com.sergo_smyk.recycler_binding.recycler_adapters;

import androidx.annotation.NonNull;
import androidx.databinding.ViewDataBinding;

import com.sergo_smyk.recycler_binding.binder.ItemPayloadBinder;
import com.sergo_smyk.recycler_binding.diif_utils.DiffCallbackWithPayload;

import java.util.LinkedList;
import java.util.List;

public class PayloadBindAdapter<VB extends ViewDataBinding, I, P>
        extends BindAdapter<VB, I> {

    protected ItemPayloadBinder<VB, I, P> itemBinder;

    public PayloadBindAdapter(int itemLayout) {
        super(itemLayout);
    }

    public void setItemPayloadBinder(@NonNull ItemPayloadBinder<VB, I, P> itemBinder) {
        super.setItemBinder(itemBinder);
        this.itemBinder = itemBinder;
    }

    public void setDiffCallbackWithPayload(@NonNull DiffCallbackWithPayload<I, P> diffCallback) {
        super.setDiffCallback(diffCallback);
    }

    @SuppressWarnings("unchecked")
    @Override
    protected boolean bindViewHolder(@NonNull VB binding,
                                     int position,
                                     @NonNull I item,
                                     @NonNull List<Object> payloads) {
        List<P> typedPayloads = new LinkedList<>();
        for (Object payload : payloads) {
            typedPayloads.add((P) payload);
        }
        itemBinder.onBindPayload(binding, typedPayloads, item, position);
        binding.getRoot().post(() ->
                itemBinder.onBindPayload(binding, typedPayloads, item, position)
        );
        return true;
    }
}
