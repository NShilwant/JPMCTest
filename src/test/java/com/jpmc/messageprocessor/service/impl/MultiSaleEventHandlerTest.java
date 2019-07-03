package com.jpmc.messageprocessor.service.impl;

import com.jpmc.messageprocessor.datastore.DataStore;
import com.jpmc.messageprocessor.exceptions.EventHandlerException;
import com.jpmc.messageprocessor.model.Adjustment;
import com.jpmc.messageprocessor.model.Event;
import com.jpmc.messageprocessor.model.Sale;
import com.jpmc.messageprocessor.model.enums.AdjustmentType;
import com.jpmc.messageprocessor.model.enums.MessageType;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class MultiSaleEventHandlerTest {

    private MultiSaleEventHandler multiSaleEventHandler;
    private List<Sale> sales;
    private Sale sale1 = new Sale("Apple", BigDecimal.valueOf(0.50), 1);
    private Sale sale2 = new Sale("Orange", BigDecimal.valueOf(0.75), 3);

    @Before
    public void setUp() {
        multiSaleEventHandler = new MultiSaleEventHandler();
        sales = new ArrayList<>();
        DataStore.salesHistory = sales;
    }

    @Test
    public void handleEvent_ok() throws Exception {
        Event event = new Event(MessageType.MULTI_SALE, sale2);
        multiSaleEventHandler.handleEvent(event);
        assertEquals(sale2, DataStore.salesHistory.get(0));
    }

    @Test(expected = EventHandlerException.class)
    public void handleEvent_unit_size_1_should_throw_exception() throws Exception {
        Event event = new Event(MessageType.MULTI_SALE, sale1);
        multiSaleEventHandler.handleEvent(event);
    }

    @Test(expected = EventHandlerException.class)
    public void handleEvent_incorrect_body_type() throws Exception {
        Event event = new Event(MessageType.MULTI_SALE, new Adjustment(AdjustmentType.MULTIPLY,
                "Apple", BigDecimal.valueOf(2)));
        multiSaleEventHandler.handleEvent(event);
    }

}