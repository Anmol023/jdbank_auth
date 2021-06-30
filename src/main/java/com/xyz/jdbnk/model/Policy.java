package com.xyz.jdbnk.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


public class Policy {
    private String id;
    private String name;
    private String companyId;
    private String categoryName;
    private String premium;
    private String image;
    private String claimSettled;
    private String cover;

    public Policy() {
        super();
    }

    public Policy(String id,String name, String companyId, String categoryName, String premium, String image, String cover, String claimSettled) {
        super();
        this.id=id;
        this.name = name;
        this.companyId = companyId;
        this.categoryName = categoryName;
        this.premium = premium;
        this.image = image;
        this.cover = cover;
        this.claimSettled= claimSettled;
    }

    public String getId() {
        return id;
    }

    public String getClaimSettled() {
        return claimSettled;
    }

    public void setClaimSettled(String claimSettled) {
        this.claimSettled = claimSettled;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public void setId(String id) {
        this.id = id;
    }


    public String getName() {
        return name;
    }


    public void setName(String name) {
        this.name = name;
    }


    public String getCompanyId() {
        return companyId;
    }


    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }


    public String getCategoryName() {
        return categoryName;
    }


    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }


    public String getPremium() {
        return premium;
    }


    public void setPremium(String premium) {
        this.premium = premium;
    }


    public String getImage() {
        return image;
    }


    public void setImage(String image) {
        this.image = image;
    }



    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Policy other = (Policy) obj;
        if (name == null) {
            if (other.name != null)
                return false;
        } else if (!name.equals(other.name))
            return false;
        return true;
    }



}