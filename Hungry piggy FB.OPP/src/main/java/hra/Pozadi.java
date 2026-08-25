package hra;

import javax.swing.*;
import java.awt.*;

public class Pozadi {
    private Image obrazek;
    private int vyskaCesty = 60;

    public Pozadi() {
        this.obrazek = new ImageIcon(getClass().getResource("/farma.png")).getImage();
    }

    public void nakresliSe(Graphics g) {
        g.drawImage(obrazek, 0, 0, 900, 500, null);
    }

    public int getVyskaCesty() {
        return vyskaCesty;
    }
}