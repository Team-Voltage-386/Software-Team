import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.highgui.Highgui;
import org.opencv.videoio.VideoCapture;

public class CameraViewer {
	private VideoCapture capture = new VideoCapture();
	private Mat mat = new Mat();
	
	public CameraViewer() {
		capture.open(0);
		capture.set(3, 480);//width
		capture.set(4, 300);//height
	}
	public Mat grabFrame() {
		//capture.grab();
		capture.read(mat);
		return mat;
	}
	public void close() {
		capture.release();
	}
}
