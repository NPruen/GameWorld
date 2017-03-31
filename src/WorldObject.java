import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.swing.*;
import javax.imageio.ImageIO;

public abstract class WorldObject {
	//Variables
	private double x, y;
	private int width, height;
	private int speed = 4;
	
	private double rotation = 0;
	private double rotationSpeed = Math.PI/32;
	
	private static final double FORWARD = 0;
	private static final double RIGHT = Math.PI/2;
	private static final double LEFT = -Math.PI/2;
	private static final double BACKWARDS = Math.PI;
	
	private boolean remoteableObject;
	
	private String imageName;
	
	private MotionType motionType;
	
	//Objects
	private World world;
	private Canvas canvas;
	private Keyboard keyboard;
	
	private BufferedImage image;
	private Graphics g;
	private Graphics2D g2d;
	
	//Enums
	private enum Border {
		NORTH,
		EAST,
		SOUTH,
		WEST;
	}
	
	public enum MotionType {
		TankDrive,
		AllOmniwheel,
		;
	}
	
	//Constructors
	public WorldObject(int x, int y, BufferedImage image, World world) {
		this.world = world;
		this.canvas = world.getCanvas();
		this.keyboard = world.getKeyboard();
		this.x = x;
		this.y = y;
		this.rotation = 0;
		setImage(image);
		setRemotable(false);
		
		System.out.println(getClass() + " initial position: " + x + ", " + y);
//		g = image.getGraphics();
//		g2d = (Graphics2D)g.create();
	}
	
	public WorldObject(int x, int y, World world) {
		this.world = world;
		this.canvas = world.getCanvas();
		this.keyboard = this.world.getKeyboard();
		this.x = x;
		this.y = y;
		this.rotation = 0;
		
		setRemotable(false);
		
		System.out.println(getClass() + " initial position: " + x + ", " + y);
	}
	
	//Methods
	public boolean canMove(double dx, double dy) {
		if(dx > 0 && isAtEdge(Border.EAST)) {
			return false;
		}
		if(dx < 0 && isAtEdge(Border.WEST)) {
			return false;
		}
		if(dy > 0 && isAtEdge(Border.SOUTH)) {
			return false;
		}
		if(dy < 0 && isAtEdge(Border.NORTH)) {
			return false;
		}
		return true;
	}
	
	public Graphics2D getGraphics2D() {
		return g2d;
	}
	
	public double getRotation() {
		return rotation;
	}
	
	public double getX() {
		return x;
	}
	public double getY() {
		return y;
	}
	
	public BufferedImage getImage() {
		return image;
	}
	
	public boolean isAtEdge(Border border) {
		boolean result = false;
		switch(border) {
			case NORTH: {
				if(y <= 0) {
					result = true;
				}
				break;
			}
			case EAST: {
				if(x >= world.getWidth()) {
					result = true;
				}
				break;
			}
			case SOUTH: {
				if(y >= world.getHeight()) {
					result = true;
				}
				break;
			}
			case WEST: {
				if(x <= 0) {
					result = true;
				}
				break;
			}
			default: {
				System.err.println("Something went wrong in WorldObject/isAtEdge");
			}
		}
		return result;
	}
	public boolean isAtEdge() { //any edge
		return (y<=0 || x >= world.getWidth() || y >= world.getHeight() || x <= 0);
	}
	
	public boolean isRemotable() {
		return remoteableObject;
	}
	
	public void move(int vector) {
		double nextX, nextY;
		
		nextX = x + Math.cos(rotation)*vector;
		nextY = y + Math.sin(rotation)*vector;
		
		if(canMove(nextX-x, nextY-y)){
			setLocation(nextX, nextY);
			System.out.println("new position: " + x + ", " + y);
		}
	}
	
	public void move(double direction) {
		double nextX, nextY;
		
		nextX = x + Math.cos(rotation+direction)*speed;
		nextY = y + Math.sin(rotation+direction)*speed;
		
		if(canMove(nextX-x, nextY-y)){
			setLocation(nextX, nextY);
			System.out.println("new position: " + x + ", " + y);
		}
	}
	
	public void remoteControl() {
		switch(motionType) {
			case TankDrive: {
				if(keyboard.isKeyDown("w")) {
					move(speed);
				}
				if(keyboard.isKeyDown("a")) {
					rotation-=rotationSpeed;
				}
				if(keyboard.isKeyDown("s")) {
					move(-speed);
				}
				if(keyboard.isKeyDown("d")) {
					rotation+=rotationSpeed;
				}
				break;
			}
			case AllOmniwheel: {
				if(keyboard.isKeyDown("w")) {
					move(FORWARD);
				}
				if(keyboard.isKeyDown("a")) {
					move(LEFT);
				}
				if(keyboard.isKeyDown("s")) {
					move(BACKWARDS);
				}
				if(keyboard.isKeyDown("d")) {
					move(RIGHT);
				}
				if(keyboard.isKeyDown("q")) {
					rotation-=rotationSpeed;
				}
				if(keyboard.isKeyDown("e")) {
					rotation+=rotationSpeed;
				}
				break;
			}
			default: {
				break;
			}
		}
		
	}
	
	public void setImage(BufferedImage image) {
		this.image = image;
		width = this.image.getWidth();
		height = this.image.getHeight();
	}
	public void setImage(String imageName) {
		this.imageName = imageName;
		try {
			image = ImageIO.read(new File(this.imageName));
			width = this.image.getWidth();
			height = this.image.getHeight();
		} catch(IOException ex) {
			//System.err.print(ex);
		}
	}
	
	public void setLocation(double x, double y) {
		this.x = x;
		this.y = y;
	}
	
	public void setMotionType(MotionType motionType) {
		this.motionType = motionType;
	}
	
	public void setRemotable(boolean remotable) {
		remoteableObject = remotable;
	}
	
	public void setRotation(double radians) {
		this.rotation = radians;
	}
	
	public void update() {
		if(remoteableObject) {
			try {
				remoteControl();
			} catch(NullPointerException ex) {
				System.err.println(getClass() + ": " + ex);
			}
		}
	}
}