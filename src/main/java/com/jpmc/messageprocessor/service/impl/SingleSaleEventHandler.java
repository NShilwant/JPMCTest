package com.jpmc.messageprocessor.service.impl;

import com.jpmc.messageprocessor.datastore.DataStore;
import com.jpmc.messageprocessor.exceptions.EventHandlerException;
import com.jpmc.messageprocessor.model.Event;
import com.jpmc.messageprocessor.service.EventHandler;
import com.jpmc.messageprocessor.model.Sale;

public class SingleSaleEventHandler implements EventHandler {

    @Override
    public void handleEvent(Event event) throws EventHandlerException {
        if (!(event.getEventBody() instanceof Sale)) {
            throw new EventHandlerException("Event body for SingleSale event is not of type Sale");
        }

        Sale sale = (Sale) event.getEventBody();
        if (sale.getTotalUnits() != 1) {
            throw new EventHandlerException("SingleSale has incorrect totalUnits value=" + sale.getTotalUnits());
        }

        DataStore.addSale(sale);
    }
}
