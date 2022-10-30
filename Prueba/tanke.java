
import java.awt.EventQueue;
import javax.swing.JFrame;

public class tanke extends JFrame {

    public tanke() {
        add(new mapa());

        setResizable(false);
        pack();

        setTitle("Tengtengan by Sabililhaq");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public static void main(String[] args) {

        EventQueue.invokeLater(() -> {
            JFrame tank = new tanke();
            tank.setVisible(true);
        });
    }
}