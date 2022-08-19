package com.forest.bss.sdk.util;

import com.google.gson.Gson;

import java.util.HashMap;

import okhttp3.MediaType;
import okhttp3.RequestBody;

public class RequestArgUtil {

    public static RequestBody convert(HashMap<String, String> args) {

        StringBuilder sb = new StringBuilder();
        sb.append("{");
        for (String key : args.keySet()) {

            sb.append("'");
            sb.append(key);
            sb.append("'");

            sb.append(":");


            sb.append("'");
            sb.append(args.get(key));
            sb.append("'");

            sb.append(",");
        }

        sb.deleteCharAt(sb.length() - 1);

        sb.append("}");

        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), sb.toString());
        return requestBody;
    }

    public static RequestBody convert2(HashMap<String, String> args) {
        return RequestBody.create(MediaType.parse("application/json"), new Gson().toJson(args));
    }

}
