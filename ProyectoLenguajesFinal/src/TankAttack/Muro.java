package TankAttack;



import java.awt.*;
import java.awt.image.ImageObserver;

public class Muro {
    public static final int width = 36;
    public static final int length = 36;
    private int x;
    private int y;
    Juego tc;


    private static Toolkit tk = Toolkit.getDefaultToolkit();
    private static Image[] wallImags = null;

    public Muro(int x, int y, Juego tc) {
        this.x = x;
        this.y = y;
        this.tc = tc;
    }


    public void draw(Graphics g) {
        g.drawImage(wallImags[0], this.x, this.y, (ImageObserver)null);
    }

    public Rectangle getRect() {
        return new Rectangle(this.x, this.y, 36, 37);
    }

    static {
        wallImags = new Image[]{tk.getImage(Muro.class.getResource("Imagenes/metalWall.gif"))};
    }
}
