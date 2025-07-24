// Modern Java Web Version - Deploys like Node.js!

@RestController
@CrossOrigin(origins = "*")
public class QuizController {
    
    @Autowired
    private QuizService quizService;
    
    @PostMapping("/api/users")
    public ResponseEntity<User> createUser(@RequestBody CreateUserRequest request) {
        User user = quizService.createUser(request.getName(), request.getEmail());
        return ResponseEntity.ok(user);
    }
    
    @GetMapping("/api/questions/random")
    public ResponseEntity<List<Question>> getRandomQuestions() {
        List<Question> questions = quizService.getRandomQuestions(10);
        return ResponseEntity.ok(questions);
    }
    
    @PostMapping("/api/quiz/submit")
    public ResponseEntity<QuizResult> submitQuiz(@RequestBody SubmitQuizRequest request) {
        QuizResult result = quizService.submitQuiz(request.getUserId(), request.getAnswers());
        return ResponseEntity.ok(result);
    }
}

// Frontend: React/Vue.js (like any Node.js app)
// Deployment: Heroku, Railway, Render - ONE CLICK! ✅ 