package dev.flash.ssm;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Created by Flash on 21/02/2017.
 */

public class Assembler {
	
	public static BufferedImage assembleLine(BufferedImage[] images) {
		for(int i = 0; i<images.length; i++){
			images[i] = insertBound(images[i]);
		}
			
		
		BufferedImage line = new BufferedImage(images.length * images[0].getWidth(), images[0].getHeight(), images[0].getType());
		Graphics g = line.getGraphics();
		for(int i = 0; i < images.length; i++) {
			g.drawImage(images[i], i * images[0].getWidth(), 0, null);
		}
		return line;
	}
	
	public static BufferedImage insertBound(BufferedImage image){
		BufferedImage newImage = new BufferedImage(image.getWidth()+1, image.getHeight()+1, image.getType());
		
		Graphics g = newImage.getGraphics();
		g.drawImage(image, 1, 1, newImage.getWidth(), newImage.getHeight(), null);
		return newImage;
	}
	
	
	
}
