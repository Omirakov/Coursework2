package coursework2.examinerservice.service;

import coursework2.examinerservice.domain.Question;
import coursework2.examinerservice.repository.MathQuestionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MathQuestionServiceTest {

    @Mock
    private MathQuestionRepository mathQuestionRepository;

    private MathQuestionService mathQuestionService;

    private final Question question1 = new Question("2 + 2", "4");
    private final Question question2 = new Question("5 * 6", "30");

    @BeforeEach
    void setUp() {
        mathQuestionService = new MathQuestionService(mathQuestionRepository);
    }

    @Test
    void shouldAddQuestionWhenValid() {
        when(mathQuestionRepository.add(question1)).thenReturn(question1);

        Question result = mathQuestionService.add(question1);

        assertEquals(question1, result);
        verify(mathQuestionRepository, times(1)).add(question1);
    }

    @Test
    void shouldAddQuestionFromString() {
        when(mathQuestionRepository.add(question1)).thenReturn(question1);

        Question result = mathQuestionService.add("2 + 2", "4");

        assertEquals(question1, result);
        verify(mathQuestionRepository, times(1)).add(question1);
    }

    @Test
    void shouldThrowIllegalArgumentExceptionWhenAddNull() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            mathQuestionService.add((Question) null);
        });

        assertTrue(exception.getMessage().contains("Вопрос не может быть null"));
    }

    @Test
    void shouldRemoveQuestionWhenExists() {
        when(mathQuestionRepository.remove(question1)).thenReturn(question1);

        Question result = mathQuestionService.remove(question1);

        assertEquals(question1, result);
        verify(mathQuestionRepository, times(1)).remove(question1);
    }

    @Test
    void shouldThrowIllegalArgumentExceptionWhenRemoveNull() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            mathQuestionService.remove(null);
        });

        assertTrue(exception.getMessage().contains("Вопрос не может быть null"));
    }

    @Test
    void shouldThrowExceptionWhenRemoveNonExistingQuestion() {
        doThrow(new IllegalArgumentException("Вопрос не найден")).when(mathQuestionRepository).remove(question1);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            mathQuestionService.remove(question1);
        });

        assertTrue(exception.getMessage().contains("Вопрос не найден"));
    }

    @Test
    void shouldGetAllQuestions() {
        List<Question> expected = Arrays.asList(question1, question2);
        when(mathQuestionRepository.getAll()).thenReturn(expected);

        Collection<Question> result = mathQuestionService.getAll();

        assertEquals(expected, result);
        verify(mathQuestionRepository, times(1)).getAll();
    }

    @Test
    void shouldGetRandomQuestionWhenNotEmpty() {
        when(mathQuestionRepository.getAll()).thenReturn(Arrays.asList(question1, question2));

        Question random = mathQuestionService.getRandomQuestion();

        assertNotNull(random);
        assertTrue(Arrays.asList(question1, question2).contains(random));
    }

    @Test
    void shouldThrowNoSuchElementExceptionWhenGetRandomQuestionAndEmpty() {
        when(mathQuestionRepository.getAll()).thenReturn(Collections.emptyList());

        Exception exception = assertThrows(NoSuchElementException.class, () -> {
            mathQuestionService.getRandomQuestion();
        });

        assertTrue(exception.getMessage().contains("Вопросов нет"));
    }
}
