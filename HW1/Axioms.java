/**
 * Created by Михаил on 20.10.2016.
 */

import java.util.*;

public class Axioms {
    static ArrayList<Expression> axioms;
    static {
        String[] axiomsAll = {
                "a->b->a",
                "(a->b)->(a->b->c)->(a->c)",
                "a->b->a&b",
                "a&b->a",
                "a&b->b",
                "a->a|b",
                "b->a|b",
                "(a->c)->(b->c)->(a|b->c)",
                "(a->b)->(a->!b)->!a",
                "!!a->a"
        };
        axioms = new ArrayList<>(axiomsAll.length);
        for (int i = 0; i < axiomsAll.length; i++) {
            axioms.add(new Parser(axiomsAll[i]).parsedExpr());
        }

    }

}
