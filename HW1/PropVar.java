

/**
 * Created by Михаил on 24.10.2016.
 */
public class PropVar extends Expression{
    final String var;
    final int varNumber;
    public PropVar (int varNumber, String var) {
        super(null);
        this.var = var;
        this.varNumber = varNumber;
        hash = 239566 * var.hashCode();
    }

    public boolean equals(Object o) {
        return o instanceof PropVar && equals((PropVar) o);
    }
    public boolean equals(PropVar prop) {
        return (var.equals(prop.var));
    }
}
