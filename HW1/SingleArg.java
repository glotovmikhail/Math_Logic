

/**
 * Created by Михаил on 23.10.2016.
 */
public class SingleArg extends Expression {
    final Expression arg;

    public SingleArg(Expression arg, Operations oper) {
        super(oper);
        this.arg = arg;
        hash = 239566 * arg.hashCode() + 30239 * oper.hashCode();
    }

    public boolean equals(Object o) {
        return o instanceof SingleArg && equals((SingleArg) o);
    }

    public boolean equals(SingleArg sa) {
        return (sa.hashCode() == hashCode() && sa.oper.equals(oper) && sa.arg.equals(arg));
    }
}
