package com.eyupyildix.impl.config;

import com.eyupyildix.config.Config;
import com.google.inject.Inject;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.jar.Manifest;

public class ManifestFile implements Config {

    private final Manifest manifest;

    @Inject
    public ManifestFile() {
        try {
            this.manifest = new Manifest(readFile());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public File getFile() {
        return null;
    }

    @Override
    public InputStream readFile() {
        String className = this.getClass().getSimpleName() + ".class";

        try {
            return new URL(getClass().getResource(className).toString().substring(0, getClass().getResource(className).toString().lastIndexOf("!") + 1) + "/META-INF/MANIFEST.MF").openStream();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Object getValue(String key) {
        return manifest.getMainAttributes().getValue(key);
    }

    @Override
    public void setValue(String key, String value) {
        throw new IllegalStateException("This config file is read-only");
    }
}
