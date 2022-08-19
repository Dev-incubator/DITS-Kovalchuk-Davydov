package com.example.dits.controllers;

import com.example.dits.dto.TestStatisticByUser;

import com.example.dits.dto.UserInfoDTO;
import com.example.dits.entity.Question;
import com.example.dits.entity.Statistic;
import com.example.dits.entity.Test;
import com.example.dits.entity.User;
import com.example.dits.mapper.UserSatatisticsMapper;

import com.example.dits.service.StatisticService;
import com.example.dits.service.TestService;
import com.example.dits.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserStatisticsController {

    private final UserService userService;
    private final StatisticService statisticService;
    private final TestService testService;
    private final UserStatisticsMapper userStatisticsMapper;

    @GetMapping("/statistics")
    public String userStatistics(Model model, HttpSession session) {
        User user = (User) session.getAttribute("user");
        List<TestStatisticByUser> statistics = userStatisticsMapper.statisticToDto(statisticService.getStatisticsByUser(user), testService.findAll());
        statistics.sort(TestStatisticByUser :: compareTo);
        model.addAttribute("statistics", statistics);

        return "user/personalStatistic";
    }
}
