package TankAttack;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.*;

public class Movimiento extends JFrame implements ActionListener {
    private final int Largo = 300;
    private final int Ancho = 300;
    private final int Talla = 15;
    private final int Amplitud = Largo * Ancho / Talla * Talla; //400

    private boolean arriba = false;
    private boolean derecha = true;
    private boolean abajo = false;
    private boolean izquierda = false;
    private int player_x;
    private int player_y;

    private boolean reloading = false;
    private int projectile_x;
    private int projectile_y;

    public Movimiento(){

    }

    private void shot() {
        if (reloading) {
            return;
        }
        reloading = true;
        projectile_x = player_x;
        projectile_y = player_y;
        if (arriba) {
            projectile_y -= Talla;
        }
        if (abajo) {
            projectile_y += Talla;
        }
        if (izquierda) {
            projectile_x -= Talla;
        }
        if (derecha) {
            projectile_x += Talla;
        }

    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }

    private class TAdapter extends KeyAdapter{

        public void KeyPressed(KeyEvent e){
            int key = e.getKeyCode();

            if (key == KeyEvent.VK_SPACE){
                shot();
            }

            if ((key == KeyEvent.VK_LEFT || key == KeyEvent.VK_A) && (!derecha)) {
                if (izquierda ){
                    if (player_x > 0){
                        player_x -= Talla;
                    }
                }else{
                    izquierda = true;
                    arriba = false;
                    abajo = false;
                }
            }

            if ((key == KeyEvent.VK_RIGHT || key == KeyEvent.VK_D) && (!izquierda)) {
                if (derecha){
                    player_x += Talla;
                }else {
                    derecha = true;
                    arriba = false;
                    abajo = false;
                }
            }

            if ((key == KeyEvent.VK_UP || key == KeyEvent.VK_W) && (!abajo)) {
                if (arriba){
                    player_y += Talla;
                }else {
                    arriba = true;
                    derecha = false;
                    izquierda = false;
                }
            }

            if ((key == KeyEvent.VK_DOWN || key == KeyEvent.VK_S) && (!arriba)) {
                if (abajo){
                    player_y -= Talla;
                }else {
                    abajo = true;
                    derecha = false;
                    izquierda = false;
                }
            }
        }

    }
}