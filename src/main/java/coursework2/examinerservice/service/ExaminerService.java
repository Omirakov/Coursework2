package coursework2.examinerservice.service;

import coursework2.examinerservice.domain.Question;

import java.util.Collection;

public interface ExaminerService {

    Collection<Question> getQuestions(int amount);

}
