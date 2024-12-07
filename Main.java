import java.util.Stack;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;




public class Main extends Application{
    Scene scene;
    VBox vBox;
    HBox hBox;
    FlowPane flowPane;
    
    Button b1;
    Button b2;
    TextField t1;
    TextField t2;
    
    TextField v1;
    TextField v2;
    TextField v3;
    TextField v4;
    VBox vBox2;
    
    
    @Override
    public void start(Stage stage){
        t1 = new TextField();
        t1.setPromptText("Enter a expression");
        t1.setMaxSize(300,35);
        
        t2 = new TextField();
        t2.setPromptText("Output: ");
        t2.setMaxSize(300,35);
        t2.setEditable(false);
        
        b1 = new Button("Converting");
        b2 = new Button("Evaluating");
        
        
        flowPane = new FlowPane();
        flowPane.setAlignment(Pos.CENTER);
        flowPane.setHgap(6);
        
        flowPane.getChildren().addAll(b1,b2);
        
        vBox = new VBox(6);
        vBox.setAlignment(Pos.CENTER);
        vBox.getChildren().addAll(t1, t2,flowPane);
        
        
        v1 = new TextField();
        v1.setPromptText("var A :");
        
        v2 = new TextField();
        v2.setPromptText("var B :");
        
        v3 = new TextField();
        v3.setPromptText("var C :");
        
        v4 = new TextField();
        v4.setPromptText("var D :");
        
        vBox2 = new VBox(6);
        vBox2.setAlignment(Pos.CENTER);
        vBox2.getChildren().addAll(v1,v2,v3,v4);
        
        hBox = new HBox(6);
        hBox.getChildren().addAll(vBox,vBox2);
        
        event();
        
        scene = new Scene(hBox, 650,700);
        stage.setScene(scene);
        stage.setTitle("Infix to Postfix Conversion and Evaluation");
        stage.show();
    }
    
    public void event(){
        b1.setOnAction(e ->{
            if(t1.getText().isEmpty()){
                t2.setText("you must enter a expression");
                return;
            }
            
            t2.setText(infixToPostfix(t1.getText()));
        });
        
        b2.setOnAction(e ->{
            if(t1.getText().isEmpty()){
                t2.setText("you must enter a expression");
                return;
            }
            
            t2.setText(String.valueOf(evaluate(t1.getText())));
        });
    }
    
    public int precedence(char c){
        switch(c){
            case '+':
            case '-':
                return 1;
            
            case '*':
            case '/':
                return 2;
            
            case '^':
                return 3;
            default:
                return -1;
        }
    }
    
    public String infixToPostfix(String exp){
        String result = "";

        Stack<Character> stack = new Stack<>();

        for (int i = 0; i < exp.length(); i++) {
            char c = exp.charAt(i);

            if (Character.isLetterOrDigit(c)) {
                result += c;
            } 
            
            else if (c == '(') {
                stack.push(c);
            } 
            
            else if (c == ')') {
                while (!stack.isEmpty() && stack.peek() != '(') {
                    result += stack.pop();
                }
                if (!stack.isEmpty() && stack.peek() == '(') {
                    stack.pop();
                }
            } 
            
            else {
                while (!stack.isEmpty() && precedence(c) < precedence(stack.peek())) {
                    result += stack.pop();
                }
                stack.push(c);
            }
        }

        while (!stack.isEmpty()) {
            result += stack.pop();
        }

        return result;
    }
    
    public double evaluate(String exp){
        Stack<Double> stack = new Stack<>();
        
        for(int i = 0; i < exp.length(); i++){
            char c = exp.charAt(i);
            
            if(Character.isDigit(c)){
                stack.push((double)c);
            }
            else if(Character.isLetter(c)){
                switch (c) {
                    case 'A':
                    case 'a':
                        if(v1.getText().isEmpty()){
                            t2.setText("you must fill the values of variable !!");
                            break;
                        }
                        
                        stack.push(Double.valueOf(v1.getText()));
                        break;
                    
                    case 'B':
                    case 'b':
                        if(v2.getText().isEmpty()){
                            t2.setText("you must fill the values of variable !!");
                            break;
                        }
                        
                        stack.push(Double.valueOf(v2.getText()));
                        break;
                    
                    case 'C':
                    case 'c':
                        if(v3.getText().isEmpty()){
                            t2.setText("you must fill the values of variable !!");
                            break;
                        }
                        
                        stack.push(Double.valueOf(v3.getText()));
                        break;
                    
                    case 'D':
                    case 'd':
                        if(v4.getText().isEmpty()){
                            t2.setText("you must fill the values of variable !!");
                            break;
                        }
                        
                        stack.push(Double.valueOf(v4.getText()));
                        break;
                    default:
                        t2.setText("we only have 4 variables A,B,C,D");
                }
            }
            else if(c == '+' || c == '-' || c == '*' || c == '/' || c == '^'){
                if(stack.size() < 2){
                    t2.setText("There is somthing wrong !!");
                    break;
                }
                
                double op1 = stack.pop();
                double op2 = stack.pop();
                double res = 0;
                
                switch (c){
                    case '+': res = op2 + op1; break;
                    case '-': res = op2 - op1; break;
                    case '*': res = op2 * op1; break;
                    case '^': res = Math.pow(op2, op1); break;
                    case '/': if(op1 == 0){ t2.setText("you cannot divide by zero"); break;} res = op2 / op1; break;
                }
                
                stack.push(res);
            }
        }
        
        if(stack.size() != 1){
            t2.setText("There is somthing wrong !!");
            return -1;
        }
        
        return stack.pop();
    }
    
    
    public static void main(String[] args) {
        launch(args);
    }
}
