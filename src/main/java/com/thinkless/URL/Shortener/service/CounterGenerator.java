package com.thinkless.URL.Shortener.service;

import com.thinkless.URL.Shortener.dao.CounterDAO;
import com.thinkless.URL.Shortener.model.Counter;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.atomic.AtomicInteger;

@Service
public class CounterGenerator {

    CounterDAO counterDAO;
    AtomicInteger currentCounter;
    AtomicInteger maxCounterRange;
    ObjectId activeCounterId;

    @Autowired
    public CounterGenerator(CounterDAO counterDAO) {
        this.counterDAO = counterDAO;
        setActiveCounterInfo();
    }

    private void setActiveCounterInfo() {
        Counter counter = counterDAO.getActiveCounter();
        this.currentCounter = new AtomicInteger(counter.getCurrentCounter());
        this.maxCounterRange = new AtomicInteger(counter.getCounterEnd());
        this.activeCounterId = counter.getId();
    }

    public Integer getNextCounter() {
        if (currentCounter.get() >= maxCounterRange.get()) {
            counterDAO.markNonActiveCounter(this.activeCounterId);
            setActiveCounterInfo();
        }
        counterDAO.incrementCounter(this.activeCounterId);
        return currentCounter.getAndIncrement();
    }
}
