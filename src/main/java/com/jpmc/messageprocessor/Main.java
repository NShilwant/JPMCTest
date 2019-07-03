package com.jpmc.messageprocessor;

import com.jpmc.messageprocessor.datastore.DataStore;
import com.jpmc.messageprocessor.service.MessageProcessor;
import com.jpmc.messageprocessor.service.ReportGenerator;
import com.jpmc.messageprocessor.service.impl.ReportGeneratorImpl;
import com.jpmc.messageprocessor.service.impl.SalesMessageProcessor;

/**
 * This application will generate a number of random Sales events, including single sale, multi sale and adjustment.
 * Then it will process these sales from an event message queue and print reports as required.
 */
public class Main {

    public static void main(String[] args) {
        ReportGenerator reportGenerator = new ReportGeneratorImpl();
        MessageProcessor messageProcessor = new SalesMessageProcessor(reportGenerator);

        MessageSimulatorInterface simulator = new MessageSimulatorInterface();
        DataStore.messageQueue.addAll(simulator.generateEvents());
        messageProcessor.startProcessing();
    }
}
