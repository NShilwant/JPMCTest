package com.jpmc.messageprocessor;

import com.jpmc.messageprocessor.model.Adjustment;
import com.jpmc.messageprocessor.model.Event;
import com.jpmc.messageprocessor.model.Sale;
import com.jpmc.messageprocessor.model.enums.AdjustmentType;
import com.jpmc.messageprocessor.model.enums.MessageType;

import java.math.BigDecimal;
import java.util.*;

public class MessageSimulatorInterface {
    Set<String> products = new HashSet<>(Arrays.asList("Apple", "Orange", "Banana", "Pear", "Mango"));
    Set<BigDecimal> adjustmentPrices = new HashSet<>(Arrays.asList(BigDecimal.valueOf(0.50),
            BigDecimal.valueOf(1.50), BigDecimal.valueOf(0.30)));
    Set<Integer> amounts = new HashSet<>(Arrays.asList(3,5,7));
    Set<AdjustmentType> adjustmentTypes = new HashSet<>(Arrays.asList(AdjustmentType.ADD, AdjustmentType.MULTIPLY, AdjustmentType.SUBTRACT));

    Map<String, BigDecimal> productPrices = new HashMap<String, BigDecimal>(){{
        put("Apple", BigDecimal.valueOf(0.50));
        put("Banana", BigDecimal.valueOf(0.20));
        put("Orange", BigDecimal.valueOf(0.40));
        put("Pear", BigDecimal.valueOf(0.45));
        put("Mango", BigDecimal.valueOf(1.00));}};


    public List<Event> generateEvents() {
        List<Event> bagOfEvents = new LinkedList<>();

        for (int i = 1; i < 60; i++) {
            if (i % 11 == 0) {
                bagOfEvents.add(new Event(MessageType.ADJUSTMENT,
                        new Adjustment((AdjustmentType) getRandom(adjustmentTypes),
                                (String) getRandom(products),
                                (BigDecimal) getRandom(adjustmentPrices))));
            }
            else if (i % 5 == 0) {
                String product = (String) getRandom(products);
                bagOfEvents.add(new Event(MessageType.MULTI_SALE,
                        new Sale(product,
                                productPrices.get(product),
                                (Integer) getRandom(amounts))));
            } else {
                String product = (String) getRandom(products);
                bagOfEvents.add(new Event(MessageType.SINGLE_SALE,
                        new Sale(product,
                                productPrices.get(product),
                                1)));
            }
        }

        return bagOfEvents;
    }

    private Object getRandom(Collection e) {
        return e.stream()
                .skip((int) (e.size() * Math.random()))
                .findFirst().get();
    }
}
