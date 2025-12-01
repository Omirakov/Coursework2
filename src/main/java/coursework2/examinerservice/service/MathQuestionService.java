package coursework2.examinerservice.service;

import coursework2.examinerservice.domain.Question;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class MathQuestionService implements QuestionService {

    private final Random random = ThreadLocalRandom.current();
    private final String[] operations = {"+", "-", "*", "/"};

    @Override
    public Question add(String question, String answer) {
        throw new UnsupportedOperationException("Добавление вопросов по математике запрещено");
    }

    @Override
    public Question add(Question question) {
        throw new UnsupportedOperationException("Добавление вопросов по математике запрещено");
    }

    @Override
    public Question remove(Question question) {
        throw new UnsupportedOperationException("Удаление вопросов по математике запрещено");
    }

    @Override
    public Collection<Question> getAll() {
        throw new UnsupportedOperationException("Получение всех математических вопросов запрещено");
    }

    @Override
    public Question getRandomQuestion() {
        String op = operations[random.nextInt(operations.length)];
        int a = random.nextInt(1, 11);
        int b = random.nextInt(1, 11);
        int result;

        switch (op) {
            case "+":
                result = a + b;
                break;
            case "-":
                result = a - b;
                break;
            case "*":
                result = a * b;
                break;
            case "/":
                if (b == 0) b = 1;
                result = a / b;
                break;
            default:
                result = 0;
        }

        return new Question(a + " " + op + " " + b + " = ?", String.valueOf(result));
    }
}