package com.babyangel.modules.ueditor.entity;

import java.util.List;
import java.util.Map;

public class UeditorResultListEntity {
    private  String state;
    private List<UrlEntity> list;
    private  int start;
    private  int total;

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<UrlEntity> getList() {
        return list;
    }

    public void setList(List<UrlEntity> list) {
        this.list = list;
    }
}
