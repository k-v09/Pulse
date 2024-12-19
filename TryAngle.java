import java.awt.*;
import javax.swing.*;

public class TryAngle extends JPanel {

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        int[] xPoints = {50, 100, 150};
        int[] yPoints = {150, 50, 150};
        int nPoints = 3;

        g.setColor(Color.RED);
        g.fillPolygon(xPoints, yPoints, nPoints);
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Filled Triangle");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(new TryAngle());
        frame.setSize(300, 200);
        frame.setVisible(true);
    }
}

/*
import java.awt.*;
import javax.swing.*;

public class TryAngle extends JPanel {

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        int[] xPoints = {100, 200, 150}; 
        int[] yPoints = {200, 200, 100}; 
        int nPoints = 3;

        g.drawPolygon(xPoints, yPoints, nPoints);
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Triangle Example");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(new TryAngle());
        frame.setSize(300, 300);
        frame.setVisible(true);
    }
}
*/
