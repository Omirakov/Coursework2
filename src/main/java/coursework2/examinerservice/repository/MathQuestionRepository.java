package coursework2.examinerservice.repository;

import coursework2.examinerservice.domain.Question;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Set;

@Repository
public class MathQuestionRepository {

    private final Set<Question> questions = new LinkedHashSet<>();

    public Question add(Question question) {
        if (question == null) {
            throw new IllegalArgumentException("Вопрос не может быть null");
        }
        questions.add(question);
        return question;
    }

    public Question remove(Question question) {
        if (question == null) {
            throw new IllegalArgumentException("Вопрос не может быть null");
        }
        if (!questions.remove(question)) {
            throw new IllegalArgumentException("Вопрос не найден: " + question);
        }
        return question;
    }

    public Collection<Question> getAll() {
        return Set.copyOf(questions);
    }

    public int size() {
        return questions.size();
    }

    public boolean contains(Question question) {
        return questions.contains(question);
    }

    @PostConstruct
    public void init() {
        add(new Question("2 + 2", "4"));
        add(new Question("5 * 6", "30"));
        add(new Question("10 в квадрате", "100"));
        add(new Question("Чему равно π (Пи)?", "3.14"));
        add(new Question("Сколько градусов в прямом угле?", "90"));
    }
}
