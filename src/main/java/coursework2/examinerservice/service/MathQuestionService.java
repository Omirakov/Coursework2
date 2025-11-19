package coursework2.examinerservice.service;

import coursework2.examinerservice.domain.Question;
import coursework2.examinerservice.repository.MathQuestionRepository;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class MathQuestionService implements QuestionService {

    private final MathQuestionRepository mathQuestionRepository;

    public MathQuestionService(MathQuestionRepository mathQuestionRepository) {
        this.mathQuestionRepository = mathQuestionRepository;
    }

    @Override
    public Question add(String question, String answer) {
        return add(new Question(question, answer));
    }

    @Override
    public Question add(Question question) {
        if (question == null) {
            throw new IllegalArgumentException("Вопрос не может быть null");
        }
        return mathQuestionRepository.add(question);
    }

    @Override
    public Question remove(Question question) {
        if (question == null) {
            throw new IllegalArgumentException("Вопрос не может быть null");
        }
        return mathQuestionRepository.remove(question);
    }

    @Override
    public Collection<Question> getAll() {
        return mathQuestionRepository.getAll();
    }

    @Override
    public Question getRandomQuestion() {
        Collection<Question> all = mathQuestionRepository.getAll();
        if (all.isEmpty()) {
            throw new NoSuchElementException("Вопросов нет");
        }
        List<Question> questionList = new ArrayList<>(all);
        int randomIndex = ThreadLocalRandom.current().nextInt(questionList.size());
        return questionList.get(randomIndex);
    }
}

