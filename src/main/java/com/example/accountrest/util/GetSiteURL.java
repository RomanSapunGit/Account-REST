package com.example.accountrest.util;

import jakarta.servlet.http.HttpServletRequest;

public class GetSiteURL {
    public static String getSiteURL(HttpServletRequest request) {
        String siteURL = request.getRequestURL().toString();
        return siteURL.replace(request.getServletPath(), "");
    }
}