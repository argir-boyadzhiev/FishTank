package ChainGang;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import mechanics.Instruction;

public class Consumer {
	
	private double x = 0;
	private double y = 0;
	
	private Color color;
	
	private double speed = 1;
	private double xSpeed = 0;
	private double ySpeed = 0;
	
	private double energy = 0;
	private int sizeRadius = 0;
	
	private boolean markers[] = new boolean[16];
	
	private Instruction inst;
	
	private int sightRadius = 0;
	
	private double wasteBuildUp = 0;
	
	private boolean dead = false;
	
	private int randomChangeTimer=0;
	
	public Consumer(double x, double y, double xSpeed, double ySpeed, int energy,int sizeRadius, Instruction inst, Color color) {
		this.x = x;
		this.y = y;
		this.xSpeed = xSpeed;
		this.ySpeed = ySpeed;
		this.energy = energy;
		this.color = color;
		this.sizeRadius = sizeRadius;
		sightRadius = 30 + sizeRadius;
		this.inst =inst;
		speedReset();
		resetMarkers();
	}
	
	private void randomChange() {
		if(randomChangeTimer <= 0) {
			randomChangeTimer = 30;
			xSpeed = speed * (Math.random()*8 - 4) /2;
			ySpeed = speed * (Math.random()*8 - 4) /2;
		}
		else randomChangeTimer--;
	}
	
	private void speedReset() {
		speed = (-Math.tan(sizeRadius/20 + 8)/2 + 1)*5;
		if(speed > 20) speed = 20;
		if(speed < 3) speed = 3;
		speed = Math.sqrt(Math.sqrt(speed))*0.2;
	}
	
	public void update(ArrayList<Food> food, ArrayList<Consumer> consumers, ArrayList<Waste> waste, int screenWidth, int screenHeight) {
		resetMarkers();
		handleFood(food);
		handleConsumers(consumers);
		if(reaction()) {
			handleThinking();
		}
		else randomChange();
		handleSize(food);
		handleWaste(waste);
		movement(screenWidth, screenHeight);
		handleEating(food, consumers);
		handleMultiplication(consumers);
	}
	
	

	
	private void handleMultiplication(ArrayList<Consumer> consumers) {
		if(sizeRadius >= inst.matureSize && energy >= 240) {
			consumers.add(new Consumer(x, y, -xSpeed, -ySpeed, 120, sizeRadius/2, giveChildInstruction(), color));
			sizeRadius /= 2;
			energy /= 2;
			sightRadius = 30 + sizeRadius;
			speedReset();
		}
		
	}
	
	private Instruction giveChildInstruction() {
		Instruction newInst = new Instruction(rm(inst.smaller[0]),rm(inst.smaller[1]),rm(inst.smaller[2]),rm(inst.smaller[3]),rm(inst.smaller[4]),rm(inst.smaller[5]),rm(inst.smaller[6]),rm(inst.smaller[7]),rm(inst.smaller[8]),rm(inst.smaller[9]),rm(inst.smaller[10]),rm(inst.smaller[11]),rm(inst.smaller[12]),rm(inst.smaller[13]),rm(inst.smaller[14]),rm(inst.smaller[15]),  rm(inst.bigger[0]),rm(inst.bigger[1]),rm(inst.bigger[2]),rm(inst.bigger[3]),rm(inst.bigger[4]),rm(inst.bigger[5]),rm(inst.bigger[6]),rm(inst.bigger[7]),rm(inst.bigger[8]),rm(inst.bigger[9]),rm(inst.bigger[10]),rm(inst.bigger[11]),rm(inst.bigger[12]),rm(inst.bigger[13]),rm(inst.bigger[14]),rm(inst.bigger[15]), (int)rm(inst.matureSize));
		
		return newInst;
	}
	
	//random mutation
	private double rm(double parentGene) {
		double newGene = parentGene - 2;
		int k = (int)(Math.random() * 10) % 5;
		if(k == 0) {
			newGene++;
		}else {
			newGene--;
		}
		
		//return newGene;
		return parentGene;
	}
	
	private void movement(int screenWidth, int screenHeight) {
		x = x + xSpeed * speed;
		y = y + ySpeed * speed;
		
		wasteBuildUp += 0.01 * sizeRadius ;
		energy -= 0.01 * sizeRadius;
		
		if(x - sizeRadius < 0 || x + sizeRadius > screenWidth) xSpeed *= -1;
		if(y - sizeRadius < 0 || y + sizeRadius > screenHeight) ySpeed *= -1;
		
		borderUnstuck(screenWidth,screenHeight);
	}
	
	private void borderUnstuck(int screenWidth, int screenHeight) {
		if(x - sizeRadius < 0) x = sizeRadius;
		if( x + sizeRadius > screenWidth) x = screenWidth - sizeRadius;
		if(y - sizeRadius < 0) y = sizeRadius;
		if(y + sizeRadius > screenHeight) y = screenHeight - sizeRadius;
	}
	
	private void handleSize(ArrayList<Food> food) {
		
		if(energy > 240 ) {
			energy -= 120;
			sizeRadius++;
			sightRadius = sizeRadius + 30;
		}
		
		if(energy <= 0) {
			dead = true;
			for(int i = 0; i < 4*sizeRadius; i++) {
				food.add(new Food(gr(x - sizeRadius/2, x + sizeRadius/2),gr(y - sizeRadius/2, y + sizeRadius/2),30,Math.random()*2-1,Math.random()*2-1));
			}
			
		}
		
	}
	
	//give random
	private int gr(double min, double max) {
		return (int)Math.round(min + Math.random()*(max-min));
	}
	
	private void handleWaste(ArrayList<Waste> waste) {
		if(wasteBuildUp > 30) {
			wasteBuildUp -= 30;
			
			boolean direction = false;
			if(Math.random()*10 > 5) direction = true;
			waste.add(new Waste(x, y, 30, direction, (int)Math.ceil(Math.random()*10 - 5)));
		}
	}
	
	private void handleEating(ArrayList<Food> food, ArrayList<Consumer> consumers) {
		
		for(int i = 0; i < food.size(); i++) {
			if(inRange(sizeRadius, food.get(i).getX(), food.get(i).getY(), food.get(i).getSizeRadius())) {
				energy += food.get(i).getEnergy();
				food.remove(i);
			}
		}
		
		for(int i = 0; i < consumers.size(); i++) {
			if(consumers.get(i).getX() == x && consumers.get(i).getY() == y) break;
			if(consumers.get(i).getSizeRadius() + 5 < sizeRadius) {
				if(inRange(sizeRadius, consumers.get(i).getX(), consumers.get(i).getY(), consumers.get(i).getSizeRadius())) {
					energy += consumers.get(i).getEnergy();
					consumers.remove(i);
				}
			}
		}
	}
	
	private boolean reaction() {
		for(int i=0;i<16;i++) {
			if(markers[i]) return true;
		}
		return false;
	}
	
	private void handleThinking() {
		
		xSpeed = 0;
		
		for(int i = 0; i < 8; i++) {
			if(markers[i]) xSpeed+= inst.smaller[i];
		}
		for(int i = 0; i < 8; i++) {
			if(markers[i+8]) xSpeed+= inst.bigger[i];
		}
		
		ySpeed = 0;
				
		for(int i = 0; i < 8; i++) {
			if(markers[i]) ySpeed+= inst.smaller[i+8];
		}
		for(int i = 0; i < 8; i++) {
			if(markers[i+8]) ySpeed+= inst.bigger[i+8];
		}
		
	}
	
	private boolean inRange(int radius, double ix, double iy, int isizeRadius) {
		if(Math.pow(ix - x, 2) + Math.pow(iy - y, 2) < Math.pow(radius + isizeRadius, 2)) return true;
		else return false;
	}
	
		
	public void render(Graphics g) {
		//g.setColor(new Color(sizeRadius,inst.matureSize,energy/100));
		g.setColor(color);
		g.fillOval((int)Math.round(x - sizeRadius), (int)Math.round(y - sizeRadius), sizeRadius * 2, sizeRadius * 2);
		g.setColor(new Color(255,0,0));
		g.drawOval((int)Math.round(x - sizeRadius), (int)Math.round(y - sizeRadius), sizeRadius * 2, sizeRadius * 2);
		//g.drawOval((int)Math.round(x - sightRadius), (int)Math.round(y - sightRadius), sightRadius * 2, sightRadius * 2);
		
	}
	
	private void resetMarkers() {
		
		for(int i = 0; i < 16; i++) {
			markers[i] = false;
		}
	}
	
	public int getSizeRadius() {
		return sizeRadius;
	}
	public double getX() {
		return x;
	}
	public double getY() {
		return y;
	}
	
	
	public boolean isDead() {
		return dead;
	}
	
	public double getEnergy() {
		
		return energy + sizeRadius*120;
	}
	
/////////////////////////// BIG METHODS
	
	private void handleFood(ArrayList<Food> food) {
		
		for(int i = 0; i < food.size(); i++) {
			if(inRange(sightRadius,food.get(i).getX(), food.get(i).getY(),food.get(i).getSizeRadius())){
				
				if(!(markers[0] && markers[1])) {
					
					if(food.get(i).getX() >= x && food.get(i).getY() <= y) {
						if(Math.abs(food.get(i).getX() - x) < Math.abs(food.get(i).getY() - y)) {
							markers[0] = true;
							break;
						}else {
							markers[1] = true;
							break;
						}
					}
				}
				
				if(!(markers[2] && markers[3])) {
					if(food.get(i).getX() >= x && food.get(i).getY() >= y) {
						if(Math.abs(food.get(i).getX() - x) < Math.abs(food.get(i).getY() - y)) {
							markers[3] = true;
							break;
						}else {
							markers[2] = true;
							break;
						}
					}
				}
				
				if(!(markers[4] && markers[5])) {
					if(food.get(i).getX() <= x && food.get(i).getY() >= y) {
						if(Math.abs(food.get(i).getX() - x) < Math.abs(food.get(i).getY() - y)) {
							markers[4] = true;
							break;
						}else {
							markers[5] = true;
							break;
						}
					}
				}
				
				if(!(markers[6] && markers[7])) {
					if(food.get(i).getX() <= x && food.get(i).getY() <= y) {
						
						if(Math.abs(food.get(i).getX() - x) < Math.abs(food.get(i).getY() - y)) {
							markers[7] = true;
							break;
						}else {
							markers[6] = true;
							break;
						}
					}
				}
				
				
			}
		}
		
		
	}
	
	
	private void handleConsumers(ArrayList<Consumer> consumers) {
		for(int i = 0; i < consumers.size(); i++) {
			if(consumers.get(i).isDead()) {
				consumers.remove(i);
			}
		}
		
		
		
		for(int i = 0; i < consumers.size(); i++) {
			if(consumers.get(i).getX() == x && consumers.get(i).getY() == y) break;
			
			if(inRange(sightRadius,consumers.get(i).getX(), consumers.get(i).getY(),consumers.get(i).getSizeRadius())){
				
				if(!(markers[0] && markers[1])) {
					if(consumers.get(i).getX() >= x && consumers.get(i).getY() <= y) {
						if(Math.abs(consumers.get(i).getX() - x) < Math.abs(consumers.get(i).getY() - y)) {
							
							if(consumers.get(i).getSizeRadius() + 5 < sizeRadius || consumers.get(i).isDead()) {
								markers[0] = true;
							}else {
								markers[8] = true;
							}
							break;
							
						}else {
							
							if(consumers.get(i).getSizeRadius() + 5 < sizeRadius || consumers.get(i).isDead()) {
								markers[1] = true;
							}else{
								markers[9] = true;
							}
							break;
						}
					}
				}
				
				if(!(markers[2] && markers[3])) {
					if(consumers.get(i).getX() >= x && consumers.get(i).getY() >= y) {
						if(Math.abs(consumers.get(i).getX() - x) < Math.abs(consumers.get(i).getY() - y)) {
							if(consumers.get(i).getSizeRadius() + 5 < sizeRadius || consumers.get(i).isDead()) {
								markers[3] = true;
							}else{
								markers[11] = true;
							}
							break;
						}else {
							if(consumers.get(i).getSizeRadius() + 5 < sizeRadius || consumers.get(i).isDead()) {
								markers[2] = true;
							}else {
								markers[10] = true;
							}
							break;
						}
					}
				}
				
				if(!(markers[4] && markers[5])) {
					if(consumers.get(i).getX() <= x && consumers.get(i).getY() >= y) {
						if(Math.abs(consumers.get(i).getX() - x) < Math.abs(consumers.get(i).getY() - y)) {
							if(consumers.get(i).getSizeRadius() + 5 < sizeRadius || consumers.get(i).isDead()) {
								markers[4] = true;
							}else{
								markers[12] = true;
							}
							break;
						}else {
							if(consumers.get(i).getSizeRadius() + 5 < sizeRadius || consumers.get(i).isDead()) {
								markers[5] = true;
							}else{
								markers[13] = true;
							}
							break;
						}
					}
				}
				
				if(!(markers[6] && markers[7])) {
					if(consumers.get(i).getX() <= x && consumers.get(i).getY() <= y) {
						if(Math.abs(consumers.get(i).getX() - x) < Math.abs(consumers.get(i).getY() - y)) {
							if(consumers.get(i).getSizeRadius() + 5 < sizeRadius || consumers.get(i).isDead()) {
								markers[7] = true;
							}else{
								markers[15] = true;
							}
							break;
						}else {
							if(consumers.get(i).getSizeRadius() + 5 < sizeRadius || consumers.get(i).isDead()) {
								markers[6] = true;
							}else {
								markers[14] = true;
							}
							break;
						}
					}
				}
				
				
			}
		}
	}
}
