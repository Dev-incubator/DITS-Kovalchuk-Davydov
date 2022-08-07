package com.example.dits.controllers;

import com.example.dits.dto.AnswerDTO;
import com.example.dits.dto.QuestionDTO;
import com.example.dits.dto.StatisticDTO;
import com.example.dits.dto.UserInfoDTO;
import com.example.dits.entity.*;
import com.example.dits.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.*;

@SuppressWarnings("unchecked")
@Controller
@RequiredArgsConstructor
@RequestMapping("/user")
public class TestPageController {

    private final TestService testService;
    private final QuestionService questionService;
    private final AnswerService answerService;
    private final StatisticService statisticService;

    @GetMapping("/goTest")
    public String goTest(@RequestParam int testId, @RequestParam(value = "theme") String topicName, ModelMap model, HttpSession session){

        Test test = testService.getTestByTestId(testId);
        List<QuestionDTO> questionList = questionService.getQuestionsByTest(test);
        int quantityOfQuestions = questionList.size();
        int questionNumber = 0;
        int quantityOfRightAnswers = 0;

        List<AnswerDTO> answers = answerService.getAnswersFromQuestionList(questionList, questionNumber);
        String questionDescription = questionService.getDescriptionFromQuestionList(questionList, questionNumber);

        session.setAttribute("testName", test.getName());
        session.setAttribute("topicName", topicName);
        session.setAttribute("questionSize", quantityOfQuestions);
        session.setAttribute("quantityOfRightAnswers", quantityOfRightAnswers);
        session.setAttribute("statistics", new ArrayList<StatisticDTO>());
        session.setAttribute("questions",questionList);
        session.setAttribute("questionNumber" , ++questionNumber);

        model.addAttribute("question", questionDescription);
        model.addAttribute("answers", answers);
        model.addAttribute("title","Testing");
        return "user/testPage";
    }

    @GetMapping("/nextTestPage")
    public String nextTestPage(@RequestParam(value = "answeredQuestion", required = false) List<Integer> answeredQuestion,
                               ModelMap model,
                               HttpSession session){

        List<QuestionDTO> questionList = (List<QuestionDTO>) session.getAttribute("questions");
        int questionNumber = (int) session.getAttribute("questionNumber");
        UserInfoDTO user = (UserInfoDTO) session.getAttribute("user");
        boolean isCorrect = answerService.isRightAnswer(answeredQuestion,questionList,questionNumber);

        List<AnswerDTO> answers = answerService.getAnswersFromQuestionList(questionList, questionNumber);
        String questionDescription = questionService.getDescriptionFromQuestionList(questionList, questionNumber);

        List<StatisticDTO> statisticList = (List<StatisticDTO>) session.getAttribute("statistics");
        statisticList.add(StatisticDTO.builder()
                .questionId(questionList.get(questionNumber -1).getQuestionId())
                .username(user.getLogin())
                .isCorrect(isCorrect)
                .build());

        session.setAttribute("statistics", statisticList);
        session.setAttribute("questionNumber" , ++questionNumber);
        model.addAttribute("question",questionDescription);
        model.addAttribute("answers", answers);
        model.addAttribute("title","Testing");
        return "user/testPage";
    }

    @GetMapping("/resultPage")
    public String testStatistic(@RequestParam(value = "answeredQuestion", required = false) List<Integer> answeredQuestion,
                                ModelMap model,
                                HttpSession session){


        List<QuestionDTO> questions = (List<QuestionDTO>) session.getAttribute("questions");
        int questionNumber = questions.size();
        boolean isCorrect = answerService.isRightAnswer(answeredQuestion,questions,questionNumber);
        UserInfoDTO user = (UserInfoDTO) session.getAttribute("user");
        List<StatisticDTO> statisticList = (List<StatisticDTO>) session.getAttribute("statistics");

        checkIfResultPage(questions, questionNumber, isCorrect, user, statisticList);
        statisticService.saveListOfStatisticsToDB(statisticList);
        model.addAttribute("title","Result");
        return "redirect:/user/chooseTest";
    }

    private void checkIfResultPage(List<QuestionDTO> questions, int questionNumber, boolean isCorrect, UserInfoDTO user, List<StatisticDTO> statisticList) {
        if (!isResultPage(questionNumber, statisticList)){
            statisticList.add(StatisticDTO.builder()
                    .questionId(questions.get(questionNumber -1).getQuestionId())
                    .username(user.getLogin())
                    .isCorrect(isCorrect)
                    .build());
        }
    }

    private boolean isResultPage(int questionNumber, List<StatisticDTO> statisticList) {
        return statisticList.size() >= questionNumber;
    }


}
