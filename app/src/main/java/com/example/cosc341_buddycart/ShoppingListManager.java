package com.example.cosc341_buddycart;

import android.content.Context;
import android.content.SharedPreferences;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class ShoppingListManager {
    private static final String PREF_NAME = "ShoppingListsPrefs";
    private static final String LISTS_KEY = "shopping_lists";
    private static final Type LIST_TYPE = new TypeToken<ArrayList<ShoppingList>>(){}.getType();

    public static void saveAllLists(Context context, List<ShoppingList> lists) {
        if (context == null) {
            return;
        }

        SharedPreferences prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        prefs.edit().putString(LISTS_KEY, new Gson().toJson(lists != null ? lists : new ArrayList<>())).apply();
    }

    public static List<ShoppingList> getSavedLists(Context context) {
        if (context == null) {
            return new ArrayList<>();
        }

        SharedPreferences prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        String json = prefs.getString(LISTS_KEY, null);

        if (json == null || json.isEmpty()) {
            return new ArrayList<>();
        }

        try {
            List<ShoppingList> lists = new Gson().fromJson(json, LIST_TYPE);
            return lists != null ? lists : new ArrayList<>();
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }
}