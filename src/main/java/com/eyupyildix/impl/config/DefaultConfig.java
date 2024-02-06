package com.eyupyildix.impl.config;

import com.eyupyildix.config.Config;
import com.google.common.base.Preconditions;
import com.google.inject.Inject;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Properties;

public class DefaultConfig implements Config {

    private final String fileName = "config.properties";
    private final Properties properties = new Properties();

    @Inject
    public DefaultConfig() {
        try {
            properties.load(readFile());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public File getFile() throws Exception {
        URL url = getClass().getClassLoader().getResource(fileName);
        Preconditions.checkNotNull(url, String.format("Could not find resource: %s", fileName));

        return new File(url.toURI());
    }

    @Override
    public InputStream readFile() {
        return getClass().getClassLoader().getResourceAsStream(fileName);
    }

    @Override
    public Object getValue(String key) {
        return properties.get(key);
    }

    @Override
    public void setValue(String key, String value) {
        throw new IllegalStateException("This config file is read-only");
    }
}
