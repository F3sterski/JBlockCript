package pack;
	import java.awt.Color;
	import java.awt.image.BufferedImage;
	import java.io.File;
	import java.io.IOException;

	import javax.imageio.ImageIO;


public class BlockCript {

	public int[][] BMPImage() throws IOException {
	    BufferedImage image = ImageIO.read(new File("plain.bmp"));

	    double szerokosc = Math.ceil(image.getWidth()/3);
	    double wysokosc = Math.ceil(image.getHeight()/4);
	    
	    int[][] array2D = new int[(int)(szerokosc*wysokosc)][12];


	    
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
	
	public static void main(String[] args) throws IOException {
		
		BlockCript BC = new BlockCript();
		
		int[][] image = BC.BMPImage();
		
		System.out.println(image);

	}

}
