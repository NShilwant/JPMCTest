package com.jpmc.messageprocessor.service;

import com.jpmc.messageprocessor.exceptions.EventHandlerException;
import com.jpmc.messageprocessor.model.Event;

public interface EventHandler {
    void handleEvent(Event event) throws EventHandlerException;
}
