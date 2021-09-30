package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;


public class Logic extends JComponent implements MouseListener {
    public static final int FIELD_EMPTY = 0;
    public static final int FIELD_BLOCK = 1;
    public static final int FIELD_R = 20;
    public static final int FIELD_G = 30;
    public static final int FIELD_B = 40;
    int [][] field;


    public Logic(){
        this.addMouseListener(this);
        field = new int[6][6];
        initGame();
        String msg = "You need to collect 3 columns of colors in this order: Red, Green, Blue";
        JOptionPane.showMessageDialog(this,msg,"Rules",JOptionPane.INFORMATION_MESSAGE);
    }


    @Override
    public void mouseClicked(MouseEvent e) {
        int x = e.getX();
        int y = e.getY();
        int i = (int) ((float) x / getWidth() * 5);
        int j = (int) ((float) y / getHeight() * 5);
        if (field[i][j] != FIELD_BLOCK) {
            if (field[i][j]==FIELD_R || field[i][j]==FIELD_G || field[i][j]==FIELD_B){

                boolean flag = true;
                for (int m=0;m<5;m++) {
                    for (int k = 0; k < 5; k++) {
                        if (field[m][k]%10==5) {
                            flag = false;
                            field[m][k]=field[m][k]-5;
                        }
                    }
                }
                if (flag) field [i][j] = field[i][j]+5;
                repaint();
            }
            else if (field[i][j]==FIELD_R+5 || field[i][j]==FIELD_G+5 || field[i][j]==FIELD_B+5){
                field [i][j] = field[i][j]-5;
                repaint();
            }


            else if (field[i][j]==FIELD_EMPTY) {

                boolean flag = false;
                for (int m=0;m<5;m++) {
                    for (int k = 0; k < 5; k++) {
                        if (field[m][k] % 10 == 5) {
                            flag = true;
                            break;
                        }
                    }
                }
                if (flag) {
                    if (field[(i + 1) % 6][j] % 10 == 5) {
                        field[i][j] = field[i + 1][j] - 5;
                        field[i + 1][j] = FIELD_EMPTY;
                        repaint();
                    } else if (field[i][j + 1] % 10 == 5) {
                        field[i][j] = field[i][j + 1] - 5;
                        field[i][(j + 1) % 6] = FIELD_EMPTY;
                        repaint();
                    } else if (field[i][(j - 1 + 6) % 6] % 10 == 5) {
                        field[i][j] = field[i][j - 1] - 5;
                        field[i][j - 1] = FIELD_EMPTY;
                        repaint();
                    } else if (field[(i - 1 + 6) % 6][j] % 10 == 5) {
                        field[i][j] = field[i - 1][j] - 5;
                        field[i - 1][j] = FIELD_EMPTY;
                        repaint();
                    } else {
                        for (int m=0;m<5;m++) {
                            for (int k = 0; k < 5; k++) {
                                if (field[m][k]%10==5) {
                                    field[m][k]=field[m][k]-5;
                                    repaint();
                                }
                            }
                        }
                    }
                }
            }


        }
        if (checkWin()) {
            JOptionPane.showMessageDialog(this,"You win!","Congratulations!",JOptionPane.INFORMATION_MESSAGE);
            initGame();
            repaint();
        }

    }



    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    protected void paintComponent(Graphics graphics){
        super.paintComponent(graphics);
        drawBlock(graphics);
    }

    public void drawBlock(Graphics graphics){
        for (int i=0;i<5;i++){
            for (int j=0;j<5;j++) {
                if (field[i][j] == FIELD_R){
                    graphics.setColor(Color.RED);
                    graphics.fillRect(i*(getWidth()/5),j*(getWidth()/5),getWidth()/5,getHeight()/5);
                }
                else if (field[i][j] == FIELD_G){

                    graphics.setColor(Color.GREEN);
                    graphics.fillRect(i*(getWidth()/5),j*(getWidth()/5),getWidth()/5,getHeight()/5);
                }
                else if (field[i][j] == FIELD_B){

                    graphics.setColor(Color.BLUE);
                    graphics.fillRect(i*(getWidth()/5),j*(getWidth()/5),getWidth()/5,getHeight()/5);
                }
                else if (field[i][j]%10 == 5) {
                    graphics.setColor(Color.MAGENTA);
                    graphics.fillRect(i*(getWidth()/5),j*(getWidth()/5),getWidth()/5,getHeight()/5);
                }

                else if (field[i][j] == FIELD_BLOCK){
                    graphics.setColor(Color.BLACK);
                    graphics.fillRect(i*(getWidth()/5),j*(getWidth()/5),getWidth()/5,getHeight()/5);
                }
            }

        }

    }

    boolean checkWin(){
        boolean win=true;
        for (int i=0;i<5;i++) {
            if (field[0][i] != FIELD_R) win = false;
            if (field[2][i] != FIELD_G) win = false;
            if (field[4][i] != FIELD_B) win = false;
        }
        return win;
    }

    public void initGame(){

        for (int i=0;i<5;i++) {
            for (int j = 0; j < 5; j++) field[i][j] = FIELD_EMPTY;
        }
        for (int i=0;i<5;i++) field[i][5] = FIELD_BLOCK;
        for (int j = 0; j < 5; j++) field[5][j] = FIELD_BLOCK;

        for (int i = 1; i<5;i+=2) {
            for (int j = 0; j<5;j+=2) field[i][j] = FIELD_BLOCK;
        }
        int R_Counter = 0;
        int G_Counter = 0;
        int B_Counter = 0;
        for (int j=0;j<5;j++){
            for (int i=0;i<5;i+=2){
                while (field[i][j]==FIELD_EMPTY) {
                    int a = (int)(Math.random() * 3 + 2);

                    if (a==FIELD_R/10 && R_Counter<5) {
                        field[i][j]=FIELD_R;
                        R_Counter++;
                    }
                    else if (a==FIELD_G/10 && G_Counter<5) {
                        field[i][j]=FIELD_G;
                        G_Counter++;
                    }
                    else if (a==FIELD_B/10 && B_Counter<5) {
                        field[i][j]=FIELD_B;
                        B_Counter++;
                    }
                }
            }
        }

    }
}
