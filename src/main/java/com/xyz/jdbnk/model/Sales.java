package com.xyz.jdbnk.model;

import java.util.List;

public class Sales {

    private List<SoldItem> soldItems;
    private Integer total;

    public Sales(){super();}

    public Sales(List<SoldItem> soldItems, Integer total) {
        this.soldItems = soldItems;
        this.total = total;
    }

    public List<SoldItem> getSoldItems() {
        return soldItems;
    }

    public void setSoldItems(List<SoldItem> soldItems) {
        this.soldItems = soldItems;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }
}
