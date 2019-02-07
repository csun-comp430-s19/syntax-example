import java.util.List;
import java.util.ArrayList;

public class Tokenizer {
    // begin instance variables
    private char[] input;
    private int inputPos;
    // end instance variables
    
    public Tokenizer(final char[] input) {
        this.input = input;
        inputPos = 0;
    }

    private NumberToken tryTokenizeNumber() {
        String digits = "";

        while (inputPos < input.length &&
               Character.isDigit(input[inputPos])) {
            digits += input[inputPos];
            inputPos++;
        }

        if (digits.length() > 0) {
            return new NumberToken(Integer.parseInt(digits));
        } else {
            return null;
        }
    }
    
    private VariableToken tryTokenizeVariable() {
        final int initialInputPos = inputPos;
        String name = "";

        // variables start with letters
        if (Character.isLetter(input[inputPos])) {
            name += input[inputPos];
            inputPos++;

            // variables can have letters or digits after the first
            // character
            while (inputPos < input.length &&
                   Character.isLetterOrDigit(input[inputPos])) {
                name += input[inputPos];
                inputPos++;
            }
        } else {
            return null;
        }

        if (name.equals("if")) {
            // reset input position
            inputPos = initialInputPos;
            return null;
        } else {
            return new VariableToken(name);
        }
    }

    private void skipWhitespace() {
        while (inputPos < input.length &&
               Character.isWhitespace(input[inputPos])) {
            inputPos++;
        }
    }
    
    private Token tokenizeOne() throws TokenizerException {
        VariableToken var = null;
        NumberToken num = null;

        if ((var = tryTokenizeVariable()) != null) {
            return var;
        } else if ((num = tryTokenizeNumber()) != null) {
            return num;
        } else if (input[inputPos] == '(') {
            inputPos++;
            return new LeftParenToken();
        } else if (input[inputPos] == 'i' &&
                   input[inputPos + 1] == 'f') {
            inputPos += 2;
            return new IfToken();
        } else {
            throw new TokenizerException("Invalid character: " +
                                         input[inputPos] + " at pos " +
                                         inputPos);
        }
    }
    
    private List<Token> tokenize() throws TokenizerException {
        List<Token> list = new ArrayList<Token>();

        while (inputPos < input.size) {
            skipWhitespace();

            if (inputPos < input.size) {
                final Token current = tokenizeOne();
                list.add(current);
            }
        }
    }
}

        
