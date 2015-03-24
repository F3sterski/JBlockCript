	import java.awt.Color;
	import java.awt.image.BufferedImage;
	import java.io.File;
	import java.io.IOException;

	import javax.imageio.ImageIO;


public class BlockCript {

	public int[][] seeBMPImage(String BMPFileName) throws IOException {
	    BufferedImage image = ImageIO.read(getClass().getResource(BMPFileName));

	    int[][] array2D = new int[image.getWidth()][image.getHeight()];

	    double szerokosc = Math.ceil(image.getWidth()/3);
	    double wysokosc = Math.ceil(image.getHeight()/4);
	    
	    for(int i=0;i<(int)szerokosc*(int)wysokosc;i++){
		    for (int x = 0; x < 3; x++){
	            for (int y = 0; y < 4; y++){
	                int color = image.getRGB((int)szerokosc+x, (int)wysokosc+y);
	                if (color==Color.BLACK.getRGB()) {
	                    array2D[i][x+y] = 1;
	                } 
	                else{
	                    array2D[i][x+y] = 0;
	                }
	            }
	        }
	    }
	    return array2D;
	}
	
	public static void main(String[] args) {
			

	}

}
