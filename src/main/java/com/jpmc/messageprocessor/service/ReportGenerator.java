package com.jpmc.messageprocessor.service;

import com.jpmc.messageprocessor.model.Adjustment;
import com.jpmc.messageprocessor.model.Sale;

import java.util.List;

public interface ReportGenerator {
    String generateSalesReport(List<Sale> sales);
    String generateAdjustmentsReport(List<Adjustment> adjustments);
}
