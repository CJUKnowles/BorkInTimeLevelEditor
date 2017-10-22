package com.turruc.engine;

import com.turruc.LevelPicker;

public class GameContainer implements Runnable {

	private Thread thread;
	private Window window;
	private Renderer renderer;
	private Input input;
	private AbstractGame game;
	
	public static LevelPicker GUI;

	private boolean running = false;
	private final double UPDATE_CAP = 1.0 / 60.0;
	// 640x480
	private int width = 800, height = 480; // "Actual" resolution. If game is pixelated style, this may be smaller, and
											// scaled to fullscreen
	private float scale = 2f; // Scales the resolution. 960x540 with a scale of 2f turns into 1920x1080
								// (literally like a render scale in games)
	private String title = "Bork In Time Level Editor";

	public GameContainer(AbstractGame game) {
		this.game = game;
	}

	// runs first, starts program
	public void start() {
		window = new Window(this);
		renderer = new Renderer(this);
		input = new Input(this);

		
		GUI = new LevelPicker(getWindow());
		
		thread = new Thread(this);
		thread.run();
		
	}

	public void stop() {

		//LevelPicker.exit();
		System.exit(0);
	}

	public void run() {
		running = true;

		boolean render = false;
		double firstTime = 0;
		double lastTime = System.nanoTime() / 1000000000.0; // 9 zeroes
		double passedTime = 0;
		double unprocessedTime = 0;

		double frameTime = 0;
		int frames = 0;
		int fps = 0;

		game.init(this);

		while (running) {
			render = false; // mark as true to uncap framerate, may screw with some calculations, should be
							// false for final version
			firstTime = System.nanoTime() / 1000000000.0;
			passedTime = firstTime - lastTime;
			lastTime = firstTime;

			unprocessedTime += passedTime;
			frameTime += passedTime;

			while (unprocessedTime >= UPDATE_CAP) {
				unprocessedTime -= UPDATE_CAP;
				render = true;

				game.update(this, (float) UPDATE_CAP);

				input.update();

				if (frameTime >= 1.0) {
					frameTime = 0;
					fps = frames;
					frames = 0;
				}
			}

			if (render) {
				renderer.clear();
				game.render(this, renderer);
				renderer.process();
				renderer.setCamX(0);
				renderer.setCamY(0);
				renderer.drawText("FPS: " + fps, 0, 0, 0xff00ffff);
				window.update();
				frames++;
			} else {
				try {
					Thread.sleep(1);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
		dispose();
	}

	private void dispose() {
	}

	// -------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public float getScale() {
		return scale;
	}

	public void setScale(float scale) {
		this.scale = scale;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Window getWindow() {
		return window;
	}

	public Input getInput() {
		return input;
	}

	public Renderer getRenderer() {
		return renderer;
	}

	public float getDt() {
		return (float) UPDATE_CAP;
	}
}
