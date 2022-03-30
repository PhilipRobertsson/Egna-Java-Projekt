package FloydSteinbergDithering;

import java.awt.Color;

public class C3 {
	
	int r, g, b;
	
	public C3(int c) {
		Color color = new Color(c);
	    this.r = color.getRed();
	    this.g = color.getGreen();
	    this.b = color.getBlue();
	}
	public C3(int r, int g, int b) {
	    this.r = r;
	    this.g = g;
	    this.b = b;
	 }
	
	public C3 add(C3 o) {
		return new C3(r + o.r, g + o.g, b + o.b);
	}
    public C3 sub(C3 o) {
        return new C3(r - o.r, g - o.g, b - o.b);
    }
    public C3 mul(double d) {
        return new C3((int) (d * r), (int) (d * g), (int) (d * b));
    }
    public int diff(C3 o) {
    	return Math.abs(r - o.r) +  Math.abs(g - o.g) +  Math.abs(b - o.b);
    }
    

    public int toRGB() {
      return toColor().getRGB();
    }
    public Color toColor() {
      return new Color(clamp(r), clamp(g), clamp(b));
    }
    public int clamp(int c) {
      return Math.max(0, Math.min(255, c));
    }
}
