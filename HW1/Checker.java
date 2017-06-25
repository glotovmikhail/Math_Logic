/**
 * Created by Михаил on 25.10.2016.
 */

import java.io.*;
import java.util.*;

class Pair<Expression, Integer> {
    Expression expr;
    int num;

    Pair(Expression expr, int num) {
        this.expr = expr;
        this.num = num;
    }

}

public class Checker {
    HashMap<Expression, Integer> provedExp, assums;
    HashMap<Expression, ArrayList<Pair<Expression, Integer>>> modusPonens;
    ArrayList<String> res, proofs;
    ArrayList<Expression> proof;

    public Checker(ArrayList<String> assumsAsStr, ArrayList<String> proofs) {
        Expression temp;
        modusPonens = new HashMap<>();
        assums = new HashMap<>();
        provedExp = new HashMap<>();
        proof = new ArrayList<>();
        res = new ArrayList<>();
        this.proofs = proofs;
        for (int i = 0; i < assumsAsStr.size(); i++) {
            temp = new Parser(assumsAsStr.get(i)).parsedExpr();
            if (!assums.containsKey(temp)) {
                assums.put(temp, i);
            }
        }
        for (int i = 0; i < proofs.size(); i++) {
            proof.add(new Parser(proofs.get(i)).parsedExpr());
        }
    }

    private void addExprForMP(Expression phi, Expression ksi, int index) {
        if (!modusPonens.containsKey(ksi)) {
            modusPonens.put(ksi, new ArrayList<>());
        }
        modusPonens.get(ksi).add(new Pair<>(phi, index));
    }

    private void good(String exp, int i) {
        Expression temp = proof.get(i);
        provedExp.put(temp, i);
        StringBuilder sb = new StringBuilder();
        sb.append("(").append((i + 1)).append(") ").append(proofs.get(i)).append(" ").append(exp);
        res.add(sb.toString());
        if (!(temp instanceof BinaryExpression)) return;
        if (((BinaryExpression) temp).oper == Operations.IMPL) {
            addExprForMP(((BinaryExpression) temp).left, ((BinaryExpression) temp).right, i);
        }

    }

    public ArrayList<String> check() {
        StringBuilder sb = new StringBuilder();
        boolean exit = false;
        for (int i = 0; i < proof.size(); i++) {
            exit = false;
            System.out.println(proof.size() + " " + i);
            Expression temp = proof.get(i);
            if (isAxiom(temp) != -1) {
                sb.delete(0, sb.length());
                sb.append("(Сх. акс. ").append(isAxiom(temp) + 1).append(")");
                good(sb.toString(), i);
                continue;
            } else if (assums.get(temp) != null) {
                sb.delete(0, sb.length());
                sb.append("(Предп. ").append(assums.get(temp) + 1).append(")");
                good(sb.toString(), i);
                continue;
            } else if (modusPonens.containsKey(temp)) {
                for (int j = 0; j < modusPonens.get(temp).size(); j++) {
                    Pair<Expression, Integer> pairTemp = modusPonens.get(temp).get(j);
                    if (provedExp.containsKey(pairTemp.expr)) {
                        sb.delete(0, sb.length());
                        sb.append("(M.P. ").append(provedExp.get(pairTemp.expr) + 1).append(", ").append(pairTemp.num + 1).append(")");
                        good(sb.toString(), i);
                        exit = true;
                    }
                }
                if (exit) continue;
            }
            sb.delete(0, sb.length());
            sb.append("(").append(i + 1).append(") ").append(proofs.get(i)).append(" (Не доказано.)");
            res.add(sb.toString());
        }
        return res;
    }
    private int isAxiom(Expression exp) {
        for (int i = 0; i < Axioms.axioms.size(); i++) {
            if (match(Axioms.axioms.get(i), exp, new HashMap<>())) {
                System.err.println(i);
                return i;
            }
        }
        return -1;
    }

    private boolean match(Expression source, Expression exp, HashMap<Integer, Expression> hm) {
        if (source instanceof BinaryExpression && exp instanceof BinaryExpression) {
            boolean con1 = match(((BinaryExpression) source).left, ((BinaryExpression) exp).left, hm);
            boolean con2 = match(((BinaryExpression) source).right, ((BinaryExpression) exp).right, hm);
            return (source.oper == exp.oper) && con1 && con2;
        } else if (source instanceof SingleArg && exp instanceof SingleArg) {
            boolean con = match(((SingleArg) source).arg, ((SingleArg) exp).arg, hm);
            return (source.oper == exp.oper) && con;
        } else if (source instanceof PropVar) {
            PropVar var = (PropVar) source;
            if (hm.containsKey(var.varNumber)) {
                return hm.get(var.varNumber).equals(exp);
            } else {
                hm.put(var.varNumber, exp);
                return true;
            }
        }
        return false;
    }

    public static void main(String[] args) throws IOException {
        ArrayList<String> assums = new ArrayList<>();
        ArrayList<String> proof = new ArrayList<>();
        ArrayList<String> res;
        long ms = System.currentTimeMillis();
        BufferedReader in = new BufferedReader(new FileReader(new File("test.in")));
        PrintWriter p = new PrintWriter(new File("test.out"));

        String head = in.readLine();
        if (head.contains("|-")) {
            String assum = head.substring(0, head.indexOf("|-"));
            String[] charAssums = assum.split("\\s*[,]+\\s*");
            for (int i = 0; i < charAssums.length; i++) {
                System.err.println(charAssums[i]);
                assums.add(charAssums[i]);
            }
            for ( ; ; ) {
                String expr = in.readLine();
                System.out.println(expr);
                if (expr == null) break;
                expr = expr.replaceAll("\\s+", "");
                proof.add(expr);
            }
        } else {
            proof.add(head.replaceAll("\\s+", ""));
        }
        String line;
        while ((line = in.readLine()) != null) {
            proof.add(line.replaceAll("\\s+", ""));
        }

        res = new Checker(assums, proof).check();

        for (int i = 0; i < res.size(); i++) {
            p.println(res.get(i));
        }
        System.err.print(System.currentTimeMillis() - ms);
        in.close();
        p.close();
    }
}
