package com.project.bookstore.Model;

import java.math.BigDecimal;

public class ResponseOrderSummary {

    private BigDecimal price;
    
    public ResponseOrderSummary(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
    
}
