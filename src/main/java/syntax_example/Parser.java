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
    
    private ParseResult parsePrimary(final int startPos) {
        // STOPPED HERE
}
