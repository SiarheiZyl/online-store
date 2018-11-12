package com.online_market.entity;


import javax.persistence.*;
import java.util.List;

/**
 * Entity class for categories
 *
 * @author Siarhei
 * @version 1.0
 */
@Entity
@Table(name = "categories")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "catergory_id")
    private int categoryId;

    @Column(name = "category_name")
    private String categoryName;

    public Category() {
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    @Override
    public String toString() {
        return categoryName;
    }
}
