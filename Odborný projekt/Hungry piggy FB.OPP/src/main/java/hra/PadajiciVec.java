package hra;

import java.awt.*;

public class PadajiciVec {
    protected int x, y;
    protected int sirka = 40;
    protected int vyska = 40;

    public PadajiciVec(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void pohniSe(int rychlost) {
        y += rychlost;
    }

    public boolean overujeKolizi(int hracX, int hracY, int hracSirka, int hracVyska) {
        return x < hracX + hracSirka && x + sirka > hracX &&
                y < hracY + hracVyska && y + vyska > hracY;
    }

    public void nakresliSe(Graphics g) {
        g.setColor(Color.GRAY);
        g.fillRect(x, y, sirka, vyska);
    }
}