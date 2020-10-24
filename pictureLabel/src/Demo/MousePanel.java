package Demo;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

public class MousePanel extends ImgPanel//JPanel
{
    int x_pos, y_pos;
    int x_2,y_2;
    boolean mark;

    public int getPointX() {
        return x_pos;
    }

    public int getPointY() {
        return y_pos;
    }
    public int getX_2()
    {
        return x_2;
    }
    public int getY_2()
    {
        return y_2;
    }

    public MousePanel() {
        addMouseListener(new MouseListener() {
            //mouseClicked():鼠标单击
            public void mouseClicked(MouseEvent e) {

                //x_pos = e.getX();
                //y_pos = e.getY();
                //repaint();
            }

            //mouseEntered():鼠标进入时
            public void mouseEntered(MouseEvent e) {
            }

            //mouseExited():鼠标离开时
            public void mouseExited(MouseEvent e) {
            }

            //mousePressed():鼠标按下去
            public void mousePressed(MouseEvent e) {
                x_pos=e.getX();
                y_pos=e.getY();
                repaint();

            }

            //mouseReleased():鼠标松开时
            public void mouseReleased(MouseEvent e) {
                x_2=e.getX();
                y_2=e.getY();
                repaint();
            }
        });
        addMouseMotionListener(new MouseMotionListener() {
            public void mouseMoved(MouseEvent e) {
            }

            public void mouseDragged(MouseEvent e) {
            }
        });
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        //g.drawString("current location is:["+x_pos+","+y_pos+"]",x_pos,y_pos);//在界面上显示
        //System.out.printf("current location is:["+x_pos+","+y_pos+"]\n",x_pos,y_pos);//在控制台显示
        g.setColor(Color.RED);
        g.drawRect(x_pos , y_pos , x_2-x_pos, y_2-y_pos);
        //g.fillOval(x_pos,y_pos,8,8);


    }
}
