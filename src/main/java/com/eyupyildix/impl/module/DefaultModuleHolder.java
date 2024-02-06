package com.eyupyildix.impl.module;

import com.eyupyildix.module.AbstractModule;
import com.eyupyildix.module.Holder;
import com.google.inject.Inject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DefaultModuleHolder implements Holder<AbstractModule> {

    private final List<AbstractModule> modules;

    @Inject
    public DefaultModuleHolder() {
        modules = new ArrayList<>();
    }

    @Override
    public List<AbstractModule> getRegisteredValues() {
        return Collections.unmodifiableList(modules);
    }

    @Override
    public void registerValue(AbstractModule module) {
        modules.add(module);
    }
}
