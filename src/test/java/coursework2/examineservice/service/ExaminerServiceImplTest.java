package coursework2.examineservice.service;

import coursework2.examinerservice.domain.Question;
import coursework2.examinerservice.service.ExaminerServiceImpl;
import coursework2.examinerservice.service.QuestionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ExaminerServiceImplTest {

    @Mock
    private QuestionService questionService;

    @InjectMocks
    private ExaminerServiceImpl examinerService;

    private final Question question1 = new Question("Вопрос 1", "Ответ 1");
    private final Question question2 = new Question("Вопрос 2", "Ответ 2");
    private final Question question3 = new Question("Вопрос 3", "Ответ 3");

    @BeforeEach
    void setUp() {
        when(questionService.getAll()).thenReturn(Arrays.asList(question1, question2, question3));
    }

    @Test
    void shouldReturnExactAmountOfQuestionsWhenEnoughAvailable() {
        when(questionService.getRandomQuestion()).thenReturn(question1).thenReturn(question2).thenReturn(question3);

        Collection<Question> result = examinerService.getQuestions(2);

        assertEquals(2, result.size());
        assertTrue(result.contains(question1));
        assertTrue(result.contains(question2));
    }

    @Test
    void shouldReturnAllQuestionsWhenAmountEqualsTotal() {
        when(questionService.getRandomQuestion()).thenReturn(question1).thenReturn(question2).thenReturn(question3);

        Collection<Question> result = examinerService.getQuestions(3);

        assertEquals(3, result.size());
        assertTrue(result.contains(question1));
        assertTrue(result.contains(question2));
        assertTrue(result.contains(question3));
    }

    @Test
    void shouldThrowExceptionWhenAmountIsZero() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            examinerService.getQuestions(0);
        });

        assertTrue(exception.getMessage().contains("Верное количество вопросов"));
    }

    @Test
    void shouldThrowExceptionWhenAmountIsNegative() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            examinerService.getQuestions(-1);
        });

        assertTrue(exception.getMessage().contains("Верное количество вопросов"));
    }

    @Test
    void shouldThrowExceptionWhenNotEnoughQuestionsAvailable() {
        when(questionService.getAll()).thenReturn(Arrays.asList(question1, question2));

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            examinerService.getQuestions(5);
        });

        assertTrue(exception.getMessage().contains("Недостаточно вопросов"));
    }

    @Test
    void shouldReturnUniqueQuestionsEvenIfRandomGivesDuplicates() {
        when(questionService.getRandomQuestion()).thenReturn(question1).thenReturn(question1).thenReturn(question1);

        Collection<Question> result = examinerService.getQuestions(1);

        assertEquals(1, result.size());
        assertTrue(result.contains(question1));
    }

    @Test
    void shouldCallGetRandomQuestionAtLeastAmountTimes() {
        when(questionService.getRandomQuestion()).thenReturn(question1).thenReturn(question2);

        examinerService.getQuestions(2);

        verify(questionService, atLeast(2)).getRandomQuestion();
    }
}
