package com.xyz.jdbnk.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.IndexDirection;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class SoldItem {
    private String id;
    private Policy policy;
    private String customerId;
    private String customerName;
    private String documentUrl;
    @Indexed(direction = IndexDirection.ASCENDING)
    private LocalDate salesDate;
    public SoldItem() {
        super();
    }
    public SoldItem(String id, Policy policy, String customerId,String customerName,String documentUrl, LocalDate salesDate) {
        super();
        this.id = id;
        this.policy = policy;
        this.customerId = customerId;
        this.salesDate = salesDate;
        this.customerName = customerName;
        this.documentUrl = documentUrl;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Policy getPolicy() {
        return policy;
    }
    public void setPolicy(Policy policy) {
        this.policy = policy;
    }
    public String getCustomerId() {
        return customerId;
    }
    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public LocalDate getSalesDate() {
        return salesDate;
    }

    public void setSalesDate(LocalDate salesDate) {
        this.salesDate = salesDate;
    }

    public String getDocumentUrl() {
        return documentUrl;
    }

    public void setDocumentUrl(String documentUrl) {
        this.documentUrl = documentUrl;
    }
}
