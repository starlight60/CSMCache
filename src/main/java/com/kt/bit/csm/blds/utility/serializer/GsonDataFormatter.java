package com.kt.bit.csm.blds.utility.serializer;

import com.google.gson.*;
import com.kt.bit.csm.blds.cache.CachedResultSet;
import com.kt.bit.csm.blds.cache.util.DataFormatter;

import javax.swing.text.DateFormatter;
import java.lang.reflect.Type;
import java.sql.Timestamp;

public class GsonDataFormatter implements DataFormatter {

    private static Gson gson;
    private volatile static DataFormatter instance;

    public static DataFormatter getInstance() {
        if (instance==null) {
            synchronized (DateFormatter.class) {
                instance = new GsonDataFormatter();
            }
        }
        return instance;
    }

    private GsonDataFormatter() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(Timestamp.class, new TimestampSerializer());
        gson = gsonBuilder.create();
    }

    private class TimestampSerializer implements JsonSerializer<Timestamp> {
        public JsonElement serialize(Timestamp timestamp, Type type, JsonSerializationContext jsonSerializationContext) {
            return new JsonPrimitive(String.valueOf(timestamp.getTime()));
        }
    }

    public String toJson(CachedResultSet resultSet) {
        if (resultSet!=null)
           return gson.toJson(resultSet);
        return null;
    }

    public CachedResultSet fromJson(String json) {
        if (json!=null) {
            return gson.fromJson(json, CachedResultSet.class);
        }
        return null;
    }
}