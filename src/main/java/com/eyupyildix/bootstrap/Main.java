package com.eyupyildix.bootstrap;

import com.eyupyildix.inject.GuiceInjectorModule;
import com.google.inject.Injector;
import com.google.inject.Key;
import com.eyupyildix.module.AbstractModule;
import com.google.inject.name.Names;

import java.util.logging.Level;
import java.util.logging.Logger;

public class Main {
    public static void main(String[] args) {
        Logger.getLogger("org.mongodb.driver").setLevel(Level.SEVERE); // Turn off MongoDB logs

        GuiceInjectorModule injectorModule = new GuiceInjectorModule();
        Injector injector = injectorModule.createInjector();

        AbstractModule mainModule = injector.getInstance(Key.get(AbstractModule.class, Names.named("module-main")));
        mainModule.start();
    }
}