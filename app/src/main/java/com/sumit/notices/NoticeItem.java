package com.sumit.notices;

public class NoticeItem {
    private String title;
    private String date;
    private String link;
    private String type;

    public NoticeItem() {
    }

    public NoticeItem(String title, String date, String link, String type) {
        this.title = title;
        this.date = date;
        this.link = link;
        this.type = type;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getLink() {
        return link;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDate() {
        return date;
    }

    public void setType(String type) { this.type = type; }

    public String getType() { return type; }
}
