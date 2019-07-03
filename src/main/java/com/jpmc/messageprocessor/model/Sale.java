package com.jpmc.messageprocessor.model;

import java.math.BigDecimal;

public class Sale {
    private String productName;
    private BigDecimal unitPrice;
    private int totalUnits;

    public Sale(String productName, BigDecimal unitPrice, int totalUnits) {
        this.productName = productName;
        this.unitPrice = unitPrice;
        this.totalUnits = totalUnits;
    }

    public String getProductName() {
        return productName;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public int getTotalUnits() {
        return totalUnits;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }

    public void setTotalUnits(int totalUnits) {
        this.totalUnits = totalUnits;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Sale sale = (Sale) o;

        if (totalUnits != sale.totalUnits) return false;
        if (productName != null ? !productName.equals(sale.productName) : sale.productName != null) return false;
        return unitPrice != null ? unitPrice.equals(sale.unitPrice) : sale.unitPrice == null;
    }

    @Override
    public int hashCode() {
        int result = productName != null ? productName.hashCode() : 0;
        result = 31 * result + (unitPrice != null ? unitPrice.hashCode() : 0);
        result = 31 * result + totalUnits;
        return result;
    }

    @Override
    public String toString() {
        return "Sale{" +
                "productName='" + productName + '\'' +
                ", unitPrice=" + unitPrice +
                ", totalUnits=" + totalUnits +
                '}';
    }
}
