package com.example.dits.aspects;

import com.example.dits.entity.User;
import com.example.dits.loggers.UserLogger;
import lombok.SneakyThrows;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpSession;
import java.util.Date;

@Aspect
@Component
public class UserLoggingAspects {
    @Autowired
    private UserLogger logger;

    @Pointcut("execution(* com.example.dits.controllers.TestPageController.goTest(..))")
    private void startTest() {}

    @Pointcut("execution(* com.example.dits.controllers.TestPageController.testStatistic(..))")
    private void finishTest() {}

    @Pointcut("execution(* com.example.dits.controllers.TestPageController.nextTestPage(..))")
    private void question() {}

    @Before("startTest()")
    public void logStart(JoinPoint jp) {
        HttpSession session = (HttpSession) jp.getArgs()[3];
        User user = (User) session.getAttribute("user");
        String logMassage = "User " + user.getFirstName() + " " + user.getLastName() +
                " started test " + jp.getArgs()[1] + " at " + new Date() + "\n";

       logger.log(logMassage);

        logMassage = "User " + user.getFirstName() + " " + user.getLastName() +
                " start to answer question number " + 1 + " at " + new Date() + "\n";

        logger.log(logMassage);
    }

    @After("finishTest()")
    public void logFinish(JoinPoint jp) {
        HttpSession session = (HttpSession) jp.getArgs()[2];
        User user = (User) session.getAttribute("user");
        String logMassage = "User " + user.getFirstName() + " " + user.getLastName() +
                " finished test " + session.getAttribute("topicName") + " at " + new Date() + "\n";

        logger.log(logMassage);
    }

    @SneakyThrows
    @After("question()")
    public void logQuestion(JoinPoint jp) {
        HttpSession session = (HttpSession) jp.getArgs()[2];
        User user = (User) session.getAttribute("user");
        String logMassage = "User " + user.getFirstName() + " " + user.getLastName() +
                " start to answer question number " + session.getAttribute("questionNumber") + " at " + new Date() + "\n";

        logger.log(logMassage);
    }
}
