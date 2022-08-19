package com.example.dits.aspects;

import com.example.dits.dto.QuestionEditModel;
import com.example.dits.dto.TestInfoDTO;
import com.example.dits.dto.TopicDTO;
import com.example.dits.dto.UserInfoDTO;
import com.example.dits.entity.User;
import com.example.dits.loggers.AdminLogger;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpSession;
import java.util.Date;

@Component
@Aspect
public class AdminLoggingAspect {
    private AdminLogger adminLogger;
    private ModelMapper modelMapper;

    @Autowired
    public AdminLoggingAspect(AdminLogger adminLogger, ModelMapper modelMapper) {
        this.adminLogger = adminLogger;
        this.modelMapper = modelMapper;
    }

    @Pointcut("execution(* com.example.dits.controllers.AdminUserController.addUser(..))")
    public void addUser() {}

    @Pointcut("execution(* com.example.dits.controllers.AdminTestController.addTopic(..))")
    public void addTopic() {}

    @Pointcut("execution(* com.example.dits.controllers.AdminTestController.addTest(..))")
    public void addTest() {}

    @Pointcut("execution(* com.example.dits.controllers.AdminTestController.addQuestion(..))")
    public void addQuestion() {}


    @After(value = "addUser() && args(userInfo, httpSession)")
    public void afterAddingUser(UserInfoDTO userInfo, HttpSession httpSession) {
        User user = modelMapper.map(userInfo, User.class);
        User admin = (User) httpSession.getAttribute("user");
        String message = "user with was added by user with login = " + admin.getLogin()
                + "\n\t\t new user: " + user.toString();

        adminLogger.log(message, new Date());
    }

    @After(value = "addTopic() && args(topic, httpSession)")
    public void afterAddingTopic(TopicDTO topic, HttpSession httpSession) {
        User admin = (User) httpSession.getAttribute("user");

        String message = "topic was added by user with login = " + admin.getLogin()
                + "\n\t\t new topic: " + topic.getTopicName();

        adminLogger.log(message, new Date());
    }

    @After(value = "addTest() && args(testInfo, httpSession)")
    public void afterAddingTest(TestInfoDTO testInfo, HttpSession httpSession) {
        User admin = (User) httpSession.getAttribute("user");

        String message = "test was added by user with login = " + admin.getLogin()
                + "\n\t\t new test: " + testInfo.getName() + ", " + testInfo.getDescription();

        adminLogger.log(message, new Date());
    }

    @After(value = "addQuestion() && args(questionModel, httpSession)")
    public void afterAddingQuestion(QuestionEditModel questionModel, HttpSession httpSession) {
        User admin = (User) httpSession.getAttribute("user");

        String message = "question was added by user with login = "
                + admin.getLogin()
                + "\n\t\t new question: " + questionModel.getQuestionName();

        adminLogger.log(message, new Date());
    }
}
