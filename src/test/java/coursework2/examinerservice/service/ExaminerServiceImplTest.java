package coursework2.examinerservice.service;

import coursework2.examinerservice.domain.Question;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ExaminerServiceImplTest {

    @Mock
    private QuestionService javaQuestionService;

    @Mock
    private QuestionService mathQuestionService;

    @InjectMocks
    private ExaminerServiceImpl examinerService;

    private final Question javaQ1 = new Question("Что такое JVM?", "Виртуальная машина Java");
    private final Question javaQ2 = new Question("Что такое JDK?", "Комплект разработчика Java");
    private final Question mathQ1 = new Question("2 + 3 = ?", "5");

    @BeforeEach
    void setUp() {
        reset(javaQuestionService, mathQuestionService);
    }

    @Test
    void shouldThrowIllegalArgumentExceptionWhenAmountIsZero() {
        assertThatThrownBy(() -> examinerService.getQuestions(0)).isInstanceOf(IllegalArgumentException.class).hasMessage("Количество вопросов должно быть положительным");
    }

    @Test
    void shouldThrowIllegalArgumentExceptionWhenAmountIsNegative() {
        assertThatThrownBy(() -> examinerService.getQuestions(-1)).isInstanceOf(IllegalArgumentException.class).hasMessage("Количество вопросов должно быть положительным");
    }

    @Test
    void shouldReturnExactAmountOfQuestionsWhenEnoughAvailable() {
        when(javaQuestionService.getRandomQuestion()).thenReturn(javaQ1);
        when(mathQuestionService.getRandomQuestion()).thenReturn(mathQ1);

        Collection<Question> result = examinerService.getQuestions(2);

        assertThat(result).hasSize(2).containsExactlyInAnyOrder(javaQ1, mathQ1);
        verify(javaQuestionService, times(1)).getRandomQuestion();
        verify(mathQuestionService, times(1)).getRandomQuestion();
    }

    @Test
    void shouldReturnMultipleQuestionsFromDifferentServicesInShuffledOrder() {
        when(javaQuestionService.getRandomQuestion()).thenReturn(javaQ1, javaQ2);
        when(mathQuestionService.getRandomQuestion()).thenReturn(mathQ1);

        Collection<Question> result = examinerService.getQuestions(3);

        assertThat(result).hasSize(3).contains(javaQ1, javaQ2, mathQ1);
    }

    @Test
    void shouldNotRepeatQuestionsFromSameServiceIfPossible() {
        when(javaQuestionService.getRandomQuestion()).thenReturn(javaQ1);
        when(mathQuestionService.getRandomQuestion()).thenReturn(mathQ1);

        Collection<Question> result = examinerService.getQuestions(2);

        assertThat(result).hasSize(2).doesNotHaveDuplicates();
    }

    @Test
    void shouldWorkWithSingleServiceWhenOnlyOneAvailable() {
        reset(javaQuestionService);
        when(javaQuestionService.getRandomQuestion()).thenReturn(javaQ1, javaQ2);

        ExaminerServiceImpl singleService = new ExaminerServiceImpl(Collections.singletonList(javaQuestionService));

        Collection<Question> result = singleService.getQuestions(2);

        assertThat(result).hasSize(2).containsExactly(javaQ1, javaQ2);
    }

    @Test
    void shouldThrowIllegalStateExceptionWhenNoServicesProvided() {
        assertThatThrownBy(() -> new ExaminerServiceImpl(Collections.emptyList())).isInstanceOf(IllegalStateException.class).hasMessage("Нет доступных сервисов вопросов");
    }

    @Test
    void shouldGenerateQuestionsEvenWhenSomeServicesReturnDuplicates() {
        when(javaQuestionService.getRandomQuestion()).thenReturn(javaQ1);
        when(mathQuestionService.getRandomQuestion()).thenReturn(javaQ1, javaQ2);

        Collection<Question> result = examinerService.getQuestions(2);

        assertThat(result).hasSize(2);
        assertThat(result).contains(javaQ1, javaQ2);
        verify(mathQuestionService, atLeast(2)).getRandomQuestion();
    }
}