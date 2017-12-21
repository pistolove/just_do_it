import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Ellipse2D;

import javax.swing.*;

public class MoveCar {
    private static int currentCenterX = (int) (Math.random() * 400 + 100);
    private static int currentCenterY = (int) (Math.random() * 100 + 100);
    private static int nextCenterX = currentCenterX;
    private static int nextCenterY = currentCenterY;

    public static void main(String[] args) throws InterruptedException {

        JFrame jf = new JFrame("汽车移动小程序");
        jf.setSize(600, 400);
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel contentPane = new JPanel();
        jf.setContentPane(contentPane);

        final JLabel label = new JLabel("    ", null, SwingConstants.LEFT);
        JButton calculate = new JButton("掷色子");
        calculate.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int moveDirection = (int) (Math.random() * 4 + 1);
                int num = (int) (Math.random() * 5 + 5);
                if (moveDirection == 1) {
                    // 向上
                    nextCenterX = currentCenterX;
                    nextCenterY = currentCenterY - num;
                    label.setText("上移" + num + "步");
                } else if (moveDirection == 2) {
                    // 向下
                    nextCenterX = currentCenterX;
                    nextCenterY = currentCenterY + num;
                    label.setText("下移" + num + "步");
                } else if (moveDirection == 3) {
                    // 向左
                    nextCenterX = currentCenterX - num;
                    nextCenterY = currentCenterY;
                    label.setText("左移" + num + "步");
                } else {
                    // 向右
                    nextCenterX = currentCenterX + num;
                    nextCenterY = currentCenterY;
                    label.setText("右移" + num + "步");
                }

            }
        });

        JButton move = new JButton("移动");
        final DrawCar car = new DrawCar(currentCenterX, currentCenterY);
        move.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                car.setCenterX(nextCenterX);
                car.setCenterY(nextCenterY);
                car.repaint();
                currentCenterX = nextCenterX;
                currentCenterY = nextCenterY;
            }
        });

        contentPane.add(calculate);
        contentPane.add(move);
        contentPane.add(label);
        contentPane.add(car);
        contentPane.revalidate();

        jf.setVisible(true);
    }

    static class DrawCar extends JComponent {
        private double centerX;
        private double centerY;

        public void setCenterX(double centerX) {
            this.centerX = centerX;
        }

        public void setCenterY(double centerY) {
            this.centerY = centerY;
        }

        public DrawCar(double centerX, double centerY) {
            this.centerX = centerX;
            this.centerY = centerY;
        }

        @Override
        public void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g;
            double redius = 10;
            Ellipse2D car = new Ellipse2D.Double();
            car.setFrameFromCenter(centerX, centerY, centerX + redius, centerY + redius);
            g2.draw(car);
        }

        public Dimension getPreferredSize() {
            return new Dimension(550, 350);
        }
    }

}
