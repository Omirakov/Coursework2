package coursework2.examinerservice.repository;

import coursework2.examinerservice.domain.Question;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collection;

import static org.assertj.core.api.Assertions.*;

class JavaQuestionRepositoryTest {

    private JavaQuestionRepository repository;
    private Question question;

    @BeforeEach
    void setUp() {
        repository = new JavaQuestionRepository();
        question = new Question("Что такое JVM?", "Виртуальная машина Java");
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
        Question notAdded = new Question("Какой метод main?", "public static void main");

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

        assertThat(repository.getAll()).hasSize(5);
    }

    @Test
    void shouldNotAllowDuplicateQuestions() {
        repository.add(question);
        repository.add(new Question("Что такое JVM?", "Виртуальная машина Java")); // дубликат

        assertThat(repository.getAll()).hasSize(1);
    }

    @Test
    void shouldTreatDifferentAnswerAsDifferentQuestion() {
        Question q1 = new Question("Что такое JVM?", "Виртуальная машина Java");
        Question q2 = new Question("Что такое JVM?", "Java Virtual Machine");
        repository.add(q1);
        repository.add(q2);

        assertThat(repository.getAll()).hasSize(2);
    }
}