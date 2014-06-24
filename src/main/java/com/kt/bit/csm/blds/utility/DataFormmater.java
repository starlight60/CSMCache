package com.kt.bit.csm.blds.utility;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.kt.bit.csm.blds.cache.CachedResultSet;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

/**
 * Created with IntelliJ IDEA.
 * User: toto
 * Date: 14. 6. 20
 * Time: 오후 3:14
 * To change this template use File | Settings | File Templates.
 */
public class DataFormmater {

    public static String toJson(CachedResultSet resultSet) {

        Gson gson = new Gson();
        JsonElement je = gson.toJsonTree(resultSet);

        return je.toString();

    }

    public static CachedResultSet fromJson(String json) {

        Gson gson = new Gson();
        CachedResultSet resultSet = gson.fromJson(json, CachedResultSet.class);

        return resultSet;

    }

    public static byte[] stringToByteArray(String str) {

        return str.getBytes();

    }

    public static byte[] objectToByteArray(Object obj) {

        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        byte[] data = null;

        try {
            ObjectOutputStream out = new ObjectOutputStream(bos);
            out.writeObject(obj);
            data = bos.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return data;

    }
}
