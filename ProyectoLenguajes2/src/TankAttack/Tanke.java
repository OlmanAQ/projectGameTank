package TankAttack;


import java.awt.EventQueue;
import javax.swing.JFrame;

public class Tanke extends JFrame {

    public Tanke() {
        add(new Mapa());

        setResizable(false);
        pack();

        setTitle("Tengtengan by Sabililhaq");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public static void main(String[] args) {

        EventQueue.invokeLater(() -> {
            JFrame tank = new Tanke();
            tank.setVisible(true);
        });
    }
}