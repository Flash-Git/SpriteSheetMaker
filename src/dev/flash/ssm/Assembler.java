package dev.flash.ssm;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

/**
 * Created by Flash on 21/02/2017.
 */

public class Assembler {
	
	public static BufferedImage assembleLine(BufferedImage[] images) {
		
		BufferedImage[] newImage = new BufferedImage[images.length];
		
		for(int i = 0; i < newImage.length; i++) {
			newImage[i] = insertBound(images[i]);
		}
		
		BufferedImage line = new BufferedImage(newImage.length * newImage[0].getWidth(), newImage[0].getHeight(), newImage[0].getType());
		Graphics g = line.getGraphics();
		for(int i = 0; i < newImage.length; i++) {
			g.drawImage(newImage[i], i * (newImage[0].getWidth() - 1), 0, null);
		}
		return line;
	}
	
	public static BufferedImage insertBound(BufferedImage image) {
		BufferedImage newImage = new BufferedImage(image.getWidth() + 2, image.getHeight() + 2, image.getType());
		
		Graphics g = newImage.getGraphics();
		g.drawImage(image, 1, 1, null);
		Random rand = new Random();
		g.setColor(new Color(rand.nextInt(254) + 1, rand.nextInt(254) + 1, rand.nextInt(254) + 1));
		g.drawRect(0, 0, newImage.getWidth() - 1, newImage.getHeight() - 1);
		g.setColor(new Color(0, 0, 0));
		g.drawRect(0, 0, 0, 0);
		g.drawRect(newImage.getWidth() - 1, 0, 0, 0);
		g.drawRect(0, newImage.getHeight() - 1, 0, 0);
		return newImage;
	}
	
	public static BufferedImage assembleRows(BufferedImage[] lines) {
		int longestRow = 0;
		int tallestRow = 0;
		int totalHeight = 0;
		for(int i = 0; i < lines.length; i++) {
			longestRow = (lines[i].getWidth() > longestRow) ? lines[i].getWidth() : longestRow;
			tallestRow = (lines[i].getHeight() > tallestRow) ? lines[i].getHeight() : tallestRow;
			totalHeight += lines[i].getHeight() - 1;
			//System.out.println(longestRow);
		}
		BufferedImage newImage = new BufferedImage(longestRow, totalHeight + 1, lines[0].getType());
		Graphics g = newImage.getGraphics();
		
		for(int i = 0; i < lines.length; i++) {
			int y = 0;
			for(int l = 0; l < i; l++){
				y+= lines[l].getHeight()-1;
			}
			g.drawImage(lines[i], 0, y, null);
		}
		return newImage;
	}
	
}