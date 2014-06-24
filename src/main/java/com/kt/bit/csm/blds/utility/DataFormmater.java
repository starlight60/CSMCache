package com.kt.bit.csm.blds.utility;

import com.google.gson.*;
import com.kt.bit.csm.blds.cache.CachedResultSet;

import javax.swing.text.DateFormatter;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.lang.reflect.Type;
import java.sql.Timestamp;

public class DataFormmater {

    private static Gson gson;
    private volatile static DataFormmater instance;

    public static DataFormmater getInstance() {
        if (instance==null) {
            synchronized (DateFormatter.class) {
                instance = new DataFormmater();
            }
        }
        return instance;
    }

    private DataFormmater() {
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
        JsonElement je = gson.toJsonTree(resultSet);
        return je.toString();

    }

    public CachedResultSet fromJson(String json) {
        return gson.fromJson(json, CachedResultSet.class);
    }
}
