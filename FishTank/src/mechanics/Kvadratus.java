package mechanics;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Polygon;

public class Kvadratus {
	private double x;
	private double y;
	private double a;
	private double w;
	
	public Kvadratus(double x, double y, double w, double a) {
		this.x = x;
		this.y = y;
		this.w = w;
		this.a = a;
	}
	
	public void update(){
		setA(a + 1);
	}
	
	public void render01(Graphics g) {
		g.setColor(new Color(255, 102, 255));
		
		
		double t = Math.sqrt(w*w/2);
		int x1 = (int)Math.round(x + t * Math.cos((45-a)/180*Math.PI));
		int y1 = (int)Math.round(y + t * Math.sin((45-a)/180*Math.PI));
		
		int x2 = (int)Math.round(x1 - w * Math.cos(a/180*Math.PI));
		int y2 = (int)Math.round(y1 + w * Math.sin(a/180*Math.PI));
		
		int y3 = y2 - (int)Math.round(w * Math.sin((90 - a)/180*Math.PI));
		
		
		for( double i = 0; i <= y2 - y3; i += 0.5) {
			for(double j = 0; j <= x1 - x2; j += 0.5) {
				point(x2 + j - Math.round((i/Math.tan((90-a)/180*Math.PI)) ),y2 - i - Math.round((j*Math.tan(a/180*Math.PI)) ),g);
			}
		}
	}
	
	
	
	public void render02(Graphics g) {
		g.setColor(new Color(255, 102, 255));
		
		double t = Math.sqrt(w*w/2);
		
		int p1x = (int)Math.round(x + t * Math.cos((45-a)/180*Math.PI));
		int p1y = (int)Math.round(y + t * Math.sin((45-a)/180*Math.PI));
		
		int p2x = (int)Math.round(p1x - w * Math.cos(a/180*Math.PI));
		int p2y = (int)Math.round(p1y + w * Math.sin(a/180*Math.PI));
		
		//int y3 = y2 - (int)Math.round(w * Math.sin((90 - a)/180*Math.PI));
		
		for(double i = 0; i < p2y - p1y; i++) {
			for(double j = 0; j < i * Math.tan((90-a)/180*Math.PI); j++) {
				point(p2x + j, p2y - i,g);
			}
		}
		
	}
	
	public void render03(Graphics g) {
		g.setColor(new Color(255, 102, 255));
		
double t = Math.sqrt(w*w/2);
		
		int p1x = (int)Math.round(x + t * Math.cos((45-a)/180*Math.PI));
		int p1y = (int)Math.round(y + t * Math.sin((45-a)/180*Math.PI));
		
		int p2x = (int)Math.round(p1x - w * Math.cos(a/180*Math.PI));
		int p2y = (int)Math.round(p1y + w * Math.sin(a/180*Math.PI));
		
		int p3x = p2x - (int)Math.round(w * Math.cos((90-a)/180*Math.PI));
		int p3y = p2y - (int)Math.round(w * Math.sin((90-a)/180*Math.PI));
		
		int p4x = p1x - (int)Math.round(w * Math.cos((90-a)/180*Math.PI));
		int p4y = p1y - (int)Math.round(w * Math.sin((90-a)/180*Math.PI));
		
		int xses[] = {p1x,p2x,p3x,p4x};
		int yses[] = {p1y,p2y,p3y,p4y};
		
		g.fillPolygon(xses,yses,4);
	}
	
	private void setA(double newA) {
		while(newA > 89) {
			newA -= 180;
		}
		a = newA;
	}
	
	private void point(double x, double y, Graphics g) {
		
		g.drawLine((int)Math.round(x),(int)Math.round(y),(int)Math.round(x),(int)Math.round(y));
	}
}
