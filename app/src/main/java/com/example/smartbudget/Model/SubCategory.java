package com.example.smartbudget.Model;

import android.content.Context;

import java.util.Objects;

public class SubCategory {
    private final String id;
    private String visibleName;
    private final int iconResourceID;
    private final int backgroundColor;
    private int visibleNameResourceID;
    private final String categoryId;

    public SubCategory(String id, int visibleNameResourceID, int iconResourceID, int backgroundColor, String categoryId) {
        this.id = id;
        this.visibleNameResourceID = visibleNameResourceID;
        this.iconResourceID = iconResourceID;
        this.backgroundColor = backgroundColor;
        this.categoryId = categoryId;
    }

    public SubCategory(String id, String visibleName, int iconResourceID, int backgroundColor, String categoryId) {
        this.id = id;
        this.categoryId = categoryId;
        this.visibleName = visibleName;
        this.iconResourceID = iconResourceID;
        this.backgroundColor = backgroundColor;
    }

    public String getCategoryVisibleName(Context context) {
        if (visibleName != null)
            return visibleName;
        return context.getResources().getString(visibleNameResourceID);
    }

    public String getCategoryId() {
        return categoryId;
    }

    public String getId() {
        return id;
    }

    public int getIconResourceID() {
        return iconResourceID;
    }

    public int getIconColor() {
        return backgroundColor;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, visibleName, iconResourceID, backgroundColor, visibleNameResourceID);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SubCategory that = (SubCategory) o;
        return iconResourceID == that.iconResourceID &&
                backgroundColor == that.backgroundColor &&
                visibleNameResourceID == that.visibleNameResourceID &&
                Objects.equals(id, that.id) &&
                Objects.equals(visibleName, that.visibleName);
    }

}
