package com.kt.bit.csm.blds.utility.serializer;

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
            /*
            final CachedResultSet cachedResultSet = new CachedResultSet();
            Object t = Boon.fromJson(json);

            List cacheRows = ((ValueList) t).list();
            for (Object row : cacheRows) {
                ValueContainer valueContainer = (ValueContainer) row;
                CacheRow tRow = new CacheRow();
                for (Map.Entry entry : ((LazyValueMap) valueContainer.value).entrySet()) {

                    for (Map.Entry entry2 : ((LazyValueMap) entry.getValue()).entrySet()) {
                        LazyValueMap columnInfo = (LazyValueMap) entry2.getValue();
                        CacheColumn column = new CacheColumn(columnInfo.get("name").toString(), Integer.valueOf(columnInfo.get("type").toString()), columnInfo.get("value"));
                        tRow.addCacheColumn(entry2.getKey().toString(), column);
                    }
                }

                cachedResultSet.addRow(tRow);
            }
            return cachedResultSet;*/
        }
        return null;
    }
}