package com.sergo_smyk.recycler_binding.recycler_adapters.view_holder;


import androidx.annotation.NonNull;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;

public class BindViewHolder<VB extends ViewDataBinding> extends RecyclerView.ViewHolder {

    private VB viewBinding;

    public BindViewHolder(@NonNull VB viewBinding) {
        super(viewBinding.getRoot());
        this.viewBinding = viewBinding;
    }

    public VB getViewBinding() {
        return viewBinding;
    }
}