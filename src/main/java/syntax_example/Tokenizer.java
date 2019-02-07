package syntax_example;

import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;

public class Tokenizer {
    // begin static variables
    private static Map<Character, Token> SINGLE_CHAR_TOKENS =
        new HashMap<Character, Token>() {{
            put('+', new PlusToken());
            put('-', new MinusToken());
            put('*', new MultToken());
            put('/', new DivToken());
            put('(', new LeftParenToken());
            put(')', new RightParenToken());
            put('{', new LeftCurlyToken());
            put('}', new RightCurlyToken());
        }};
    private static IfToken IF_TOKEN = new IfToken();
    // end static variables
    
    // begin instance variables
    private final char[] input;
    private int inputPos; // position in the input
    // end instance variables
    
    public Tokenizer(final char[] input) {
        this.input = input;
        inputPos = 0;
    }

    // assumes there is at least one character left
    // returns null if it couldn't parse a number
    private NumberToken tryTokenizeNumber() {
        final int initialInputPos = inputPos;
        String digits = "";

        while (inputPos < input.length &&
               Character.isDigit(inputPos)) {
            digits += input[inputPos];
            inputPos++;
        }

        if (digits.length() > 0) {
            return new NumberToken(Integer.parseInt(digits));
        } else {
            // reset position
            inputPos = initialInputPos;
            return null;
        }
    }

    // assumes there is at least one character left
    // returns null if it couldn't parse a variable
    private VariableToken tryTokenizeVariable() {
        final int initialInputPos = inputPos;
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
            // reset position
            inputPos = initialInputPos;
            return null;
        }

        if (name.equals("if")) {
            // reset position
            inputPos = initialInputPos;
            return null;
        } else {
            return new VariableToken(name);
        }
    }

    // assumes there is at least one character left
    // returns null if it couldn't parse a token
    private Token tryTokenizeSingleChar() {
        final Token result = SINGLE_CHAR_TOKENS.get(input[inputPos]);
        if (result != null) {
            inputPos++;
        }
        return result;
    }
    
    private void skipWhitespace() {
        while (inputPos < input.length &&
               Character.isWhitespace(input[inputPos])) {
            inputPos++;
        }
    }

    // returns null if there are no more tokens
    public Token tokenizeSingle() throws TokenizerException {
        VariableToken var = null;
        NumberToken num = null;
        Token singleCharToken = null;

        skipWhitespace();

        if (inputPos >= input.length) {
            return null;
        } else if ((var = tryTokenizeVariable()) != null) {
            return var;
        } else if ((num = tryTokenizeNumber()) != null) {
            return num;
        } else if (inputPos < input.length - 1 &&
                   input[inputPos] == 'i' &&
                   input[inputPos + 1] == 'f') {
            inputPos += 2;
            return IF_TOKEN;
        } else if ((singleCharToken = tryTokenizeSingleChar()) != null) {
            return singleCharToken;
        } else {
            throw new TokenizerException("Invalid character " +
                                         input[inputPos] +
                                         " at position " +
                                         inputPos);
        }
    }
    
    public List<Token> tokenize() throws TokenizerException {
        List<Token> list = new ArrayList<Token>();
        Token current = null;
            
        while ((current = tokenizeSingle()) != null) {
            list.add(current);
        }

        return list;
    }
}

        
