package com.qgsstrive.sciencemap;

import android.content.Context;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Properties;

/**
 * @date 2021/2/1 11:20
 */
public class ConfigMgr {
    /** 配置文件 */
    private static final String STR_CONFIG_FILE = "GpsLines.ini";
    private static HashMap<String, Properties> sections = new HashMap<>();
    private static transient Properties properties;
    private static ConfigMgr sConfigMgr;
    private final Context mContext;

    private ConfigMgr(Context context) {
        mContext = context.getApplicationContext();
    }

    public static synchronized ConfigMgr getInstance(Context context) {
        if (null == sConfigMgr) {
            sConfigMgr = new ConfigMgr(context);
        }
        return sConfigMgr;
    }

    public String readProperties(String section, String key) {
        try {
            InputStream inputStream = mContext.getResources().getAssets().open(STR_CONFIG_FILE);
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            read(reader);
            reader.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        Properties p = sections.get(section);

        if (p == null) {
            return null;
        }

        return p.getProperty(key);
    }

    private void read(BufferedReader reader) throws IOException {
        String line;
        while ((line = reader.readLine()) != null) {
            parseLine(line);
        }
    }

    private void parseLine(String line) {
        line = line.trim();
        if (line.matches("\\[.*\\]")) {
            String section = line.replaceFirst("\\[(.*)\\]", "$1");
            properties = new Properties();
            sections.put(section, properties);
        } else if (line.matches(".*=.*")) {
            if (properties != null) {
                int i = line.indexOf('=');
                String name = line.substring(0, i);
                String value = line.substring(i + 1);
                properties.setProperty(name, value);
            }
        }
    }
}
