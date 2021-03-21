package ru.otus.model;

import java.util.List;

public class ObjectForMessage implements Cloneable {
    private List<String> data;

    public List<String> getData() {
        return data;
    }

    public void setData(List<String> data) {
        this.data = data;
    }

    @Override
    public Object clone() {

        var newObj = new ObjectForMessage();
        newObj.setData(data);
        return newObj;
    }
}
