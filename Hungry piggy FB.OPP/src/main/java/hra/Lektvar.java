package hra;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public class Lektvar extends PadajiciVec {
    private Image obrazek;

    public Lektvar(int x, int y) {
        super(x, y);
        this.obrazek = new ImageIcon(Objects.requireNonNull(getClass().getResource("/lektvar.png"))).getImage();
    }

    @Override
    public void nakresliSe(Graphics g) {
        g.drawImage(obrazek, x, y, sirka, vyska, null);
    }

    @Override
    public void zpracujKolizi(Hra hra) {
        hra.pridejSkore(50);
    }
}