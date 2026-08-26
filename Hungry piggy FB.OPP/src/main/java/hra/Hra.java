package hra;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;

public class Hra extends JPanel implements ActionListener, KeyListener {

    private static final int SIRKA_OKNA = 900;
    private static final int VYSKA_OKNA = 500;

    private Timer casovac;
    private Timer animacniTimer;
    private Random random = new Random();
    private boolean hraBezi = true;
    private int skore = 0;
    private int casOchrany = 0;
    private int aktualniSnimek = 0;
    private int pocetZivotu = 3;

    private Image srdce;

    private Hrac hrac;
    private Pozadi pozadi;
    private ArrayList<PadajiciVec> veci = new ArrayList<>();
    private int rychlostPadu = 5;

    public Hra() {
        this.setPreferredSize(new Dimension(SIRKA_OKNA, VYSKA_OKNA));
        this.setFocusable(true);
        this.addKeyListener(this);

        pozadi = new Pozadi();
        hrac = new Hrac(SIRKA_OKNA / 2 - 30, 360);
        srdce = new ImageIcon(getClass().getResource("/heart.png")).getImage();

        casovac = new Timer(16, this);
        casovac.start();

        animacniTimer = new Timer(100, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                aktualniSnimek++;
                if (aktualniSnimek > 11) {
                    aktualniSnimek = 0;
                }
            }
        });
        animacniTimer.start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        pozadi.nakresliSe(g);

        if (hraBezi) {
            hrac.setSnimek(aktualniSnimek);
            hrac.nakresliSe(g);
            for (PadajiciVec vec : veci) {
                vec.nakresliSe(g);
            }

            g.setColor(Color.BLACK);
            g.setFont(new Font("Arial", Font.BOLD, 20));
            g.drawString("Skore: " + skore, 20, 30);

            if (casOchrany > 0) {
                g.drawString("Ochrana: " + (casOchrany / 60) + "s", 20, 60);
            }

            g.setColor(Color.BLACK);
            g.setFont(new Font("Arial", Font.BOLD, 20));
            g.drawString("Zivoty:", 15, 110);
            for (int i = 0; i < pocetZivotu; i++) {
                g.drawImage(srdce, 95 + (i * 30), 92, 25, 25, null);
            }
        }

        if (!hraBezi) {
            g.setColor(new Color(0, 0, 0, 200));
            g.fillRect(0, 0, SIRKA_OKNA, VYSKA_OKNA);

            g.setColor(Color.WHITE);
            Font font1 = new Font("Arial", Font.BOLD, 40);
            Font font2 = new Font("Arial", Font.BOLD, 24);
            Font font3 = new Font("Arial", Font.BOLD, 20);

            g.setFont(font1);
            FontMetrics fm1 = g.getFontMetrics();
            g.drawString("KONEC HRY!", (SIRKA_OKNA / 2) - (fm1.stringWidth("KONEC HRY!") / 2), 230);

            g.setFont(font2);
            FontMetrics fm2 = g.getFontMetrics();
            g.drawString("Skóre: " + skore, (SIRKA_OKNA / 2) - (fm2.stringWidth("Skóre: " + skore) / 2), 280);

            g.setFont(font3);
            FontMetrics fm3 = g.getFontMetrics();
            g.drawString("Stiskni R pro restart", (SIRKA_OKNA / 2) - (fm3.stringWidth("Stiskni R pro restart") / 2), 320);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (hraBezi) {
            if (random.nextInt(100) < 4) {
                int x = random.nextInt(SIRKA_OKNA - 30);
                int typ = random.nextInt(10);
                if (typ < 4) veci.add(new Bomba(x, -30));
                else if (typ < 6) veci.add(new Lektvar(x, -30));
                else if (typ < 8) veci.add(new Stit(x, -30));
                else veci.add(new Jidlo(x, -30));
            }

            if (casOchrany > 0) casOchrany--;

            for (int i = 0; i < veci.size(); i++) {
                PadajiciVec vec = veci.get(i);
                vec.pohniSe(rychlostPadu);

                if (vec.y > VYSKA_OKNA) {
                    veci.remove(i);
                    i--;
                } else if (vec.overujeKolizi(hrac.getX(), hrac.getY(), hrac.getSirka(), hrac.getVyska())) {

                    // Pokud je aktivní štít, bomba se jen odrazí a zmizí
                    if (vec instanceof Bomba && casOchrany > 0) {
                        veci.remove(i);
                        i--;
                    } else {
                        vec.zpracujKolizi(this);
                        veci.remove(i);
                        i--;
                    }
                }
            }
        }
        repaint();
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (!hraBezi) {
            return;
        }

        int klavesa = e.getKeyCode();
        if (klavesa == KeyEvent.VK_LEFT) hrac.pohniDoleva();
        if (klavesa == KeyEvent.VK_RIGHT) hrac.pohniDoprava();
        if (klavesa == KeyEvent.VK_R) {
            skore = 0;
            casOchrany = 0;
            veci.clear();
            hrac.reset();
            hraBezi = true;
            pocetZivotu = 3;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {}
    @Override
    public void keyTyped(KeyEvent e) {}

    public void pridejSkore(int body) {
        skore += body;
    }

    public void uberZivot() {
        if (pocetZivotu > 1) {
            pocetZivotu--;
        } else {
            pocetZivotu = 0;
            hraBezi = false;
        }
    }

    public void aktivujOchranu() {
        casOchrany = 180;
    }
}