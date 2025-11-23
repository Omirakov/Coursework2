package coursework2.examinerservice.controller;

import coursework2.examinerservice.domain.Question;
import coursework2.examinerservice.service.MathQuestionService;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/exam/math")
public class MathQuestionController {

    private final MathQuestionService mathQuestionService;

    public MathQuestionController(MathQuestionService mathQuestionService) {
        this.mathQuestionService = mathQuestionService;
    }

    @PostMapping("/add")
    public Question addQuestion(@RequestParam String question, @RequestParam String answer) {
        return mathQuestionService.add(question, answer);
    }

    @DeleteMapping("/remove")
    public Question removeQuestion(@RequestParam String question, @RequestParam String answer) {
        return mathQuestionService.remove(new Question(question, answer));
    }

    @GetMapping("/find")
    public Collection<Question> getAllQuestions() {
        return mathQuestionService.getAll();
    }

    @GetMapping("/random")
    public Question getRandomQuestion() {
        return mathQuestionService.getRandomQuestion();
    }
}