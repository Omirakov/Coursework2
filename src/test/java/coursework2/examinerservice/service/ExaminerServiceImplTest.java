package coursework2.examinerservice.service;

import coursework2.examinerservice.domain.Question;
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

    private final Question q1 = new Question("Что такое JVM?", "Виртуальная машина Java");
    private final Question q2 = new Question("Что такое JRE?", "Среда выполнения Java");
    private final Question q3 = new Question("Что такое JDK?", "Комплект разработчика Java");

    @BeforeEach
    void setUp() {
        when(questionService.getAll()).thenReturn(Arrays.asList(q1, q2, q3));
    }

    @Test
    void shouldReturnExactAmountOfQuestionsWhenEnoughAvailable() {
        when(questionService.getRandomQuestion()).thenReturn(q1).thenReturn(q2);

        Collection<Question> result = examinerService.getQuestions(2);

        assertEquals(2, result.size());
        assertTrue(result.contains(q1));
        assertTrue(result.contains(q2));
    }

    @Test
    void shouldReturnAllQuestionsWhenAmountEqualsTotal() {
        when(questionService.getRandomQuestion()).thenReturn(q1).thenReturn(q2).thenReturn(q3);

        Collection<Question> result = examinerService.getQuestions(3);

        assertEquals(3, result.size());
        assertTrue(result.containsAll(Arrays.asList(q1, q2, q3)));
    }

    @Test
    void shouldThrowIllegalArgumentExceptionWhenAmountIsZero() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            examinerService.getQuestions(0);
        });

        String expectedMessage = "Количество вопросов должно быть положительным";
        assertTrue(exception.getMessage().contains(expectedMessage));
    }

    @Test
    void shouldThrowIllegalArgumentExceptionWhenAmountIsNegative() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            examinerService.getQuestions(-1);
        });

        String expectedMessage = "Количество вопросов должно быть положительным";
        assertTrue(exception.getMessage().contains(expectedMessage));
    }

    @Test
    void shouldThrowIllegalArgumentExceptionWhenNotEnoughQuestionsAvailable() {
        when(questionService.getAll()).thenReturn(Arrays.asList(q1, q2));

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            examinerService.getQuestions(5);
        });

        String expectedMessage = "Недостаточно вопросов в базе";
        assertTrue(exception.getMessage().contains(expectedMessage));
    }

    @Test
    void shouldReturnUniqueQuestionsAndAvoidDuplicates() {
        when(questionService.getRandomQuestion()).thenReturn(q1).thenReturn(q1).thenReturn(q2);

        Collection<Question> result = examinerService.getQuestions(2);

        assertEquals(2, result.size());
        assertTrue(result.contains(q1));
        assertTrue(result.contains(q2));
        verify(questionService, times(3)).getRandomQuestion();
    }

    @Test
    void shouldCallGetRandomQuestionUntilUniqueSetIsCollected() {
        when(questionService.getRandomQuestion()).thenReturn(q1, q2, q1, q3);

        Collection<Question> result = examinerService.getQuestions(3);

        assertEquals(3, result.size());
        assertTrue(result.containsAll(Arrays.asList(q1, q2, q3)));
        verify(questionService, times(4)).getRandomQuestion();
    }
}