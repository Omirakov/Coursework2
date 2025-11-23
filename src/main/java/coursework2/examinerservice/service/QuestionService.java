package coursework2.examinerservice.service;

import coursework2.examinerservice.domain.Question;
import org.springframework.web.HttpRequestMethodNotSupportedException;

import java.util.Collection;

public interface QuestionService {

    Question add(String question, String answer) throws HttpRequestMethodNotSupportedException;

    Question add(Question question) throws HttpRequestMethodNotSupportedException;

    Question remove(Question question) throws HttpRequestMethodNotSupportedException;

    Collection<Question> getAll() throws HttpRequestMethodNotSupportedException;

    Question getRandomQuestion();
}
