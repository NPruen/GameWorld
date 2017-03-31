import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

import java.util.List;
import java.util.ArrayList;

import javax.swing.*;

public class Canvas extends JPanel {
	//Variables
	private int cellSize;
	private int mouseX, mouseY;
	private boolean mousePressed;
	private boolean mouseClicked;
	
	//Objects
	private BufferedImage image;
	private Graphics g;
	private List<WorldObject> objects;
	private World world;
	private MouseListener mouse = new MouseListener() {
		@Override
		public void mouseReleased(MouseEvent e) {
			mousePressed = false;
		}
		@Override
		public void mousePressed(MouseEvent e) {
			mousePressed = true;
		}
		@Override
		public void mouseExited(MouseEvent e) {
			
		}
		@Override
		public void mouseEntered(MouseEvent e) {
			
		}
		@Override
		public void mouseClicked(MouseEvent e) {
//			mouseClicked = true;
		}
	};
	private MouseMotionListener mouseMotion = new MouseMotionListener() {
		@Override
		public void mouseMoved(MouseEvent e) {
			mouseX = e.getX();
			mouseY = e.getY();
		}
		@Override
		public void mouseDragged(MouseEvent e) {
			
		}
	};
	
	//Constructor
	public Canvas(int width, int height, World world) {
		this.world = world;
		this.cellSize = this.world.getCellSize();
		
		objects = new ArrayList<WorldObject>();
		
		image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		g = image.getGraphics();
		
		addMouseListener(mouse);
		addMouseMotionListener(mouseMotion);
		
		setPreferredSize(new Dimension(width, height));
	}
	
	//Methods
	public int getMouseX() {
		return mouseX;
	}
	public int getMouseY() {
		return mouseY;
	}
	
	public boolean mousePressed() {
		try {
			return mousePressed;
		} catch(Exception e) {
			System.err.println(e);
		}
		return false;
	}
	public boolean mouseClicked() {
		try {
			return mouseClicked;
		} catch(Exception e) {
			System.err.println(e);
		}
		return false;
	}
	
	
	public void drawObjects(List<WorldObject> objects) {
		this.objects = objects;
		repaint();
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		Graphics2D g2d = (Graphics2D)g.create();
		
		for(WorldObject obj : objects) {
			BufferedImage img = obj.getImage();
			if(obj.isRemotable()) {
				g2d.translate((int)obj.getX(), (int)obj.getY());
				g2d.rotate(obj.getRotation());
				g2d.translate(-(int)obj.getX(), -(int)obj.getY());
			}
			if(img != null) {
				g2d.drawImage(img, (int)obj.getX() - obj.getImage().getWidth()/2, (int)obj.getY()-obj.getImage().getHeight()/2, null);
			} else {
				System.out.println("picture not found");
			}
		}
	}
}
