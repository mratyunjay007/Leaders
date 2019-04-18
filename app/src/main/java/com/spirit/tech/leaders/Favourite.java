package com.spirit.tech.leaders;

public class Favourite
{
    private String id;
    private String leadername;
    private String segment;
    private String data;
    private int position;
    private String title;
    private int color;

    public Favourite(String id, String leadername, String segment, String data, int position, String title,int color) {
        this.id = id;
        this.leadername = leadername;
        this.segment = segment;
        this.data = data;
        this.position = position;
        this.title=title;
        this.color = color;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLeadername() {
        return leadername;
    }

    public void setLeadername(String leadername) {
        this.leadername = leadername;
    }

    public String getSegment() {
        return segment;
    }

    public void setSegment(String segment) {
        this.segment = segment;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }
}
