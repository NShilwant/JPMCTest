package com.jpmc.messageprocessor.model;

import com.jpmc.messageprocessor.model.enums.MessageType;

public class Event {
    private MessageType messageType;
    private Object eventBody;

    public Event(MessageType messageType, Object eventBody) {
        this.messageType = messageType;
        this.eventBody = eventBody;
    }

    public MessageType getMessageType() {
        return messageType;
    }

    public Object getEventBody() {
        return eventBody;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Event event = (Event) o;

        if (messageType != event.messageType) return false;
        return eventBody != null ? eventBody.equals(event.eventBody) : event.eventBody == null;
    }

    @Override
    public int hashCode() {
        int result = messageType != null ? messageType.hashCode() : 0;
        result = 31 * result + (eventBody != null ? eventBody.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Event{" +
                "messageType=" + messageType +
                ", eventBody=" + eventBody +
                '}';
    }
}
