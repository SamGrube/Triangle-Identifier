import java.awt.*;
import java.awt.EventQueue;
import java.lang.Math;
import java.awt.Graphics;
import javax.swing.JFrame;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Font;
import javax.swing.border.MatteBorder;

@SuppressWarnings("serial")
public class TriangleID extends JFrame{

	private JFrame mainWindow;
	private JPanel canvas;
	private JTextField angle2TextField;
	private JTextField angle1TextField;
	private JLabel triangleType;
 	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
					TriangleID window = new TriangleID();
					window.mainWindow.setVisible(true);
			}
		});
	}
	
	public TriangleID() {
		initialize();
	}
	
	public void initialize() {
		mainWindow = new JFrame();
		mainWindow.getContentPane().setForeground(new Color(0, 0, 0));
		mainWindow.getContentPane().setFont(new Font("Tahoma", Font.PLAIN, 11));
		mainWindow.setBackground(new Color(240, 240, 240));
		mainWindow.setType(Type.POPUP);
		mainWindow.setUndecorated(false);
		mainWindow.setVisible(true);
		mainWindow.setBounds(50, 50, 800, 800);
		mainWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainWindow.setTitle("Triangle Surface Simulator");
		mainWindow.getContentPane().setLayout(new BorderLayout(0, 0));
		
		canvas = new JPanel() {
			
			public void paint(Graphics g) {									
				int XleftVertex = 100,XrightVertex = 700;								// Width of the triangle
				int L = XrightVertex - XleftVertex;										// Base of the triangle
				int YBase = 700;														// Where the base of the triangle lies
				double phi1 = radian(Double.valueOf(angle1TextField.getText()));		// Left Angle of triangle
				double phi2 = radian(Double.valueOf(angle2TextField.getText()));		// Right Angle of Triangle
				int peakX = XleftVertex + (int)Math.round(L*Math.sin(phi2)*Math.cos(phi1) / Math.sin(phi1 + phi2));
				int peakY = YBase - (int)Math.round(Math.tan(phi1)*(peakX-XleftVertex));// (X,Y) coordinates of the triangle's peak
				// There is currently a bug when calculating the peak if phi1 = 90
				super.paint(g);
				// Triangle Surface Drawing
				g.drawLine(XleftVertex,YBase,XrightVertex,YBase);  		// Base of triangle
				g.drawLine(XleftVertex,YBase,peakX,peakY); 				// left triangle leg
				g.drawLine(XrightVertex,YBase,peakX,peakY);				// right triangle leg
				
			}
		};
		// canvas to draw on
		canvas.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		canvas.setBackground(Color.WHITE);
		mainWindow.getContentPane().add(canvas, BorderLayout.CENTER);
		
		// control panel that holds input text fields and button
		JPanel controlPanel = new JPanel();
		mainWindow.getContentPane().add(controlPanel, BorderLayout.SOUTH);
		
		// Text Field for input of the triangle's leftmost angle
		angle1TextField = new JTextField();
		angle1TextField.setText("60");
		angle1TextField.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String phi1 = angle1TextField.getText();
				angle1TextField.setText(phi1);
			}
		});
		angle1TextField.setToolTipText("Left Angle (0<x<90)");
		controlPanel.add(angle1TextField);
		angle1TextField.setColumns(10);
		
		// Text Field for input of the triangle's rightmost angle
		angle2TextField = new JTextField();
		angle2TextField.setText("60");
		angle2TextField.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String phi2 = angle2TextField.getText();
				angle2TextField.setText(phi2);
			}
		});
		angle2TextField.setToolTipText("Right Angle 0<x<90");
		controlPanel.add(angle2TextField);
		angle2TextField.setColumns(10);
		
		// Edit Triangle button that repaints based off of text field inputs
		// Also sets text of label to show what type of triangle is drawn
		JButton btnBegin = new JButton("Repaint");
		btnBegin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				triangleType.setText("Equilateral Triangle");
				if (Integer.valueOf(angle1TextField.getText()) == 60 && Integer.valueOf(angle2TextField.getText()) == 60)
					triangleType.setText("Equilateral Triangle");
				else if(Integer.valueOf(angle1TextField.getText()) == 90 || Integer.valueOf(angle2TextField.getText()) == 90)
					triangleType.setText("Right Triangle");
				else if((Integer.valueOf(angle1TextField.getText()) < 90 && Integer.valueOf(angle2TextField.getText()) < 90))
					triangleType.setText("Acute Triangle");
				canvas.repaint();
			}
		});
		controlPanel.add(btnBegin);
		
		// Display label for type of triangle
		triangleType = new JLabel();
		triangleType.setText("Equilateral Triangle");
		controlPanel.add(triangleType);
		
	}
	// Convert degrees to radians because sin and cos take Rad arguments
	public double radian(double degree) {
		return ((degree * Math.PI) / 180.0);
	}
	
}