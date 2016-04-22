package com.example.rodri.todo.category;

/**
 * Created by rodri on 4/9/2016.
 */
public class Category {

    private long id;
    private String categoryName;
    private boolean checked = false; // verify if is checked(checkbox)

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public boolean isChecked() {
        return checked;
    }

}
