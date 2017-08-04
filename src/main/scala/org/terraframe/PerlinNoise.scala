package org.terraframe;

import java.lang.Math;

object PerlinNoise {
    def perlinNoise(x: Double, p: Double, n: Int): Double = {
        var total: Double = 0;
        var freq, ampl: Double = 0;
        (0 until n+1).foreach { i =>
            freq = Math.pow(2, i);
            ampl = Math.pow(p, i);
            total = total + InterpolateNoise(x * freq) * ampl;
        }
        return total;
    }

    private[PerlinNoise] def InterpolateNoise(x: Double): Double = {
        val ix: Int = x.toInt;
        val fx: Double = x - ix;
        val v1: Double = smoothNoise(ix);
        val v2: Double = smoothNoise(ix + 1);
        return Interpolate(v1, v2, fx);
    }

    private[PerlinNoise] def smoothNoise(x: Int): Double = {
        return noise(x)/2 + noise(x-1)/4 + noise(x+1)/4;
    }

    private[PerlinNoise] def noise(x: Int): Double = {
        val x2 = (x << 13) ^ x;
        return (1.0 - ((x2 * (x2 * x2 * 15731 + 789221) + 1376312589) & 0x7fffffff) / 1073741824.0);
    }

    private[PerlinNoise] def Interpolate(a: Double, b: Double, x: Double): Double =  {
        val ft: Double = x * Math.PI;
        val f: Double = (1 - Math.cos(ft)) / 2;
        return a * (1 - f) + b * f;
    }
}
