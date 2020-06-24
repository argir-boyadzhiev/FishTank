package creature;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

import mechanics.Instruction;

public class Creature03 {
	
	private Instruction inst;
	
	private int screenWidth = 1200;
	private int screenHeight = 800;
	
	private double x = 0;
	private double y = 0;

	private int focusTime = 0;
	
	private double xSpeed = -1;
	private double ySpeed = -1;
	private double energy = 500;
	private double size = 30;
	private double sightRadius = 50;
	
	
	public Creature03(Instruction inst, double x, double y) {
		this.inst = inst;
		this.x = x;
		this.y = y;
	}
	
	
}
