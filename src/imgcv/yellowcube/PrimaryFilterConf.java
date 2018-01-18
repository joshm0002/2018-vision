package imgcv.yellowcube;

import org.opencv.core.Scalar;

public interface PrimaryFilterConf {
	interface Colorproc {
		// TODO: tune these
		public final int[]     COLOR_MAX = {110, 220, 170};
		public final int[]     COLOR_MIN = {90,  90,  100};
		public final boolean[] KEEP      = {true, true, true};
	}
	
	interface Target {
		public final double CUBE_HEIGHT_INCHES = 11;
		public final double CUBE_WIDTH_INCHES = 13;
	}
	
	interface Camera {
		public final double FOV_X_DEGREES       = 67; 
		public final double FOV_Y_DEGREES       = 51;
		public final double FOV_X_RADIANS       = Math.toRadians(FOV_X_DEGREES);
		public final double FOV_Y_RADIANS       = Math.toRadians(FOV_Y_DEGREES);
		public final double RESOLUTION_X_PIXELS = 800;
		public final double RESOLUTION_Y_PIXELS = 600;
	}
	
	interface Color {
		public final Scalar IN_REGION_COLOR = new Scalar(100, 255, 100);
		public final Scalar OUT_REGION_COLOR = new Scalar(150, 100, 50);
		public final Scalar CENTER_REGION_COLOR = new Scalar(100, 100, 100);
	}
}
