package com.urlshortner.controller;

import com.urlshortner.Service.UrlShortnerService;
import com.urlshortner.model.UrlData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/url")
public class ShortenerController {

    @Autowired
    public UrlShortnerService urlShortnerService;

    @PostMapping("/shortener")
    public ResponseEntity<String> shortUrl(@RequestBody UrlData urlData){
        String shortUrl = urlShortnerService.shortenUrl(urlData);
        return ResponseEntity.ok(shortUrl);
    }

    @GetMapping("/{shortUrl}")
    public ResponseEntity<String> getGivenUrl(@PathVariable String shortUrl){
        String longUrl = urlShortnerService.getUrl(shortUrl);
        if(longUrl != null){
            return ResponseEntity.status(302).header("Location", longUrl).build();
        }
        return ResponseEntity.notFound().build();
    }


}
