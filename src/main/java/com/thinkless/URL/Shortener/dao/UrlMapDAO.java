package com.thinkless.URL.Shortener.dao;


import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.thinkless.URL.Shortener.model.UrlMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class UrlMapDAO {

    public static final String MAPPED_URL = "mappedURL";
    private static final String URL_COLLECTION_NAME = "urls";
    private final MongoCollection<UrlMap> mongoCollection;

    @Autowired
    public UrlMapDAO(MongoDatabase mongoDatabase) {
        mongoCollection = mongoDatabase.getCollection(URL_COLLECTION_NAME, UrlMap.class);
    }

    public void putUrlMap(UrlMap urlMap) {
        mongoCollection.insertOne(urlMap);
    }

    public UrlMap getUrlMapForToMappedUrl(String mappedUrl) {
        return mongoCollection.find(Filters.eq(MAPPED_URL, mappedUrl)).first();
    }
}
