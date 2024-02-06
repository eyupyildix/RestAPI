package com.eyupyildix.config;

import java.io.File;
import java.io.InputStream;

public interface Config {

    File getFile() throws Exception;
    InputStream readFile();
    Object getValue(String key);
    void setValue(String key, String value);

}
