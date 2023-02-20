package geotag;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

public class camera2 {
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
//		int width = 556;
//	    int height = 930;
//	    Size scaleSize = new Size(width,height);
//	    Imgproc.resize(image, image, scaleSize , 0, 0, Imgproc.INTER_AREA);
		
		double width = image.width();
		double height = image.height();
	    
	    
	    Mat overlay = image.clone();
//	    # Rectangle parameters
//	    int x= 160, y =  height-150, w=385, h=140;
//	    int x1= 455, y1 =  height-164, w1=90, h1=13;
	    double x= (width-((width*2.3)/3)), y =  (height-((height*1)/10)), w=(((width*2.3)/3)-((width*0.13)/3)), h=((height*1)/10)-10; //((height*0.13)/10)
	    double x1= (width-((width*2)/10)), y1 =  (height-((height*1.2)/10)), w1=((width*2.3)/10)-((width*0.13)/3), h1=((height*1.2)/10)-10;
	    
	    
	    Imgproc.rectangle (overlay,new Point(x, y),new Point(x+w, y+h),new Scalar(0, 0, 0),-1);
	    Imgproc.rectangle (overlay,new Point(x1, y1),new Point(x1+w1, y1+h1),new Scalar(0, 0, 0),-1);
	    
	    overlay = puttext(overlay, x1+((w1*=0.9)/10) + h1-((height*1)/10), y1+((h1-((height*1)/10))), 
	    		"GPS Map Camera",8, Imgproc.FONT_HERSHEY_SIMPLEX, 0);
	    overlay = puttext(overlay, 170, 805, text, 15, Imgproc.FONT_HERSHEY_SIMPLEX, 2);
	    int extra = 0;
	    for(String s : texts) {
	    	overlay = puttext(overlay, 170, 830+extra, s, 9, Imgproc.FONT_HERSHEY_SIMPLEX, 0);
	    	extra+=20;
	    }
	    	
	    
	    double alpha = 0.7;  // Transparency factor
	    Mat image_new = new Mat();
	    Core.addWeighted(overlay, alpha, image, 1-alpha, 0, image_new);
	    
	    image_new = putimg(image_new, Imgcodecs.imread("images/small gps.jpg"),
	    		h1-((height*1)/10), h1-((height*1)/10), x1+((w1*0.5)/10), y1+((h1-((height*1.1)/10))));
	    image_new = putimg(image_new, Imgcodecs.imread("images/big map.png"),
	    		h, h, width-((width*2.96)/3), height-((height*1)/10));
	    
	    
	    
	    Imgcodecs.imwrite("images/main.jpg", image_new);
//	    HighGui.imshow("Adding two images", image_new);
//	    HighGui.waitKey(0);
	    
	}

	    		
	private static Mat puttext(Mat overlay, double x, double y, String text, double size, int font, int thickness) {
		Imgproc.putText(overlay, text, new Point(x,y), font, 
				Imgproc.getFontScaleFromHeight(font, (int) size), new Scalar(255,255,255), thickness);
		return overlay;
	}


	private static Mat putimg(Mat image_new, Mat img, double w, double h, double x, double y) {
		Size scaleSize = new Size(w,h);
	    Imgproc.resize(img, img, scaleSize, Imgproc.INTER_AREA);
	    double[] vals = {x,y,img.width(),img.height()};
	    Rect roi= new Rect(vals);
	    img.copyTo( new Mat(image_new,roi) );
		return image_new;
	}

}
