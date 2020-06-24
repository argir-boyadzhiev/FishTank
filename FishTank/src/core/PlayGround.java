package core;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.image.BufferStrategy;
import java.util.ArrayList;

import ChainGang.Waste;
import mechanics.Instruction;
import ChainGang.Consumer;
import ChainGang.Food;
import ChainGang.Producer;

public class PlayGround {
	
	private Display display;
	private String title;
	private int width, height;
	private boolean running;
	private BufferStrategy bs;
	private Graphics g;
	
	
	private ArrayList<Waste> waste = new ArrayList<Waste>();
	private ArrayList<Food> food = new ArrayList<Food>();
	
	private ArrayList<Producer> producers = new ArrayList<Producer>();
	
	private ArrayList<Consumer> consumers = new ArrayList<Consumer>();
	
	public PlayGround(String title, int width, int height) {
		this.width = width;
		this.height = height;
		this.title = title;
		
		//Creatures
		
		producers.add(new Producer(0, 300, new Point(100,600), new Point(150,720), new Point(250,450), 6200, new Color(20,120,50)));
		producers.add(new Producer(300, 700, new Point(360,550), new Point(500,500), new Point(620,670), 6200, new Color(20,190,50)));
		producers.add(new Producer(700, 900, new Point(750,600), new Point(800,660), new Point(870,530), 6200, new Color(20,120,90)));
		producers.add(new Producer(900, 1200, new Point(970,670), new Point(1080,430), new Point(1150,470), 6200, new Color(20,100,100)));
		
		waste.add(new Waste(200, 200, 30, true, 3));
		waste.add(new Waste(400, 400, 30, true, 3));
		waste.add(new Waste(800, 300, 30, true, 3));
		
		food.add(new Food(600, 380, 30,-1, 0));
		
		consumers.add(new Consumer(300, 400, 1, 1, 100, 15, new Instruction(1,2,2,1,-1,-2,-2,-1,-2,-1,1,2,2,1,-1,-2,  -1,-2,-2,-1,1,2,2,1,2,1,-1,-2,-2,-1,1,2, 5), new Color(252, 186, 3)));
		consumers.add(new Consumer(700, 500, 1, 1, 112, 30, new Instruction(1,2,2,1,-1,-2,-2,-1,-2,-1,1,2,2,1,-1,-2,  -1,-2,-2,-1,1,2,2,1,2,1,-1,-2,-2,-1,1,2, 30), new Color(250, 72, 12)));
		consumers.add(new Consumer(325, 600, 1, 1, 100, 15, new Instruction(1,2,2,1,-1,-2,-2,-1,-2,-1,1,2,2,1,-1,-2,  -1,-2,-2,-1,1,2,2,1,2,1,-1,-2,-2,-1,1,2, 16), new Color(200,100,0)));
		
		//
		
	}
	
	private void tick() {
		
		for(int i = 0; i < producers.size(); i++) {
			producers.get(i).update(waste,food);
		}
		for(int i = 0; i < consumers.size(); i++) {
			consumers.get(i).update(food,consumers,waste,width,height);
		}
		for(int i = 0; i < waste.size(); i++) {
			waste.get(i).update();
		}
		for(int i = 0; i < food.size(); i++) {
			food.get(i).update();
		}
		
		
	}
	
	private void render() {
		bs = display.getCanvas().getBufferStrategy();
		if(bs == null) {
			display.getCanvas().createBufferStrategy(3);
			return;
		}
		g = bs.getDrawGraphics();
		g.setColor(new Color(168, 251, 255));
		g.fillRect(0, 0, width, height);
		
		
		//DRAW
		for(int i = 0; i < producers.size(); i++) {
			producers.get(i).render(g);
		}
		
		for(int i = 0; i < waste.size(); i++) {
			waste.get(i).render(g);
		}
		for(int i = 0; i < food.size(); i++) {
			food.get(i).render(g);
		}
		
		for(int i = 0; i < consumers.size(); i++) {
			consumers.get(i).render(g);
		}
		
		
		//DRAW
		
		
		bs.show();
		g.dispose();
	}
	
	private void mainLoop() {
		int fps = 60;
		double timePerTick = 1_000_000_000 / fps;
		double delta = 0;
		long now;
		long lastTime = System.nanoTime();
		
		while(running) {
			now = System.nanoTime();
			delta += (now-lastTime)/timePerTick;
			lastTime = now;
			
			if(delta>=1) {
				tick();
				render();
				delta--;
			}
		}
	}
	
	public void start() {
		display = new Display(title, width, height);
		running = true;
		mainLoop();
	}
	
}
