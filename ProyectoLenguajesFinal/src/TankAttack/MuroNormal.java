package TankAttack;




import java.awt.*;

    public class MuroNormal {
    public static final int width = 22;
    public static final int length = 21;
    int x, y;

    Juego tc;
    private static Toolkit tk = Toolkit.getDefaultToolkit();
    private static Image[] wallImags = null;
    static {
        wallImags = new Image[] {
                tk.getImage(MuroNormal.class.getResource("Imagenes/commonWall.gif")), };
    }

    public MuroNormal(int x, int y, Juego tc) {
        this.x = x;
        this.y = y;
        this.tc = tc;
    }

    public void draw(Graphics g) {
        g.drawImage(wallImags[0], x, y, null);
    }

    public Rectangle getRect() {
        return new Rectangle(x, y, width, length);
    }
}
