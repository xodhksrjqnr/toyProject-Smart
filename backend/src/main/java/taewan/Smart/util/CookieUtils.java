package taewan.Smart.util;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CookieUtils {

    public static Cookie findCookie(Cookie[] cookies, String name) {
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(name))
                    return cookie;
            }
        }
        return null;
    }

    public static void expireCookie(HttpServletRequest request, HttpServletResponse response, String cookieName) {
        Cookie cookie = findCookie(request.getCookies(), cookieName);
        cookie.setMaxAge(0);
        response.addCookie(cookie);
    }
}
