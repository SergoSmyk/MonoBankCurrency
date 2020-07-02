package com.sergo_smyk.recycler_binding.recycler_adapters;

import java.util.List;

public interface AdapterWithItems<E> {

    void setItems(List<E> items);

    void notifyInfoChanged();
}
