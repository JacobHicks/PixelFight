package com.jacobhicks;

import sun.text.resources.ro.CollationData_ro;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;

public class Main {
    //boolean[][] pixels = new boolean[200][175];
    Pixel[][] ai = new Pixel[(int) (125 / 1.15)][(int) (80 / 1.15)];
    public static final Color[] colors = {Color.RED, Color.BLUE};
    public static final Color[] colors1 = {new Color(148, 0, 211), new Color(75, 0, 130)};
    public static final Color[] colors2 = {Color.yellow, Color.ORANGE};
    double likelyhood = 0.0;
    private Main() {
        ArrayList<Integer> data = new ArrayList<>();
        data.add(0);
        JFrame frame = new JFrame("PIXEL FIGHT");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.setLayout(null);
        JLabel label = new JLabel();
        label.setBounds(650, 500, 160, 16);
        JLabel siml = new JLabel();
        siml.setBounds(600, 300, 160, 16);
        frame.add(label);
        frame.add(siml);
        label.setVisible(true);
        siml.setVisible(true);
        frame.setVisible(true);
        while(true) {
            double aggression = 1;
            for (int i = 0; i < ai.length; i++) {
                for (int x = 0; x < ai[i].length; x++) {
                    if(i == 0 || x == 0 || i == ai.length - 1 || x == ai[i].length - 1) {
                        ai[i][x] = new Pixel(Color.BLACK);
                    }
                    else {
                        ai[i][x] = new Pixel(colors[i > ai.length / 2 ? 1 : 0]);
                    }
                }
            }
            Graphics g = frame.getGraphics();
            long sims = 0;
            int reds;
            do {
                reds = 0;
                for(int r = 0; r < ai.length; r++) {
                    for(int c = 0; c < ai[r].length; c++) {
                        ai[r][c].update(ai, r, c);
                        if(ai[r][c].getOut()) {
                            g.setColor(Color.BLACK);
                        }
                        else if(ai[r][c].getColor().equals(Color.BLUE)) {
                            Color color = colors1[(int) (colors1.length * Math.random())];
                            g.setColor(new Color(Math.min(255, color.getRed() + ((ai[r][c].getProtect() > 0) ? (int)(255 * (40.0 / ai[r][c].getProtect())) : 0)), (ai[r][c].getProtect() > 0) ? color.getGreen() / 2 : color.getGreen(), (ai[r][c].getProtect() > 0) ? color.getBlue() / 2 : color.getBlue()));
                        }
                        else if(ai[r][c].getColor().equals(Color.RED)) {
                            Color color = colors2[(int) (colors2.length * Math.random())];
                            g.setColor(new Color(Math.min(255, color.getRed() + ((ai[r][c].getProtect() > 0) ? (int)(255 * (40.0 / ai[r][c].getProtect())) : 0)), (ai[r][c].getProtect() > 0) ? color.getGreen() / 2 : color.getGreen(), (ai[r][c].getProtect() > 0) ? color.getBlue() / 2 : color.getBlue()));
                        }
                        else {
                            g.setColor(ai[r][c].getColor());
                        }
                        g.fillRect((int)(r * (5 * 1.15)) - 3, (int)(c * (5 * 1.15)) - 3, (int)(5 * 1.15) + 3, (int)(5 * 1.15) + 3);
                        if(ai[r][c].getColor().equals(Color.RED)) {
                            reds++;
                        }
                    }
                }
                sims++;
                label.setText(String.format("%.2f", ((double) reds / (ai.length * ai[0].length)) * 100) + "%");
                if(sims % 10 == 0) {
                    g.setColor(Color.green);
                    g.fillRect(9, 409, 600, 150);
                    g.setColor(Color.BLACK);
                    g.drawRect(10, 410, 600, 150);
                    g.drawRect(9, 409, 600, 150);
                    if(reds > ai.length * ai[0].length / 2) {
                        g.setColor(colors2[0]);
                    }
                    else {
                        g.setColor(colors1[0]);
                    }
                    data.add((int) (((double) reds) / (ai.length * ai[0].length) * 100));
                    if (data.size() > 100) {
                        data.set(0, (data.get(0) + data.remove(1)) / 2);
                    }
                    for (int i = 0; i < data.size() - 1; i++) {
                        g.drawLine(10 + (i * (600 / (data.size() - 1))), (int) (560 - (150 / 100.0) * data.get(i)), (10 + ((i + 1) * (600 / (data.size() - 1)))), (int) ((560 - (150 / 100.0) * data.get(i + 1))));
                        //g.fillRect(10 + (i * (600 / (data.size() - 1))), (int) (560 - (150 / 100.0) * data.get(i)) - 5, 10, 10);
                    }
                }

        } while ((reds <= (ai.length * ai[0].length) * .90) && (reds >= (ai.length * ai[0].length) * .10));
            data.clear();
            g.setColor(Color.white);
            g.fillRect(9, 409, 600, 150);
    }
    }


    public static void main(String[] args) {
        Main m = new Main();
    }
}
