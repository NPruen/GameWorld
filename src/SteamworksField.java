public class SteamworksField extends World {
	private static int fieldLength = 800, fieldWidth = 500;
	
	private Robot red1, blue1;
	private Block block;
	
	public SteamworksField() {
		super(fieldLength, fieldWidth, 1, "FRC Steamworks");
		
		block = new Block(50, 100, this);
		red1 = new Helios(0, fieldWidth/2, Robot.Alliance.RED, this);
		blue1 = new Robot(fieldLength, fieldWidth/2, Robot.Alliance.BLUE, this);
		
		addObject(block);
		addObject(red1);
		addObject(blue1);
		blue1.setRemotable(false);
	}
	
}