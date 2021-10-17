package com.example.quiz.controller;

import com.example.quiz.model.UserAccess;
import com.example.quiz.repository.UserAccessRepository;
import com.example.quiz.service.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;

@Controller
public class QuizController {

    @Autowired
    private QuizService quizService;

    private UserAccessRepository userAccessRepository;

    public QuizController(UserAccessRepository userAccessRepository) {
        this.userAccessRepository = userAccessRepository;
    }

    @GetMapping("/")
    public String home() {
        return "home";
    }

    @GetMapping("/start")
    public String start(@RequestParam String user, Model model) {

        UserAccess userAccess = new UserAccess();
        userAccess.setName(user);
        userAccess.setAccessTime(LocalDateTime.now());
        userAccess = userAccessRepository.save(userAccess);
        model.addAttribute("userAccess", userAccess);
        return "/start";
    }

    @GetMapping("/endquiz")
    public String endQuiz(@RequestParam int userAccessId, Model m) {
        UserAccess userAccess = userAccessRepository.findById(userAccessId).get();
        m.addAttribute("userAccess", userAccess);

        return "/endquiz";
    }

    @GetMapping("/results")
    public String quizResults(@RequestParam int userAccessId, Model m) {
        UserAccess userAccess = userAccessRepository.findById(userAccessId).get();
        m.addAttribute("userAccess", userAccess);
        m.addAttribute("totalPoints", quizService.getResult(userAccess));

        return "/results";
    }
}
