package coursework2.examinerservice.domain;

import java.util.Objects;

public class Question {

    private final String question;
    private final String answer;

    public Question(String question, String answer) {
        if (question == null || question.isBlank()) {
            throw new IllegalArgumentException("Вопрос не может быть пустой строкой или null");
        }
        if (answer == null || answer.isBlank()) {
            throw new IllegalArgumentException("Ответ не может быть пустой строкой или null");
        }
        this.question = question;
        this.answer = answer;
    }

    public String getQuestion() {
        return question;
    }

    public String getAnswer() {
        return answer;
    }

    @Override
    public String toString() {
        return "Question{" + "question='" + question + '\'' + ", answer='" + answer + '\'' + '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Question q)) return false;
        return Objects.equals(this.question, q.question) && Objects.equals(answer, q.answer);
    }

    @Override
    public int hashCode() {
        return Objects.hash(question, answer);
    }

}
