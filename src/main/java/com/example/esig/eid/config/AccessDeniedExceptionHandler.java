package com.example.esig.eid.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@Component
public class AccessDeniedExceptionHandler implements AccessDeniedHandler {

    @Override
    public void handle
            (HttpServletRequest request, HttpServletResponse response, AccessDeniedException ex)
            throws IOException, ServletException {
        log.info("access denied redirecting to error page");
        response.sendRedirect("/errorPage");
    }
}
