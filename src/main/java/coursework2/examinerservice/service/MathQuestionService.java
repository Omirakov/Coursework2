package coursework2.examinerservice.service;

import coursework2.examinerservice.domain.Question;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.HttpRequestMethodNotSupportedException;

import java.util.Collection;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class MathQuestionService implements QuestionService {

    private final Random random = ThreadLocalRandom.current();
    private final String[] operations = {"+", "-", "*", "/"};

    @Override
    public Question add(String question, String answer) throws HttpRequestMethodNotSupportedException {
        throw new HttpRequestMethodNotSupportedException("POST");
    }

    @Override
    public Question add(Question question) throws HttpRequestMethodNotSupportedException {
        throw new HttpRequestMethodNotSupportedException("POST");
    }

    @Override
    public Question remove(Question question) throws HttpRequestMethodNotSupportedException {
        throw new HttpRequestMethodNotSupportedException("DELETE");
    }

    @Override
    public Collection<Question> getAll() throws HttpRequestMethodNotSupportedException {
        throw new HttpRequestMethodNotSupportedException("GET");
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

