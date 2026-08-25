package hra;

import javax.swing.*;
import java.awt.*;

public class Bomba extends PadajiciVec {
    private Image obrazek;

    public Bomba(int x, int y) {
        super(x, y);
        this.obrazek = new ImageIcon(getClass().getResource("/bomba.png")).getImage();
    }

    @Override
    public void nakresliSe(Graphics g) {
        g.drawImage(obrazek, x, y, sirka, vyska, null);
    }
}