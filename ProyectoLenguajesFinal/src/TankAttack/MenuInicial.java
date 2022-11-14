package TankAttack;
import javax.swing.*;

public class MenuInicial {

    public MenuInicial(){


        //crear un boton que diga jugar
        JButton jugar = new JButton("Jugar");
        //crear un boton que diga salir
        JButton salir = new JButton("Salir");
        //crear una ventana que diga Tank Attack
        JFrame ventana = new JFrame("Menu Principal");
        //agregar los botones a la ventana
        ventana.add(jugar);
        ventana.add(salir);
        //hacer que la ventana se pueda cerrar
        ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //hacer que la ventana sea visible
        ventana.setVisible(true);
        //hacer que la ventana no se pueda redimensionar
        ventana.setResizable(false);
        //hacer que la ventana tenga un tamaño de 500x500
        ventana.setSize(900,600);
        //hacer que la ventana se abra en el centro de la pantalla
        ventana.setLocationRelativeTo(null);
        ventana.setLayout(null);
        //ubicar los botones en el centro de la ventana uno debajo del otro
        jugar.setBounds(350, 300, 200, 40);
        salir.setBounds(350, 365, 200, 40);
        //colocarle una imagen de fondo a la ventana
        JLabel label1 = new JLabel(new ImageIcon("src/Imagenes/fondo1.jpg"));
        ventana.add(label1);
        //colocarle el setBounds a la imagen de fondo capturando el tamaño de la ventana en variables
        int ancho = ventana.getWidth();
        int alto = ventana.getHeight();
        label1.setBounds(0, 0, ancho, alto);
        //colocarle un icono a la ventana
        ventana.setIconImage(new ImageIcon("src/Imagenes/tankGArriba.png").getImage());


        //Aqui se configuran los botones, para abrir la ventana del juego y cerrar la ventana del menu

        //crear un evento que cierre la ventana
        salir.addActionListener(e -> System.exit(0));

        jugar.addActionListener(e -> {
            ventana.dispose();
            JFrame VentanaJuego = new JFrame("Tank Attack");
            //pedirle al usuario que seleccion un nivel del 1 al 3 con el JoptionPane.
            String[] options = { "Nivel 1", "Nivel 2", "Nivel 3" };
            var nivel = JOptionPane.showOptionDialog(null, "Seleccione un Nivel", "Nivel",
                    0, 3, null, options, options[0]);

            //crear un objeto de la clase Juego
            VentanaJuego.add(new Juego(nivel));

            VentanaJuego.setResizable(false);
            VentanaJuego.pack();
            VentanaJuego.setTitle("Tank Attack");
            VentanaJuego.setLocationRelativeTo(null);
            VentanaJuego.setVisible(true);
            VentanaJuego.setIconImage(new ImageIcon("src/Imagenes/tankGArriba.png").getImage());

            //cuando la ventanaJuego se cierre que se abra la ventana del menu
            VentanaJuego.addWindowListener(new java.awt.event.WindowAdapter() {
                @Override
                public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                    ventana.setVisible(true);
                }

            });


        });
    }

    public static void main(String[] args){
        MenuInicial menu = new MenuInicial();
    }


}
