package model;

import java.util.List;

public class TreeItem {
    private String name;
    private List<TreeItem> children;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<TreeItem> getChildren() {
        return children;
    }

    public void setChildren(List<TreeItem> children) {
        this.children = children;
    }
}
