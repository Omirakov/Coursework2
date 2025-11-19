package coursework2.examinerservice.controller;

import coursework2.examinerservice.domain.Question;
import coursework2.examinerservice.service.JavaQuestionService;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/exam/java")
public class JavaQuestionController {

    private final JavaQuestionService javaQuestionService;

    public JavaQuestionController(JavaQuestionService javaQuestionService) {
        this.javaQuestionService = javaQuestionService;
    }

    @PostMapping("/add")
    public Question addQuestion(@RequestParam String question, @RequestParam String answer) {
        return javaQuestionService.add(question, answer);
    }

    @DeleteMapping("/remove")
    public Question removeQuestion(@RequestBody Question question) {
        return javaQuestionService.remove(question);
    }

    @GetMapping("/find")
    public Collection<Question> getAllQuestions() {
        return javaQuestionService.getAll();
    }

    @GetMapping("/random")
    public Question getRandomQuestion() {
        return javaQuestionService.getRandomQuestion();
    }
}
