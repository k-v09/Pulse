import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.ArrayList;

public class LoneBoid extends JPanel {
    private static final int WIDTH = 1000;
    private static final int HEIGHT = 1000;

   public LoneBoid() {
        Boid dude = new Boid(WIDTH / 2, HEIGHT / 2, 90.0, 300);
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setBackground(Color.BLACK);
   } 

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        Boid q = new Boid(WIDTH / 2, HEIGHT / 2, 90, 50);
        int[] xPoints = {(int)Math.floor(q.a.i), (int)Math.floor(q.b.i), (int)Math.floor(q.c.i)};
        int[] yPoints = {(int)Math.floor(q.a.j), (int)Math.floor(q.b.j), (int)Math.floor(q.c.j)};
        
        g2d.setColor(Color.RED);
        g2d.fillPolygon(xPoints, yPoints, 3);
    }
/*
    private void boidWrap(Boid boid) {
        if (boid.position.x < 0) boid.position.x = WIDTH;
        if (boid.position.x > WIDTH) boid.position.x = 0;
        if (boid.position.y < 0) boid.position.y = HEIGHT;
        if (boid.position.y > HEIGHT) boid.position.y = 0;
    }
*/
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Lonely Guy");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            LoneBoid bbg = new LoneBoid();
            frame.add(bbg);
            frame.setSize(WIDTH, HEIGHT);
            frame.setVisible(true);
        });
    }

    private class Boid {
        Vector center, velocity, a, b, c;
        double theta;
        int size;

        Boid(double x, double y, double theta, int size) {
            center = new Vector(x, y);
            this.theta = theta;
            this.size = size;
            velocity = new Vector(0, 0);
            a = new Vector(center.i, (center.j + 0.577*size));
            b = new Vector((center.i - 0.577*size), (center.j - 0.423*size));
            c = new Vector((center.i + 0.577*size), (center.j - 0.423*size));
        }
    }

    private class Vector {
        double i, j;

        Vector(double i, double j) {
            this.i = i;
            this.j = j;
        }

        double magnitude() {
            return Math.sqrt(i*i + j*j);
        }
        double distance(Vector v) {
            double dx = this.i - v.i;
            double dy = this.j - v.j;
            return Math.sqrt(dx*dx + dy*dy);
        }
    }
}
