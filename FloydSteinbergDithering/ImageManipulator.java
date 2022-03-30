package FloydSteinbergDithering;

import java.awt.image.BufferedImage;

public class ImageManipulator {
	
	public static void  toGrayscale(BufferedImage img) {
		// Code from Martijn Courteaux on stackoverflow
		for(int x = 0; x < img.getWidth(); x++)
		for(int y = 0; y < img.getHeight(); y++)
		{
			int rgb = img.getRGB(x, y);
			int r = (rgb >> 16) & 0xFF;
			int g = (rgb >> 8) & 0xFF;
			int b = (rgb & 0xFF);
			
			//Normalize and gamma correct:
			double rr = Math.pow(r / 255.0, 2.2);
			double gg = Math.pow(g / 255.0, 2.2);
			double bb = Math.pow(b / 255.0, 2.2);
			
			// Calculate luminance:
			double lum = 0.2126 * rr + 0.7152 * gg + 0.0722 * bb;
			
			//Gamma compand and rescale to byte range:
			int grayLevel = (int) (255.0 * Math.pow(lum, 1.0/2.2));
			int gray = (grayLevel <<16) + (grayLevel << 8) + grayLevel;
			img.setRGB(x, y, gray);
		}
	}
	
	public static BufferedImage dither(BufferedImage img) {
		
		C3[] palette = new C3[] {
				new C3(    0,     0,     0), // Black
				new C3(255, 255, 255)  //White
		};
		
		int w = img.getWidth();
	    int h = img.getHeight();
	    
	    C3[][] d = new C3[h][w];
	    
	    for (int y = 0; y < h; y++) {
	    	for (int x = 0; x < w; x++) {
	    		d[y][x] = new C3(img.getRGB(x, y));
	    	}
	    }
	    
	    for (int y = 0; y < img.getHeight(); y++) {
	        for (int x = 0; x < img.getWidth(); x++) {

	          C3 oldColor = d[y][x];
	          C3 newColor = findClosestPaletteColor(oldColor, palette);
	          img.setRGB(x, y, newColor.toColor().getRGB());

	          C3 err = oldColor.sub(newColor);

	          if (x+1 < w)         d[y  ][x+1] = d[y  ][x+1].add(err.mul((Math.random()*7)/16));
	          if (x-1>=0 && y+1<h) d[y+1][x-1] = d[y+1][x-1].add(err.mul((Math.random()*3)/16));
	          if (y+1 < h)         d[y+1][x  ] = d[y+1][x  ].add(err.mul((Math.random()*5)/16));
	          if (x+1<w && y+1<h)  d[y+1][x+1] = d[y+1][x+1].add(err.mul((Math.random()*1)/16));
	        }
	      }

	      return img;
	}
	private static C3 findClosestPaletteColor(C3 c, C3[] palette) {
	    C3 closest = palette[0];

	    for (C3 n : palette) 
	      if (n.diff(c) < closest.diff(c))
	        closest = n;

	    return closest;
	  }
		
		/*
		
		for(int x = 1; x < img.getWidth() - 1; x++) 
		for(int y = 1; y < img.getHeight() - 1; y++) 
		{
			int oldRGB = img.getRGB(x, y);
			int oldR = (oldRGB >> 16) & 0xFF;
			int oldG = (oldRGB >> 8) & 0xFF;
			int oldB = (oldRGB & 0xFF);
			
			
			// Factor is the amount of colors + 1 which will be used in the result. factor = 1 will use either black or white.  rgb(0,0,0) = black while rgb(255,255,255) = white.
			int newR = (int) Math.round((factor * oldR)/255.0) * (255/factor);
			int newG = (int) Math.round((factor * oldG)/255.0) * (255/factor);
			int newB = (int) Math.round((factor * oldB)/255.0) * (255/factor);
			
			// Compile the new R, G and B values to one int
			Color compiledRGB = new Color(newR,newG,newB);
			int newRGB = compiledRGB.getRGB();
			
			// Compute the error between the new and old RGB values
			
			int qErrorR = Math.abs(oldR - newR);
			int qErrorG = Math.abs(oldG - newG);
			int qErrorB =	Math.abs(oldB - newB);
			
			int index = img.getRGB(x + 1, y);
			int r = (index >> 16) & 0xFF;
			int g = (index >> 8) & 0xFF;
			int b = (index & 0xFF);
			r =  r + (int) Math.round(qErrorR * 7/16.0);
			g = g + (int) Math.round(qErrorG * 7/16.0);
			b = b + (int) Math.round(qErrorB * 7/16.0);
			compiledRGB = new Color(r,g,b);
			newRGB = compiledRGB.getRGB();
			img.setRGB(x + 1, y, newRGB);
			
			index = img.getRGB(x - 1, y + 1);
			r = (index >> 16) & 0xFF;
			g = (index >> 8) & 0xFF;
			b = (index & 0xFF);
			r =  r + (int) Math.round(qErrorR * 3/16.0);
			g = g + (int) Math.round(qErrorG * 3/16.0);
			b = b + (int) Math.round(qErrorB * 3/16.0);
			compiledRGB = new Color(r,g,b);
			newRGB = compiledRGB.getRGB();
			img.setRGB(x - 1, y + 1, newRGB);
			
			index = img.getRGB(x, y + 1);
			r = (index >> 16) & 0xFF;
			g = (index >> 8) & 0xFF;
			b = (index & 0xFF);
			r =  r + (int) Math.round(qErrorR * 5/16.0);
			g = g + (int) Math.round(qErrorG * 5/16.0);
			b = b + (int) Math.round(qErrorB * 5/16.0);
			compiledRGB = new Color(r,g,b);
			newRGB = compiledRGB.getRGB();
			img.setRGB(x, y + 1, newRGB);
			
			index = img.getRGB(x +1, y + 1);
			r = (index >> 16) & 0xFF;
			g = (index >> 8) & 0xFF;
			b = (index & 0xFF);
			r =  r + (int) Math.round(qErrorR * 1/16.0);
			g = g + (int) Math.round(qErrorG * 1/16.0);
			b = b + (int) Math.round(qErrorB * 1/16.0);
			compiledRGB = new Color(r,g,b);
			newRGB = compiledRGB.getRGB();
			img.setRGB(x + 1, y + 1, newRGB);
			
		}
		*/
}
