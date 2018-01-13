package com.tofaha.vlsmtofaha.model;

public class Video {
    private String id;
    private String image;
    private String period;
    private String source;
    private String title;

    public Video(){}

    public Video(String image, String title, String period, String source) {
        this.image = image;
        this.title = title;
        this.period = period;
        this.source = source;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImage() {
        return this.image;
    }

    public String getPeriod() {
        return this.period;
    }

    public String getSource() {
        return this.source;
    }

    public String getTitle() {
        return this.title;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
