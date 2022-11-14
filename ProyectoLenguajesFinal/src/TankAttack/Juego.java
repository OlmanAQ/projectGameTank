package TankAttack;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;

public class Juego extends JPanel implements ActionListener {

    //declaracion de varaibels
    private final int Largo = 900;
    private final int Ancho = 700;
    private final int Talla = 30;

    private int nivelJuego;

    private int delay = 150;
    private int CantObjetivo = 2;
    private int CantVidas = 3;

    private int player_x;
    private int player_y;

    private int target_x;
    private int target_y;
    private int target_x2;
    private int target_y2;

    private boolean Izquierda = false;
    private boolean Derecha = true;
    private boolean Arriba = false;
    private boolean Abajo = false;
    private boolean vida = true;

    private Timer timer1;

    private ImageIcon ImagenJugador;
    private ImageIcon bala;
    private ImageIcon objetivo;
    private ImageIcon objetivo2;

    private boolean mostrarBalas;

    private int puntaje = 0;

    private int projectile_x;
    private int projectile_y;

    private boolean disparar;
    private boolean dispararE;

    private boolean GolpearObjetivo = false;
    private boolean GolpearObjetivo2 = false;

    private boolean balaDere;
    private boolean balaIzqui;
    private boolean balaArriba;
    private boolean balaAbajo;

    private boolean paused = false;


    List<Muro> metalWall = new ArrayList<Muro>();
    public int[][] mapTileNum;
    public Rectangle getRect() {
        return new Rectangle(player_x, player_y, Ancho, Largo);
    }

    //instancia de la clase tanke
    private TankeEnemigo[] TankeEnemigo = new TankeEnemigo[10];
    private TankeEnemigo2[] TankeEnemigo2 = new TankeEnemigo2[10];

    //metodo constructor de la clase mapa
    public Juego(int nivel) {
        this.nivelJuego = nivel;
        CantObjetivo = nivelJuego + 1;
        addKeyListener(new TAdapter());// moverse con las teclas
        setBackground(Color.WHITE); //color de fondo
        setFocusable(true); //para que el juego se pueda jugar
        setPreferredSize(new Dimension(Largo, Ancho)); //tamaño del juego
        CargarImagenes();//se cargan las imagenes del jugador, balas y objetivos
        IniciarJuego(); // se inicia el juego
    }


    private void CargarImagenes() {
        ImagenJugador = new ImageIcon("src/Imagenes/tankVDer.png");
        bala = new ImageIcon("src/Imagenes/balaDer.png");
        objetivo = new ImageIcon("src/Imagenes/naziLogo.png");
        objetivo2 = new ImageIcon("src/Imagenes/torre.png");
    }


    private void IniciarJuego() {
        //POSICION INICIAL DEL JUGADOR
        player_x = 10 * Talla;
        player_y = 10 * Talla;
        //POSICION INICIAL DEL OBJETIVO
        projectile_x = -10* Talla;
        projectile_y = -10* Talla;

        int rX = ((int)(Math.random() * 100))/5;
        int rY = ((int)(Math.random() * 100))/5;

        target_x = rX * Talla;
        target_y = rY * Talla;


        int rX2 = ((int)(Math.random() * 100))/5;
        int rY2 = ((int)(Math.random() * 100))/5;

        target_x2 = rX2 * Talla;
        target_y2 = rY2 * Talla;



    // crear unn tanke dependiendo del nivel
        for (int i = 0; i < CantObjetivo; i++) {
            TankeEnemigo[i] = new TankeEnemigo(Talla, 3*Talla);

        }
        TankeEnemigo2[0] = new TankeEnemigo2(3*Talla, 3*Talla);

        reubicarEnemigo(); // ubicar denuevo el enemigo

        timer1 = new Timer(delay, this);
        timer1.start();
        if (nivelJuego == 0) {
            for (int i = 0; i < 50; i++) {
                if (i < 20) {
                    metalWall.add(new Muro(0 + 20 * (i), 0, this));
                    metalWall.add(new Muro(0, 0 + 20 * (i), this));
                    metalWall.add(new Muro(860, 0 + 20 * (i), this));
                    metalWall.add(new Muro(0 + 20 * (i), 660, this));

                    metalWall.add(new Muro(450, 20 + 20 * (i), this));
                } else if (i < 25) {
                    metalWall.add(new Muro(0 + 20 * (i), 0, this));
                    metalWall.add(new Muro(0, 0 + 20 * (i), this));
                    metalWall.add(new Muro(860, 0 + 20 * (i), this));
                    metalWall.add(new Muro(0 + 20 * (i), 660, this));
                } else {
                    metalWall.add(new Muro(0 + 20 * (i), 0, this));
                    metalWall.add(new Muro(0, 0 + 20 * (i), this));
                    metalWall.add(new Muro(860, 0 + 20 * (i), this));
                    metalWall.add(new Muro(0 + 20 * (i), 660, this));
                    metalWall.add(new Muro(450, 20 + 20 * (i), this));
                }
            }
        } else if (nivelJuego == 1) {
            for (int i = 0; i < 50; i++) {
                if (i < 15) {
                    metalWall.add(new Muro(0 + 20 * (i), 0, this));

                    metalWall.add(new Muro(0, 0 + 20 * (i), this));
                    metalWall.add(new Muro(860, 0 + 20 * (i), this));
                    metalWall.add(new Muro(0 + 20 * (i), 660, this));

                    //hacer 3 cuadros de metal
                    metalWall.add(new Muro(300, 20 + 20 * (i), this));


                } else {
                    metalWall.add(new Muro(0 + 20 * (i), 0, this));
                    metalWall.add(new Muro(0, 0 + 20 * (i), this));

                    metalWall.add(new Muro(860, 0 + 20 * (i), this));
                    metalWall.add(new Muro(0 + 20 * (i), 660, this));

                    //hacer 3 cuadros de metal
                    metalWall.add(new Muro(600, 20 + 20 * (i), this));


                }
            }
        } else if (nivelJuego == 2) {
            for (int i = 0; i < 50; i++) {
                if (i < 15) {
                    metalWall.add(new Muro(0 + 20 * (i), 0, this));

                    metalWall.add(new Muro(0, 0 + 20 * (i), this));
                    metalWall.add(new Muro(860, 0 + 20 * (i), this));
                    metalWall.add(new Muro(0 + 20 * (i), 660, this));

                    //hacer cuadros pequeños de metal
                    metalWall.add(new Muro(450, 20 + 20 * (i), this));
                    metalWall.add(new Muro(20 + 20 * (i) , 450, this));


                }
                else if (i < 20) {
                    metalWall.add(new Muro(0 + 20 * (i), 0, this));
                    metalWall.add(new Muro(0, 0 + 20 * (i), this));
                    metalWall.add(new Muro(860, 0 + 20 * (i), this));
                    metalWall.add(new Muro(0 + 20 * (i), 660, this));


                } else {
                    metalWall.add(new Muro(0 + 20 * (i), 0, this));
                    metalWall.add(new Muro(0, 0 + 20 * (i), this));


                    metalWall.add(new Muro(860, 0 + 20 * (i), this));
                    metalWall.add(new Muro(0 + 20 * (i), 660, this));


                }
            }
        }
    }




    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        imagen(g);
    }

    private void imagen(Graphics g) {
        if (vida) {
            Puntuacion(g);
            if(CantObjetivo == 0){
               Ganar(g);
            }else  {
                for (int i = 0; i < metalWall.size(); i++) {
                    Muro w = metalWall.get(i);
                    w.draw(g);
                }

                g.drawImage(ImagenJugador.getImage(), player_x, player_y, this);
                if (mostrarBalas){
                    g.drawImage(bala.getImage(), projectile_x, projectile_y, this);
                }
                g.drawImage(objetivo.getImage(), target_x, target_y, this);
                g.drawImage(objetivo2.getImage(), target_x2, target_y2, this);
                //dibujar un tanque por cada nivel
                for (int i = 0; i < CantObjetivo; i++) {
                    g.drawImage(TankeEnemigo[i].ImagenEnemigo.getImage(), TankeEnemigo[i].CordenadaX, TankeEnemigo[i].CordenadaY, this);
                }
                g.drawImage(TankeEnemigo2[0].ImagenEnemigo.getImage(), TankeEnemigo[0].CordenadaX, TankeEnemigo[0].CordenadaY, this);

                Toolkit.getDefaultToolkit().sync();
            }



        } else {
            Perder(g);
        }
    }

    private void Puntuacion(Graphics g){

        String msg = "puntaje : " + getPuntaje();
        Font small = new Font("Times New Roman", Font.BOLD, 18);
        FontMetrics metr = getFontMetrics(small);
        g.setColor(Color.black);
        g.setFont(small);
        g.drawString(msg,(Largo - metr.stringWidth(msg)) / 16, 48);

        String msg2 = "Vidas : " + CantVidas;
        g.setFont(small);
        g.drawString(msg2,(Largo - metr.stringWidth(msg2)) / 16, 68);

        String msg3 = "Nivel : " + nivelJuego + 1;
        g.setFont(small);
        g.drawString(msg3,(Largo - metr.stringWidth(msg3)) / 16, 88);



    }

    private void reaparicionObjetivo(){

        target_x = 1000 * Talla;
        target_y = 1000 * Talla;

    }
    private void reaparicionObjetivo2(){

        target_x2 = 1000 * 15;
        target_y2 = 1000 * 15;
    }

    private void TipoBala(){
        mostrarBalas = projectile_x >= 0 && projectile_x < Largo && projectile_y >= 0 && projectile_y < Ancho;
    }


    //para obtener el puntaje
    private int getPuntaje(){
        return puntaje;
    }

    //funcion para saber si la bala toca el objetivo o el tanke toca el objetivo
    private void PosicionDeBalaYObjetivo(){
        if ((projectile_x == target_x && projectile_y == target_y)|| (player_x == target_x && player_y == target_y)){ //si la bala de da o si el tanque toma el objetivo
            mostrarBalas = false; // oculta la bala
            GolpearObjetivo = true;
        }else mostrarBalas = true;
    }
    private void PosicionDeBalaYObjetivo2(){
        if ((projectile_x == target_x2 && projectile_y == target_y2)|| (player_x == target_x2 && player_y == target_y2)){ //si la bala de da o si el tanque toma el objetivo
            mostrarBalas = false; // oculta la bala
            GolpearObjetivo2 = true;
        }else mostrarBalas = true;
    }



    private void marcarObjetivo(){
        PosicionDeBalaYObjetivo();

        if (GolpearObjetivo){
            reaparicionObjetivo();
            CantObjetivo--;
            puntaje++;
            GolpearObjetivo = false;
            projectile_x = -5 * Talla;
            balaArriba = true;
            balaAbajo = false;
            balaIzqui = false;
            balaDere = false;
        }
        PosicionDeBalaYObjetivo2();
        if(GolpearObjetivo2){
            CantObjetivo--;
            reaparicionObjetivo2();
            puntaje++;
            GolpearObjetivo2 = false;
            projectile_x = -5 * Talla;
            balaArriba = true;
            balaAbajo = false;
            balaIzqui = false;
            balaDere = false;
        }
    }



    private void disparar(){
        TipoBala();

        if (disparar && !mostrarBalas){
            //asignarle la posicion de donde esta el tanque a la bala
            projectile_x = player_x;
            projectile_y = player_y;
            disparar = false;
            if (Izquierda){
                bala = new ImageIcon("src/Imagenes/balaIzq.png");
                balaIzqui = true;
                balaArriba = false;
                balaDere = false;
                balaAbajo = false;
            }

            if (Derecha){
                bala = new ImageIcon("src/Imagenes/balaDer.png");
                balaIzqui = false;
                balaArriba = false;
                balaDere = true;
                balaAbajo = false;
            }

            if (Arriba){
                bala = new ImageIcon("src/Imagenes/balaArriba.png");
                balaIzqui = false;
                balaArriba = true;
                balaDere = false;
                balaAbajo = false;
            }

            if (Abajo){
                bala = new ImageIcon("src/Imagenes/balaAbj.png");
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

    private void Perder(Graphics g) {
        //usar la propiedades de la clase para colocar una imagen
        ImageIcon gameOver = new ImageIcon("src/Imagenes/gameOver.png");
        g.drawImage(gameOver.getImage(), 200, 0, this);


        String msg = "Game Over,Presiona la X para salir";
        Font small = new Font("Times New Roman", Font.BOLD, 25);
        FontMetrics metr = getFontMetrics(small);

        g.setColor(Color.red);
        g.setFont(small);
        g.drawString(msg, (Largo - metr.stringWidth(msg)) / 2, 500);
        //cambiar el color del fondo
        setBackground(Color.GRAY);


        String msg2 = "Puntaje Obtenido : " + getPuntaje();
        Font small2 = new Font("Times New Roman", Font.BOLD, 25);
        FontMetrics metr2 = getFontMetrics(small2);

        g.setColor(Color.white);
        g.setFont(small2);
        g.drawString(msg2, (Largo - metr2.stringWidth(msg2)) / 2, 550);


    }

    private void Ganar(Graphics g) {
        //usar la propiedades de la clase para colocar una imagen
        ImageIcon win = new ImageIcon("src/Imagenes/victoria.png");
        //que la imagen tome el tamaño de la pantalla
        g.drawImage(win.getImage(), 0, 0, Largo, Ancho, this);

        String msg = "Presiona la X para salir";
        Font small = new Font("Times New Roman", Font.BOLD, 25);
        FontMetrics metr = getFontMetrics(small);

        g.setColor(Color.white);
        g.setFont(small);
        g.drawString(msg, (Largo - metr.stringWidth(msg)) / 2, 540);
        //cambiar el color del fondo
       ;

    }

    private void move() {
        if (Izquierda) {
            player_x -= Talla;
        }

        if (Derecha) {
            player_x += Talla;
        }

        if (Arriba) {
            player_y -= Talla;
        }

        if (Abajo) {
            player_y += Talla;
        }
    }

    private void checkCollision() {

        if (player_y >= Ancho) {
            vida = false;
        }

        if (player_y < 0) {
            vida = false;
        }

        if (player_x >= Largo) {
            vida = false;
        }

        if (player_x < 0) {
            vida = false;
        }

        if (!vida) {
            timer1.stop();
        }
    }

    int a = 0;
    @Override
    public void actionPerformed(ActionEvent e) {
        if (vida) {
            checkCollision();
            disparar();
            marcarObjetivo();
            checkEnemy();
            if (a%4 == 0){
                movientoEnemigo();
            }
            a++;
        }
        repaint();
    }

    public void checkEnemy(){

        //realizar el if por cada nivel
        for (int i = 0; i < CantObjetivo; i++) {
        if (TankeEnemigo[i].CordenadaX == projectile_x && TankeEnemigo[i].CordenadaY == projectile_y){
            puntaje += 3;
            mostrarBalas = false;
            projectile_x = -5 * Talla;
            projectile_y = -5 * Talla;
            reubicarEnemigo();
            }
        }
        if (TankeEnemigo2[0].CordenadaX == projectile_x && TankeEnemigo2[0].CordenadaY == projectile_y){
            puntaje += 3;
            mostrarBalas = false;
            projectile_x = -5 * Talla;
            projectile_y = -5 * Talla;
            reubicarEnemigo();
            }
    }


    public void movientoEnemigo(){

        int distanciaX = player_x - TankeEnemigo[0].CordenadaX ;
        int distanciaY = player_y - TankeEnemigo[0].CordenadaY ;


        distanciaX = distanciaX >= 0? distanciaX : -distanciaX;
        distanciaY = distanciaY >= 0? distanciaY : -distanciaY;

        //realizar un clico por cada nivel
        for (int i = 0; i < CantObjetivo; i++) {
            //dispararEnemigo(TankeEnemigo[i]);

            if (TankeEnemigo[i].vida){
                if (player_x == TankeEnemigo[i].CordenadaX && player_y == TankeEnemigo[i].CordenadaY){
                    CantVidas--;
                    if (CantVidas == 0){
                        this.vida = false;
                    }
                }
                //utilizar la funcion DispararEnemigo para que el enemigo dispare


                if (player_x >= TankeEnemigo[i].CordenadaX && distanciaX >= distanciaY){
                    TankeEnemigo[i].CordenadaX += Talla;

                    //si esta a 10 pixeles de distancia del jugador dispara
                    if (distanciaX <= 10){
                        dispararEnemigo(TankeEnemigo[i]);
                    }

                    if(nivelJuego == 0){
                        TankeEnemigo[i].ImagenEnemigo = new ImageIcon("src/Imagenes/tankRDer.png");
                    }
                    else if(nivelJuego == 1){
                        TankeEnemigo[i].ImagenEnemigo = new ImageIcon("src/Imagenes/tankGDer.png");
                    }
                    else if(nivelJuego == 2){
                        TankeEnemigo[i].ImagenEnemigo = new ImageIcon("src/Imagenes/tankNDer.png");
                    }
                }

                if (player_x < TankeEnemigo[i].CordenadaX && distanciaX >= distanciaY){
                    TankeEnemigo[i].CordenadaX -= Talla;

                    if (distanciaX <= 10){
                        dispararEnemigo(TankeEnemigo[i]);
                    }

                    if(nivelJuego == 0){
                        TankeEnemigo[i].ImagenEnemigo = new ImageIcon("src/Imagenes/tankRIzq.png");
                    }
                    else if(nivelJuego == 1){
                        TankeEnemigo[i].ImagenEnemigo = new ImageIcon("src/Imagenes/tankGIzq.png");
                    }
                    else if(nivelJuego == 2){
                        TankeEnemigo[i].ImagenEnemigo = new ImageIcon("src/Imagenes/tankNIzq.png");
                    }
                }

                if (player_y >= TankeEnemigo[i].CordenadaY && distanciaY > distanciaX){
                    TankeEnemigo[i].CordenadaY += Talla;

                    if (distanciaX <= 10){
                        dispararEnemigo(TankeEnemigo[i]);
                    }

                    if(nivelJuego == 0){
                        TankeEnemigo[i].ImagenEnemigo = new ImageIcon("src/Imagenes/tankRAbj.png");
                    }
                    else if(nivelJuego == 1){
                        TankeEnemigo[i].ImagenEnemigo = new ImageIcon("src/Imagenes/tankGAbj.png");
                    }
                    else if(nivelJuego == 2){
                        TankeEnemigo[i].ImagenEnemigo = new ImageIcon("src/Imagenes/tankNAbj.png");
                    }

                }

                if (player_y < TankeEnemigo2[0].CordenadaY && distanciaY > distanciaX){
                    TankeEnemigo[0].CordenadaY -= Talla;

                    if (distanciaX <= 10){
                        dispararEnemigo(TankeEnemigo[i]);
                    }

                    if(nivelJuego == 0){
                        TankeEnemigo[i].ImagenEnemigo = new ImageIcon("src/Imagenes/tankRArriba.png");
                    }
                    else if(nivelJuego == 1){
                        TankeEnemigo[i].ImagenEnemigo = new ImageIcon("src/Imagenes/tankGArriba.png");
                    }
                    else if(nivelJuego == 2){
                        TankeEnemigo[i].ImagenEnemigo = new ImageIcon("src/Imagenes/tankNArriba.png");
                    }
                }
            }
        }




    }

    public void reubicarEnemigo(){
        int r = (int)(Math.random() * 100);
     //realizar un cliclo por cada nivel
        for (int i = 0; i < CantObjetivo; i++) {
            if (r<25){
                TankeEnemigo[0].CordenadaX = -1 * Talla;
                TankeEnemigo[0].CordenadaY = -1 * Talla;

            }else if (r >= 25 && r < 50){
                TankeEnemigo[0].CordenadaX = -1 * Talla;
                TankeEnemigo[0].CordenadaY = 21 * Talla;


            }else if (r >= 50 && r < 75){
                TankeEnemigo[0].CordenadaX = 21 * Talla;
                TankeEnemigo[0].CordenadaY = 21 * Talla;


            }else if (r >= 75){
                TankeEnemigo[0].CordenadaX = 21 * Talla;
                TankeEnemigo[0].CordenadaY = -1 * Talla;


            }
        }
    }

    private void dispararE(){
        TipoBala();

        if (dispararE && !mostrarBalas){
            //asignarle la posicion de donde esta el tanque a la bala
            projectile_x = TankeEnemigo[0].CordenadaX;
            projectile_y = TankeEnemigo[0].CordenadaY;
            disparar = false;
            if (Izquierda){
                bala = new ImageIcon("src/Imagenes/balaIzq.png");
                balaIzqui = true;
                balaArriba = false;
                balaDere = false;
                balaAbajo = false;
            }

            if (Derecha){
                bala = new ImageIcon("src/Imagenes/balaDer.png");
                balaIzqui = false;
                balaArriba = false;
                balaDere = true;
                balaAbajo = false;
            }

            if (Arriba){
                bala = new ImageIcon("src/Imagenes/balaArriba.png");
                balaIzqui = false;
                balaArriba = true;
                balaDere = false;
                balaAbajo = false;
            }

            if (Abajo){
                bala = new ImageIcon("src/Imagenes/balaAbj.png");
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

    //funcion para que el tanque enemigo cuando este cerca del jugador dispare
    public void dispararEnemigo(TankeEnemigo enemigo){
        //obtener la distancia a la que el jugador se encuentra del enemigo
        int distanciaX = player_x - enemigo.CordenadaX ;
        int distanciaY = player_y - enemigo.CordenadaY ;

        //obtener el valor absoluto de la distancia
        distanciaX = distanciaX >= 0? distanciaX : -distanciaX;
        distanciaY = distanciaY >= 0? distanciaY : -distanciaY;

        //si la distancia es menor a 3 entonces disparar
        //enviar una bala desde la posicion del enemigo hasta la posicion del jugador

        if (distanciaX <= 3 && distanciaY <= 3){
           //hacer la accion de disparar del enemigo hacia el jugador
            dispararE = true;
            dispararE();

        }

    }


    //__________________Movimiento del tanque______________________
    private class TAdapter extends KeyAdapter {

        @Override
        public void keyPressed(KeyEvent e){
            int key = e.getKeyCode();
            if (key == KeyEvent.VK_P){
                if (!paused){
                    paused = true;
                    timer1.stop();
                    //enviar un mensaje que el juego esta pausado y que presione P para continuar
                    JOptionPane.showMessageDialog(null, "Juego Pausado, Presione P para continuar");

                }else {
                    paused = false;
                    timer1.start();
                }
            }

            if (key == KeyEvent.VK_SPACE){
                disparar = true;
            }

            if ((key == KeyEvent.VK_LEFT || key == KeyEvent.VK_A) && (!Derecha) && !paused) {
                if (Izquierda){
                    move();
                }else{
                    Izquierda = true;
                    Arriba = false;
                    Abajo = false;
                    ImagenJugador = new ImageIcon("src/Imagenes/tankVIzq.png");
                }
            }

            if ((key == KeyEvent.VK_RIGHT || key == KeyEvent.VK_D) && (!Izquierda) && !paused) {
                if (Derecha){
                    move();
                }else {
                    Derecha = true;
                    Arriba = false;
                    Abajo = false;
                    ImagenJugador = new ImageIcon("src/Imagenes/tankVDer.png");
                }
            }

            if ((key == KeyEvent.VK_UP || key == KeyEvent.VK_W) && (!Abajo) && !paused) {
                if (Arriba){
                    move();
                }else {
                    Arriba = true;
                    Derecha = false;
                    Izquierda = false;
                    ImagenJugador = new ImageIcon("src/Imagenes/tankVArriba.png");
                }
            }

            if ((key == KeyEvent.VK_DOWN || key == KeyEvent.VK_S) && (!Arriba) && !paused) {
                if (Abajo){
                    move();
                }else {
                    Abajo = true;
                    Derecha = false;
                    Izquierda = false;
                    ImagenJugador = new ImageIcon("src/Imagenes/tankVAbj.png");
                }
            }
        }
    }
}