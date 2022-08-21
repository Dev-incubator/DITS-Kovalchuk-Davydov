package com.example.dits.controllers;

import com.example.dits.dto.*;
import com.example.dits.entity.Statistic;
import com.example.dits.mapper.UserStatisticsMapper;
import com.example.dits.service.StatisticService;
import com.example.dits.service.TestService;
import com.example.dits.service.TopicService;
import com.example.dits.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Controller
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminStatisticController {
    private final StatisticService statisticService;
    private final TopicService topicService;
    private final UserService userService;

    private final TestService testService;

    private final UserStatisticsMapper mapper;

    @GetMapping("/adminStatistic")
    public String testStatistic(ModelMap model){
        List<TopicDTO> topicDTOList = topicService.findAll();
        model.addAttribute("topicList",topicDTOList);
        model.addAttribute("title","Statistic");
        return "admin/test-statistic";
    }

    @ResponseBody
    @GetMapping("/getTestsStatistic")
    public List<TestStatistic> getTestsStatistics(@RequestParam int id) {
        return statisticService.getListOfTestsWithStatisticsByTopic(id);
    }

    @ResponseBody
    @GetMapping("/getUserTestsStatistic")
    public List<TestStatisticByUser> getUserTestsStatistic(@RequestParam int id) {
        List<Statistic> statistics = statisticService.getStatisticsByUser(userService.getUserById(id));
        List<TestStatisticByUser> statisticDto = mapper.statisticToDto(statistics, testService.findAll());
        statisticDto.sort(TestStatisticByUser :: compareTo);
        return statisticDto;
    }

    @GetMapping("/getUserStatistic")
    public String userStatistic(ModelMap model){
        model.addAttribute("usersList", userService.getAllUsers());
        return "admin/user-statistic";
    }

    @ResponseBody
    @GetMapping("/adminStatistic/removeStatistic/byId")
    public String removeStatisticByUserId(@RequestParam int id){
        statisticService.removeStatisticByUserId(id);
        return "success";
    }

    @GetMapping("/adminStatistic/removeStatistic/all")
    public String removeAllStatistic(){
        statisticService.deleteAll();
        return "redirect:/admin/adminStatistic";
    }
}