
package com.maceswinger.utils;

public class Easing {

	public static float bounceIn(float t, float b, float c, float d) {
		return c - bounceOut(d - t, 0, c, d) + b;
	}

	public static float bounceOut(float t, float b, float c, float d) {
		if ((t /= d) < (1 / 2.75f)) {
			return c * (7.5625f * t * t) + b;
		} else if (t < (2 / 2.75f)) {
			return c * (7.5625f * (t -= (1.5f / 2.75f)) * t + .75f) + b;
		} else if (t < (2.5 / 2.75)) {
			return c * (7.5625f * (t -= (2.25f / 2.75f)) * t + .9375f) + b;
		} else {
			return c * (7.5625f * (t -= (2.625f / 2.75f)) * t + .984375f) + b;
		}
	}

	public static float bounceInOut(float t, float b, float c, float d) {
		if (t < d / 2)
			return bounceIn(t * 2, 0, c, d) * .5f + b;
		else
			return bounceOut(t * 2 - d, 0, c, d) * .5f + c * .5f + b;
	}

	public static float elasticIn(float t, float b, float c, float d) {
		if (t == 0)
			return b;
		if ((t /= d) == 1)
			return b + c;
		float p = d * .3f;
		float a = c;
		float s = p / 4;
		return -(a * (float) Math.pow(2, 10 * (t -= 1)) * (float) Math.sin((t
				* d - s)
				* (2 * (float) Math.PI) / p))
				+ b;
	}

	public static float elasticIn(float t, float b, float c, float d, float a,
			float p) {
		float s;
		if (t == 0)
			return b;
		if ((t /= d) == 1)
			return b + c;
		if (a < Math.abs(c)) {
			a = c;
			s = p / 4;
		} else {
			s = p / (2 * (float) Math.PI) * (float) Math.asin(c / a);
		}
		return -(a * (float) Math.pow(2, 10 * (t -= 1)) * (float) Math.sin((t
				* d - s)
				* (2 * Math.PI) / p))
				+ b;
	}

	public static float elasticOut(float t, float b, float c, float d) {
		if (t == 0)
			return b;
		if ((t /= d) == 1)
			return b + c;
		float p = d * .3f;
		float a = c;
		float s = p / 4;
		return (a * (float) Math.pow(2, -10 * t)
				* (float) Math.sin((t * d - s) * (2 * (float) Math.PI) / p) + c + b);
	}

	public static float elasticOut(float t, float b, float c, float d, float a,
			float p) {
		float s;
		if (t == 0)
			return b;
		if ((t /= d) == 1)
			return b + c;
		if (a < Math.abs(c)) {
			a = c;
			s = p / 4;
		} else {
			s = p / (2 * (float) Math.PI) * (float) Math.asin(c / a);
		}
		return (a * (float) Math.pow(2, -10 * t)
				* (float) Math.sin((t * d - s) * (2 * (float) Math.PI) / p) + c + b);
	}

	public static float elasticInOut(float t, float b, float c, float d) {
		if (t == 0)
			return b;
		if ((t /= d / 2) == 2)
			return b + c;
		float p = d * (.3f * 1.5f);
		float a = c;
		float s = p / 4;
		if (t < 1)
			return -.5f
					* (a * (float) Math.pow(2, 10 * (t -= 1)) * (float) Math
							.sin((t * d - s) * (2 * (float) Math.PI) / p)) + b;
		return a * (float) Math.pow(2, -10 * (t -= 1))
				* (float) Math.sin((t * d - s) * (2 * (float) Math.PI) / p)
				* .5f + c + b;
	}

	public static float elasticInOut(float t, float b, float c, float d,
			float a, float p) {
		float s;
		if (t == 0)
			return b;
		if ((t /= d / 2) == 2)
			return b + c;
		if (a < Math.abs(c)) {
			a = c;
			s = p / 4;
		} else {
			s = p / (2 * (float) Math.PI) * (float) Math.asin(c / a);
		}
		if (t < 1)
			return -.5f
					* (a * (float) Math.pow(2, 10 * (t -= 1)) * (float) Math
							.sin((t * d - s) * (2 * (float) Math.PI) / p)) + b;
		return a * (float) Math.pow(2, -10 * (t -= 1))
				* (float) Math.sin((t * d - s) * (2 * (float) Math.PI) / p)
				* .5f + c + b;
	}

	public static float backIn(float t, float b, float c, float d) {
		float s = 1.70158f;
		return c * (t /= d) * t * ((s + 1) * t - s) + b;
	}

	public static float backIn(float t, float b, float c, float d, float s) {
		return c * (t /= d) * t * ((s + 1) * t - s) + b;
	}

	public static float backOut(float t, float b, float c, float d) {
		float s = 1.70158f;
		return c * ((t = t / d - 1) * t * ((s + 1) * t + s) + 1) + b;
	}

	public static float backOut(float t, float b, float c, float d, float s) {
		return c * ((t = t / d - 1) * t * ((s + 1) * t + s) + 1) + b;
	}

	public static float backInOut(float t, float b, float c, float d) {
		float s = 1.70158f;
		if ((t /= d / 2) < 1)
			return c / 2 * (t * t * (((s *= (1.525f)) + 1) * t - s)) + b;
		return c / 2 * ((t -= 2) * t * (((s *= (1.525f)) + 1) * t + s) + 2) + b;
	}

	public static float backInOut(float t, float b, float c, float d, float s) {
		if ((t /= d / 2) < 1)
			return c / 2 * (t * t * (((s *= (1.525f)) + 1) * t - s)) + b;
		return c / 2 * ((t -= 2) * t * (((s *= (1.525f)) + 1) * t + s) + 2) + b;
	}

	public static float expoIn(float t, float b, float c, float d) {
		return (t == 0) ? b : c * (float) Math.pow(2, 10 * (t / d - 1)) + b;
	}

	public static float expoOut(float t, float b, float c, float d) {
		return (t == d) ? b + c : c * (-(float) Math.pow(2, -10 * t / d) + 1)
				+ b;
	}

	public static float expoInOut(float t, float b, float c, float d) {
		if (t == 0)
			return b;
		if (t == d)
			return b + c;
		if ((t /= d / 2) < 1)
			return c / 2 * (float) Math.pow(2, 10 * (t - 1)) + b;
		return c / 2 * (-(float) Math.pow(2, -10 * --t) + 2) + b;
	}
}

