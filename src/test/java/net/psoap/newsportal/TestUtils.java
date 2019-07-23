package net.psoap.newsportal;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.List;

public class TestUtils {
    public static final String URL = "http://localhost:9090/api/v1/";
    public static final String BEARER_TOKEN = "BearereyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIxIiwiaXNzIjoicHNvYXAiLCJpYXQiOjE1NTA2NDk0MDQsImV4cCI6MTU1MTI0OTQwNH0.G1D2bZY0RksBMy09KoIw_9qJW02XOJrBjvzvFYjTZOY";

    public static List jsonToList(String json, TypeToken token) {
        Gson gson = new Gson();
        return gson.fromJson(json, token.getType());
    }

    public static String objectToJson(Object obj) {
        Gson gson = new Gson();
        return gson.toJson(obj);
    }

    public static <T> T jsonToObject(String json, Class<T> classOf) {
        Gson gson = new Gson();
        return gson.fromJson(json, classOf);
    }
}
