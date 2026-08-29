package hra;

import javax.swing.*;
import java.awt.*;

public class Stit extends PadajiciVec {
    private Image obrazek;

    public Stit(int x, int y) {
        super(x, y);
        this.obrazek = new ImageIcon(getClass().getResource("/stit.png")).getImage();
    }

    @Override
    public void nakresliSe(Graphics g) {
        g.drawImage(obrazek, x, y, sirka, vyska, null);
    }

    @Override
    public void zpracujKolizi(Hra hra) {
        hra.aktivujOchranu();
    }
}