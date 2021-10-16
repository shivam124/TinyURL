package com.thinkless.URL.Shortener.service;

import com.thinkless.URL.Shortener.dao.UrlMapDAO;
import com.thinkless.URL.Shortener.model.UrlMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class UrlMapper {
    public static final String BASE62 = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz1234567890";
    static private final Random randomGenerator = new Random();
    private final CounterGenerator counterGenerator;
    private final UrlMapDAO urlMapDAO;

    @Autowired
    UrlMapper(CounterGenerator counterGenerator, UrlMapDAO urlMapDAO) {
        this.counterGenerator = counterGenerator;
        this.urlMapDAO = urlMapDAO;
    }

    public String mapUrlAndStore(String url) {
        String mappedUrl = convertToBase62(counterGenerator.getNextCounter());
        UrlMap urlMap = new UrlMap(url, mappedUrl);
        urlMapDAO.putUrlMap(urlMap);
        return mappedUrl;
    }

    public String getOriginalUrl(String mappedUrl) {
        UrlMap urlMap = urlMapDAO.getUrlMapForToMappedUrl(mappedUrl);
        if (urlMap == null) {
            return "Url doesn't exist";
        }
        return urlMap.getOriginalURL();
    }

    String convertToBase62(int counter) {
        long mapValue = (((long) counter) << 8) + (long) (byte) randomGenerator.nextLong();
        StringBuilder stringBuilder = new StringBuilder();
        while (mapValue != 0) {
            stringBuilder.append(BASE62.charAt((int) (mapValue % 62)));
            mapValue /= 62;
        }
        if (stringBuilder.length() == 0) {
            stringBuilder.append('A');
        }
        return stringBuilder.toString();
    }
}
