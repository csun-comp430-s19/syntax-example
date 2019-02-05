import java.util.List;
import java.util.ArrayList;

public class Tokenizer {
    //public static Optional<VariableToken>
    public static VariableToken tryTokenizeVariable(char[] input,
                                                    int inputPos) {
        String name = "";

        if (Character.isLetter(input[inputPos])) {
            name += input[inputPos];
            inputPos++;
            
            while (inputPos < input.length &&
                   Character.isLetterOrDigit(input[inputPos])) {
                name += input[inputPos];
                inputPos++;
            }
        } else {
            return null;
        }

        if (name.equals("if")) {
            return null;
        } else {
            return new VariableToken(name);
        }
    }
        
    public static List<Token> tokenize(char[] input) {
        List<Token> list = new ArrayList<Token>();
        int inputPos = 0;

        while (inputPos < input.size) {
            if (input[inputPos] == '(') {
                list.add(new LeftParenToken());
                inputPos++;
            } else if (input[inputPos] == 'i' &&
                       input[inputPos + 1] == 'f') {
                list.add(new IfToken());
                inputPos += 2;
            }
        }
    }
}

        
