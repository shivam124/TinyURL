package com.thinkless.URL.Shortener.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.bson.types.ObjectId;

public class Counter {
    ObjectId id;

    @JsonProperty("nodeId")
    Integer nodeId;

    @JsonProperty("counterStart")
    Integer counterStart;

    @JsonProperty("counterEnd")
    Integer counterEnd;

    @JsonProperty("currentCounter")
    Integer currentCounter;

    @JsonProperty("active")
    boolean isActive;

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public Integer getNodeId() {
        return nodeId;
    }

    public void setNodeId(Integer nodeId) {
        this.nodeId = nodeId;
    }

    public Integer getCounterStart() {
        return counterStart;
    }

    public void setCounterStart(Integer counterStart) {
        this.counterStart = counterStart;
    }

    public Integer getCounterEnd() {
        return counterEnd;
    }

    public void setCounterEnd(Integer counterEnd) {
        this.counterEnd = counterEnd;
    }

    public Integer getCurrentCounter() {
        return currentCounter;
    }

    public void setCurrentCounter(Integer currentCounter) {
        this.currentCounter = currentCounter;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public Counter() {
    }

    public Counter(Integer nodeId, Integer counterStart, Integer counterEnd, Integer currentCounter, boolean isActive) {
        this.id = new ObjectId();
        this.nodeId = nodeId;
        this.counterStart = counterStart;
        this.counterEnd = counterEnd;
        this.currentCounter = currentCounter;
        this.isActive = isActive;
    }
}
