package imgcv.yellowcube;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import img.core.util.RectangularTarget;

public class NTPost {
	protected NetworkTableInstance root       = null;
	protected NetworkTable         table      = null;
	protected NetworkTableEntry    frameEntry = null;
	protected NetworkTableEntry    foundEntry = null;
	protected NetworkTableEntry    angleEntry = null;
	
	public NTPost(String ip) {
		root = NetworkTableInstance.getDefault();
		root.startClient(ip);
		table = NetworkTableInstance.getDefault().getTable("SmartDashboard");
		frameEntry = table.getEntry("VisFrame");
		foundEntry = table.getEntry("FoundTarget");
		angleEntry = table.getEntry("AwayAngle");
	}
	
    public void setNetworkTable(NetworkTable nt) {
    	table = nt;
    }
    
	public void postToNetwork(int fc, RectangularTarget vals) {
		frameEntry.setNumber(fc);
		foundEntry.setBoolean(vals.hasSolution());
		angleEntry.setNumber(vals.getAngleRotation());
	}
}
