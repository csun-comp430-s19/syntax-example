package syntax_example;

public class UnaryMinusExp implements Exp {
    public final Exp child;

    public UnaryMinusExp(final Exp child) {
        this.child = child;
    }
}
