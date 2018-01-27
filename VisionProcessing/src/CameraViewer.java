import org.opencv.core.Mat;
import org.opencv.videoio.VideoCapture;

public class CameraViewer {
	private VideoCapture capture = new VideoCapture();
	private Mat mat = new Mat();
	
	public CameraViewer() {
		capture.open(0);
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
