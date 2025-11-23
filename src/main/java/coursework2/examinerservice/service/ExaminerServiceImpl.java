package coursework2.examinerservice.service;

import coursework2.examinerservice.domain.Question;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class ExaminerServiceImpl implements ExaminerService {

    private final QuestionService questionService;

    public ExaminerServiceImpl(QuestionService questionService) {
        this.questionService = questionService;
    }

    @Override
    public Collection<Question> getQuestions(int amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Количество вопросов должно быть положительным");
        }

        Collection<Question> allQuestions = questionService.getAll();
        if (allQuestions.size() < amount) {
            throw new IllegalArgumentException("Недостаточно вопросов в базе: требуется " + amount + ", доступно " + allQuestions.size());
        }

        Set<Question> randomQuestions = new HashSet<>();
        while (randomQuestions.size() < amount) {
            randomQuestions.add(questionService.getRandomQuestion());
        }

        return List.copyOf(randomQuestions);
    }
}
