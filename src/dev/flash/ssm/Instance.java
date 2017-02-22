package dev.flash.ssm;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Flash on 21/02/2017.
 */

public class Instance {
	
	public static void init() {
		
		final File folder = new File("res/SpriteSheet");
		ArrayList<String> files = getFilesForFolder(folder);
		ArrayList<String[]> lines = new ArrayList<>();
		ArrayList<String> anims = new ArrayList<>();
		for(String file : files) {//all files
			if(file.contains("00")) {
				if(anims.size() > 0) {
					lines.add(getLine(anims));
					anims.clear();
					anims.add(file);
				} else {
					anims.add(file);
				}
			} else {
				anims.add(file);
			}
		}
		lines.add(getLine(anims));
		
		BufferedImage lineImages[] = new BufferedImage[lines.size()];
		for(int b = 0; b < lines.size(); b++) {
			String[] pathArray = lines.get(b);
			BufferedImage[] images = new BufferedImage[pathArray.length];
			for(int i = 0; i < images.length; i++) {
				images[i] = ImageLoader.loadImage("/SpriteSheet/" + pathArray[i]);
			}
			lineImages[b] = Assembler.assembleLine(images);
		}
		
		BufferedImage sheet = Assembler.assembleRows(lineImages);
		
		try {
			ImageIO.write(sheet, "png", new File("res/Spritesheet.png"));
		} catch(IOException e) {
			e.printStackTrace();
		}
		
		System.out.println("FINISHED WITHOUT ERROR");
	}
	
	public static String[] getLine(ArrayList<String> strings) {
		String[] line = new String[strings.size()];
		for(int i = 0; i < strings.size(); i++) {
			line[i] = strings.get(i);
		}
		return line;
	}
	
	public static ArrayList<String> getFilesForFolder(final File folder) {
		ArrayList<String> strings = new ArrayList<>();
		for(final File fileEntry : folder.listFiles()) {
			if(fileEntry.isDirectory()) {
				getFilesForFolder(fileEntry);
			} else {
				strings.add(fileEntry.getName());
			}
		}
		return strings;
	}
	
}