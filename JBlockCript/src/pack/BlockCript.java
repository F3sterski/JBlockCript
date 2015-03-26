package pack;
	import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.imageio.ImageIO;


public class BlockCript {
	
	public int CryptX;
	public int CryptY;
	private double szerokosc;
	private double wysokosc;
	
	public BlockCript(int x,int y){
		this.CryptX=x;
		this.CryptY=y;
	}
	
	public int[][] BMPImagetoArray() throws IOException {
		
	    BufferedImage image = ImageIO.read(new File("plain.bmp"));
	    
	    szerokosc = Math.floor(image.getWidth()/CryptX);
	    wysokosc = Math.floor(image.getHeight()/CryptY);
	    
	    System.out.println((int)szerokosc+" "+(int)wysokosc);
	    
	    int[][] array2D = new int[(int)(szerokosc*wysokosc)][CryptX*CryptY];
	    int pozycja=0;
	    int pozx=0,pozy=0;
	    
	    for(int i=0;i<(int)szerokosc*(int)wysokosc;i++){
		    for (int y = 0; y < CryptY; y++){
	            for (int x = 0; x < CryptX; x++){
	                int color = image.getRGB(CryptX*pozx+x, CryptY*pozy+y);
	            	System.out.println((CryptX*pozx+x)+ " "+ (CryptY*pozy+y) + " "+ color);
	                if (color==Color.BLACK.getRGB()) {
	                    array2D[i][pozycja%(CryptX*CryptY)] = 1;
	                } 
	                else{
	                    array2D[i][pozycja%(CryptX*CryptY)] = 0;
	                }
	                pozycja++;
	            }
	        }
	    	if(pozx<szerokosc) pozx++;
	    	if(pozx==szerokosc){
	    		pozy++;
	    		pozx=0;
	    	}
	    }
	    return array2D;
	}
	
	public void BMPArraytoImage(int[][] imageArray) throws IOException {


	    BufferedImage image = new BufferedImage((int)szerokosc*CryptX, (int)wysokosc*CryptY, BufferedImage.TYPE_INT_RGB);
	    int pozycja=0;
	    int pozx=0,pozy=0;
	    
	    for(int i=0;i<(int)szerokosc*(int)wysokosc;i++){
		    for (int y = 0; y < CryptY; y++){
	            for (int x = 0; x < CryptX; x++){
	                int color = imageArray[i][pozycja%(CryptX*CryptY)];
	            	System.out.print(color);
	                if (color==0) {
	                    image.setRGB(CryptX*pozx+x,CryptY*pozy+y,Color.WHITE.getRGB());
	                } 
	                else{
	                	image.setRGB(CryptX*pozx+x,CryptY*pozy+y,Color.BLACK.getRGB());
	                }
	                pozycja++;
	            }
	        }
		    System.out.println();
	    	if(pozx<szerokosc) pozx++;
	    	if(pozx==szerokosc){
	    		pozy++;
	    		pozx=0;
	    	}
	    }
	    
	    ImageIO.write(image, "bmp", new File("saved.bmp"));
	}
	
	
	public static void main(String[] args) throws IOException {
		
		BlockCript BC = new BlockCript(3,4);
		
		int[][] image = BC.BMPImagetoArray();
		
			for(int i=0;i<4;i++){
				for(int j=0;j<12;j++){
					System.out.print(image[i][j]);
				}
				System.out.println();
			}
		BC.BMPArraytoImage(image);
	}

}
