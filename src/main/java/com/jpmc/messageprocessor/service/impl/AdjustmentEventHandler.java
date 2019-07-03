package com.jpmc.messageprocessor.service.impl;

import com.jpmc.messageprocessor.datastore.DataStore;
import com.jpmc.messageprocessor.model.Event;
import com.jpmc.messageprocessor.exceptions.EventHandlerException;
import com.jpmc.messageprocessor.model.Adjustment;
import com.jpmc.messageprocessor.model.Sale;
import com.jpmc.messageprocessor.model.enums.AdjustmentType;
import com.jpmc.messageprocessor.service.EventHandler;

import java.math.BigDecimal;

public class AdjustmentEventHandler implements EventHandler {
    @Override
    public void handleEvent(Event event) throws EventHandlerException {
        if (!(event.getEventBody() instanceof Adjustment)) {
            throw new EventHandlerException("Event body for Adjustment event is not of type Adjustment");
        }

        Adjustment adjustment = (Adjustment) event.getEventBody();

        applyAdjustment(adjustment);

        DataStore.addAdjustment(adjustment);
    }

    private void applyAdjustment(Adjustment adjustment) {
        for (Sale sale : DataStore.salesHistory) {
            if (sale.getProductName().equals(adjustment.getProductName())) {

                if (adjustment.getType() == AdjustmentType.ADD) {
                    sale.setUnitPrice(sale.getUnitPrice().add(adjustment.getAmount()));
                } else if (adjustment.getType() == AdjustmentType.MULTIPLY) {
                    sale.setUnitPrice(sale.getUnitPrice().multiply(adjustment.getAmount()));
                } else if (adjustment.getType() == AdjustmentType.SUBTRACT) {
                    BigDecimal newPrice = sale.getUnitPrice().subtract(adjustment.getAmount());
                    newPrice = newPrice.compareTo(BigDecimal.ZERO) < 0 ? BigDecimal.ZERO : newPrice;
                    sale.setUnitPrice(newPrice);
                }
            }
        }
    }
}
