package it.polito.teaching.cv;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.MatOfPoint2f;
import org.opencv.core.Point;
import org.opencv.core.RotatedRect;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;
import org.opencv.videoio.VideoCapture;

import it.polito.elite.teaching.cv.utils.Utils;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * The controller associated with the only view of our application. The
 * application logic is implemented here. It handles the button for
 * starting/stopping the camera, the acquired video stream, the relative
 * controls and the image segmentation process.
 * 
 * @author <a href="mailto:luigi.derussis@polito.it">Luigi De Russis</a>
 * @version 2.0 (2017-03-10)
 * @since 1.0 (2015-01-13)
 * 
 */
public class ObjRecognitionController {
    // FXML camera button
    @FXML
    private Button cameraButton;
    // the FXML area for showing the current frame
    @FXML
    private ImageView originalFrame;
    // the FXML area for showing the mask
    @FXML
    private ImageView maskImage;
    // the FXML area for showing the output of the morphological operations
    @FXML
    private ImageView morphImage;
    // FXML slider for setting HSV ranges
    @FXML
    private Slider hueStart;
    @FXML
    private Slider hueStop;
    @FXML
    private Slider saturationStart;
    @FXML
    private Slider saturationStop;
    @FXML
    private Slider valueStart;
    @FXML
    private Slider valueStop;
    // FXML label to show the current values set with the sliders
    @FXML
    private Label hsvCurrentValues;

    // a timer for acquiring the video stream
    private ScheduledExecutorService timer;
    // the OpenCV object that performs the video capture
    private VideoCapture capture = new VideoCapture();
    // a flag to change the button behavior
    private boolean cameraActive;

    // property for object binding
    private ObjectProperty<String> hsvValuesProp;

    /**
     * The action triggered by pushing the button on the GUI
     */
    @FXML
    private void startCamera() {
	// bind a text property with the string containing the current range of
	// HSV values for object detection
	hsvValuesProp = new SimpleObjectProperty<>();
	this.hsvCurrentValues.textProperty().bind(hsvValuesProp);

	// set a fixed width for all the image to show and preserve image ratio
	this.imageViewProperties(this.originalFrame, 400);
	this.imageViewProperties(this.maskImage, 200);
	this.imageViewProperties(this.morphImage, 200);

	if (!this.cameraActive) {
	    // start the video capture
	    this.capture.open(0);

	    // is the video stream available?
	    if (this.capture.isOpened()) {
		this.cameraActive = true;

		// grab a frame every 33 ms (30 frames/sec)
		Runnable frameGrabber = new Runnable() {

		    @Override
		    public void run() {
			// effectively grab and process a single frame
			Mat frame = grabFrame();
			// convert and show the frame
			Image imageToShow = Utils.mat2Image(frame);
			updateImageView(originalFrame, imageToShow);
		    }
		};

		this.timer = Executors.newSingleThreadScheduledExecutor();
		this.timer.scheduleAtFixedRate(frameGrabber, 0, 33, TimeUnit.MILLISECONDS);

		// update the button content
		this.cameraButton.setText("Stop Camera");
	    } else {
		// log the error
		System.err.println("Failed to open the camera connection...");
	    }
	} else {
	    // the camera is not active at this point
	    this.cameraActive = false;
	    // update again the button content
	    this.cameraButton.setText("Start Camera");

	    // stop the timer
	    this.stopAcquisition();
	}
    }

    /**
     * Get a frame from the opened video stream (if any)
     * 
     * @return the {@link Image} to show
     */

    private Mat grabFrame() {
	Mat frame = new Mat();
	List<RotatedRect> rects = new ArrayList<>();
	Mat hierarchy = new Mat(), mat = new Mat(), image = new Mat();
	List<MatOfPoint> finalContours = new ArrayList<>();
	Mat base = new Mat();
	Mat grey = new Mat();
	Mat edges = new Mat();
	// check if the capture is open
	if (this.capture.isOpened()) {

	    try {
		// read the current frame

		this.capture.read(base);

		// if the frame is not empty, process it
		if (true) {
		    // init

		    Size blurSize = new Size(9, 9);
		    Scalar colorStart = new Scalar(this.hueStart.getValue(), this.saturationStart.getValue(),
			    this.valueStart.getValue());
		    Scalar colorEnd = new Scalar(this.hueStop.getValue(), this.saturationStop.getValue(),
			    this.valueStop.getValue());
		    Size erodeSize = new Size(10, 10);
		    Size dilateSize = new Size(10, 10);
		    Size edgeDilateSize = new Size(4, 4);

		    // remove some noise
		    Imgproc.blur(base, mat, blurSize);
		    // Mat dilateElement = Imgproc.getStructuringElement(Imgproc.MORPH_RECT, new
		    // Size(20, 20));
		    // Mat erodeElement = Imgproc.getStructuringElement(Imgproc.MORPH_RECT, new
		    // Size(20, 20));

		    Imgproc.cvtColor(mat, mat, Imgproc.COLOR_BGR2HSV);
		    /*
		     * SmartDashboard.putNumber("Lower Left 0", mat.get(0, 0)[0]);
		     * SmartDashboard.putNumber("Lower Left 1", mat.get(0, 0)[1]);
		     * SmartDashboard.putNumber("Lower Left 2", mat.get(0, 0)[2]);
		     */
		    Core.inRange(mat, colorStart, colorEnd, mat);
		    // Imgproc.dilate(mat, mat, dilateElement);
		    // Imgproc.erode(mat, mat, erodeElement);

		    // for (int r = 0; r < base.rows(); r++) {
		    // for (int c = 0; c < base.cols(); c++) {
		    // if (mat.get(r, c)[0] == 0) {
		    // base.put(r, c, new double[] { 0, 0, 0 });
		    // }
		    // }
		    // }
		    Imgproc.erode(mat, mat, Imgproc.getStructuringElement(Imgproc.MORPH_RECT, erodeSize));
		    Imgproc.dilate(mat, mat, Imgproc.getStructuringElement(Imgproc.MORPH_RECT, dilateSize));
		    Image imageToShow = Utils.mat2Image(mat);
		    updateImageView(maskImage, imageToShow);
		    Imgproc.cvtColor(base, grey, Imgproc.COLOR_BGR2GRAY);
		    // Core.multiply(grey, new Scalar(3), grey);
		    Imgproc.Canny(grey, edges, 100, 200);

		    Imgproc.dilate(edges, edges, Imgproc.getStructuringElement(Imgproc.MORPH_RECT, edgeDilateSize));

		    Imgproc.erode(edges, edges, Imgproc.getStructuringElement(Imgproc.MORPH_RECT, new Size(3, 3)));
		    // new Size(20, 20)));
		    imageToShow = Utils.mat2Image(edges);
		    updateImageView(morphImage, imageToShow);
		    Core.bitwise_not(edges, edges);
		    Core.bitwise_and(mat, edges, mat);

		    finalContours.clear();
		    Imgproc.findContours(mat, finalContours, hierarchy, Imgproc.RETR_TREE, Imgproc.CHAIN_APPROX_SIMPLE);
		    rects.clear();
		    for (int i = 0; i < finalContours.size(); i++) {
			RotatedRect rect = Imgproc.minAreaRect(new MatOfPoint2f(finalContours.get(i).toArray()));
			if (rect.size.height > 10 && rect.size.width > 10 /*
									   * && rect.size.width / rect.size.height > .6
									   * && rect.size.width / rect.size.height < 1.5
									   */ && rect.angle < 10)
			    rects.add(rect);
		    }
		    double closest = -1;
		    int smallestI = 0;
		    for (int i = 0; i < rects.size(); i++) {
			if (rects.get(i).size.height * rects.get(i).size.height < closest) {
			    closest = rects.get(i).size.height * rects.get(i).size.height;
			    smallestI = i;
			}
		    }

		    for (int i = 0; i < rects.size(); i++) {
			Point[] vertices = new Point[4];
			rects.get(i).points(vertices);
			MatOfPoint points = new MatOfPoint(vertices);
			if (i != smallestI)
			    Imgproc.drawContours(base, Arrays.asList(points), -1, new Scalar(255, 255, 255), 5);
			else
			    Imgproc.drawContours(base, Arrays.asList(points), -1, new Scalar(0, 255, 0), 5);
		    }
		}

	    } catch (Exception e) {
		// log the (full) error
		System.err.print("Exception during the image elaboration...");
		e.printStackTrace();
	    }
	}

	return base;
    }

    /**
     * Given a binary image containing one or more closed surfaces, use it as a mask
     * to find and highlight the objects contours
     * 
     * @param maskedImage
     *            the binary image to be used as a mask
     * @param frame
     *            the original {@link Mat} image to be used for drawing the objects
     *            contours
     * @return the {@link Mat} image with the objects contours framed
     */
    private Mat findAndDrawBalls(Mat maskedImage, Mat frame) {
	// init
	List<MatOfPoint> contours = new ArrayList<>();
	Mat hierarchy = new Mat();

	// find contours
	Imgproc.findContours(maskedImage, contours, hierarchy, Imgproc.RETR_CCOMP, Imgproc.CHAIN_APPROX_SIMPLE);

	// if any contour exist...
	if (hierarchy.size().height > 0 && hierarchy.size().width > 0) {
	    // for each contour, display it in blue
	    for (int idx = 0; idx >= 0; idx = (int) hierarchy.get(0, idx)[0]) {
		Imgproc.drawContours(frame, contours, idx, new Scalar(250, 0, 0));
	    }
	}

	return frame;
    }

    /**
     * Set typical {@link ImageView} properties: a fixed width and the information
     * to preserve the original image ration
     * 
     * @param image
     *            the {@link ImageView} to use
     * @param dimension
     *            the width of the image to set
     */
    private void imageViewProperties(ImageView image, int dimension) {
	// set a fixed width for the given ImageView
	image.setFitWidth(dimension);
	// preserve the image ratio
	image.setPreserveRatio(true);
    }

    /**
     * Stop the acquisition from the camera and release all the resources
     */
    private void stopAcquisition() {
	if (this.timer != null && !this.timer.isShutdown()) {
	    try {
		// stop the timer
		this.timer.shutdown();
		this.timer.awaitTermination(33, TimeUnit.MILLISECONDS);
	    } catch (InterruptedException e) {
		// log any exception
		System.err.println("Exception in stopping the frame capture, trying to release the camera now... " + e);
	    }
	}

	if (this.capture.isOpened()) {
	    // release the camera
	    this.capture.release();
	}
    }

    /**
     * Update the {@link ImageView} in the JavaFX main thread
     * 
     * @param view
     *            the {@link ImageView} to update
     * @param image
     *            the {@link Image} to show
     */
    private void updateImageView(ImageView view, Image image) {
	Utils.onFXThread(view.imageProperty(), image);
    }

    /**
     * On application close, stop the acquisition from the camera
     */
    protected void setClosed() {
	this.stopAcquisition();
    }

}
