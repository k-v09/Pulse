import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.*;

public class BoidSim extends JPanel {
    private static final int WIDTH = 1200;
    private static final int HEIGHT = 800;
    private static final int NUM_BOIDS = 100;
    private static final double MAX_SPEED = 4.0;
    private static final double SEPARATION_RADIUS = 40.0;
    private static final double ALIGNMENT_RADIUS = 60.0;
    private static final double COHESION_RADIUS = 60.0;
    private static final double SEPARATION_FACTOR = 3;
    private static final double ALIGNMENT_FACTOR = 0.8;
    private static final double COHESION_FACTOR = 0.7;

    private ArrayList<Boid> boids;
    private Random random;

    public BoidSim() {
        boids = new ArrayList<>();
        random = new Random();
        
        for (int i = 0; i < NUM_BOIDS; i++) {
            double x = random.nextDouble() * WIDTH;
            double y = random.nextDouble() * HEIGHT;
            Boid boid = new Boid(x, y);
            boids.add(boid);
        }

        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setBackground(Color.BLACK);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        for (Boid boid : boids) {
            g2d.setColor(Color.WHITE);
            g2d.fillOval((int) boid.position.x, (int) boid.position.y, 5, 5);
        }
    }

    public void updateBoids() {
        for (Boid currentBoid : boids) {
            Vector separation = calculateSeparation(currentBoid);
            Vector alignment = calculateAlignment(currentBoid);
            Vector cohesion = calculateCohesion(currentBoid);
            currentBoid.velocity.add(separation.multiply(SEPARATION_FACTOR));
            currentBoid.velocity.add(alignment.multiply(ALIGNMENT_FACTOR));
            currentBoid.velocity.add(cohesion.multiply(COHESION_FACTOR));
            limitSpeed(currentBoid);
            currentBoid.position.add(currentBoid.velocity);
            wrapAround(currentBoid);
        }
    }

    private Vector calculateSeparation(Boid currentBoid) {
        Vector separation = new Vector(0, 0);
        int nearbyBoids = 0;

        for (Boid otherBoid : boids) {
            if (otherBoid != currentBoid) {
                double distance = currentBoid.position.distance(otherBoid.position);
                if (distance < SEPARATION_RADIUS) {
                    Vector diff = currentBoid.position.subtract(otherBoid.position);
                    separation.add(diff.normalize().divide(distance));
                    nearbyBoids++;
                }
            }
        }

        return nearbyBoids > 0 ? separation.divide(nearbyBoids) : separation;
    }

    private Vector calculateAlignment(Boid currentBoid) {
        Vector averageVelocity = new Vector(0, 0);
        int nearbyBoids = 0;

        for (Boid otherBoid : boids) {
            if (otherBoid != currentBoid) {
                double distance = currentBoid.position.distance(otherBoid.position);
                if (distance < ALIGNMENT_RADIUS) {
                    averageVelocity.add(otherBoid.velocity);
                    nearbyBoids++;
                }
            }
        }

        return nearbyBoids > 0 ? averageVelocity.divide(nearbyBoids).subtract(currentBoid.velocity) : averageVelocity;
    }

    private Vector calculateCohesion(Boid currentBoid) {
        Vector centerOfMass = new Vector(0, 0);
        int nearbyBoids = 0;

        for (Boid otherBoid : boids) {
            if (otherBoid != currentBoid) {
                double distance = currentBoid.position.distance(otherBoid.position);
                if (distance < COHESION_RADIUS) {
                    centerOfMass.add(otherBoid.position);
                    nearbyBoids++;
                }
            }
        }

        if (nearbyBoids > 0) {
            centerOfMass = centerOfMass.divide(nearbyBoids);
            return centerOfMass.subtract(currentBoid.position).divide(100);
        }
        return centerOfMass;
    }

    private void limitSpeed(Boid boid) {
        double speed = boid.velocity.magnitude();
        if (speed > MAX_SPEED) {
            boid.velocity = boid.velocity.normalize().multiply(MAX_SPEED);
        }
    }

    private void wrapAround(Boid boid) {
        if (boid.position.x < 0) boid.position.x = WIDTH;
        if (boid.position.x > WIDTH) boid.position.x = 0;
        if (boid.position.y < 0) boid.position.y = HEIGHT;
        if (boid.position.y > HEIGHT) boid.position.y = 0;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Boids Simulation");
            BoidSim simulation = new BoidSim();
            frame.add(simulation);
            frame.pack();
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
            Timer timer = new Timer(16, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    simulation.updateBoids();
                    simulation.repaint();
                }
            });
            timer.start();
        });
    }

    private class Boid {
        Vector position;
        Vector velocity;

        Boid(double x, double y) {
            position = new Vector(x, y);
            // Random initial velocity
            velocity = new Vector(
                random.nextDouble() * 2 - 1, 
                random.nextDouble() * 2 - 1
            );
        }
    }

    private class Vector {
        double x, y;

        Vector(double x, double y) {
            this.x = x;
            this.y = y;
        }

        void add(Vector v) {
            this.x += v.x;
            this.y += v.y;
        }
        Vector subtract(Vector v) {
            return new Vector(this.x - v.x, this.y - v.y);
        }
        Vector multiply(double scalar) {
            return new Vector(this.x * scalar, this.y * scalar);
        }
        Vector divide(double scalar) {
            return new Vector(this.x / scalar, this.y / scalar);
        }
        double magnitude() {
            return Math.sqrt(x * x + y * y);
        }
        Vector normalize() {
            double mag = magnitude();
            return mag > 0 ? divide(mag) : new Vector(0, 0);
        }
        double distance(Vector v) {
            double dx = this.x - v.x;
            double dy = this.y - v.y;
            return Math.sqrt(dx * dx + dy * dy);
        }
    }
}
