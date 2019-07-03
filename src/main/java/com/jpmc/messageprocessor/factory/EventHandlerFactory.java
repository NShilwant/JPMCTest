package com.jpmc.messageprocessor.factory;

import com.jpmc.messageprocessor.model.enums.MessageType;
import com.jpmc.messageprocessor.service.EventHandler;
import com.jpmc.messageprocessor.service.impl.AdjustmentEventHandler;
import com.jpmc.messageprocessor.service.impl.MultiSaleEventHandler;
import com.jpmc.messageprocessor.service.impl.SingleSaleEventHandler;

import java.util.HashMap;
import java.util.Map;

public class EventHandlerFactory {
    private static Map<MessageType, EventHandler> instanceStore;

    static {
        initSingletonStore();
    }

    public static EventHandler getHandler(MessageType type) {
        final EventHandler handler = instanceStore.get(type);

        if (handler == null) {
            System.err.println("Unrecognized event type " + type + ". Ignoring the event");
        }

        return handler;
    }

    // To prevent creation of a number of unused objects
    private static void initSingletonStore() {
        instanceStore = new HashMap<>();
        instanceStore.put(MessageType.SINGLE_SALE, new SingleSaleEventHandler());
        instanceStore.put(MessageType.MULTI_SALE, new MultiSaleEventHandler());
        instanceStore.put(MessageType.ADJUSTMENT, new AdjustmentEventHandler());
    }
}
