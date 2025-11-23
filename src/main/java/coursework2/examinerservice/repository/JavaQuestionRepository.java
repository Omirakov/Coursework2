package coursework2.examinerservice.repository;

import coursework2.examinerservice.domain.Question;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Set;

@Repository
public class JavaQuestionRepository {

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
        add(new Question("Что такое JVM?", "Виртуальная машина Java"));
        add(new Question("Что такое JRE?", "Среда выполнения Java"));
        add(new Question("Что такое JDK?", "Комплект разработчика Java"));
        add(new Question("Какой метод является точкой входа в Java-приложение?", "main"));
        add(new Question("Как объявить строковую переменную в Java?", "String s = \"значение\";"));
    }
}

