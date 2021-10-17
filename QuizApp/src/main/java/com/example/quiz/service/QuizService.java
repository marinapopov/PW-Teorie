package com.example.quiz.service;

import com.example.quiz.model.UserAccess;
import com.example.quiz.model.UserAnswer;
import com.example.quiz.repository.UserAnswerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class QuizService {
    @Autowired
    UserAnswerRepository userAnswerRepository;

    public int getResult(UserAccess userAccess) {
        int score = 0;

        for (UserAnswer userAnswer : userAnswerRepository.findAllByUserAccessId(userAccess.getId())) {
            if (userAnswer.getQuestionChoice().getId() == userAnswer.getQuestion().getAnswer()) {
                score += userAnswer.getQuestion().getPoints();
            }
        }
        return score;
    }
}
