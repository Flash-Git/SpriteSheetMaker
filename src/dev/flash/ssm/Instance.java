package dev.flash.ssm;

import java.awt.*;
import java.awt.image.BufferStrategy;
import java.io.IOException;

/**
 * Created by Flash on 21/02/2017.
 */

public class Instance implements Runnable {
	
	//display handles JFrame
	private Display display;
	private BufferStrategy bs;
	private Graphics g;
	
	//Total ticks
	public static int tickCount;
	
	//Instance variables
	private int width, height;
	private String title;
	private boolean running;
	private Thread thread;
	private int fps;
	
	//Input
	
	public Instance(String title, int width, int height) {
		this.title = title;
		this.width = width;
		this.height = height;
	}
	
	private void init() {
		//Create window
		display = new Display(title, width, height);
		/*
		display.getFrame().addKeyListener(keyManager);
		display.getFrame().addMouseListener(mouseManager);
		display.getFrame().addMouseWheelListener(mouseManager);
		display.getFrame().addMouseMotionListener(mouseManager);
		display.getCanvas().addMouseListener(mouseManager);
		display.getCanvas().addMouseWheelListener(mouseManager);
		display.getCanvas().addMouseMotionListener(mouseManager);
		*/
		//Load the game's assets
		/*try {
			//Assets.init();
		} catch(IOException e) {
			System.out.println("UNABLE TO LOAD ASSETS");
			System.out.println(e.getCause());
		}*/
		
	}
	

	private void tick(double delta) {
	}
	
	private void render() {
		//Draw frames before displaying them
		bs = display.getCanvas().getBufferStrategy();
		if(bs == null) {
			display.getCanvas().createBufferStrategy(2);//amount of stored up frames ready before pushing to screen
			return;
		}
		g = bs.getDrawGraphics();
		
		//Clear Screen
		g.clearRect(0, 0, getWidth(), getHeight());
		g.setColor(new Color(20, 20, 20));
		g.fillRect(0, 0, getWidth(), getHeight());
		g.setColor(Color.WHITE);
		
		//Draw Here
		
		//End Draw
		bs.show();
		g.dispose();
	}
	
	@Override
	public void run() {
		init();
		int targetFps = 154;
		double timePerTick = 1000000000 / targetFps;
		double delta = 0;
		long now;
		long lastTime = System.nanoTime();
		long timer = 0;
		int ticks = 0;
		long deltaTime;//better to have this be a long that converts to int or pass a long into tick()?
		long deltaLastTime = System.nanoTime();
		
		long deltaNow;
		
		long startTime = System.currentTimeMillis() / 1000;
		
		while(running) {
			now = System.nanoTime();
			timer += now - lastTime;
			delta += (now - lastTime) / timePerTick;
			
			lastTime = now;
			
			if(delta >= 1) {//this should avoid lost or gained frames from speeding up or slowing down the game
				deltaNow = System.nanoTime();
				deltaTime = deltaNow - deltaLastTime;
				tick(deltaTime / 1000000);//converts nano to milli
				render();
				ticks++;
				delta--;
				tickCount++;
				deltaLastTime = deltaNow;
			}
			
			if(timer >= 1000000000) {
				System.out.println(" FPS: " + ticks + " " + (System.currentTimeMillis() / 1000 - startTime) + "s");
				fps = ticks;
				ticks = 0;
				timer = 0;
			}
		}
		stop();
	}
	
	//Creates the thread
	public synchronized void start() {
		if(running == true) {
			return;
		}
		running = true;
		thread = new Thread(this);
		thread.start();
	}
	
	//Stops the code cleanly
	public synchronized void stop() {
		if(!running) {
			return;
		}
		running = false;
		try {
			thread.join();
		} catch(InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	//Getters and Setters
	
	public int getFPS() {
		return fps;
	}

	public void setWidth(int width) {
		this.width = width;
	}
	
	public void setHeight(int height) {
		this.height = height;
	}
	
	public int getWidth() {
		return display.getWidth();
	}
	
	public int getHeight() {
		return display.getHeight();
	}
	
}