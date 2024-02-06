package com.eyupyildix.module;

import java.util.List;

public interface Holder<V> {

    List<V> getRegisteredValues();
    void registerValue(V value);

}
