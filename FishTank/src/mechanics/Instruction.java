package mechanics;

public class Instruction {
	public double smaller[] = new double[16];
	public double bigger[] = new double[16];
	public int matureSize;
	public Instruction(double s1, double s2, double s3, double s4, double s5, double s6, double s7, double s8, double s9, double s10, double s11, double s12, double s13, double s14, double s15, double s16, double b1, double b2, double b3, double b4, double b5, double b6, double b7, double b8, double b9, double b10, double b11, double b12, double b13, double b14, double b15, double b16, int matureSize) {
		
		//to x
		smaller[0] = s1;
		smaller[1] = s2;
		smaller[2] = s3;
		smaller[3] = s4;
		smaller[4] = s5;
		smaller[5] = s6;
		smaller[6] = s7;
		smaller[7] = s8;
		
		//to y
		smaller[8] = s9;
		smaller[9] = s10;
		smaller[10] = s11;
		smaller[11] = s12;
		smaller[12] = s13;
		smaller[13] = s14;
		smaller[14] = s15;
		smaller[15] = s16;
		
		bigger[0] = b1;
		bigger[1] = b2;
		bigger[2] = b3;
		bigger[3] = b4;
		bigger[4] = b5;
		bigger[5] = b6;
		bigger[6] = b7;
		bigger[7] = b8;
		
		bigger[8] = s9;
		bigger[9] = s10;
		bigger[10] = s11;
		bigger[11] = s12;
		bigger[12] = s13;
		bigger[13] = s14;
		bigger[14] = s15;
		bigger[15] = s16;
		
		this.matureSize = matureSize;
	}
}
