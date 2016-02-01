package org.usfirst.frc.team3164.robot.vission;

import java.util.Comparator;
import java.util.Vector;

import org.usfirst.frc.team3164.robot.Robot;

import com.ni.vision.NIVision;
import com.ni.vision.NIVision.IMAQdxCameraControlMode;
import com.ni.vision.NIVision.Image;
import com.ni.vision.NIVision.ImageType;

import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Camera {
	private class ParticleReport implements Comparator<ParticleReport>, Comparable<ParticleReport> {
		double PercentAreaToImageArea;
		double Area;
		double BoundingRectLeft;
		double BoundingRectTop;
		double BoundingRectRight;
		double BoundingRectBottom;

		public int compareTo(ParticleReport r) {
			return (int) (r.Area - this.Area);
		}

		public int compare(ParticleReport r1, ParticleReport r2) {
			return (int) (r1.Area - r2.Area);
		}
	};

	private class Scores {
		double Area;
		double Aspect;
	};

	private Image frame;
	private Image binaryFrame;
	private int imaqError;
	private NIVision.Range TOTE_HUE_RANGE = new NIVision.Range(130, 180); // Default
																			// hue
																			// //100,140
	// range for
	// yellow
	// tote
	private NIVision.Range TOTE_SAT_RANGE = new NIVision.Range(90, 255); // Default
																			// //20,225
	// saturation
	// range for
	// yellow
	// tote
	private NIVision.Range TOTE_VAL_RANGE = new NIVision.Range(90, 300); // Default
																			// //100,300
	// value
	// range for
	// yellow
	// tote
	private double AREA_MINIMUM = 0.5; // Default Area minimum for particle as a
	// percentage of total image area
	private double LONG_RATIO = 2.22; // Tote long side = 26.9 / Tote height =
										// 12.1 =
	// 2.22
	private double SHORT_RATIO = 1.4; // Tote short side = 16.9 / Tote height =
										// 12.1 =
	// 1.4
	private double SCORE_MIN = 50.0; // Minimum score to be considered a tote
	private double VIEW_ANGLE = 60; // View angle fo camera, set to Axis m1011
									// by
	// default, 64 for m1013, 51.7 for 206, 52 for
	// HD3000 square, 60 for HD3000 640x480
	private NIVision.ParticleFilterCriteria2 criteria[] = new NIVision.ParticleFilterCriteria2[1];
	private NIVision.ParticleFilterOptions2 filterOptions = new NIVision.ParticleFilterOptions2(0, 0, 1, 1);
	private Scores scores = new Scores();
	private int session;

	public Camera() {
		// create images
		// frame = NIVision.imaqCreateImage(ImageType.IMAGE_RGB, 0);TODO
		binaryFrame = NIVision.imaqCreateImage(ImageType.IMAGE_U8, 0);
		criteria[0] = new NIVision.ParticleFilterCriteria2(NIVision.MeasurementType.MT_AREA_BY_IMAGE_AREA, AREA_MINIMUM,
				100.0, 0, 0);

		// TODO
		frame = NIVision.imaqCreateImage(NIVision.ImageType.IMAGE_RGB, 0);

		// the camera name (ex "cam0") can be found through the roborio web
		// interface
		session = NIVision.IMAQdxOpenCamera("cam0",IMAQdxCameraControlMode.CameraControlModeController);
		// NIVision.IMAQdxCameraControlMode.CameraControlModeController);
		
		//TODO Figure out what this needs to be
		
		//session = Robot.instance.camses;
		//session = 0;
		
		// TODO

		// Put default values to SmartDashboard so fields will appear
		SmartDashboard.putNumber("Tote hue min", TOTE_HUE_RANGE.minValue);
		SmartDashboard.putNumber("Tote hue max", TOTE_HUE_RANGE.maxValue);
		SmartDashboard.putNumber("Tote sat min", TOTE_SAT_RANGE.minValue);
		SmartDashboard.putNumber("Tote sat max", TOTE_SAT_RANGE.maxValue);
		SmartDashboard.putNumber("Tote val min", TOTE_VAL_RANGE.minValue);
		SmartDashboard.putNumber("Tote val max", TOTE_VAL_RANGE.maxValue);
		SmartDashboard.putNumber("Area min %", AREA_MINIMUM);

		onSetupCode();
	}

	private CameraSearchThread thr = null;

	private void onSetupCode() {
		thr = new CameraSearchThread();
		thr.start();
	}

	public void stopWatcher() {
		if (thr != null) {
			thr.kill();
			thr = null;
		}
	}

	public void stopWatcherWait() {
		if (thr != null) {
			thr.kill();
			while (thr.isAlive()) {
			}
			thr = null;
		}
	}

	public void restartWatcher() {
		if (thr == null) {
			thr = new CameraSearchThread();
			thr.start();
		}
	}

	private static int ITERATIONS_WITH_TOTE = 10;// 100
	private static int ITERATIONS_LOST_TOTE = 5;

	private class CameraSearchThread extends Thread {
		private boolean kill = false;

		public CameraSearchThread() {
			
		}

		public void kill() {
			kill = true;
			// NIVision.IMAQdxCloseCamera(session);
		}

		private int foundFor = 0;
		private int lostFor = 0;

		@Override
		public void run() {
			while (!kill) {
				System.out.println("Test");
				// read file in from disk. For this example to run you need to
				// copy
				// image.jpg from the SampleImages folder to the
				// directory shown below using FTP or SFTP:
				// http://wpilib.screenstepslive.com/s/4485/m/24166/l/282299-roborio-ftp
				// NIVision.imaqReadFile(frame,
				// "/home/lvuser/SampleImages/image.jpg");TODO
				NIVision.IMAQdxGrab(session, frame, 1);

				// Update threshold values from SmartDashboard. For performance
				// reasons it is recommended to remove this after calibration is
				// finished.
				TOTE_HUE_RANGE.minValue = (int) SmartDashboard.getNumber("Tote hue min", TOTE_HUE_RANGE.minValue);
				TOTE_HUE_RANGE.maxValue = (int) SmartDashboard.getNumber("Tote hue max", TOTE_HUE_RANGE.maxValue);
				TOTE_SAT_RANGE.minValue = (int) SmartDashboard.getNumber("Tote sat min", TOTE_SAT_RANGE.minValue);
				TOTE_SAT_RANGE.maxValue = (int) SmartDashboard.getNumber("Tote sat max", TOTE_SAT_RANGE.maxValue);
				TOTE_VAL_RANGE.minValue = (int) SmartDashboard.getNumber("Tote val min", TOTE_VAL_RANGE.minValue);
				TOTE_VAL_RANGE.maxValue = (int) SmartDashboard.getNumber("Tote val max", TOTE_VAL_RANGE.maxValue);

				// Threshold the image looking for yellow (tote color)
				NIVision.imaqColorThreshold(binaryFrame, frame, 255, NIVision.ColorMode.HSV, TOTE_HUE_RANGE,
						TOTE_SAT_RANGE, TOTE_VAL_RANGE);

				// Send particle count to dashboard
				int numParticles = NIVision.imaqCountParticles(binaryFrame, 1);
				SmartDashboard.putNumber("Masked particles", numParticles);

				// Send masked image to dashboard to assist in tweaking mask.
				CameraServer.getInstance().setImage(binaryFrame);

				// filter out small particles
				float areaMin = (float) SmartDashboard.getNumber("Area min %", AREA_MINIMUM);
				criteria[0].lower = areaMin;
				imaqError = NIVision.imaqParticleFilter4(binaryFrame, binaryFrame, criteria, filterOptions, null);
				
				// Send particle count after filtering to dashboard
				numParticles = NIVision.imaqCountParticles(binaryFrame, 1);
				SmartDashboard.putNumber("Filtered particles", numParticles);

				if (numParticles > 0) {
					// Measure particles and sort by particle size
					Vector<ParticleReport> particles = new Vector<ParticleReport>();
					for (int particleIndex = 0; particleIndex < numParticles; particleIndex++) {
						ParticleReport par = new ParticleReport();
						par.PercentAreaToImageArea = NIVision.imaqMeasureParticle(binaryFrame, particleIndex, 0,
								NIVision.MeasurementType.MT_AREA_BY_IMAGE_AREA);
						par.Area = NIVision.imaqMeasureParticle(binaryFrame, particleIndex, 0,
								NIVision.MeasurementType.MT_AREA);
						par.BoundingRectTop = NIVision.imaqMeasureParticle(binaryFrame, particleIndex, 0,
								NIVision.MeasurementType.MT_BOUNDING_RECT_TOP);
						par.BoundingRectLeft = NIVision.imaqMeasureParticle(binaryFrame, particleIndex, 0,
								NIVision.MeasurementType.MT_BOUNDING_RECT_LEFT);
						par.BoundingRectBottom = NIVision.imaqMeasureParticle(binaryFrame, particleIndex, 0,
								NIVision.MeasurementType.MT_BOUNDING_RECT_BOTTOM);
						par.BoundingRectRight = NIVision.imaqMeasureParticle(binaryFrame, particleIndex, 0,
								NIVision.MeasurementType.MT_BOUNDING_RECT_RIGHT);
						particles.add(par);
					}
					particles.remove(null);

					// This example only scores the largest particle. Extending
					// to
					// score all particles and choosing the desired one is left
					// as
					// an exercise
					// for the reader. Note that this scores and reports
					// information
					// about a single particle (single L shaped target). To get
					// accurate information
					// about the location of the tote (not just the distance)
					// you
					// will need to correlate two adjacent targets in order to
					// find
					// the true center of the tote.
					scores.Aspect = AspectScore(particles.elementAt(0));
					SmartDashboard.putNumber("Aspect", scores.Aspect);
					scores.Area = AreaScore(particles.elementAt(0));
					SmartDashboard.putNumber("Area", scores.Area);
					boolean isTote = scores.Aspect > SCORE_MIN && scores.Area > SCORE_MIN;

					// Send distance and tote status to dashboard. The bounding
					// rect, particularly the horizontal center (left - right)
					// may
					// be useful for rotating/driving towards a tote
					SmartDashboard.putBoolean("IsTote", isTote);
					SmartDashboard.putNumber("Distance", computeDistance(binaryFrame, particles.elementAt(0)));

					if (isTote) {
						if (foundFor == ITERATIONS_WITH_TOTE) {
							//cb.call();
							kill();
						}
						foundFor++;
					} else {
						if (lostFor == ITERATIONS_LOST_TOTE) {
							foundFor = 0;
						}
						lostFor++;
					}

				} else {
					SmartDashboard.putBoolean("IsTote", false);
				}

				//TODO add the next line into
//				Timer.waitMillis(5);
			}
		}
	}

	@SuppressWarnings("unused")
	private static boolean CompareParticleSizes(ParticleReport particle1, ParticleReport particle2) {
		// we want descending sort order
		return particle1.PercentAreaToImageArea > particle2.PercentAreaToImageArea;
	}

	/**
	 * Converts a ratio with ideal value of 1 to a score. The resulting function
	 * is piecewise linear going from (0,0) to (1,100) to (2,0) and is 0 for all
	 * inputs outside the range 0-2
	 */
	private double ratioToScore(double ratio) {
		return (Math.max(0, Math.min(100 * (1 - Math.abs(1 - ratio)), 100)));
	}

	private double AreaScore(ParticleReport report) {
		double boundingArea = (report.BoundingRectBottom - report.BoundingRectTop)
				* (report.BoundingRectRight - report.BoundingRectLeft);
		// Tape is 7" edge so 49" bounding rect. With 2" wide tape it covers 24"
		// of the rect.
		return ratioToScore((49 / 24) * report.Area / boundingArea);
	}

	/**
	 * Method to score if the aspect ratio of the particle appears to match the
	 * retro-reflective target. Target is 7"x7" so aspect should be 1
	 */
	private double AspectScore(ParticleReport report) {
		return ratioToScore(((report.BoundingRectRight - report.BoundingRectLeft)
				/ (report.BoundingRectBottom - report.BoundingRectTop)));
	}

	/**
	 * Computes the estimated distance to a target using the width of the
	 * particle in the image. For more information and graphics showing the math
	 * behind this approach see the Vision Processing section of the
	 * ScreenStepsLive documentation.
	 * 
	 * @param image
	 *            The image to use for measuring the particle estimated
	 *            rectangle
	 * @param report
	 *            The Particle Analysis Report for the particle
	 * @param isLong
	 *            Boolean indicating if the target is believed to be the long
	 *            side of a tote
	 * @return The estimated distance to the target in feet.
	 */
	private double computeDistance(Image image, ParticleReport report) {
		double normalizedWidth, targetWidth;
		NIVision.GetImageSizeResult size;

		size = NIVision.imaqGetImageSize(image);
		normalizedWidth = 2 * (report.BoundingRectRight - report.BoundingRectLeft) / size.width;
		targetWidth = 7;

		return targetWidth / (normalizedWidth * 12 * Math.tan(VIEW_ANGLE * Math.PI / (180 * 2)));
	}
}