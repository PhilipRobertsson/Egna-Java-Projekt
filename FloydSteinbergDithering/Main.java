package FloydSteinbergDithering.Java.FloydSteinbergDithering;

import java.io.*;

import javax.imageio.ImageIO;

import java.awt.image.BufferedImage;

public class Main {

	public static void main(String[] args) throws IOException{
		String filePath = System.getProperty("user.dir");
		System.out.println(filePath);
		String input;
		BufferedReader b = new BufferedReader(new InputStreamReader(System.in));
		BufferedImage img = null;
		File newFile;
		// Load Image
		try {
			do {
				System.out.print("Type filename: ");
				input = b.readLine();
				input = filePath + "\\" + input;
				newFile = new File(input);
			}while(!newFile.exists());
			img = ImageIO.read(new File(input));
		}catch(IOException e) {
			System.out.println(e.getMessage());
		}
		//Manipulate Image
		System.out.println(img.toString());
		ImageManipulator.toGrayscale(img);
		img = ImageManipulator.floydSteinberg(img);
		
		//Save Image
		try {
			File outputFile = new File("saved.jpg");
			ImageIO.write(img, "jpg", outputFile);
		}catch(IOException e) {
			System.out.println(e.getMessage());
		}
	}

}
