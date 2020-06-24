package mechanics;

public class DNA {
	
	private double XtoX;
	private double YtoX;
	private double EtoX;
	private double StoX;
	
	private double XtoY;
	private double YtoY;
	private double EtoY;
	private double StoY;
	
	//X - x coordinate of the detected object
	//Y - y -||-
	//E - how much energy the creature has
	//S - size of detected object
	
	
	public DNA(double XtoX, double YtoX, double EtoX, double StoX, double XtoY, double YtoY, double EtoY, double StoY) {
		this.XtoX = XtoX;
		this.YtoX = YtoX;
		this.EtoX = EtoX;
		this.StoX = StoX;
		
		this.XtoY = XtoY;
		this.YtoY = YtoY;
		this.EtoY = EtoY;
		this.StoY = StoY;
		
	}
	
	
	public double getXtoX() {
		return XtoX;
	}
	public double getYtoX() {
		return YtoX;
	}
	public double getEtoX() {
		return EtoX;
	}
	public double getStoX() {
		return StoX;
	}
	
	
	public double getXtoY() {
		return XtoY;
	}
	public double getYtoY() {
		return YtoY;
	}
	public double getEtoY() {
		return EtoY;
	}
	public double getStoY() {
		return StoY;
	}
}
