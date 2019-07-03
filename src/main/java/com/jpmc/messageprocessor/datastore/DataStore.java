package com.jpmc.messageprocessor.datastore;

import com.jpmc.messageprocessor.model.Event;
import com.jpmc.messageprocessor.exceptions.EmptyMessageQueueException;
import com.jpmc.messageprocessor.model.Adjustment;
import com.jpmc.messageprocessor.model.Sale;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * As a requirement is to keep everything in memory, this class will play the role of a data store
 * which will be directly accessible from the other classes. Ideally there should be a Repository layer,
 * which will play the role of an interface to the actual data store - relational or non-relational DB.
 */
public class DataStore {
    public static List<Sale> salesHistory = new ArrayList<>();
    public static List<Adjustment> adjustmentsHistory = new ArrayList<>();
    public static Queue<Event> messageQueue = new LinkedList<>();

    public static Event nextEvent() throws EmptyMessageQueueException {
        if (messageQueue.isEmpty()) {
            throw new EmptyMessageQueueException("The event queue is empty");
        }
        return messageQueue.poll();
    }

    public static boolean hasNextEvent() {
        return !messageQueue.isEmpty();
    }

    public static int totalSales() {
        return salesHistory.size();
    }

    public static void addSale(Sale sale) {
        salesHistory.add(sale);
    }

    public static void addAdjustment(Adjustment adjustment) {
        adjustmentsHistory.add(adjustment);
    }

}
