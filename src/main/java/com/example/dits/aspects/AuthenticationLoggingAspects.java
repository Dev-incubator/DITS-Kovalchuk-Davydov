package com.example.dits.aspects;

import com.example.dits.entity.User;
import com.example.dits.loggers.AuthenticationLogger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Date;

@Aspect
@Component
public class AuthenticationLoggingAspects {
    @Autowired
    private AuthenticationLogger logger;

    @Pointcut("execution(* com.example.dits.controllers.SecurityController.loginHandle(..))")
    private void authentication() {}

    @Pointcut("execution(* com.example.dits.controllers.SecurityController.logoutPage(..))")
    private void exit() {}

    @After("authentication()")
    public void logAuthentication(JoinPoint jp) {
        HttpSession session = (HttpSession) jp.getArgs()[0];
        User user = (User) session.getAttribute("user");
        String logMassage = user.getRole().getRoleName() + " " + user.getFirstName() + " " + user.getLastName() +
                " logged in at " + new Date() + "\n";

        logger.log(logMassage);
    }

    @Before("exit()")
    public void logExit(JoinPoint jp) {
        HttpServletRequest request = (HttpServletRequest) jp.getArgs()[0];
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        String logMassage = user.getRole().getRoleName() + " " + user.getFirstName() + " " + user.getLastName() +
                " logged out at " + new Date() + "\n";

        logger.log(logMassage);
    }
}
