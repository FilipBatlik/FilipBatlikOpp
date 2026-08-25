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

    private Hrac hrac;
    private Pozadi pozadi;
    private ArrayList<PadajiciVec> veci = new ArrayList<>();
    private int rychlostPadu = 5;

    public Hra() {
        this.setPreferredSize(new Dimension(SIRKA_OKNA, VYSKA_OKNA));
        this.setFocusable(true);
        this.addKeyListener(this);

        pozadi = new Pozadi();
        // Prase je vysoké 60, cesta začíná na 440.
        // Proto y = 440 - 60 = 380
        hrac = new Hrac(SIRKA_OKNA / 2 - 30, 360);

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

        if (!hraBezi) {
            g.setColor(Color.RED);
            g.setFont(new Font("Arial", Font.BOLD, 40));
            g.drawString("KONEC HRY!", SIRKA_OKNA / 2 - 100, VYSKA_OKNA / 2);
            g.setFont(new Font("Arial", Font.BOLD, 20));
            g.drawString("Stiskni R pro restart", SIRKA_OKNA / 2 - 90, VYSKA_OKNA / 2 + 40);
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
                    if (vec instanceof Jidlo) {
                        skore += 10;
                        veci.remove(i);
                        i--;
                    } else if (vec instanceof Lektvar) {
                        skore += 50;
                        veci.remove(i);
                        i--;
                    } else if (vec instanceof Stit) {
                        casOchrany = 180;
                        veci.remove(i);
                        i--;
                    } else if (vec instanceof Bomba) {
                        if (casOchrany > 0) {
                            veci.remove(i);
                            i--;
                        } else {
                            hraBezi = false;
                            break;
                        }
                    }
                }
            }
        }
        repaint();
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int klavesa = e.getKeyCode();
        if (klavesa == KeyEvent.VK_LEFT) hrac.pohniDoleva();
        if (klavesa == KeyEvent.VK_RIGHT) hrac.pohniDoprava();
        if (klavesa == KeyEvent.VK_R) {
            skore = 0;
            casOchrany = 0;
            veci.clear();
            hrac.reset();
            hraBezi = true;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {}
    @Override
    public void keyTyped(KeyEvent e) {}
}