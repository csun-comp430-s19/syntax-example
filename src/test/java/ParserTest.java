package syntax_example;

import java.util.Arrays;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

public class ParserTest {
    // specify null for expected if it's not supposed to parse
    public void assertParses(final Token[] tokens,
                             final Exp expected) {
        final Parser parser = new Parser(tokens);
        try {
            final Exp received = parser.parseExp();
            assertTrue("Expected parse failure; got: " + received,
                       expected != null);
            assertEquals(expected, received);
        } catch (final ParserException e) {
            assertTrue(("Unexpected parse failure for " +
                        Arrays.toString(tokens) +
                        ": " + e.getMessage()),
                       expected == null);
        }
    } // assertParses

    @Test
    public void testParsesInteger() {
        assertParses(new Token[]{ new NumberToken(123) },
                     new NumberExp(123));
    }

    @Test
    public void testParsesVariable() {
        assertParses(new Token[]{ new VariableToken("foo") },
                     new VariableExp("foo"));
    }

    @Test
    public void testParsesUnaryMinus() {
        final Token[] tokens = { new MinusToken(),
                                 new NumberToken(42) };
        final Exp expected =
            new UnaryMinusExp(new NumberExp(42));
        assertParses(tokens, expected);
    }

    @Test
    public void testParsesIf() {
        final Token[] tokens = { new IfToken(),
                                 new LeftParenToken(),
                                 new NumberToken(0),
                                 new RightParenToken(),
                                 new LeftCurlyToken(),
                                 new NumberToken(1),
                                 new RightCurlyToken(),
                                 new ElseToken(),
                                 new LeftCurlyToken(),
                                 new NumberToken(2),
                                 new RightCurlyToken() };
        final Exp expected = new IfExp(new NumberExp(0),
                                       new NumberExp(1),
                                       new NumberExp(2));
        assertParses(tokens, expected);
    }

    @Test
    public void testParsesParens() {
        final Token[] tokens = { new LeftParenToken(),
                                 new NumberToken(1),
                                 new RightParenToken() };
        final Exp expected = new NumberExp(1);
        assertParses(tokens, expected);
    }

    @Test
    public void testParsesPlus() {
        final Token[] tokens = { new NumberToken(1),
                                 new PlusToken(),
                                 new NumberToken(2) };
        final Exp expected = new BinopExp(new NumberExp(1),
                                          new PlusOp(),
                                          new NumberExp(2));
        assertParses(tokens, expected);
    }

    @Test
    public void testParsesMinus() {
        final Token[] tokens = { new NumberToken(1),
                                 new MinusToken(),
                                 new NumberToken(2) };
        final Exp expected = new BinopExp(new NumberExp(1),
                                          new MinusOp(),
                                          new NumberExp(2));
        assertParses(tokens, expected);
    }

    @Test
    public void testParsesMult() {
        final Token[] tokens = { new NumberToken(1),
                                 new MultToken(),
                                 new NumberToken(2) };
        final Exp expected = new BinopExp(new NumberExp(1),
                                          new MultOp(),
                                          new NumberExp(2));
        assertParses(tokens, expected);
    }

    @Test
    public void testParsesDiv() {
        final Token[] tokens = { new NumberToken(1),
                                 new DivToken(),
                                 new NumberToken(2) };
        final Exp expected = new BinopExp(new NumberExp(1),
                                          new DivOp(),
                                          new NumberExp(2));
        assertParses(tokens, expected);
    }

    @Test
    public void testArithmeticLeftAssociative() {
        final Token[] tokens = { new NumberToken(1),
                                 new PlusToken(),
                                 new NumberToken(2),
                                 new MinusToken(),
                                 new NumberToken(3) };
        final Exp expected = new BinopExp(new BinopExp(new NumberExp(1),
                                                       new PlusOp(),
                                                       new NumberExp(2)),
                                          new MinusOp(),
                                          new NumberExp(3));
        assertParses(tokens, expected);
    }

    @Test
    public void testArithmeticPrecedence() {
        final Token[] tokens = { new NumberToken(1),
                                 new MinusToken(),
                                 new NumberToken(2),
                                 new DivToken(),
                                 new NumberToken(3) };
        final Exp expected = new BinopExp(new NumberExp(1),
                                          new MinusOp(),
                                          new BinopExp(new NumberExp(2),
                                                       new DivOp(),
                                                       new NumberExp(3)));
        assertParses(tokens, expected);
    }

    @Test
    public void testArithmeticPrecedenceWithParens() {
        final Token[] tokens = { new LeftParenToken(),
                                 new NumberToken(1),
                                 new MinusToken(),
                                 new NumberToken(2),
                                 new RightParenToken(),
                                 new DivToken(),
                                 new NumberToken(3) };
        final Exp expected = new BinopExp(new BinopExp(new NumberExp(1),
                                                       new MinusOp(),
                                                       new NumberExp(2)),
                                          new DivOp(),
                                          new NumberExp(3));
        assertParses(tokens, expected);
    }

    @Test
    public void testUnaryWithBinaryMinusUnaryFirst() {
        final Token[] tokens = { new MinusToken(),
                                 new NumberToken(1),
                                 new MinusToken(),
                                 new NumberToken(2) };
        final Exp expected = new BinopExp(new UnaryMinusExp(new NumberExp(1)),
                                          new MinusOp(),
                                          new NumberExp(2));
        assertParses(tokens, expected);
    }

    @Test
    public void testUnaryWithBinaryMinusUnarySecond() {
        final Token[] tokens = { new NumberToken(1),
                                 new MinusToken(),
                                 new MinusToken(),
                                 new NumberToken(2) };
        final Exp expected = new BinopExp(new NumberExp(1),
                                          new MinusOp(),
                                          new UnaryMinusExp(new NumberExp(2)));
        assertParses(tokens, expected);
    }
}
