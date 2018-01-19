package imgcv.yellowcube;

import java.util.ArrayList;
import java.util.List;

import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Point;
import org.opencv.imgproc.Imgproc;

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

public class PrimaryFilter extends Filter implements MatFilter, PrimaryFilterConf {
	/*
	 * TODO:
	 * - add variables and tune stuff
	 * - look through PinkSquare algo and see if there's anywhere I need to refactor
	 */
	
	private Sequence sequence;
	private RectangularTarget _Finder;
	public PrimaryFilter() {
		sequence = createSequence();
		
		// TODO: fov tune for laptop webcam (or bust out the AXIS...)
		_Finder = new RectangularTarget(Target.CUBE_WIDTH_INCHES, Target.CUBE_HEIGHT_INCHES, 
				Camera.RESOLUTION_X_PIXELS, Camera.RESOLUTION_Y_PIXELS, 44.136); 
	}

	public static Sequence createSequence() {
		Sequence sequence = new Sequence();
		sequence.addFilter(ColorSpace.createRGBtoHSV());
		sequence.addFilter(new ColorRange(Procimg.COLOR_MIN, Procimg.COLOR_MAX, Procimg.KEEP));
		sequence.addFilter(new Erode(Procimg.ERODE_FACTOR));
		sequence.addFilter(new Dilate(Procimg.DILATE_FACTOR));
		sequence.addFilter(new Blur(Procimg.BLUR_FACTOR));
		return sequence;
	}
	
	// TODO: make this not DoNothingFilter
	public Mat process(Mat srcImage) {
		int hImg = srcImage.rows();
		int wImg = srcImage.cols();
		int imgMid = hImg / 2;
		Mat output = srcImage;

		Mat copy = srcImage.clone();
		Mat d1 = sequence.process(copy);

		// Keep a clean copy of binary 1/0 matrix as find contours does the
		// other stuff
		Mat binary = d1.clone();

		// Uncomment to use BW image as output to draw on
		// Imgproc.cvtColor(d1, output, Imgproc.COLOR_GRAY2BGR);

		List<MatOfPoint> contours = new ArrayList<>();
		List<PolygonCv> polygons = new ArrayList<>();
		List<PolygonCv> rejects = new ArrayList<>();

		float maxWidth = -1;

		Mat heirarchy = new Mat();
		Imgproc.findContours(d1, contours, heirarchy, Imgproc.RETR_LIST,
				Imgproc.CHAIN_APPROX_SIMPLE);
		int n = contours.size();
		for (int i = 0; i < n; i++) {
			MatOfPoint contour = contours.get(i);
			// Hmmm, can we do a quick check on contour height/width before
			// trying to extract polygon?
			PolygonCv poly = PolygonCv.fromContour(contour, 6.0);
			int pts = poly.size();
			float h = poly.getHeight();
			float w = poly.getWidth();
			int hw = (int) (w > 0 ? h / w * 100 : 0);
			float distFromTop = poly.getMinY();
			float distFromMid = imgMid - (distFromTop + h);

			if ((w > 10) && (h > 10) && (hw > 50) && (hw < 300) && (pts >= 4)
					&& (pts <= 16)) {
				Point leftBot = new Point();
				Point leftTop = new Point();
				poly.findLeftEdge(leftBot, leftTop, 0.15);

				Point rightBot = new Point();
				Point rightTop = new Point();
				poly.findRightEdge(rightBot, rightTop, 0.15);

				double leftHeight = Math.abs(leftTop.y - leftBot.y);
				double rightHeight = Math.abs(rightTop.y - rightBot.y);
				double heightRatio = (leftHeight > 1) ? rightHeight
						/ leftHeight : 0;
				if (leftHeight >= 10 && rightHeight >= 10
						&& (heightRatio > 0.75) && (heightRatio < 1.25)
						&& holeCheck(binary, poly)) {
					polygons.add(poly);
					if (w > maxWidth) {
						maxWidth = w;
					}
					if (_Debug) {
						System.out
								.println("Accepted: sides: " + pts + " ("
										+ poly.getWidth() + ", "
										+ poly.getHeight() + ")  H/W: " + hw
										+ "  distFromTop: " + distFromTop
										+ "  distFromMid: " + distFromMid);

					}
				} else {
					if (_Debug) {
						rejects.add(poly);

						System.out
								.println("Rejected: left height: " + leftHeight
										+ "  right height: " + rightHeight);
					}
				}

			} else if (_Debug) {
				rejects.add(poly);

				System.out.println("Rejected: sides: " + pts + " ("
						+ poly.getWidth() + ", " + poly.getHeight()
						+ ")  H/W: " + hw + "  distFromTop: " + distFromTop
						+ "  distFromMid: " + distFromMid);
			}
		}

		int pCnt = polygons.size();
		PolygonCv[] pArr = new PolygonCv[pCnt];
		polygons.toArray(pArr);

		// If debugging, draw all of the rejected polygons
		if (_Debug) {
			for (PolygonCv p : rejects) {
				p.draw(output, ScalarColors.YELLOW, 1);
			}
		}

		// Not searching for pairs, just add contours for all of the polygons
		PolygonCv good = null;

		for (int i = 0; i < pCnt; i++) {
			PolygonCv p = pArr[i];
			float w = p.getWidth();
			if ((w == maxWidth) && (good == null)) {
				p.draw(output, ScalarColors.GREEN, 1);
				p.drawInfo(output, ScalarColors.GREEN);

				_Finder.setImageSize(wImg, hImg);
				if (_Finder.computeSolution(p)) {
					good = p;
				}
				if (_Debug) {
					System.out.println(_Finder);
				}
			} else {
				p.draw(output, ScalarColors.ORANGE, 1);
			}
		}

		// Draw details about target (if found)
		if (good != null) {
			_Finder.drawVerticalLines(output);
			_Finder.drawCrossHair(output);
			_Finder.drawCamInfo(output);
			_Finder.drawRobotInfo(output);
			_Finder.drawWallInfo(output);
		}

		return output;
	}
}
