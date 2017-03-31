public class Helios extends Robot {
	Helios(int x, int y, Robot.Alliance alliance, World world) {
		super(x, y, alliance, world);
		setImage(allianceStringChooser("images/Helios_Blue_Alliance.png", "images/Helios_Red_Alliance.png"));
		setMotionType(MotionType.AllOmniwheel);
	}
}