package TankAttack.Prolog;

import org.jpl7.*;
import org.jpl7.Query;


public class Prolog {
    public static void main(String[] args) {

        String t1 = "consult('rutas.pl')";
        Query q1 = new Query(t1);
        System.out.println(t1 + " " + (q1.hasSolution() ? "succeeded" : "failed"));

        String t2 = "path(x00y00,x06y10,L,R).";
        Query q2 = new Query(t2);
        System.out.println(t2 + " " + (q2.hasSolution() ? "succeeded" : "failed"));

        Variable X = new Variable("X");
        Variable Y = new Variable("Y");
        Query q3 =
                new Query(
                        "path",
                        new Term[] {new Atom("x00y00"),new Atom("x06y10"),X,Y}
                );

        java.util.Map<String,Term> solution;

        solution = q3.oneSolution();

        System.out.println( "X = " + solution.get("X"));
        System.out.println( "Y = " + solution.get("Y"));


            }
}
