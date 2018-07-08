
package com.example.nishantvirmani.moneytapwiki.models;

import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Terms {

    @SerializedName("description")
    @Expose
    private List<String> description = null;
    private String descriptionText;

    public List<String> getDescription() {
        return description;
    }

    public void setDescription(List<String> description) {
        this.description = description;
    }

    public String getDescriptionText() {
        StringBuilder builder = new StringBuilder();
        for (String str : description) {
            builder.append(str);
        }
        return builder.toString();
    }
}
