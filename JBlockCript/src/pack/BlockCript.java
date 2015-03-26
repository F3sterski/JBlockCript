package pack;
	import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
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
	    
	    int[][] array2D = new int[(int)(szerokosc*wysokosc)][CryptX*CryptY];
	    int pozycja=0;
	    int pozx=0,pozy=0;
	    
	    for(int i=0;i<(int)szerokosc*(int)wysokosc;i++){
		    for (int y = 0; y < CryptY; y++){
	            for (int x = 0; x < CryptX; x++){
	                int color = image.getRGB(CryptX*pozx+x, CryptY*pozy+y);
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
	                if (color==0) {
	                    image.setRGB(CryptX*pozx+x,CryptY*pozy+y,Color.WHITE.getRGB());
	                } 
	                else{
	                	image.setRGB(CryptX*pozx+x,CryptY*pozy+y,Color.BLACK.getRGB());
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
	    
	    ImageIO.write(image, "bmp", new File("saved.bmp"));
	}
	
	public static char[] key;
	
	private static void ReadKey(){
        try {
    		
            FileReader fileReader = new FileReader("key.txt");
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line = bufferedReader.readLine();
            key = line.toCharArray();
            
            if(key.length>25){
	        	System.err.println("Niepoprawny klucz");
	        	System.exit(0);
            }
            
            for(int i=0;i<key.length;i++){
    			if ((key[i] < 'a' || key[i] > 'z')){
    				throw new IllegalArgumentException("Niepoprawny znak w kluczu");
    			}
            }
            
            bufferedReader.close();
            
    	}
	    catch(FileNotFoundException ex) {
	        System.err.println("Unable to open file 'key.txt'");   
	        ex.printStackTrace();
	        System.exit(0);
	    }
	    catch(IOException ex) {
	        System.err.println("Error reading file 'key.txt'");  
	        ex.printStackTrace();
	        System.exit(0);
	    }
	    catch(NullPointerException ex){
	    	ex.printStackTrace();
	    	System.exit(0);
	    }
	}
	
	private static int[] CryptLine(int[] BeforeLine){
		int i=0;
		int[] AfterLine= new int[BeforeLine.length];
		for(int j=0;j<BeforeLine.length;j++){
			AfterLine[j]=((((BeforeLine[j])+((int)key[i]))%2));
			i++;
			i%=key.length;
		}
		return AfterLine;
	}
	
	
	
	public static void main(String[] args) throws IOException {
		System.out.println("Start");
		ReadKey();
		
		BlockCript BC = new BlockCript(1,20);
		int[][] image = BC.BMPImagetoArray();
		
		for(int j=0;j<image.length;j++){
			image[j]=CryptLine(image[j]);
		}
		
		BC.BMPArraytoImage(image);
		System.out.println("Ready");
	}

}
