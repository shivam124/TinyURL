package com.thinkless.URL.Shortener.dao;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Sorts;
import com.mongodb.client.model.Updates;
import com.thinkless.URL.Shortener.model.Counter;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import static com.thinkless.URL.Shortener.utils.CounterFactory.createCounterFromMaxCounterRange;
import static com.thinkless.URL.Shortener.utils.CounterFactory.defaultCounter;

@Repository
public class CounterDAO {
    private static final String _ID = "_id";
    private static final String NODE_ID = "nodeId";
    private static final String IS_ACTIVE = "active";
    private static final String MAX_COUNTER = "counterEnd";
    private static final String CURRENT_COUNTER = "currentCounter";
    private static final String COUNTER_COLLECTION_NAME = "counters";

    private final Integer nodeId;
    private final MongoCollection<Counter> mongoCollection;

    @Autowired
    public CounterDAO(MongoDatabase mongoDatabase,
                      @Value("${node.id}") Integer nodeId) {
        mongoCollection = mongoDatabase.getCollection(COUNTER_COLLECTION_NAME, Counter.class);
        this.nodeId = nodeId;
    }

    public Counter getActiveCounter() {
        Counter activeCounter = mongoCollection.find(Filters.and(Filters.eq(NODE_ID, this.nodeId),
                Filters.eq(IS_ACTIVE, true))).first();
        if (activeCounter == null) {
            return allocateNewCounterRange();
        } else {
            return activeCounter;
        }
    }

    @SuppressWarnings("ConstantConditions")
    private Counter allocateNewCounterRange() {
        Counter newCounter;
        if (mongoCollection.countDocuments() == 0) {
            newCounter = defaultCounter(nodeId);
        } else {
            Counter lastCounter = mongoCollection.find()
                    .sort(Sorts.descending(MAX_COUNTER)).limit(1).first();
            newCounter = createCounterFromMaxCounterRange(nodeId, lastCounter);
        }
        mongoCollection.insertOne(newCounter);
        return newCounter;
    }

    public void incrementCounter(ObjectId id) {
        mongoCollection.updateOne(Filters.eq(_ID, id), Updates.inc(CURRENT_COUNTER, 1));
    }

    public void markNonActiveCounter(ObjectId id) {
        mongoCollection.updateOne(Filters.eq(_ID, id), Updates.set(IS_ACTIVE, false));
    }
}
