
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
//import com.github.sarxos.webcam.Webcam;

public class HoughLines {
	static Mat mat;
	static Mat invMat;
	static BufferedImage image;
	//public static Webcam webcam;
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
			Core.inRange(mat, new Scalar(20, 100, 105), new Scalar(40, 255, 255), mat);
			Imgproc.dilate(mat, mat, dilateElement);
//			Imgproc.dilate(mat, mat, dilateElement);
			Imgproc.erode(mat, mat, erodeElement);
//			Imgproc.erode(mat, mat, erodeElement);
//			Imgproc.dilate(mat, mat, dilateElement);
			ImageIO.write(matToBufferedImage(mat), "PNG", new File("mat.png"));
			Mat backdrop = new Mat(new Size(mat.width(), mat.height()), CvType.CV_8UC1);
			mat.copyTo(backdrop);
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
			Imgproc.Canny(grey, edges, 100, 200);
			
			//Imgproc.dilate(edges, edges, dilateElement);
			//Imgproc.erode(edges, edges, erodeElement);

			Mat colorEdges = new Mat();
			Mat lines = new Mat(new Size(mat.width(), mat.height()), CvType.CV_8UC3);
			//Mat vectors = new Mat();
			Mat contours = new Mat();
			List<MatOfPoint> goodContours = new ArrayList<>();
			Mat hierarchy = new Mat();
//			Mat corners = new Mat();
//			Imgproc.cornerHarris(grey, corners, 9, 5, .1);
//			System.out.println(corners.cols() + ", " + corners.get(0, 0).length);
//			for( int j = 0; j < corners.rows() ; j++){
//		        for( int i = 0; i < corners.cols(); i++){
//		            if (corners.get(j,i)[0] > .01){
//		        		System.out.println(i + ", " + j + " : " + corners.get(j,i)[0]);
//		                Imgproc.circle(base, new Point(i,j), 5 , new Scalar(255, 255, 255), 2 ,8 , 0);
//		                j += 1;
//		            }
//		        }
//		    }
			Mat lineMat = new Mat(new Size(mat.width(), mat.height()), CvType.CV_8UC1);
			
			Imgproc.HoughLinesP(edges, contours, mat.width() / mat.height() , Math.PI/180, 1);
			System.out.println(contours.rows());
			for (int i = 0; i < contours.rows(); i++) {
		        double data[] = contours.get(i, 0);
//		        double rho1 = data[0];
//		        double theta1 = data[1];
//		        Boolean skip = false;
//		        for(int j = 0; j < i; j++) {
//		        	if(Math.abs(contours.get(j,  0)[0] - rho1) < 30 && Math.abs(contours.get(j,  0)[1] - theta1) < Math.PI/15) {
//		        		System.out.println(Math.abs(contours.get(j,  0)[0] - rho1));
//		        		contours.put(i, 0, 0, 0);
//		        		skip = true;
//		        		break;
//		        	}
//		        }
//		        if(!skip) {
//		        double cosTheta = Math.cos(theta1);
//		        double sinTheta = Math.sin(theta1);
//		        double x0 = cosTheta * rho1;
//		        double y0 = sinTheta * rho1;
//		        Point pt1 = new Point(x0 + 10000 * (-sinTheta), y0 + 10000 * cosTheta);
//		        Point pt2 = new Point(x0 - 10000 * (-sinTheta), y0 - 10000 * cosTheta);
		        //Imgproc.line(base, pt1, pt2, new Scalar(0, 0, 255), 1);
//		        Imgproc.line(lineMat, pt1, pt2, new Scalar(255), 5);
		        Imgproc.line(lineMat, new Point(data[0], data[1]), new Point(data[2], data[3]), new Scalar(255));
		        }
//		    }
			//Core.bitwise_and(lineMat.inv(), backdrop, lineMat);
			//Imgproc.dilate(lineMat, lineMat, Imgproc.getStructuringElement(Imgproc.MORPH_RECT, new Size(9, 9)));
			//Imgproc.erode(lineMat, lineMat, Imgproc.getStructuringElement(Imgproc.MORPH_RECT, new Size(9, 9)));
			for(int r = 0; r < base.rows(); r++) {
				for(int c = 0; c < base.cols(); c++) {
					//System.out.println(mat.get(r, c)[0]);
					if(lineMat.get(r, c)[0] == 255){
						mat.put(r, c, new double[] {0});
					}
				}
			}
			Imgproc.erode(mat, mat, Imgproc.getStructuringElement(Imgproc.MORPH_RECT, new Size(30, 30)));
			//Imgproc.HoughLinesP(edges, vectors, 1, Math.PI/180, 10);
			ImageIO.write(matToBufferedImage(mat), "PNG", new File("lines.png"));
			List<MatOfPoint> finalContours = new ArrayList<MatOfPoint>();
			//Imgproc.findContours(lineMat.submat(new Rect(0, 0, lineMat.width(), lineMat.height()-75)), finalContours, hierarchy, Imgproc.RETR_TREE, Imgproc.CHAIN_APPROX_SIMPLE);
//			for(int r = 0; r < base.rows(); r++) {
//				for(int c = 0; c < base.cols(); c++) {
//					//System.out.println(mat.get(r, c)[0]);
//					if(mat.get(r, c)[0] == 0){
//						lineMat.put(r, c, new double[] {0, 0, 0});
//					}
//				}
//			}
			Imgproc.findContours(mat, finalContours, hierarchy, Imgproc.RETR_TREE, Imgproc.CHAIN_APPROX_SIMPLE);
			List<RotatedRect> rects = new ArrayList<>();

			for (int i=0; i<finalContours.size(); i++)
		    {
				Imgproc.drawContours(base, finalContours, i,new Scalar(255, 255, 255));
				RotatedRect rect = Imgproc.minAreaRect(new MatOfPoint2f(finalContours.get(i).toArray()));
				if(rect.size.height > 20 && rect.size.width > 20 && rect.size.width < mat.width())
					rects.add(rect);
		    }
			for(RotatedRect r : rects) {
				Point[] vertices = new Point[4];
			    r.points(vertices);
			    MatOfPoint points = new MatOfPoint(vertices);
			    Imgproc.drawContours(base, Arrays.asList(points), -1, new Scalar(255, 255, 255), 9);
				//Imgproc.rectangle(lines, new Point(r.x, r.y), new Point(r.x + r.width, r.y + r.height), new Scalar(255, 255, 255));
			}
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
			ImageIO.write(matToBufferedImage(base), "PNG", new File("rectangles.png"));
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
