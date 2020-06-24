package ChainGang;

import java.awt.Color;
import java.awt.Graphics;

public class Food {
	private double x;
	private double y;
	private double xSpeed;
	private double ySpeed;
	private int energy;
	
	private int sizeRadius = 2;
	
	public Food(double x, double y, int energy, double xSpeed, double ySpeed) {
		this.x = x;
		this.y = y;
		this.energy = energy;
		this.xSpeed = xSpeed;
		this.ySpeed = ySpeed;
	}
	
	public void update() {
		y += ySpeed * 0.5;
		x += xSpeed * 0.5;
		
		borderSpeedChange();
	}
	
	private void borderSpeedChange() {
		if(y > 750 - sizeRadius || y < sizeRadius) ySpeed *= -1;
		if(x > 1200 - sizeRadius || x < sizeRadius) xSpeed *= -1;
	}
	
	public void render(Graphics g) {
		g.setColor(new Color(209, 82, 255));
		g.fillOval((int)Math.round(x - sizeRadius), (int)Math.round(y - sizeRadius), sizeRadius * 2, sizeRadius * 2);
		//g.setColor(new Color(255,0,0));
		//g.drawOval((int)Math.round(x - sizeRadius), (int)Math.round(y - sizeRadius), sizeRadius * 2, sizeRadius * 2);
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
	public int getSizeRadius() {
		return sizeRadius;
	}
}
