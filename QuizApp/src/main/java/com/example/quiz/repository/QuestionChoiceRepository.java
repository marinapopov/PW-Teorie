package com.example.quiz.repository;

import com.example.quiz.model.QuestionChoice;
import org.springframework.data.repository.CrudRepository;

public interface QuestionChoiceRepository extends CrudRepository<QuestionChoice, Integer> {
}
