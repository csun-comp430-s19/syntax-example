package syntax_example;

public class MultOp implements Op {
    public int hashCode() { return 2; }
    public boolean equals(final Object other) {
        return other instanceof MultOp;
    }
    public String toString() {
        return "*";
    }
}
