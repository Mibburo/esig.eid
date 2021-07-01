package com.example.esig.eid.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;

@Slf4j
@Controller
public class ESigController {

    @Value("redirect.link")
    private String redirectLink;

    public final static String TOKEN_NAME = "access_token";

    @RequestMapping(value = { "/sign-now/register"})
    public String login(@CookieValue(value = TOKEN_NAME, required = false) String jwtCookie,
                              @CookieValue(value = "type", required = false) String typeCookie,
                              HttpServletRequest req, Principal principal, ModelMap model, RedirectAttributes redirectAttrs) {

        return "redirectView";
    }

    @RequestMapping(value = { "/errorPage"})
    public String errorPage(@CookieValue(value = TOKEN_NAME, required = false) String jwtCookie,
                        @CookieValue(value = "type", required = false) String typeCookie,
                        HttpServletRequest req, Principal principal, ModelMap model, RedirectAttributes redirectAttrs) {

        return "errorPage";
    }
}
