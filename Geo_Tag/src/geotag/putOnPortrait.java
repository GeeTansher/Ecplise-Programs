package geotag;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.highgui.HighGui;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

public class putOnPortrait {
	static String newLine = System.getProperty("line.separator");
	final static String text= "Palri, Uttar Pradesh, India";
	final static String text1= "9G5M+53W, Shamli Rd, Palri, Uttar Pradesh"
			+ newLine
			+ "251318, India"
			+ newLine
			+ "Lat 29.358261°"
			+ newLine
			+ "Long 77.532764°"
			+ newLine
			+ "30/10/22 03:05 PM GMT+05:30";

	public static void main(String[] args) {
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
		System.out.println("hello");
		final String[] texts = text1.split(newLine, 5);
		Mat image = Imgcodecs.imread("images/mainImg.jpg");
		int width = 1080;
	    int height = 1920;
	    Size scaleSize = new Size(width,height);
	    Imgproc.resize(image, image, scaleSize , 0, 0, Imgproc.INTER_AREA);
	    
	    
	    Mat overlay = image.clone();
//	    Rectangle parameters
	    int x= 320, y =  height-350, w=755, h=330;
	    int x1= 720, y1 =  height-405, w1=355, h1=60;
	    
	    
	    Imgproc.rectangle (overlay,new Point(x, y),new Point(x+w, y+h),new Scalar(0, 0, 0),-1);
	    Imgproc.rectangle (overlay,new Point(x1, y1),new Point(x1+w1, y1+h1),new Scalar(0, 0, 0),-1);
	    
	    overlay = puttext(overlay, 768, height-370, 
	    		"GPS Map Camera",24, Imgproc.FONT_HERSHEY_SIMPLEX, 4);
	    overlay = puttext(overlay, 335, height-300, text, 30, Imgproc.FONT_HERSHEY_SIMPLEX, 6);
	    int extra = 0;
	    for(String s : texts) {
	    	overlay = puttext(overlay, 335, height-250+extra, s, 20, Imgproc.FONT_HERSHEY_SIMPLEX, 3);
	    	extra+=50;
	    }
	    	
	    
	    double alpha = 0.5;  // Transparency factor
	    Mat image_new = new Mat();
	    Core.addWeighted(overlay, alpha, image, 1-alpha, 0, image_new);
	    
	    image_new = putimg(image_new, Imgcodecs.imread("images/small gps.jpg"),25,25 ,730, height-395);
	    image_new = putimg(image_new, Imgcodecs.imread("images/big map.png"), 314, 330, 0, height-350);
	    
	    
	    
	    Imgcodecs.imwrite("images/main.jpg", image_new);
//	    HighGui.imshow("Adding two images", image_new);
//	    HighGui.waitKey(0);
	    
	}

	    		
	private static Mat puttext(Mat overlay, int x, int y, String text, int size, int font, int thickness) {
		Imgproc.putText(overlay, text, new Point(x,y), font, 
				Imgproc.getFontScaleFromHeight(font, (int) size), new Scalar(255,255,255), thickness);
		return overlay;
	}


	private static Mat putimg(Mat image_new, Mat img, int w, int h, int x, int y) {
		Size scaleSize = new Size(w,h);
	    Imgproc.resize(img, img, scaleSize, Imgproc.INTER_AREA);
	    Rect roi= new Rect(x,y,w,h);
	    img.copyTo( new Mat(image_new,roi) );
		return image_new;
	}

}
