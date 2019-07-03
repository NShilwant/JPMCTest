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

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class AdjustmentEventHandlerTest {

    private AdjustmentEventHandler adjustmentEventHandler;
    private List<Sale> sales;
    private Sale sale1 = new Sale("Apple", BigDecimal.valueOf(0.50), 1);
    private Sale sale2 = new Sale("Orange", BigDecimal.valueOf(0.75), 1);
    private Sale sale3 = new Sale("Apple", BigDecimal.valueOf(0.50), 2);

    @Before
    public void setUp() {
        sales = new ArrayList<>();
        sales.add(sale1);
        sales.add(sale2);
        sales.add(sale3);
        adjustmentEventHandler = new AdjustmentEventHandler();
        DataStore.salesHistory = sales;
    }

    @Test
    public void handleEvent_add() throws Exception {
        Event event = new Event(MessageType.ADJUSTMENT, new Adjustment(AdjustmentType.ADD,
                "Apple", BigDecimal.valueOf(0.30)));

        adjustmentEventHandler.handleEvent(event);
        for (Sale sale : DataStore.salesHistory) {
            if (sale.getProductName().equals("Apple")) {
                assertEquals(BigDecimal.valueOf(0.80), sale.getUnitPrice());
            } else if (sale.getProductName().equals("Orange")) {
                assertEquals(BigDecimal.valueOf(0.75), sale.getUnitPrice());
            }
        }
    }

    @Test
    public void handleEvent_subtract() throws Exception {
        Event event = new Event(MessageType.ADJUSTMENT, new Adjustment(AdjustmentType.SUBTRACT,
                "Apple", BigDecimal.valueOf(0.30)));

        adjustmentEventHandler.handleEvent(event);
        for (Sale sale : DataStore.salesHistory) {
            if (sale.getProductName().equals("Apple")) {
                assertEquals(BigDecimal.valueOf(0.20), sale.getUnitPrice());
            } else if (sale.getProductName().equals("Orange")) {
                assertEquals(BigDecimal.valueOf(0.75), sale.getUnitPrice());
            }
        }
    }

    @Test
    public void handleEvent_multiply() throws Exception {
        Event event = new Event(MessageType.ADJUSTMENT, new Adjustment(AdjustmentType.MULTIPLY,
                "Apple", BigDecimal.valueOf(2)));

        adjustmentEventHandler.handleEvent(event);
        for (Sale sale : DataStore.salesHistory) {
            if (sale.getProductName().equals("Apple")) {
                assertEquals(BigDecimal.valueOf(1.00), sale.getUnitPrice());
            } else if (sale.getProductName().equals("Orange")) {
                assertEquals(BigDecimal.valueOf(0.75), sale.getUnitPrice());
            }
        }
    }

    @Test(expected = EventHandlerException.class)
    public void handleEvent_incorrect_body_type() throws Exception {
        Event event = new Event(MessageType.MULTI_SALE, sale1);
        adjustmentEventHandler.handleEvent(event);
    }

}