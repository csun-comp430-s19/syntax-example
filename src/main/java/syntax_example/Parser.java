public class Parser {
    private final Token[] tokens;

    public Parser(final Token[] tokens) {
        this.tokens = tokens;
    }

    private class ParseResult {
        public final Exp exp;
        public final int pos;

        public ParseResult(final Exp exp,
                           final int pos) {
            this.exp = exp;
            this.pos = pos;
        }
    }

    private ParseResult parseExp(final int startPos) throws ParseException {
        return parseAdditive(startPos);
    }

    private ParseResult parseAdditive(final int startPos) throws ParseException {
        final ParseResult startingMult = parseMultiplicative(startPos);

        if (startingMult.pos < tokens.length &&
            (tokens[startingMult.pos] instanceof PlusToken ||
             tokens[startingMult.pos] instanceof MinusToken)) {

            
        final Token nextToken = tokens[startingMult.pos];
        
        if (nextToken instanceof PlusToken) {
            final ParseResult nextMult = parseMultiplicative(startingMult.pos + 1);
            return new BinaryExp(startingMult.exp,
                                 new PlusOp(),
                                 nextMult.exp);
        } else if (nextToken instanceof MinusToken) {
            final ParseResult nextMult = parseMultiplicative(startingMult.pos + 1);
            return new BinaryExp(startingMult.exp,
                                 new MinusOp(),
                                 nextMult.exp);            
        } else {
            throw new ParseException("neither plus nor minus: " + nextToken.toString());
        }
    }

    private ParseResult parseMultiplicative(final int startPos) throws ParseException {
        // ???
    }
    
    private void ensureTokenIs(final int position, final Token expected) throws ParseException {
        final Token tokenHere = tokens[position];
        if (!expected.equals(tokenHere)) {
            throw new ParseException(expected.toString() + " expected at position: " + position);
        }
    }
    
    private ParseResult parsePrimary(final int startPos) throws ParseException {
        final Token tokenHere = tokens[startPos];

        if (tokenHere instanceof NumberToken) {
            final NumberToken asNumber = (NumberToken)token;
            final Exp expHere = new NumberExp(asNumber.number);
            final int nextPos = startPos + 1;
            return new ParseResult(expHere, nextPos);
        } else if (tokenHere instanceof VariableToken) {
            final VariableToken asVariable = (VariableToken)token;
            final Exp expHere = new VariableExp(asVariable.name);
            final int nextPos = startPos + 1;
            return new ParseResult(expHere, nextPos);
        } else if (tokenHere instanceof MinusToken) {
            final ParseResult nested = parsePrimary(startPos + 1);
            final Exp expHere = new UnaryMinusExp(nested.exp);
            final int nextPos = nested.pos;
            return new ParseResult(expHere, nextPos);
        } else if (tokenHere instanceof IfToken) {
            ensureTokenIs(startPos + 1, new LeftParenToken());
            final ParseResult guard = parseExp(startPos + 2);
            ensureTokenIs(guard.pos, new RightParenToken());
            ensureTokenIs(guard.pos + 1, new LeftCurlyToken());
            final ParseResult ifTrue = parseExp(guard.pos + 2);
            ensureTokenIs(ifTrue.pos, new RightCurlyToken());
            ensureTokenIs(ifTrue.pos + 1, new ElseToken());
            ensureTokenIs(ifTrue.pos + 2, new LeftCurlyToken());
            final ParseResult ifFalse = parseExp(ifTrue.pos + 3);
            ensureTokenIs(ifFalse.pos, new RightCurlyToken());
            final Exp expHere = new IfExp(guard.exp, ifTrue.exp, ifFalse.exp);
            final int nextPos = ifFalse.pos + 1;
            return new ParseResult(expHere, nextPos);
        } else if (tokenHere instanceof LeftParenToken) {
            final ParseResult nested = parseExp(startPos + 1);
            ensureTokenIs(nested.pos, new RightParenToken());
            return new ParseResult(nested.exp, nested.pos + 1);
        } else {
            throw new ParseException("not a primary at pos: " + startPos);
        }
    }
}
