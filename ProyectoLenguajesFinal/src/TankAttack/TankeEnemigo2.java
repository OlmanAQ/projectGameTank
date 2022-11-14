package TankAttack;


import javax.swing.*;

public class TankeEnemigo2 {
    private int velocidad = 50;

    TankeEnemigo2(int inicialX, int inicialY){
        this.CordenadaX = inicialX;
        this.CordenadaY = inicialY;
    }

    protected int CordenadaX;
    protected int CordenadaY;
    private final int Largo = 900;
    private final int Ancho = 700;
    private final int Talla = 30;

    private boolean arriba = false;
    private boolean abajo = false;
    private boolean izquierda = false;
    private boolean derecha = true;
    protected boolean vida = true;

    private ImageIcon bala;
    private boolean balaDere;
    private boolean balaIzqui;
    private boolean balaArriba;
    private boolean balaAbajo;

    private int projectile_x;
    private int projectile_y;
    private boolean mostrarBalas;
    private boolean disparar;


    protected ImageIcon ImagenEnemigo = new ImageIcon("src/Imagenes/tankGDer.png");

    protected void turnLeft(){
        if (arriba){
            arriba = false;
            izquierda = true;
            ImagenEnemigo = new ImageIcon("src/Imagenes/tankGIzq.png");
        }

        if (izquierda){
            izquierda = false;
            abajo = true;
            ImagenEnemigo = new ImageIcon("src/Imagenes/tankGAbj.png");
        }

        if (abajo){
            abajo = false;
            derecha = true;
            ImagenEnemigo = new ImageIcon("src/Imagenes/tankGDer.png");
        }

        if (derecha){
            derecha = false;
            arriba = true;
            ImagenEnemigo = new ImageIcon("src/Imagenes/tankGArriba.png");
        }
    }

    protected void turnRight(){
        if (arriba){
            arriba = false;
            derecha = true;
            ImagenEnemigo = new ImageIcon("src/Imagenes/tankGDer.png");
        }

        if (derecha){
            derecha = false;
            abajo = true;
            ImagenEnemigo = new ImageIcon("src/Imagenes/tankGAbj.png");
        }

        if (abajo){
            abajo = false;
            izquierda = true;
            ImagenEnemigo = new ImageIcon("src/Imagenes/tankGIzq.png");
        }

        if (izquierda){
            izquierda = false;
            arriba = true;
            ImagenEnemigo = new ImageIcon("src/Imagenes/tankGArriba.png");
        }
    }

    protected void move(){

    }
    private void TipoBala(){
        mostrarBalas = projectile_x >= 0 && projectile_x < Largo && projectile_y >= 0 && projectile_y < Ancho;
    }


    public void disparar(){
        TipoBala();

        if (disparar && !mostrarBalas){
            //asignarle la posicion de donde esta el tanque a la bala
            projectile_x = CordenadaX;
            projectile_y = CordenadaY;
            disparar = false;
            if (izquierda){
                bala = new ImageIcon("src/Imagenes/bulletLeft.png");
                balaIzqui = true;
                balaArriba = false;
                balaDere = false;
                balaAbajo = false;
            }

            if (derecha){
                bala = new ImageIcon("src/Imagenes/bulletRight.png");
                balaIzqui = false;
                balaArriba = false;
                balaDere = true;
                balaAbajo = false;
            }

            if (arriba){
                bala = new ImageIcon("src/Imagenes/bulletUp.png");
                balaIzqui = false;
                balaArriba = true;
                balaDere = false;
                balaAbajo = false;
            }

            if (abajo){
                bala = new ImageIcon("src/Imagenes/bulletDown.png");
                balaIzqui = false;
                balaArriba = false;
                balaDere = false;
                balaAbajo = true;
            }
        }

        if (balaIzqui){
            projectile_x -= Talla;
        }

        if (balaDere){
            projectile_x += Talla;
        }

        if (balaArriba){
            projectile_y -= Talla;
        }

        if (balaAbajo){
            projectile_y += Talla;
        }

    }

}