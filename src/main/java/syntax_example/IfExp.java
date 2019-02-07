package syntax_example;

public class IfExp implements Exp {
    public final Exp guard;
    public final Exp ifTrue;
    public final Exp ifFalse;

    public IfExp(final Exp guard,
                 final Exp ifTrue,
                 final Exp ifFalse) {
        this.guard = guard;
        this.ifTrue = ifTrue;
        this.ifFalse = ifFalse;
    }

    public int hashCode() {
        return (guard.hashCode() +
                ifTrue.hashCode() +
                ifFalse.hashCode());
    }

    public boolean equals(final Object other) {
        if (other instanceof IfExp) {
            final IfExp otherExp = (IfExp)other;
            return (otherExp.guard.equals(guard) &&
                    otherExp.ifTrue.equals(ifTrue) &&
                    otherExp.ifFalse.equals(ifFalse));
        } else {
            return false;
        }
    }
}
