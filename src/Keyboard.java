import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashMap;

public class Keyboard {
	private boolean[] pressedKeys;
	private int numOfKeys = 6;
	private HashMap<String, Integer> keyMap;
	
	private World world;
	
	private KeyListener keyboard = new KeyListener() {
		@Override
		public void keyTyped(KeyEvent e) {
			
		}
		@Override
		public synchronized void keyReleased(KeyEvent e) {
			pressedKeys[keyMap.get(""+e.getKeyChar())] = false;
		}
		@Override
		public synchronized void keyPressed(KeyEvent e) {
			pressedKeys[keyMap.get(""+e.getKeyChar())] = true;
//			System.out.println(""+e.getKeyChar());
		}
	};
	
	public Keyboard(World world) {
		this.world = world;
		pressedKeys = new boolean[numOfKeys];
		for(boolean k : pressedKeys) {
			k = false;
		}
		keyMap = new HashMap<String, Integer>();
		keyMap.put("a", 0);
		keyMap.put("s", 1);
		keyMap.put("w", 2);
		keyMap.put("d", 3);
		keyMap.put("q", 4);
		keyMap.put("e", 5);
		
		try {
			this.world.getFrame().addKeyListener(keyboard);
		} catch(NullPointerException ex) {
			System.err.print(ex);
		}
	}
	
	public boolean isKeyDown(String c) {
		return pressedKeys[keyMap.get(c)];
	}
}