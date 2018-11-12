package com.online_market.entity;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

/**
 * Entity class for items
 *
 * @author Siarhei
 * @version 1.0
 */
@Entity
@Table(name = "items")
public class Item {

    @Transient
    private static final String IMAGES_LOCATION = "/resources/images/";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "item_id")
    private int itemId;

    @Column(name = "item_name")
    private String itemName;

    @Column(name = "price")
    private double price;

    @Column(name = "available_count")
    private int availableCount;

    @Transient
    private String picture = IMAGES_LOCATION + itemId + ".jpg";

    @ManyToOne
    @JoinColumn(name = "item_category")
    private Category category;

    @OneToOne
    @JoinColumn(name = "params")
    private Param params;

//    @ManyToMany(fetch = FetchType.EAGER)
//    @JoinTable(
//            name = "item_params",
//            joinColumns = { @JoinColumn(name = "item") },
//            inverseJoinColumns = { @JoinColumn(name = "param") }
//    )
//    private List<Param> params;

    public Item() {
    }

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }


    public int getAvailableCount() {
        return availableCount;
    }

    public void setAvailableCount(int availableCount) {
        this.availableCount = availableCount;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Param getParams() {
        return params;
    }

    public void setParams(Param params) {
        this.params = params;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Item item = (Item) o;
        return getItemId() == item.getItemId();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getItemId());
    }

    @Override
    public String toString() {
        return "Item [id=" + itemId + ",item name= " + itemName + "]";
    }
}
