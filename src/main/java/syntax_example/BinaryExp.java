package syntax_example;

public class BinaryExp implements Exp {
    public final Exp left;
    public final Op op;
    public final Exp right;

    public BinaryExp(final Exp left,
                     final Op op,
                     final Exp right) {
        this.left = left;
        this.op = op;
        this.right = right;
    }
}

        
