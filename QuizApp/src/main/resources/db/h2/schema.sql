DROP TABLE user_answer IF EXISTS;
DROP TABLE user_access IF EXISTS;
DROP TABLE question IF EXISTS;
DROP TABLE question_choice IF EXISTS;

CREATE TABLE user_access (
    id                  INTEGER IDENTITY PRIMARY KEY,
    name                VARCHAR(30),
    access_time         DATE
);

CREATE TABLE question (
    id                  INTEGER IDENTITY PRIMARY KEY,
    text                VARCHAR,
    points              INTEGER,
    answer              INTEGER
);

CREATE TABLE question_choice (
    id                  INTEGER IDENTITY PRIMARY KEY,
    text                VARCHAR,
    question_id         INTEGER
);

ALTER TABLE question_choice ADD CONSTRAINT fk_question_choice_question FOREIGN KEY (question_id) REFERENCES question (id);

CREATE TABLE user_answer (
  id                  INTEGER IDENTITY PRIMARY KEY,
  user_access_id      INTEGER,
  question_id         INTEGER,
  question_choice_id  INTEGER
);

ALTER TABLE user_answer ADD CONSTRAINT fk_user_answer_user_access FOREIGN KEY (user_access_id) REFERENCES user_access (id);
ALTER TABLE user_answer ADD CONSTRAINT fk_user_answer_question FOREIGN KEY (question_id) REFERENCES question (id);
ALTER TABLE user_answer ADD CONSTRAINT fk_user_answer_question_choice FOREIGN KEY (question_choice_id) REFERENCES question_choice (id);