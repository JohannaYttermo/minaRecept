package com.example.minaRecept.config;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import java.io.IOException;

public class CustomAuthenticationFailureHandler implements AuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        // Ange ditt eget meddelande här
        String errorMessage = "Fel användarnamn eller lösenord. Försök igen.";

        // Ställ in felmeddelandet i sessionen
        request.getSession().setAttribute("error", errorMessage);

        // Omdirigera till login-sidan med felmeddelandet
        response.sendRedirect("/login?error");
    }


}