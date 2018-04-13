package imgcv.yellowcube;

import org.opencv.core.Scalar;
import org.opencv.core.Size;

public interface PrimaryFilterConf {
	interface Procimg {
		// TODO: tune these
		// color limits
		public final int[]     COLOR_MAX = {100, 190, 190};
		public final int[]     COLOR_MIN = {80, 150, 120};
		public final boolean[] KEEP      = {true, true, true};
		
		// dilate/erode
		public final int  DILATE_FACTOR  = 7;
		public final int  ERODE_FACTOR   = 5;
		// !!!WEBCAM ONLY!!!
		public final Size WC_BLUR_FACTOR = new Size(7, 7);
	}
	
	interface Constraints {
		public final double POLYGON_EPSILON = 6.0;
		
		public final double POLYGON_SZ_MAX = 50;
		public final double POLYGON_SZ_MIN = 4;
		
		public final double POLYGON_H_MAX = 10;
		public final double POLYGON_W_MAX = 10;
		
		public final double POLYGON_RAT_MIN = 50;
		public final double POLYGON_RAT_MAX = 300;
	}
	
	interface Target {
		public final double CUBE_HEIGHT_INCHES = 11;
		public final double CUBE_WIDTH_INCHES  = 13;
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
		public final Scalar IN_REGION_COLOR     = new Scalar(100, 255, 100);
		public final Scalar OUT_REGION_COLOR    = new Scalar(150, 100, 50);
		public final Scalar CENTER_REGION_COLOR = new Scalar(100, 100, 100);
	}
}
