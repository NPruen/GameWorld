public class Robot extends WorldObject {
	//Variables
	Alliance alliance;
	
	//Objects
	
	//Enums
	public enum Alliance {
		BLUE,
		RED;
	}
	
	//Constructors
	public Robot(int x, int y, Alliance alliance, World world) {
		super(x, y, world);
		this.alliance = alliance;
		setImage(allianceStringChooser("images/Robot_Blue_Alliance.png", "images/Robot_Red_Alliance.png"));
		setRemotable(true);
		setMotionType(MotionType.TankDrive);
	}
	
	//Methods
	public String allianceStringChooser(String blueAlliance, String redAlliance) {
		switch (alliance) {
			case BLUE: {
				return blueAlliance;
			}
			case RED: {
				return redAlliance;
			}
			default: {
				System.err.println("No alliance");
			}
		}
		return null;
	}
}