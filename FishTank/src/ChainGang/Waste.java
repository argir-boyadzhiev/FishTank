package ChainGang;

import java.awt.Color;
import java.awt.Graphics;

public class Waste {
	private double x;
	private double y;
	private boolean direction;// true = left; false = right
	private int period;
	private int energy;
	
	private int size = 2;
	
	public Waste(double x, double y, int energy, boolean direction, int period) {
		this.x = x;
		this.y = y;
		this.energy = energy;
		this.direction = direction;
		this.period = period;
	}
	
	public void update() {
		y += 0.5;
		x += period * 0.01;
		
		
		if(period > 30 || period < -30) {
			direction = !direction;
		}
		
		if(direction) {
			period++;
		}else {
			period--;
		}
		
		bottomBlock();
	}
	
	private void bottomBlock() {
		if(y > 750 - size) {
			y = 750 - size;
		}
	}
	
	public void render(Graphics g) {
		g.setColor(new Color(30, 30, 56));
		g.fillOval((int)Math.round(x), (int)Math.round(y), size * 2, size);
	}
	
	public double getX() {
		return x;
	}
	public double getY() {
		return y;
	}
	public int getEnergy() {
		return energy;
	}
}
