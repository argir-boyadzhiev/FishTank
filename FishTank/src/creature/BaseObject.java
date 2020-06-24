package creature;

import java.awt.Graphics;
import java.util.ArrayList;

public abstract class BaseObject {
	
	//x and y coordinates
	private double x;
	private double y;
	
	private double sizeRadius;
	private double energy;
	
	/*
	1 = living matter
	2 = dead matter
	3 = waste matter
	*/
	private int material;
	
	public abstract void render(Graphics g);
	
	
}
