package syntax_example;

public class UnaryMinusExp implements Exp {
    public final Exp nested;

    public UnaryMinusExp(final Exp nested) {
        this.nested = nested;
    }

    public int hashCode() {
        return nested.hashCode();
    }

    public boolean equals(final Object other) {
        return (other instanceof UnaryMinusExp &&
                ((UnaryMinusExp)other).nested.equals(nested));
    }

    public String toString() {
        return "(- " + nested.toString() + ")";
    }
}
