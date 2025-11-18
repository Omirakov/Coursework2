package coursework2.examinerservice.service;

import coursework2.examinerservice.domain.Question;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class JavaQuestionService implements QuestionService {

    private final Set<Question> questions = new LinkedHashSet<>();
    private static final ThreadLocalRandom RANDOM = ThreadLocalRandom.current();

    @Override
    public Question add(String question, String answer) {
        return add(new Question(question, answer));
    }

    @Override
    public Question add(Question question) {
        if (question == null) {
            throw new IllegalArgumentException("Вопрос не может быть null");
        }
        questions.add(question);
        return question;
    }

    @Override
    public Question remove(Question question) {
        if (question == null) {
            throw new IllegalArgumentException("Вопрос не может быть null");
        }
        if (!questions.remove(question)) {
            throw new NoSuchElementException("Вопрос не найден: " + question);
        }
        return question;
    }

    @Override
    public Collection<Question> getAll() {
        return Set.copyOf(questions);
    }

    @Override
    public Question getRandomQuestion() {
        if (questions.isEmpty()) {
            throw new NoSuchElementException("Вопросов нет");
        }
        List<Question> questionList = new ArrayList<>(questions);
        int randomIndex = RANDOM.nextInt(questionList.size());
        return questionList.get(randomIndex);
    }
}
