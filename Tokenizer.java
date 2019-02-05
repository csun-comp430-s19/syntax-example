import java.util.List;
import java.util.ArrayList;

public class Tokenizer {
    public static VariableToken tryTokenizeVariable(char[] input,
                                                    int inputPos) {
        List<Character> name = new ArrayList<Character>();

        if (input[inputPos] is legal first character) {
            name.add(input[inputPos]);
            while (inputPos < input.length) {
                if (input[inputPos] is legal next character) {
                    
        
    public static List<Token> tokenize(char[] input) {
        List<Token> list = new ArrayList<Token>();
        int inputPos = 0;

        while (inputPos < input.size) {
            if (input[inputPos] == '(') {
                list.add(new LeftParenToken());
            } else if (input[inputPos] == 'i' &&
                input[inputPos + 1] == 'f') {
                list.add(new IfToken());
            }
        }
    }
}

        
