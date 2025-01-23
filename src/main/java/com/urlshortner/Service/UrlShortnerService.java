package com.urlshortner.Service;

import com.urlshortner.model.UrlData;
import io.micrometer.common.util.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Random;

@Service
public class UrlShortnerService {

    final Map<String, String> shortLongMap = new HashMap<>();
    final Map<String, String> longShortMap = new HashMap<>();

    final String CHARACTERS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

    private final Random random= new Random();
    private static final int shortUrlLength = 10;

    @Value("${baseUrl}")
    private String baseUrl;


    public String shortenUrl(UrlData urlData) {
        if(Objects.isNull(urlData) || StringUtils.isBlank(urlData.getUrl())){
            throw new IllegalArgumentException("Invalid URL");
        }
        String longUrl = urlData.getUrl();
        if(longShortMap.containsKey(longUrl)){
            return baseUrl+longShortMap.get(longUrl);
        }

        String code;
        do {
            code = generateRandomString(shortUrlLength);
        } while (shortLongMap.containsKey(code));

        shortLongMap.put(code,longUrl);
        longShortMap.put(longUrl,code);
        return baseUrl+code;
    }

    private String generateRandomString(int shortUrlLength) {
        StringBuilder urlCode = new StringBuilder(shortUrlLength);
        for(int i = 0; i < shortUrlLength; i++) {
            urlCode.append(CHARACTERS.charAt(random.nextInt(CHARACTERS.length())));
        }
        return urlCode.toString();
    }

    public  String getUrl(String shortUrl){
        return shortLongMap.get(shortUrl);
    }


}
