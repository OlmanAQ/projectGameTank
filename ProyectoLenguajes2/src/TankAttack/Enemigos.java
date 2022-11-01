package TankAttack;


import javax.swing.*;

public class Enemigos {
    Enemigos(int inisialX, int inisialY){
        this.koorX = inisialX;
        this.koorY = inisialY;
    }

    protected int koorX;
    protected int koorY;

    private boolean atas = false;
    private boolean bawah = false;
    private boolean kiri = false;
    private boolean kanan = true;
    protected boolean idup = true;

    protected ImageIcon gambarMusuh = new ImageIcon("src/tankRight3.png");

    protected void turnLeft(){
        if (atas){
            atas = false;
            kiri = true;
            gambarMusuh = new ImageIcon("src/tankLeft3.png");
        }

        if (kiri){
            kiri = false;
            bawah = true;
            gambarMusuh = new ImageIcon("src/tankDown3.png");
        }

        if (bawah){
            bawah = false;
            kanan = true;
            gambarMusuh = new ImageIcon("src/tankKanan3.png");
        }

        if (kanan){
            kanan = false;
            atas = true;
            gambarMusuh = new ImageIcon("src/tankUp3.png");
        }
    }

    protected void turnRight(){
        if (atas){
            atas = false;
            kanan = true;
            gambarMusuh = new ImageIcon("src/tankRight3.png");
        }

        if (kanan){
            kanan = false;
            bawah = true;
            gambarMusuh = new ImageIcon("src/tankDown3.png");
        }

        if (bawah){
            bawah = false;
            kiri = true;
            gambarMusuh = new ImageIcon("src/tankLeft3.png");
        }

        if (kiri){
            kiri = false;
            atas = true;
            gambarMusuh = new ImageIcon("src/tankUp3.png");
        }
    }

    protected void move(){

    }

}