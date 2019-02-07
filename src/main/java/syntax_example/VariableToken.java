package syntax_example;

public class VariableToken implements Token {
    public final String name;

    public VariableToken(String name) {
        this.name = name;
    }

    public int hashCode() { return name.hashCode(); }
    public boolean equals(final Object other) {
        return (other instanceof VariableToken &&
                ((VariableToken)other).name.equals(name));
    }
    public String toString() {
        return name;
    }
}
