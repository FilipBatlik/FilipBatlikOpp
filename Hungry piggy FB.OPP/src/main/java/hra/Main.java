package hra;
//test
import javax.swing.*;
import java.awt.*;

public class Main {
    public static void main(String[] args) {
        Dimension velikostOkna = new Dimension(900, 500);
        JFrame okno = new JFrame("Prasátko Drop");
        Hra hra = new Hra();
        okno.add(hra);
        okno.setResizable(false);
        okno.setSize(velikostOkna);
        okno.setLocationRelativeTo(null);
        okno.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        okno.setVisible(true);
    }
}