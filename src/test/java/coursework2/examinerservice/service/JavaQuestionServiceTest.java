package coursework2.examinerservice.service;

import coursework2.examinerservice.domain.Question;
import coursework2.examinerservice.repository.JavaQuestionRepository;
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
class JavaQuestionServiceTest {

    @Mock
    private JavaQuestionRepository javaQuestionRepository;

    @InjectMocks
    private JavaQuestionService javaQuestionService;

    private final String QUESTION_TEXT = "Что такое JVM?";
    private final String ANSWER_TEXT = "Виртуальная машина Java";
    private final Question question = new Question(QUESTION_TEXT, ANSWER_TEXT);

    @BeforeEach
    void setUp() {
        reset(javaQuestionRepository);
    }

    @Test
    void shouldAddQuestionFromString() {
        when(javaQuestionRepository.add(question)).thenReturn(question);

        Question result = javaQuestionService.add(QUESTION_TEXT, ANSWER_TEXT);

        assertThat(result).isEqualTo(question);
        verify(javaQuestionRepository, times(1)).add(question);
    }

    @Test
    void shouldAddQuestionObjectWhenValid() {
        when(javaQuestionRepository.add(question)).thenReturn(question);

        Question result = javaQuestionService.add(question);

        assertThat(result).isEqualTo(question);
        verify(javaQuestionRepository, times(1)).add(question);
    }

    @Test
    void shouldThrowIllegalArgumentExceptionWhenAddNullQuestion() {
        assertThatThrownBy(() -> javaQuestionService.add((Question) null)).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("Вопрос не может быть null");
    }

    @Test
    void shouldThrowIllegalArgumentExceptionWhenAddNullStringQuestion() {
        assertThatThrownBy(() -> javaQuestionService.add(null, "ответ")).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("Вопрос не может быть null");
    }

    @Test
    void shouldThrowIllegalArgumentExceptionWhenAddNullStringAnswer() {
        assertThatThrownBy(() -> javaQuestionService.add("вопрос", null)).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("Вопрос не может быть null");
    }

    @Test
    void shouldRemoveQuestionWhenExists() {
        when(javaQuestionRepository.remove(question)).thenReturn(question);

        Question removed = javaQuestionService.remove(question);

        assertThat(removed).isEqualTo(question);
        verify(javaQuestionRepository, times(1)).remove(question);
    }

    @Test
    void shouldThrowIllegalArgumentExceptionWhenRemoveNull() {
        assertThatThrownBy(() -> javaQuestionService.remove(null)).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("Вопрос не может быть null");
    }

    @Test
    void shouldGetAllQuestions() {
        List<Question> expected = Collections.singletonList(question);
        when(javaQuestionRepository.getAll()).thenReturn(expected);

        Collection<Question> all = javaQuestionService.getAll();

        assertThat(all).containsExactlyElementsOf(expected);
        verify(javaQuestionRepository, times(1)).getAll();
    }

    @Test
    void shouldGetRandomQuestionWhenRepositoryHasQuestions() {
        when(javaQuestionRepository.getAll()).thenReturn(Collections.singletonList(question));

        Question random = javaQuestionService.getRandomQuestion();

        assertThat(random).isEqualTo(question);
        verify(javaQuestionRepository, times(1)).getAll();
    }

    @Test
    void shouldThrowNoSuchElementExceptionWhenGetRandomQuestionAndRepositoryEmpty() {
        when(javaQuestionRepository.getAll()).thenReturn(Collections.emptyList());

        assertThatThrownBy(() -> javaQuestionService.getRandomQuestion()).isInstanceOf(NoSuchElementException.class).hasMessageContaining("Вопросов нет");
        verify(javaQuestionRepository, times(1)).getAll();
    }

}