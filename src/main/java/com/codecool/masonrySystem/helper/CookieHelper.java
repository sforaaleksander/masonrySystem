package com.codecool.masonrySystem.helper;

import com.sun.net.httpserver.HttpExchange;

import java.net.HttpCookie;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CookieHelper {
    private static final String SESSION_COOKIE_NAME = "sessionId";

    public List<HttpCookie> parseCookies(String cookieString){
        List<HttpCookie> cookies = new ArrayList<>();
        if(cookieString == null || cookieString.isEmpty()){
            return cookies;
        }
        for(String cookie : cookieString.split(";")){
            int indexOfEq = cookie.indexOf('=');
            String cookieName = cookie.substring(0, indexOfEq);
            String cookieValue = cookie.substring(indexOfEq + 1);
            cookies.add(new HttpCookie(cookieName, cookieValue));
        }
        return cookies;
    }

    public Optional<HttpCookie> findCookieByName(String name, List<HttpCookie> cookies){
        for(HttpCookie cookie : cookies){
            if(cookie.getName().equals(name))
                return Optional.of(cookie);
        }
        return Optional.empty();
    }

    public void createCookie(HttpExchange httpExchange, String name, String value) {
        Optional<HttpCookie> cookie;
        cookie = Optional.of(new HttpCookie(name, value));
        cookie.get().setPath("/login/");
        String cookieString = cookie.get().toString();
        httpExchange.getResponseHeaders().add("Set-Cookie", cookieString);
    }

    public String getSessionIdFromCookie(HttpCookie cookie) {
        return cookie.getValue().replace("\"", "");
    }

    public Optional<HttpCookie> getSessionIdCookie(HttpExchange httpExchange, String name) {
        String cookieStr = httpExchange.getRequestHeaders().getFirst("Cookie");
        List<HttpCookie> cookies = parseCookies(cookieStr);
        return findCookieByName(name, cookies);
    }

    public static String getSessionCookieName() {
        return SESSION_COOKIE_NAME;
    }
}