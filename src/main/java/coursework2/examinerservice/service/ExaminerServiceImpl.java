package coursework2.examinerservice.service;

import coursework2.examinerservice.domain.Question;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ExaminerServiceImpl implements ExaminerService {

    private final List<QuestionService> questionServices;

    public ExaminerServiceImpl(List<QuestionService> questionServices) {
        this.questionServices = new ArrayList<>(questionServices);
        if (this.questionServices.isEmpty()) {
            throw new IllegalStateException("Нет доступных сервисов вопросов");
        }
    }

    @Override
    public Collection<Question> getQuestions(int amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Количество вопросов должно быть положительным");
        }

        Set<Question> result = new LinkedHashSet<>();
        List<QuestionService> shuffledServices = new ArrayList<>(questionServices);
        Collections.shuffle(shuffledServices);

        while (result.size() < amount) {
            for (QuestionService service : shuffledServices) {
                if (result.size() >= amount) break;
                result.add(service.getRandomQuestion());
            }
        }

        return List.copyOf(result);
    }
}
