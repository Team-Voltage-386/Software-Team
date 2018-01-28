
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.Vector;

import javax.imageio.ImageIO;

import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.core.MatOfPoint;
import org.opencv.core.MatOfPoint2f;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.RotatedRect;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.videoio.VideoCapture;
import com.github.sarxos.webcam.Webcam;

public class Corners {
	static Mat mat;
	static Mat invMat;
	static BufferedImage image;
	public static Webcam webcam;
	public static Mat hierarchy;
	public static VideoCapture camera;
	public static CameraViewer viewer;
	public static Random rng = new Random();
	public static void main(String[] args) throws IOException {
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
		/*webcam = Webcam.getDefault();
		if (webcam != null) {
			System.out.println("Webcam: " + webcam.getName());
		} else {
			System.out.println("No webcam detected");
		}*/
		viewer = new CameraViewer();
		start();
		
	}
	public static MatOfPoint2f approxCurve;
	/**
	 * @throws IOException
	 */
	public static void start() throws IOException {
		//while(true) {
			System.out.println("Im in");
			Mat base = new Mat();
			
			base = viewer.grabFrame();//viewer.grabFrame();
			viewer.close();
			System.out.println(base.toString());
			Mat mat = new Mat();
			Imgproc.blur(base, mat, new Size(9, 9));
			Mat dilateElement = Imgproc.getStructuringElement(Imgproc.MORPH_RECT, new Size(20, 20));
			Mat erodeElement = Imgproc.getStructuringElement(Imgproc.MORPH_RECT, new Size(20, 20));

			Imgproc.cvtColor(mat, mat, Imgproc.COLOR_BGR2HSV);
			Core.inRange(mat, new Scalar(20, 130, 130), new Scalar(40, 255, 255), mat);
//			Imgproc.erode(mat, mat, erodeElement);
//			Imgproc.dilate(mat, mat, dilateElement);
			ImageIO.write(matToBufferedImage(mat), "PNG", new File("mat.png"));
			for(int r = 0; r < base.rows(); r++) {
				for(int c = 0; c < base.cols(); c++) {
					//System.out.println(mat.get(r, c)[0]);
					if(mat.get(r, c)[0] == 0){
						base.put(r, c, new double[] {0, 0, 0});
					}
				}
			}
			System.out.println("We good");
			ImageIO.write(matToBufferedImage(base), "PNG", new File("backAgain.png"));
			Mat grey = new Mat();
			Imgproc.cvtColor(base, grey, Imgproc.COLOR_BGR2GRAY);
		    ImageIO.write(matToBufferedImage(mat), "PNG", new File("FirstMat.png"));
			/*Mat dilateElement = Imgproc.getStructuringElement(Imgproc.MORPH_RECT, new Size(24, 24));
			Mat erodeElement = Imgproc.getStructuringElement(Imgproc.MORPH_RECT, new Size(24, 24));*/
			
			
			Mat corners = new Mat();
			Imgproc.cornerHarris(grey, corners, 9, 5, .1);
			System.out.println(corners.cols() + ", " + corners.get(0, 0).length);
			for( int j = 0; j < corners.rows() ; j++){
		        for( int i = 0; i < corners.cols(); i++){
		            if (corners.get(j,i)[0] > .1){
		        		System.out.println(i + ", " + j + " : " + corners.get(j,i)[0]);
		                Imgproc.circle(base, new Point(i,j), 5 , new Scalar(255, 255, 255), 2 ,8 , 0);
		                j += 1;
		            }
		        }
		    }
			ImageIO.write(matToBufferedImage(base), "PNG", new File("Circles.png")); //Imgproc.cornerHarris(7, dst, blockSize, ksize, k);
	}
	public static Mat bufferedImageToMat(BufferedImage bi) {
		  Mat mat = new Mat(bi.getHeight(), bi.getWidth(), CvType.CV_8UC1);
		  byte[] data = ((DataBufferByte) bi.getRaster().getDataBuffer()).getData();
		  mat.put(0, 0, data);
		  return mat;
		} 
	
	private static BufferedImage matToBufferedImage(Mat original) throws IOException
	{
		MatOfByte mob=new MatOfByte();
	    Imgcodecs.imencode(".jpg", original, mob);
	    byte ba[]=mob.toArray();

	    BufferedImage bi=ImageIO.read(new ByteArrayInputStream(ba));
	    return bi;
	}
	public static byte[][] matToByte(Mat m) {
	    //first index is pixel, second index is channel
	    int numChannels=m.channels();//is 3 for 8UC3 (e.g. RGB)
	    int frameSize=m.rows()*m.cols();
	    byte[] byteBuffer= new byte[frameSize*numChannels];
	    m.get(0,0,byteBuffer);

	    //write to separate R,G,B arrays
	    byte[][] out=new byte[frameSize][numChannels];
	    for (int p=0,i = 0; p < frameSize; p++) {
	        for (int n = 0; n < numChannels; n++,i++) {
	            out[p][n]=byteBuffer[i];
	            System.out.println(p + ", " + n+ ", " + byteBuffer[i]);
	        }
	    }
	    return out;
	}
	
}
