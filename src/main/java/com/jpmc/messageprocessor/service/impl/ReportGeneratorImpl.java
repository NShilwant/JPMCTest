package com.jpmc.messageprocessor.service.impl;

import com.jpmc.messageprocessor.model.Adjustment;
import com.jpmc.messageprocessor.model.Sale;
import com.jpmc.messageprocessor.service.ReportGenerator;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ReportGeneratorImpl implements ReportGenerator {

    @Override
    public String generateSalesReport(List<Sale> sales) {
        Map<String, BigDecimal> productTotalSum = new HashMap<>();
        Map<String, Integer> productCounts = new HashMap<>();

        BigDecimal total = BigDecimal.ZERO;
        int totalUnitsSold = 0;
        for (Sale sale : sales) {
            if (!productTotalSum.containsKey(sale.getProductName())) {
                BigDecimal curSum = sale.getUnitPrice().multiply(BigDecimal.valueOf(sale.getTotalUnits()));
                productTotalSum.put(sale.getProductName(), curSum);

            } else {
                BigDecimal curSum = productTotalSum.get(sale.getProductName());
                curSum = curSum.add(sale.getUnitPrice().multiply(BigDecimal.valueOf(sale.getTotalUnits())));
                productTotalSum.put(sale.getProductName(), curSum);
            }

            if (!productCounts.containsKey(sale.getProductName())) {
                productCounts.put(sale.getProductName(), sale.getTotalUnits());
            } else {
                productCounts.put(sale.getProductName(), productCounts.get(sale.getProductName()) + sale.getTotalUnits());
            }
        }

        StringBuilder sb = new StringBuilder();
        sb.append("=========== SALES REPORT ===========\n");
        for (Map.Entry<String, BigDecimal> entry : productTotalSum.entrySet()) {
            totalUnitsSold += productCounts.get(entry.getKey());
            sb.append("# ")
                    .append(entry.getKey())
                    .append(" - units sold: ")
                    .append(productCounts.get(entry.getKey()))
                    .append(" , sales amount: £")
                    .append(entry.getValue().toString())
                    .append("\n");
            total = total.add(entry.getValue());
        }
        sb.append("Total units sold - " + totalUnitsSold)
                .append("\n")
                .append("All sales - £")
                .append(total.toString())
                .append("\n\n");

        return sb.toString();
    }

    @Override
    public String generateAdjustmentsReport(List<Adjustment> adjustments) {
        StringBuilder sb = new StringBuilder();
        sb.append("The applicationg is pausing and will stop processing new messages.\n");
        sb.append("=========== ADJUSTMENTS REPORT ===========\n");
        for (Adjustment adjustment : adjustments) {
            sb.append("# ")
                    .append(adjustment)
                    .append("\n");
        }
        return sb.toString();
    }
}
