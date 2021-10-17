package com.example.quiz.controller;

import com.example.quiz.model.Question;
import com.example.quiz.model.UserAccess;
import com.example.quiz.model.UserAnswer;
import com.example.quiz.repository.QuestionChoiceRepository;
import com.example.quiz.repository.QuestionRepository;
import com.example.quiz.repository.UserAccessRepository;
import com.example.quiz.repository.UserAnswerRepository;
import com.example.quiz.service.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class QuestionController {
    @Autowired
    private UserAccessRepository userAccessRepository;

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private QuestionChoiceRepository questionChoiceRepository;

    @Autowired
    private UserAnswerRepository userAnswerRepository;

    @PostMapping("/submit-question-answer")
    public String submitUserAnswer(UserAnswerForm form, @RequestParam int userAccessId) throws Exception {
        UserAccess userAccess = userAccessRepository.findById(userAccessId).orElseThrow(() -> new Exception("No user access row found"));
        Question question = questionRepository.findById(form.getQuestion()).get();

        UserAnswer userAnswer = new UserAnswer();
        userAnswer.setUserAccess(userAccess);
        userAnswer.setQuestion(question);
        userAnswer.setQuestionChoice(questionChoiceRepository.findById(form.getChoice()).get());
        userAnswerRepository.save(userAnswer);

        return "redirect:/view-next-question?userAccessId=" + userAccess.getId() + "&questionId=" + question.getId();
    }

    @GetMapping("/question")
    public String quiz(@RequestParam int userAccessId, @RequestParam int questionId, Model m) throws Exception {
        UserAccess userAccess = userAccessRepository.findById(userAccessId).get();
        Question question = questionRepository.findById(questionId).orElseThrow(() -> new Exception("No question row found"));

        m.addAttribute("userAccess", userAccess);
        m.addAttribute("question", question);

        return "/question";
    }

    @GetMapping("/view-next-question")
    public String viewNextQuestion(@RequestParam int userAccessId, @RequestParam int questionId, Model m) throws Exception {
        UserAccess userAccess = userAccessRepository.findById(userAccessId).orElseThrow(() -> new Exception("No user access row found"));
        questionId++;
        Question question = questionRepository.findById(questionId).orElse(null);
        if (question != null) {
            m.addAttribute("question", question);
            m.addAttribute("userAccess", userAccess);
        } else {
            return "redirect:/endquiz?userAccessId=" + userAccess.getId();
        }
        return "redirect:/question?userAccessId=" + userAccess.getId() + "&questionId=" + question.getId();
    }
}
