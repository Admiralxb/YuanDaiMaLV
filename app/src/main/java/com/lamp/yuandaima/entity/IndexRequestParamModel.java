package com.lamp.yuandaima.entity;

/**
 * Created by baishixin on 16/2/24.
 */
public class IndexRequestParamModel {
    private String category;
    private String keywords;
    private String index;

    public IndexRequestParamModel() {
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    public String getIndex() {
        return index;
    }

    public void setIndex(String index) {
        this.index = index;
    }

    public IndexRequestParamModel(String category, String keywords, String index) {

        this.category = category;
        this.keywords = keywords;
        this.index = index;
    }

    @Override
    public String toString() {
        return "IndexRequestParamModel{" +
                "category='" + category + '\'' +
                ", keywords='" + keywords + '\'' +
                ", index=" + index +
                '}';
    }
}
