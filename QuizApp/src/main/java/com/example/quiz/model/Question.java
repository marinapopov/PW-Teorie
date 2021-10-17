package com.example.quiz.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@Data
@Entity
@Table(name="question")
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String text;

    private int points;

    private int answer;

    @OneToMany(mappedBy = "question",fetch = FetchType.EAGER)
    private List<QuestionChoice> choices;
}
