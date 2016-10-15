import java.lang.Math;

public class PerlinNoise {
	public static double perlinNoise(double x, double p, int n) {
		double total = 0;
		int i;
		double freq, ampl;
		for (i=0; i<n+1; i++) {
			freq = Math.pow(2, i);
			ampl = Math.pow(p, i);
			total = total + interpolateNoise(x * freq) * ampl;
		}
		return total;
	}
	
	private static double interpolateNoise(double x) {
		int ix = (int)x;
		double fx = x - ix;
		double v1 = smoothNoise(ix);
		double v2 = smoothNoise(ix + 1);
		return interpolate(v1, v2, fx);
	}
	
	private static double smoothNoise(int x) {
		return noise(x)/2 + noise(x-1)/4 + noise(x+1)/4;
	}
	
	private static double noise(int x) {
		x = (x << 13) ^ x;
		return (1.0 - ((x * (x * x * 15731 + 789221) + 1376312589) & 0x7fffffff) / 1073741824.0);
	}
	
	private static double interpolate(double a, double b, double x) {
		double ft = x * Math.PI;
		double f = (1 - Math.cos(ft)) / 2;
		return a * (1 - f) + b * f;
	}
}
