/**
 * Created by Михаил on 23.10.2016.
 */
public abstract class Expression {
    public final Operations oper;
    protected int hash;

    public Expression(Operations oper) {
        this.oper = oper;
    }

    public int hashCode() {
        return hash;
    }
}
