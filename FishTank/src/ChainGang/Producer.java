package ChainGang;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;

public class Producer {
	
	private int rootX1;
	private int rootX2;
	
	private ArrayList<Point> foodPoints = new ArrayList<Point>();
	
	private int energy;
	
	private Color color;
	
	public Producer(int rootX1, int rootX2, Point p1, Point p2, Point p3, int energy, Color color) {
		
		this.rootX1 = rootX1;
		this.rootX2 = rootX2;
		
		foodPoints.add(p1);
		foodPoints.add(p2);
		foodPoints.add(p3);
		
		this.energy = energy;
		
		this.color = color;
	}
	
	public void update(ArrayList<Waste> waste, ArrayList<Food> food) {
		handleWaste(waste);
		handleFood(food);
	}
	
	private void handleWaste(ArrayList<Waste> waste) {
		for(int i = 0; i < waste.size(); i++) {
			if(waste.get(i).getY() > 740) {
				if(rootX1 <= waste.get(i).getX() && waste.get(i).getX() <= rootX2) {
					energy += waste.get(i).getEnergy();
					waste.remove(i);
				}
			}
		}
	}
	
	private void handleFood(ArrayList<Food> food) {
		if(energy > 30) {
			energy -= 30;
			
			short randomPoint = (short)Math.floor((Math.random() * 3));
			
			double xSpeed = Math.random()*2 - 1;
			
			food.add(new Food(foodPoints.get(randomPoint).getX(), foodPoints.get(randomPoint).getY(), 30, xSpeed, -0.2));
			
		}
	}
	
	public void render(Graphics g) {
		g.setColor(color);
		g.fillRect(rootX1, 750-5, rootX2 - rootX1, 5);
		
		for(int i=0;i<3;i++) {
			g.fillRect(foodPoints.get(i).x - 20, 750-15, 40 , 15);
			g.fillRect(foodPoints.get(i).x - 3, foodPoints.get(i).y , 6 , 750 - foodPoints.get(i).y);
		}
	}
}
