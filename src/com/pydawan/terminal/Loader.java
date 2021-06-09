package com.pydawan.terminal;
import java.util.concurrent.atomic.AtomicReference;

/**
 * Loader
 */
public final class Loader {

    private static final long REFRESH_DELAY = 500L;

    public static void load(TerminalPrinter term, Loadable loadable) {
        AtomicReference<Double> status = new AtomicReference<>(0d);
        Thread thread = new Thread(() -> loadable.load(status), "Loader");
        thread.start();

        while(thread.isAlive()) {
            term.printLoading(20, status.get());
            try {
                Thread.sleep(REFRESH_DELAY);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}