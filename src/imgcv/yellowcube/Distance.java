package imgcv.yellowcube;

import img.core.PolygonCv;
import img.core.util.ScalarColors;
import img.core.filters.Blur;
import img.core.filters.ColorRange;
import img.core.filters.ColorSpace;
import img.core.filters.Dilate;
import img.core.filters.Erode;
import img.core.filters.MatFilter;
import img.core.filters.Sequence;
import img.core.util.RectangularTarget;

public class Distance extends Filter implements MatFilter{

	
	public static double getDistance(PolygonCv poly) {
		
		double xpix = poly.getWidth();
		double ypix = poly.getHeight();
		double dist;
		double realdist=0;
		
		/*
		 * TODO
		 *check da maths
		 *didn't try too hard to verify valid vals
		 *fix line 31 maths
		*/
		
		if(1.1*xpix < ypix && ypix < 1.4*xpix) {
			dist = (double) (5.5/Math.tan(xpix*.0019));
			realdist=dist;
		}
		if (.8*xpix < ypix && ypix < 1.1*xpix) {
			dist = (double) (7.5/Math.tan(xpix*.0019));
			realdist=dist;		
		}
		if(.63*xpix < ypix && ypix < .8*xpix) {
			dist = (double) (5.5/Math.tan(xpix*.0019));
			realdist = (double) (((dist+.64131)/.650674)-8.368); 
		}
		
		if (.5*xpix < ypix && ypix < .63*xpix) {
			dist = (double) (7.5/Math.tan(xpix*.0019));
			realdist = (double) (((dist+.904963)/.71684)-9.1924);
		}
		return realdist;
	}
}
	