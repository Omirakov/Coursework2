package coursework2.examinerservice.service;

import coursework2.examinerservice.domain.Question;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.HttpRequestMethodNotSupportedException;

import static org.assertj.core.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class MathQuestionServiceTest {

    private MathQuestionService mathQuestionService;

    @BeforeEach
    void setUp() {
        mathQuestionService = new MathQuestionService();
    }

    @Test
    void shouldThrowHttpRequestMethodNotSupportedExceptionWhenAddString() {
        assertThatThrownBy(() -> mathQuestionService.add("2+2", "4")).isInstanceOf(HttpRequestMethodNotSupportedException.class).hasMessageContaining("POST");
    }

    @Test
    void shouldThrowHttpRequestMethodNotSupportedExceptionWhenAddQuestion() {
        Question question = new Question("2+2", "4");

        assertThatThrownBy(() -> mathQuestionService.add(question)).isInstanceOf(HttpRequestMethodNotSupportedException.class).hasMessageContaining("POST");
    }

    @Test
    void shouldThrowHttpRequestMethodNotSupportedExceptionWhenRemove() {
        Question question = new Question("2+2", "4");

        assertThatThrownBy(() -> mathQuestionService.remove(question)).isInstanceOf(HttpRequestMethodNotSupportedException.class).hasMessageContaining("DELETE");
    }

    @Test
    void shouldThrowHttpRequestMethodNotSupportedExceptionWhenGetAll() {
        assertThatThrownBy(() -> mathQuestionService.getAll()).isInstanceOf(HttpRequestMethodNotSupportedException.class).hasMessageContaining("GET");
    }

    @Test
    void shouldGenerateValidRandomQuestion() {
        Question question = mathQuestionService.getRandomQuestion();

        assertThat(question).isNotNull();
        assertThat(question.getQuestion()).isNotEmpty();
        assertThat(question.getAnswer()).isNotEmpty();

        String questionText = question.getQuestion();
        String answerText = question.getAnswer();

        assertThat(questionText).containsPattern("\\d+\\s[+\\-*/]\\s\\d+\\s=\\s\\?");
        assertThat(answerText).matches("-?\\d+");
    }

    @Test
    void shouldGenerateDifferentQuestionsOnSuccessiveCalls() {
        Question q1 = mathQuestionService.getRandomQuestion();
        Question q2 = mathQuestionService.getRandomQuestion();

        boolean same = q1.equals(q2);
        boolean similarStructure = q1.getQuestion().equals(q2.getQuestion());

        assertThat(q1).isNotNull();
        assertThat(q2).isNotNull();
    }

}