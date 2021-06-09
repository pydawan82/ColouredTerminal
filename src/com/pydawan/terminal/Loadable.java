package com.pydawan.terminal;

import java.util.concurrent.atomic.AtomicReference;

@FunctionalInterface
public interface Loadable {

    public void load(AtomicReference<Double> status);
    
}
