package coursework2.examinerservice.repository;

import coursework2.examinerservice.domain.Question;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collection;

import static org.assertj.core.api.Assertions.*;

class MathQuestionRepositoryTest {

    private MathQuestionRepository repository;
    private Question question;

    @BeforeEach
    void setUp() {
        repository = new MathQuestionRepository();
        question = new Question("2 + 2", "4");
    }

    @Test
    void shouldAddQuestionWhenValid() {
        Question result = repository.add(question);

        assertThat(result).isEqualTo(question);
        assertThat(repository.getAll()).containsExactly(question);
    }

    @Test
    void shouldThrowIllegalArgumentExceptionWhenAddNull() {
        assertThatThrownBy(() -> repository.add(null)).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("Вопрос не может быть null");
    }

    @Test
    void shouldAddQuestionWithValidTextAndAnswer() {
        Question q = new Question("5 * 5", "25");
        repository.add(q);

        assertThat(repository.getAll()).contains(q);
    }

    @Test
    void shouldThrowIllegalArgumentExceptionWhenAddQuestionWithNullQuestion() {
        assertThatThrownBy(() -> new Question(null, "ответ")).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("Вопрос не может быть пустой строкой или null");
    }

    @Test
    void shouldThrowIllegalArgumentExceptionWhenAddQuestionWithBlankQuestion() {
        assertThatThrownBy(() -> new Question("  ", "ответ")).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("Вопрос не может быть пустой строкой или null");
    }

    @Test
    void shouldThrowIllegalArgumentExceptionWhenAddQuestionWithNullAnswer() {
        assertThatThrownBy(() -> new Question("вопрос", null)).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("Ответ не может быть пустой строкой или null");
    }

    @Test
    void shouldThrowIllegalArgumentExceptionWhenAddQuestionWithBlankAnswer() {
        assertThatThrownBy(() -> new Question("вопрос", "  ")).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("Ответ не может быть пустой строкой или null");
    }

    @Test
    void shouldRemoveQuestionWhenExists() {
        repository.add(question);
        Question removed = repository.remove(question);

        assertThat(removed).isEqualTo(question);
        assertThat(repository.getAll()).doesNotContain(question);
    }

    @Test
    void shouldThrowIllegalArgumentExceptionWhenRemoveNull() {
        assertThatThrownBy(() -> repository.remove(null)).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("Вопрос не может быть null");
    }

    @Test
    void shouldThrowIllegalArgumentExceptionWhenRemoveNonExistingQuestion() {
        Question notAdded = new Question("5 - 3", "2");

        assertThatThrownBy(() -> repository.remove(notAdded)).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("Вопрос не найден");
    }

    @Test
    void shouldGetAllQuestionsAsUnmodifiableCollection() {
        repository.add(new Question("Q1", "A1"));
        repository.add(new Question("Q2", "A2"));

        Collection<Question> all = repository.getAll();

        assertThat(all).hasSize(2);
        assertThatThrownBy(() -> all.clear()).isInstanceOf(UnsupportedOperationException.class);
    }

    @Test
    void shouldContainAddedQuestion() {
        repository.add(question);

        boolean contains = repository.contains(question);

        assertThat(contains).isTrue();
    }

    @Test
    void shouldNotContainNotAddedQuestion() {
        boolean contains = repository.contains(question);

        assertThat(contains).isFalse();
    }

    @Test
    void shouldReturnSizeCorrectly() {
        assertThat(repository.size()).isZero();

        repository.add(question);

        assertThat(repository.size()).isEqualTo(1);
    }

    @Test
    void shouldAddFiveQuestionsViaInit() {
        repository.init();

        Collection<Question> all = repository.getAll();
        assertThat(all).hasSize(5);
    }

    @Test
    void shouldNotAllowDuplicateQuestions() {
        repository.add(question);
        repository.add(new Question("2 + 2", "4")); // тот же вопрос

        assertThat(repository.getAll()).hasSize(1);
    }

    @Test
    void shouldTreatDifferentAnswerAsDifferentQuestion() {
        Question q1 = new Question("2 + 2", "4");
        Question q2 = new Question("2 + 2", "5");
        repository.add(q1);
        repository.add(q2);

        assertThat(repository.getAll()).hasSize(2);
    }
}
