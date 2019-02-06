package syntax_example;

public class NumberToken implements Token {
    public int number;

    public NumberToken(int number) {
        this.number = number;
    }
}
