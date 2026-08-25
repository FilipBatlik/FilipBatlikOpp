package hra;

import javax.swing.*;
import java.awt.*;

public class Hrac {
    private int x, y;
    private int sirka = 60;
    private int vyska = 60;
    private int startX, startY;
    private int snimek = 0;
    private String smer = "vpravo";
    private boolean seHybe = false;

    private Image[] tanec;
    private Image[] chuze;

    public Hrac(int x, int y) {
        this.x = x;
        this.y = y;
        this.startX = x;
        this.startY = y;

        tanec = new Image[12];
        chuze = new Image[12];

        for (int i = 0; i <= 11; i++) {
            tanec[i] = new ImageIcon(getClass().getResource("/tanec_" + i + ".png")).getImage();
            chuze[i] = new ImageIcon(getClass().getResource("/chuze_" + i + ".png")).getImage();
        }
    }

    public void setSnimek(int s) {
        this.snimek = s;
    }

    public void pohniDoleva() {
        if (x > 0) {
            x -= 12;
        }
        smer = "vlevo";
        seHybe = true;
    }

    public void pohniDoprava() {
        if (x < 840) {
            x += 12;
        }
        smer = "vpravo";
        seHybe = true;
    }

    public void reset() {
        x = startX;
        y = startY;
        smer = "vpravo";
        seHybe = false;
    }

    public int getX() { return x; }
    public int getY() { return y; }
    public int getSirka() { return sirka; }
    public int getVyska() { return vyska; }

    public void nakresliSe(Graphics g) {
        Image aktualniObrazek;
        if (seHybe) {
            aktualniObrazek = chuze[snimek];
        } else {
            aktualniObrazek = tanec[snimek];
        }

        if (smer.equals("vpravo")) {
            Graphics2D g2d = (Graphics2D) g;
            int xPosun = x + sirka;
            g2d.drawImage(aktualniObrazek, xPosun, y, -sirka, vyska, null);
        } else {

            g.drawImage(aktualniObrazek, x, y, sirka, vyska, null);
        }
    }
}