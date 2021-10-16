package com.thinkless.URL.Shortener.controllers;

import com.thinkless.URL.Shortener.service.UrlMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("app")
public class UrlShortenerController {
    final static String REDIRECT = "redirect:";
    @Autowired
    UrlMapper urlMapper;

    @GetMapping("/mapUrl")
    public String mapUrl(@RequestParam(value = "url") String url) {
        return urlMapper.mapUrlAndStore(url);
    }

    @GetMapping("/{mappedUrl}")
    public void redirectToOriginalUrl(@PathVariable(value = "mappedUrl") String mappedUrl,
                                      HttpServletResponse httpServletResponse) {
        httpServletResponse.setHeader("Location", urlMapper.getOriginalUrl(mappedUrl));
        httpServletResponse.setStatus(302);
    }
}
