/**
 * Created by Михаил on 23.10.2016.
 */
public class BinaryExpression extends Expression {
    final Expression left, right;
    public BinaryExpression (Expression left, Expression right, Operations oper) {
        super(oper);
        this.left = left;
        this.right = right;
        hash = 23930 * left.hashCode() + 30566 * right.hashCode() + oper.hashCode() * 56630;
    }
    public boolean equals(Object o) {
        return o instanceof BinaryExpression && equals((BinaryExpression) o);
    }
    public boolean equals(BinaryExpression Exp) {
        if (Exp.hashCode() == hashCode() && left.equals(Exp.left) && right.equals(Exp.right) && oper.equals(Exp.oper)) {
            return true;
        } else return false;
    }
}
