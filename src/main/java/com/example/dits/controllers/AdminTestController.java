package com.example.dits.controllers;


import com.example.dits.dto.*;
import com.example.dits.entity.Question;
import com.example.dits.entity.Test;
import com.example.dits.entity.Topic;
import com.example.dits.mapper.QuestionMapper;
import com.example.dits.mapper.TestMapper;
import com.example.dits.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminTestController {
    private final TopicService topicService;
    private final TestService testService;
    private final QuestionService questionService;
    private final TestMapper testMapper;
    private final QuestionMapper questionMapper;
    private final RoleService roleService;

    @GetMapping("/testBuilder")
    public String getTopics(ModelMap model) {
        model.addAttribute("topicLists",topicService.findAll());
        model.addAttribute("title","Test editor");
        return "admin/test-editor";
    }

    @ResponseBody
    @PostMapping("/addTopic")
    public List<TopicDTO> addTopic(@RequestBody TopicDTO topic){
        Topic topicToSave = new Topic(topic.getTopicName());
        topicService.save(topicToSave);
        return getTopicDTOList();
    }

    @ResponseBody
    @PutMapping("/editTopic")
    public List<TopicDTO> editTopic(@RequestBody TopicDTO topic){
        topicService.updateTopicName(topic.getTopicId(), topic.getTopicName());
        return getTopicDTOList();
    }

    @ResponseBody
    @DeleteMapping("/removeTopic")
    public List<TopicDTO> removeTopic(@RequestParam int topicId){
        topicService.removeTopicByTopicId(topicId);
        return getTopicDTOList();
    }

    @ResponseBody
    @PostMapping("/addTest")
    public List<TestWithQuestionsDTO> addTest(@RequestBody TestInfoDTO testInfo){
        Topic topic = topicService.getTopicByTopicId(testInfo.getTopicId());
        Test test = Test.builder().name(testInfo.getName()).description(testInfo.getDescription()).topic(topic).build();
        testService.save(test);
        return getTestWithQuestionsDTOList(topic);
    }

    @ResponseBody
    @PutMapping("/editTest")
    public List<TestWithQuestionsDTO> editTest(@RequestBody TestInfoDTO testInfo){
        testService.update(testInfo.getTestId(),testInfo.getName(), testInfo.getDescription());
        return getTestWithQuestionsDTOList(topicService.getTopicByTopicId(testInfo.getTopicId()));
    }

    @ResponseBody
    @DeleteMapping("/removeTest")
    public List<TestWithQuestionsDTO> removeTest(@RequestParam int testId, @RequestParam int topicId){
        testService.removeTestByTestId(testId);
        return getTestWithQuestionsDTOList(topicService.getTopicByTopicId(topicId));
    }

    @ResponseBody
    @PostMapping("/addQuestion")
    public List<TestWithQuestionsDTO> addQuestion(@RequestBody QuestionEditModel questionModel){
        questionService.addQuestion(questionModel);
        return getTestWithQuestionsDTOList(topicService.getTopicByTopicId(questionModel.getTopicId()));
    }

    @ResponseBody
    @PutMapping("/editQuestionAnswers")
    public List<TestWithQuestionsDTO> editQuestionAnswers(@RequestBody QuestionEditModel questionModel){
        questionService.editQuestion(questionModel);
        return getTestWithQuestionsDTOList(topicService.getTopicByTopicId(questionModel.getTopicId()));
    }

    @ResponseBody
    @DeleteMapping("/removeQuestion")
    public List<TestWithQuestionsDTO> removeQuestion(@RequestParam int questionId, @RequestParam int topicId){
        questionService.removeQuestionById(questionId);
        return getTestWithQuestionsDTOList(topicService.getTopicByTopicId(topicId));
    }

    @ResponseBody
    @GetMapping("/getTopics")
    public List<TopicDTO> getTopicList(){
        return topicService.findAll();
    }

    @ResponseBody
    @GetMapping("/getTests")
    public List<TestWithQuestionsDTO> getTestsWithQuestions(@RequestParam int id) {
        List<Test> testList = testService.getTestsByTopic_TopicId(id);
        return testList.stream().map(testMapper::convertToTestDTO).collect(Collectors.toList());

    }

    @ResponseBody
    @GetMapping("/getAnswers")
    public QuestionWithAnswersDTO getQuestionsWithAnswers(@RequestParam int id){
        Question question = questionService.getQuestionById(id);
        questionMapper.convertToQuestionWithAnswersDTO(question);
        return questionMapper.convertToQuestionWithAnswersDTO(question);
    }

    @ResponseBody
    @GetMapping("/getRoles")
    public List<String> getRoles(){
        return roleService.getAllRoles();
    }

    private List<TestWithQuestionsDTO> getTestWithQuestionsDTOList(Topic topic) {
        return topic.getTestList().stream().map(testMapper::convertToTestDTO).collect(Collectors.toList());
    }

    private List<TopicDTO> getTopicDTOList() {
        return topicService.findAll();
    }
}