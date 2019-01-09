package com.jacobhicks;

import java.awt.*;

public class Pixel {
    Color color;
    int protect = 0;
    boolean out = false;
    public Pixel(Color c) {
        color = c;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public Color getColor() {
        return color;
    }
    public int getProtect() {
        if(protect < 0) return 0;
        return protect;
    }

    public boolean getOut() {
        return out;
    }

    public void update(Pixel[][] gb, int r, int c) {
        if(!color.equals(Color.BLACK)) {
            int val = update(gb, r, c, 3, color);
            if(val < 53) {
                out = true;
            }
            else {
                out = false;
            }
            if (Math.random() * 40 + val < 50 && protect <= 0 || (!(r <= 0 || c <= 0 || r >= gb.length - 1 || c >= gb[r].length - 1) && !(
                    gb[r + 1][c].getColor().equals(color) ||
                            gb[r + 1][c + 1].getColor().equals(color) ||
                            gb[r + 1][c - 1].getColor().equals(color) ||
                            gb[r - 1][c].getColor().equals(color) ||
                            gb[r - 1][c + 1].getColor().equals(color) ||
                            gb[r - 1][c - 1].getColor().equals(color) ||
                            gb[r][c + 1].getColor().equals(color) ||
                            gb[r][c - 1].getColor().equals(color)))) {
                protect = (int) (Math.random() * 40);
                if (color.equals(Color.RED)) {
                    color = Color.BLUE;
                } else {
                    color = Color.RED;
                }
            } else {
                protect--;
            }
        }
    }
    public int update(Pixel[][] gb, int r, int c, int val, Color color) {
        if(r < 0 || c < 0 || r >= gb.length || c >= gb[r].length || val <= 0) {
            return 0;
        }
        if(gb[r][c].color.equals(color) || color.equals(Color.BLACK)) {
            return val + update(gb, r + 1, c, val - 1, gb[r][c].color) + update(gb, r + 1, c + 1, val - 1, gb[r][c].color)
                    + update(gb, r + 1, c - 1, val - 1, gb[r][c].color) + update(gb, r - 1, c, val - 1, gb[r][c].color)
                    + update(gb, r - 1, c + 1, val - 1, gb[r][c].color) + update(gb, r - 1, c - 1, val - 1, gb[r][c].color)
                    + update(gb, r, c + 1, val - 1, gb[r][c].color) + update(gb, r, c - 1, val - 1, gb[r][c].color);
        }
        else if(gb[r][c].color.equals(Color.BLACK)) {
            return val;
        }
        return 0;
    }
}
