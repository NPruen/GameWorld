import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

import javax.swing.*;

public class World {
	//Variables
	private int width, height, cellSize;
	
	private int speed;
	
	private List<WorldObject> objects;
	private List<WorldObject> objectsToRemove;
	
	//Objects
	private JFrame frame;
	private Canvas canvas;
	private Keyboard keyboard;
	
	private ActionListener clock = new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent arg0) {
			update();
		}
	};
	
	private Timer timer;
	
	//Constructors
	public World(int width, int height, int cellSize, String worldName) {
		this.width = width;
		this.height = height;
		this.cellSize = cellSize;
		
		canvas = new Canvas(width, height, this);
		
		objects = new ArrayList<WorldObject>();
		
		frame = new JFrame(worldName);
		keyboard = new Keyboard(this);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(width+15, height+30);
		frame.add(canvas);
		frame.setVisible(true);
		
		speed = 25;
		timer = new Timer(speed, clock);
		timer.start();
	}
	
	//Methods
	public void addObject(WorldObject object) {
		objects.add(object);
		System.out.println(objects.toString());
	}
	
	public Canvas getCanvas() {
		return canvas;
	}
	
	public int getCellSize() {
		return cellSize;
	}
	
	public JFrame getFrame() {
		return frame;
	}
	
	public Keyboard getKeyboard() {
		return keyboard;
	}
	
	public int getWidth() {
		return width;
	}
	public int getHeight() {
		return height;
	}
	
	public void removeObject(WorldObject object) {
		objectsToRemove.add(object);
	}
	
	private void removeObjectsToRemove() {
		try {
			if(objectsToRemove.size() > 0) {
				for(WorldObject obj : objectsToRemove) {
					objects.remove(obj);
				}
				objectsToRemove = new ArrayList<WorldObject>(0);
			}
		} catch(NullPointerException ex) {
			
		}
	}
	
	public void setSpeed(int speed) {
		this.speed = speed;
		timer.stop();
		timer = new Timer(speed, clock);
		timer.start();
	}
	
	public void start() {
		if(!timer.isRunning()) {
			timer.start();
		}
	}
	public void stop() {
		if(timer.isRunning()) {
			timer.stop();
		}
	}
	
	public void update() {
		updateObjects();
		removeObjectsToRemove();
		canvas.drawObjects(objects);
	}
	
	public void updateObjects() {
		for(WorldObject object : objects) {
			object.update();
		}
	}
}