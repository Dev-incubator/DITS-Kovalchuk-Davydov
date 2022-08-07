package com.example.dits.controllers;

import com.example.dits.dto.*;
import com.example.dits.entity.Topic;
import com.example.dits.service.TopicService;
import com.example.dits.service.UserService;
import com.example.dits.service.impl.StatisticServiceImpl;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminStatisticController {
    private final StatisticServiceImpl statisticService;
    private final TopicService topicService;
    private final UserService userService;

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