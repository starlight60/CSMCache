package com.kt.bit.csm.blds.cache.util.serializer;

import com.kt.bit.csm.blds.cache.CachedResultSet;
import com.kt.bit.csm.blds.cache.util.DataFormatter;
import org.boon.Boon;

import javax.swing.text.DateFormatter;

@Deprecated
public class BoonDataFormatter implements DataFormatter {

    private volatile static DataFormatter instance;

    public static DataFormatter getInstance() {
        if (instance==null) {
            synchronized (DateFormatter.class) {
                instance = new BoonDataFormatter();
            }
        }
        return instance;
    }

    private BoonDataFormatter() {
    }

    public String toJson(CachedResultSet resultSet) {
        if (resultSet!=null)
            return Boon.toJson(resultSet);
        return null;
    }

    public CachedResultSet fromJson(String json) {
        if (json!=null) {

            return Boon.fromJson(json, CachedResultSet.class);
        }
        return null;
    }
}