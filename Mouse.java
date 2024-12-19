import java.awt.*; 
import java.awt.event.*; 
import javax.swing.*; 

class Mouse extends JFrame { 

	boolean b; 
	JLabel l, l1; 
	public static void main(String args[]) {
		Mouse m = new Mouse(); 
	} 

	Mouse() 
	{ 
		super("mouse"); 
		l = new JLabel(""); 
		l1 = new JLabel(""); 

		JPanel p = new JPanel(); 
		p.add(l); 
		p.add(l1); 

		add(p); 
		show(); 
		setSize(300, 300); 

		b = true; 
		execute(); 
	} 

	public void execute() 
	{ 
		while (b) { 
			PointerInfo pi = MouseInfo.getPointerInfo(); 
			Point p = pi.getLocation();
			l.setText("x position =" + p.getX()); 
			l1.setText("y position =" + p.getY()); 
		} 
	} 
} 

