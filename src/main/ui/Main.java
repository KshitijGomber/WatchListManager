package ui;

//public class Main {
//    public static void main(String[] args) {
//        new WatchlistApp();
//    }
//}

import model.Event;
import model.EventLog;

public class Main {
    public static void main(String[] args) {
        new WatchlistGUI(new WatchlistApp());
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            EventLog eventLog = EventLog.getInstance();
            System.out.println("Event Log:");
            for (Event event : eventLog) {
                System.out.println(event);
            }
        }));

    }
}
