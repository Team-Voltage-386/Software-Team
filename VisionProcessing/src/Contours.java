
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
import org.opencv.video.KalmanFilter;
import org.opencv.videoio.VideoCapture;
import com.github.sarxos.webcam.Webcam;

public class Contours {
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
			
			
			Mat edges = new Mat();
			Imgproc.Canny(grey, edges, 60, 230);
			
			//Imgproc.dilate(edges, edges, Imgproc.getStructuringElement(Imgproc.MORPH_RECT, new Size(9, 9)));
			//Imgproc.erode(edges, edges, Imgproc.getStructuringElement(Imgproc.MORPH_RECT, new Size(9, 9)));

			Mat colorEdges = new Mat();
			Mat lines = new Mat(new Size(mat.width(), mat.height()), CvType.CV_8UC3);
			//Mat vectors = new Mat();
			List<MatOfPoint> contours = new ArrayList<>();
			List<MatOfPoint> goodContours = new ArrayList<>();
			Mat hierarchy = new Mat();
			Imgproc.findContours(edges, contours, hierarchy, Imgproc.RETR_TREE, Imgproc.CHAIN_APPROX_SIMPLE,new Point(0,0) );
			for (int i=0; i<contours.size(); i++) {
			    if ((true)) {   //has parent, inner (hole) contour of a closed edge (looks good)
			    	Imgproc.drawContours(lines, contours, i, new Scalar(rng.nextInt(25)*10,rng.nextInt(25)*10,rng.nextInt(25)*10), 1);
					goodContours.add(contours.get(i));
			    }
			}   	//contours.remove(i);

			for(MatOfPoint points : contours) {
				for(int y = 10; y < points.height()-10; y++) {
					double[] point = points.get(y, 0);
					double[] nextPoint = points.get(y + 10, 0);
					double[] prevPoint = points.get(y - 10, 0);
					if(Math.abs(Math.atan((point[1] - prevPoint[1])/ (point[0] - prevPoint[0])) - Math.atan((point[1] - nextPoint[1])/ (point[0] - nextPoint[0]))) > Math.PI/2.1) {
						Imgproc.circle(lines, new Point(point[0], point[1]), 5 , new Scalar(255, 255, 255), 2 ,8 , 0);
						System.out.println(point[0] + ", " + point[1]);
					}
				}
			}
			ImageIO.write(matToBufferedImage(lines), "PNG", new File("lines.png"));
			List<RotatedRect> rects = new ArrayList<>();

			for (int i=0; i<goodContours.size(); i++)
		    {
				RotatedRect rect = Imgproc.minAreaRect(new MatOfPoint2f(goodContours.get(i).toArray()));
				if(rect.size.height > 20 && rect.size.width > 20)
					rects.add(rect);
		    }
			for(RotatedRect r : rects) {
				Point[] vertices = new Point[4];
			    r.points(vertices);
			    MatOfPoint points = new MatOfPoint(vertices);
			    Imgproc.drawContours(lines, Arrays.asList(points), -1, new Scalar(255, 255, 255), 9);
				//Imgproc.rectangle(lines, new Point(r.x, r.y), new Point(r.x + r.width, r.y + r.height), new Scalar(255, 255, 255));
			}
			//Imgproc.HoughLinesP(edges, vectors, 1, Math.PI/180, 10);
			//ImageIO.write(matToBufferedImage(lines), "PNG", new File("lines.png"));
			/*for(int c = 0; c < lines.width(); c++) {
				for(int r = 0; r < lines.height(); r++) {
					System.out.println(r + ", " + c + ", " + lines.get(r, c));
				}
			}*/
			/*for(int c = 0; c < vectors.width(); c++) {
				for(int r = 0; r < vectors.height(); r++) {
					System.out.println(r + ", " + c + ", " + vectors.get(r, c)[0]);
					Imgproc.line(lines, new Point(vectors.get(r, c)[0], vectors.get(r, c)[1]),  new Point(vectors.get(r, c)[2], vectors.get(r, c)[3]), new Scalar(255));
				}
			}*/
			ImageIO.write(matToBufferedImage(lines), "PNG", new File("rectangles.png"));
			edges.copyTo(colorEdges);
			Imgproc.cvtColor(colorEdges, colorEdges, Imgproc.COLOR_GRAY2BGRA);
			//step 2
			Scalar newColor= new Scalar(0,255,0);    //this will be green
			colorEdges.setTo(newColor, edges);
			//step 3
			colorEdges.copyTo(mat, colorEdges);
			ImageIO.write(matToBufferedImage(mat), "PNG", new File("corners.png"));
			System.out.println("Done");
		//}
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
