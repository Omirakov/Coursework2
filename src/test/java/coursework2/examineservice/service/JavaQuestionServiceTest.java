package coursework2.examineservice.service;

import coursework2.examinerservice.domain.Question;
import coursework2.examinerservice.service.JavaQuestionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collection;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class JavaQuestionServiceTest {

    private JavaQuestionService javaQuestionService;

    private final String QUESTION_TEXT = "Что такое Java?";
    private final String ANSWER_TEXT = "Язык программирования";
    private final Question question = new Question(QUESTION_TEXT, ANSWER_TEXT);

    @BeforeEach
    void setUp() {
        javaQuestionService = new JavaQuestionService();
    }

    @Test
    void shouldAddQuestionWhenValid() {
        Question result = javaQuestionService.add(QUESTION_TEXT, ANSWER_TEXT);

        assertThat(result).isEqualTo(question);
        assertThat(javaQuestionService.getAll()).contains(question);
    }

    @Test
    void shouldAddQuestionObjectWhenValid() {
        Question result = javaQuestionService.add(question);

        assertThat(result).isEqualTo(question);
        assertThat(javaQuestionService.getAll()).contains(question);
    }

    @Test
    void shouldThrowIllegalArgumentExceptionWhenQuestionIsNull() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            javaQuestionService.add((Question) null);
        });

        assertThat(exception.getMessage()).contains("не может быть null");
    }

    @Test
    void shouldThrowIllegalArgumentExceptionWhenQuestionTextIsNull() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            javaQuestionService.add(null, ANSWER_TEXT);
        });

        assertThat(exception.getMessage()).contains("не может быть null");
    }

    @Test
    void shouldThrowIllegalArgumentExceptionWhenAnswerTextIsNull() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            javaQuestionService.add(QUESTION_TEXT, null);
        });

        assertThat(exception.getMessage()).contains("не может быть null");
    }

    @Test
    void shouldThrowIllegalArgumentExceptionWhenQuestionIsBlank() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            javaQuestionService.add("", "Ответ");
        });

        assertThat(exception.getMessage()).contains("не может быть null или пустой");
    }

    @Test
    void shouldThrowIllegalArgumentExceptionWhenAnswerIsBlank() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            javaQuestionService.add("Вопрос", "   ");
        });

        assertThat(exception.getMessage()).contains("не может быть null или пустой");
    }

    @Test
    void shouldRemoveQuestionWhenExists() {
        javaQuestionService.add(question);
        Question removed = javaQuestionService.remove(question);

        assertThat(removed).isEqualTo(question);
        assertThat(javaQuestionService.getAll()).doesNotContain(question);
    }

    @Test
    void shouldThrowNoSuchElementExceptionWhenRemoveNonExistingQuestion() {
        Exception exception = assertThrows(NoSuchElementException.class, () -> {
            javaQuestionService.remove(question);
        });

        assertThat(exception.getMessage()).contains("не найден");
    }

    @Test
    void shouldThrowIllegalArgumentExceptionWhenRemoveNull() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            javaQuestionService.remove(null);
        });

        assertThat(exception.getMessage()).contains("не может быть null");
    }

    @Test
    void shouldGetAllQuestions() {
        Question question1 = new Question("Вопрос 1", "Ответ 1");
        Question question2 = new Question("Вопрос 2", "Ответ 2");

        javaQuestionService.add(question1);
        javaQuestionService.add(question2);

        Collection<Question> allQuestions = javaQuestionService.getAll();

        assertThat(allQuestions).containsExactlyInAnyOrder(question1, question2);
        assertThat(allQuestions).isUnmodifiable();
    }

    @Test
    void shouldGetRandomQuestionWhenNotEmpty() {
        Question question1 = new Question("Вопрос 1", "Ответ 1");
        Question question2 = new Question("Вопрос 2", "Ответ 2");

        javaQuestionService.add(question1);
        javaQuestionService.add(question2);

        Question random = javaQuestionService.getRandomQuestion();

        assertThat(javaQuestionService.getAll()).contains(random);
    }

    @Test
    void shouldThrowNoSuchElementExceptionWhenGetRandomQuestionAndEmpty() {
        Exception exception = assertThrows(NoSuchElementException.class, () -> {
            javaQuestionService.getRandomQuestion();
        });

        assertThat(exception.getMessage()).contains("Вопросов нет");
    }
}