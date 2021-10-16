package com.thinkless.URL.Shortener.model;

public class UrlMap {

    String originalURL;
    String mappedURL;

    public String getOriginalURL() {
        return originalURL;
    }

    public void setOriginalURL(String originalURL) {
        this.originalURL = originalURL;
    }

    public String getMappedURL() {
        return mappedURL;
    }

    public void setMappedURL(String mappedURL) {
        this.mappedURL = mappedURL;
    }

    public UrlMap() {
    }

    public UrlMap(String originalURL, String mappedURL) {
        this.originalURL = originalURL;
        this.mappedURL = mappedURL;
    }
}
