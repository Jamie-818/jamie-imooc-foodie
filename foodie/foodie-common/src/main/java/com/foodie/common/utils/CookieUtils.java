package com.foodie.common.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

/**
 * Cookie 工具类
 *
 * @author jamie
 */
public final class CookieUtils {

    final static Logger logger = LoggerFactory.getLogger(CookieUtils.class);

    private static final String SPACE = " ";

    private static final String LOCALHOST = "localhost";

    /**
     * 得到Cookie的值, 不编码
     *
     * @param request 请求对象
     * @param cookieName cookie名
     * @return java.lang.String cookie值
     */
    public static String getCookieValue(HttpServletRequest request, String cookieName) {
        return getCookieValue(request, cookieName, false);
    }

    /**
     * 得到Cookie的值
     *
     * @param request 请求对象
     * @param cookieName cookie名
     * @param isDecoder 是否Decoder编码
     * @return java.lang.String
     */
    public static String getCookieValue(HttpServletRequest request, String cookieName, boolean isDecoder) {
        Cookie[] cookieList = request.getCookies();
        if(cookieList == null || cookieName == null){
            return null;
        }
        String retValue = null;
        try{
            for(Cookie cookie: cookieList){
                if(cookie.getName().equals(cookieName)){
                    if(isDecoder){
                        retValue = URLDecoder.decode(cookie.getValue(), "UTF-8");
                    }else{
                        retValue = cookie.getValue();
                    }
                    break;
                }
            }
        }catch(UnsupportedEncodingException e){
            e.printStackTrace();
        }
        return retValue;
    }

    /**
     * 得到Cookie的值
     *
     * @param request 请求对象
     * @param cookieName cookie名
     * @param encodeString encode编码字符串
     * @return java.lang.String
     */
    public static String getCookieValue(HttpServletRequest request, String cookieName, String encodeString) {
        Cookie[] cookieList = request.getCookies();
        if(cookieList == null || cookieName == null){
            return null;
        }
        String retValue = null;
        try{
            for(int i = 0; i < cookieList.length; i++){
                if(cookieList[i].getName().equals(cookieName)){
                    retValue = URLDecoder.decode(cookieList[i].getValue(), encodeString);
                    break;
                }
            }
        }catch(UnsupportedEncodingException e){
            e.printStackTrace();
        }
        return retValue;
    }

    /**
     * 设置Cookie的值 不设置生效时间默认浏览器关闭即失效,也不编码
     *
     * @param request 请求对象
     * @param response 返回对象
     * @param cookieName cookie名
     * @param cookieValue cookie值
     */
    public static void setCookie(HttpServletRequest request, HttpServletResponse response, String cookieName,
                                 String cookieValue) {
        setCookie(request, response, cookieName, cookieValue, -1);
    }

    /**
     * @param request
     * @param response
     * @param cookieName
     * @param cookieValue
     * @param cookieMaxage
     */
    public static void setCookie(HttpServletRequest request, HttpServletResponse response, String cookieName,
                                 String cookieValue, int cookieMaxage) {
        setCookie(request, response, cookieName, cookieValue, cookieMaxage, false);
    }

    /**
     * @param request
     * @param response
     * @param cookieName
     * @param cookieValue
     * @param isEncode
     */
    public static void setCookie(HttpServletRequest request, HttpServletResponse response, String cookieName,
                                 String cookieValue, boolean isEncode) {
        setCookie(request, response, cookieName, cookieValue, -1, isEncode);
    }

    /**
     * @param request
     * @param response
     * @param cookieName
     * @param cookieValue
     * @param cookieMaxage
     * @param isEncode
     */
    public static void setCookie(HttpServletRequest request, HttpServletResponse response, String cookieName,
                                 String cookieValue, int cookieMaxage, boolean isEncode) {
        doSetCookie(request, response, cookieName, cookieValue, cookieMaxage, isEncode);
    }

    /**
     * @param request
     * @param response
     * @param cookieName
     * @param cookieValue
     * @param cookieMaxage
     * @param encodeString
     */
    public static void setCookie(HttpServletRequest request, HttpServletResponse response, String cookieName,
                                 String cookieValue, int cookieMaxage, String encodeString) {
        doSetCookie(request, response, cookieName, cookieValue, cookieMaxage, encodeString);
    }

    /**
     * @param request
     * @param response
     * @param cookieName
     */
    public static void deleteCookie(HttpServletRequest request, HttpServletResponse response, String cookieName) {
        doSetCookie(request, response, cookieName, null, -1, false);
        // doSetCookie(request, response, cookieName, "", -1, false);
    }

    /**
     * @param request
     * @param response
     * @param cookieName
     * @param cookieValue
     * @param cookieMaxage cookie生效的最大秒数
     * @param isEncode
     */
    private static final void doSetCookie(HttpServletRequest request, HttpServletResponse response, String cookieName
            , String cookieValue, int cookieMaxage, boolean isEncode) {
        try{
            if(cookieValue == null){
                cookieValue = "";
            }else if(isEncode){
                cookieValue = URLEncoder.encode(cookieValue, "utf-8");
            }
            Cookie cookie = new Cookie(cookieName, cookieValue);
            if(cookieMaxage > 0){
                cookie.setMaxAge(cookieMaxage);
            }
            if(null != request){
                // 设置域名的cookie
                String domainName = getDomainName(request);
                logger.info("========== domainName: {} ==========", domainName);
                if(!LOCALHOST.equals(domainName)){
                    cookie.setDomain(domainName);
                }
            }
            cookie.setPath("/");
            response.addCookie(cookie);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    /**
     * @param request
     * @param response
     * @param cookieName
     * @param cookieValue
     * @param cookieMaxage cookie生效的最大秒数
     * @param encodeString
     */
    private static final void doSetCookie(HttpServletRequest request, HttpServletResponse response, String cookieName
            , String cookieValue, int cookieMaxage, String encodeString) {
        try{
            if(cookieValue == null){
                cookieValue = "";
            }else{
                cookieValue = URLEncoder.encode(cookieValue, encodeString);
            }
            Cookie cookie = new Cookie(cookieName, cookieValue);
            if(cookieMaxage > 0){
                cookie.setMaxAge(cookieMaxage);
            }
            if(null != request){
                // 设置域名的cookie
                String domainName = getDomainName(request);
                logger.info("========== domainName: {} ==========", domainName);
                if(!LOCALHOST.equals(domainName)){
                    cookie.setDomain(domainName);
                }
            }
            cookie.setPath("/");
            response.addCookie(cookie);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    /**
     * @return
     */
    private static final String getDomainName(HttpServletRequest request) {
        String domainName = null;

        String serverName = request.getRequestURL().toString();
        if(serverName == null || "".equals(serverName)){
            domainName = "";
        }else{
            serverName = serverName.toLowerCase();
            serverName = serverName.substring(7);
            final int end = serverName.indexOf("/");
            serverName = serverName.substring(0, end);
            if(serverName.indexOf(":") > 0){
                String[] ary = serverName.split("\\:");
                serverName = ary[0];
            }

            final String[] domains = serverName.split("\\.");
            int len = domains.length;
            if(len > 3 && !RegexUtils.checkIp(serverName)){
                // www.xxx.com.cn
                domainName = "." + domains[len - 3] + "." + domains[len - 2] + "." + domains[len - 1];
            }else if(len <= 3 && len > 1){
                // xxx.com or xxx.cn
                domainName = "." + domains[len - 2] + "." + domains[len - 1];
            }else{
                domainName = serverName;
            }
        }
        return domainName;
    }

}
