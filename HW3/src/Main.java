import java.io.*;

/**
 * Created by mikhail on 30.04.17.
 */
public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream("src/input.txt"), "UTF-8"));
        PrintWriter p = new PrintWriter(new OutputStreamWriter(new FileOutputStream("output.txt"), "UTF-8"));
        int a = Integer.valueOf(in.readLine());
        String sc = "0";
        while (a > 0) {
            sc += "\'";
            a--;
        }
        in.close();
        in = new BufferedReader(new InputStreamReader(new FileInputStream("src/proof.txt"), "UTF-8"));
        String first = "|-(" + sc + "+0')*(" + sc + "+0')=(" + sc + "*" + sc + ")+(0''*" + sc + ")+0'";
        String beforeBeforeLast = "@" + sc + "((" + sc + "+0')*(" + sc + "+0')=(" + sc + "*" + sc + ")+(0''*" + sc + ")+0')->";
        String beforeLast = "((" + sc + "+0')*(" + sc + "+0')=(" + sc + "*" + sc + ")+(0''*" + sc + ")+0')";
        String last = "((" + sc + "+0')*(" + sc + "+0')=(" + sc + "*" + sc + ")+(0''*" + sc + ")+0')";

        String s;
        p.println(first);
        while ((s = in.readLine()) != null) {
            p.println(s);
        }
        p.println(beforeBeforeLast + beforeLast);
        p.print(last);
        p.close();
        in.close();
    }
}
