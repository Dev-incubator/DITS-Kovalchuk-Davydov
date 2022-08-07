package com.example.dits.controllers;

import com.example.dits.dto.TestInfoDTO;
import com.example.dits.dto.TopicDTO;
import com.example.dits.service.TestService;
import com.example.dits.service.TopicService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/user")
public class ChooseTestController {

    private final TestService testService;
    private final TopicService topicService;

    @GetMapping("/chooseTest")
    public String userPage(ModelMap model) {
        List<TopicDTO> topicsWithQuestions = topicService.getTopicsWithQuestions();
        model.addAttribute("title", "Testing");
        model.addAttribute("topicWithQuestions", topicsWithQuestions);
        return "user/chooseTest";
    }

    @ResponseBody
    @GetMapping("/chooseTheme")
    public List<TestInfoDTO> getTestNameAndDescriptionFromTopic(@RequestParam(value = "theme", required = false) String topicName, HttpSession session) {
        List<TestInfoDTO> tests = testService.getTestsByTopicName(topicName);
        session.setAttribute("tests", tests);
        return tests;
    }
}
