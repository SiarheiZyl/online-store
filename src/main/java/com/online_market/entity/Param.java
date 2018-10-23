package com.online_market.entity;

import javax.persistence.*;

@Entity
@Table(name = "params")
public class Param {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "param_id")
    private int paramId;

    @Column(name = "param_name")
    private String paramName;

    public Param() {
    }

    public int getParamId() {
        return paramId;
    }

    public void setParamId(int paramId) {
        this.paramId = paramId;
    }

    public String getParamName() {
        return paramName;
    }

    public void setParamName(String paramName) {
        this.paramName = paramName;
    }
}
