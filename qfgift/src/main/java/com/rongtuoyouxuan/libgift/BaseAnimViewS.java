package com.rongtuoyouxuan.libgift;

import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Interpolator;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;

/**
 * 说明：BaseAnimViewS
 * <p/>
 * 作者：fanly
 * <p/>
 * 类型：interface
 * <p/>
 * 时间：2016/12/12 10:40
 * <p/>
 * 版本：verson 1.0
 */
public class BaseAnimViewS {
	public static long animDuration = 600;
	public static long longAnimDuration = 1000;
	public static int width, height;

	public static Animation SlideFromBottom(Interpolator inter) {
		TranslateAnimation transAnim = new TranslateAnimation(Animation.RELATIVE_TO_PARENT, 0,
				Animation.RELATIVE_TO_PARENT, 0, Animation.RELATIVE_TO_PARENT,
				1.0f, Animation.RELATIVE_TO_PARENT, 0);
		transAnim.setFillAfter(true);
		transAnim.setDuration(animDuration);
		if (inter != null) {
			transAnim.setInterpolator(inter);
		}
		return transAnim;
	}

	public static Animation SlideToBottom(Interpolator inter) {
		TranslateAnimation transAnim = new TranslateAnimation(Animation.RELATIVE_TO_PARENT, 0,
				Animation.RELATIVE_TO_PARENT, 0, Animation.RELATIVE_TO_PARENT,
				0, Animation.RELATIVE_TO_PARENT, 1.0f);
		transAnim.setFillAfter(true);
		transAnim.setDuration(animDuration);
		if (inter != null) {
			transAnim.setInterpolator(inter);
		}

		return transAnim;
	}

	public static Animation SlideFromTop(Interpolator inter) {
		TranslateAnimation transAnim = new TranslateAnimation(Animation.RELATIVE_TO_PARENT, 0,
				Animation.RELATIVE_TO_PARENT, 0, Animation.RELATIVE_TO_PARENT,
				-1.0f, Animation.RELATIVE_TO_PARENT, 0);
		transAnim.setFillAfter(true);
		transAnim.setDuration(animDuration);
		if (inter != null) {
			transAnim.setInterpolator(inter);
		}
		return transAnim;
	}

	public static Animation SlideToTop(Interpolator inter) {
		TranslateAnimation transAnim = new TranslateAnimation(Animation.RELATIVE_TO_PARENT, 0,
				Animation.RELATIVE_TO_PARENT, 0, Animation.RELATIVE_TO_PARENT,
				0, Animation.RELATIVE_TO_PARENT, -1.0f);
		transAnim.setFillAfter(true);
		transAnim.setDuration(animDuration);
		if (inter != null) {
			transAnim.setInterpolator(inter);
		}

		return transAnim;
	}

	public static Animation SlideFromLeft(Interpolator inter) {
		TranslateAnimation transAnim = new TranslateAnimation(Animation.RELATIVE_TO_PARENT, -1.0f,
				Animation.RELATIVE_TO_PARENT, 0, Animation.RELATIVE_TO_PARENT,
				0, Animation.RELATIVE_TO_PARENT, 0);
		transAnim.setFillAfter(true);
		transAnim.setDuration(animDuration);
		if (inter != null) {
			transAnim.setInterpolator(inter);
		}
		return transAnim;
	}

	public static Animation SlideToLeft(Interpolator inter) {
		TranslateAnimation transAnim = new TranslateAnimation(Animation.RELATIVE_TO_PARENT, 0,
				Animation.RELATIVE_TO_PARENT, -1.0f,
				Animation.RELATIVE_TO_PARENT, 0, Animation.RELATIVE_TO_PARENT,
				0f);
		transAnim.setFillAfter(true);
		transAnim.setDuration(animDuration);
		if (inter != null) {
			transAnim.setInterpolator(inter);
		}

		return transAnim;
	}

	public static Animation FadingIn() {
		AlphaAnimation alphaAnim = new AlphaAnimation(0, 1);
		alphaAnim.setFillAfter(true);
		alphaAnim.setDuration(animDuration);
		return alphaAnim;
	}

	public static Animation FadingOut() {
		AlphaAnimation alphaAnim = new AlphaAnimation(1, 0);
		alphaAnim.setFillAfter(true);
		alphaAnim.setDuration(animDuration);
		return alphaAnim;
	}

	public static Animation RotaLeftCenterIn(Interpolator inter) {
		RotateAnimation rotateAnim = new RotateAnimation(-90, 0, Animation.RELATIVE_TO_PARENT,
				0f, Animation.RELATIVE_TO_PARENT, 0.5f);
		if (inter != null) {
			rotateAnim.setInterpolator(inter);
		}
		rotateAnim.setFillAfter(true);
		rotateAnim.setDuration(animDuration);
		return rotateAnim;

	}

	public static Animation RotaLeftCenterOut(Interpolator inter) {
		RotateAnimation rotateAnim = new RotateAnimation(0, 180, Animation.RELATIVE_TO_PARENT,
				0f, Animation.RELATIVE_TO_PARENT, 0.5f);
		if (inter != null) {
			rotateAnim.setInterpolator(inter);
		}
		rotateAnim.setFillAfter(true);
		rotateAnim.setDuration(animDuration);
		return rotateAnim;

	}

	public static Animation RotaLeftTopIn(Interpolator inter) {
		RotateAnimation rotateAnim = new RotateAnimation(-90, 0, Animation.RELATIVE_TO_PARENT,
				0f, Animation.RELATIVE_TO_PARENT, 0f);
		if (inter != null) {
			rotateAnim.setInterpolator(inter);
		}
		rotateAnim.setFillAfter(true);
		rotateAnim.setDuration(animDuration);
		return rotateAnim;

	}

	public static Animation RotaLeftTopOut(Interpolator inter) {
		RotateAnimation rotateAnim = new RotateAnimation(0, -90, Animation.RELATIVE_TO_PARENT,
				0f, Animation.RELATIVE_TO_PARENT, 0f);
		if (inter != null) {
			rotateAnim.setInterpolator(inter);
		}
		rotateAnim.setFillAfter(true);
		rotateAnim.setDuration(animDuration);
		return rotateAnim;

	}

	public static Animation RotaCenterIn(Interpolator inter) {
		RotateAnimation rotateAnim = new RotateAnimation(0, 360, Animation.RELATIVE_TO_PARENT,
				0.5f, Animation.RELATIVE_TO_PARENT, 0.5f);
		if (inter != null) {
			rotateAnim.setInterpolator(inter);
		}
		rotateAnim.setFillAfter(true);
		rotateAnim.setDuration(animDuration);
		return rotateAnim;

	}

	public static Animation RotaCenterOut(Interpolator inter) {
		RotateAnimation rotateAnim = new RotateAnimation(0, -360, Animation.RELATIVE_TO_PARENT,
				0.5f, Animation.RELATIVE_TO_PARENT, 0.5f);
		if (inter != null) {
			rotateAnim.setInterpolator(inter);
		}
		rotateAnim.setFillAfter(true);
		rotateAnim.setDuration(animDuration);
		return rotateAnim;

	}

	public static Animation ScaleBig(Interpolator inter) {
		ScaleAnimation scaleAnim = new ScaleAnimation(0, 1.0f, 0, 1.0f,
				Animation.RELATIVE_TO_PARENT, 0.5f,
				Animation.RELATIVE_TO_PARENT, 0.5f);
		if (inter != null) {
			scaleAnim.setInterpolator(inter);
		}
		scaleAnim.setFillAfter(true);
		scaleAnim.setDuration(animDuration);
		return scaleAnim;

	}

	public static Animation ScaleSmall(Interpolator inter) {
		ScaleAnimation scaleAnim = new ScaleAnimation(1.0f, 0, 1.0f, 0,
				Animation.RELATIVE_TO_PARENT, 0.5f,
				Animation.RELATIVE_TO_PARENT, 0.5f);
		if (inter != null) {
			scaleAnim.setInterpolator(inter);
		}
		scaleAnim.setFillAfter(true);
		scaleAnim.setDuration(animDuration);
		return scaleAnim;

	}

	public static Animation ScaleBigLeftTop(Interpolator inter) {
		ScaleAnimation scaleAnim = new ScaleAnimation(0, 1.0f, 0, 1.0f,
				Animation.RELATIVE_TO_PARENT, 0, Animation.RELATIVE_TO_PARENT,
				0);
		if (inter != null) {
			scaleAnim.setInterpolator(inter);
		}
		scaleAnim.setFillAfter(true);
		scaleAnim.setDuration(animDuration);
		return scaleAnim;

	}

	public static Animation ScaleSmallLeftTop(Interpolator inter) {
		ScaleAnimation scaleAnim = new ScaleAnimation(1.0f, 0, 1.0f, 0,
				Animation.RELATIVE_TO_PARENT, 0, Animation.RELATIVE_TO_PARENT,
				0);
		if (inter != null) {
			scaleAnim.setInterpolator(inter);
		}
		scaleAnim.setFillAfter(true);
		scaleAnim.setDuration(animDuration);
		return scaleAnim;

	}

	public static Animation ScaleToBigHorizontalIn(Interpolator inter) {
		ScaleAnimation scaleAnim = new ScaleAnimation(0f, 1.0f, 1.0f, 1.0f,
				Animation.RELATIVE_TO_PARENT, 0.5f,
				Animation.RELATIVE_TO_PARENT, 0);
		if (inter != null) {
			scaleAnim.setInterpolator(inter);
		}
		scaleAnim.setFillAfter(true);
		scaleAnim.setDuration(animDuration);
		return scaleAnim;

	}

	public static Animation ScaleToBigHorizontalOut(Interpolator inter) {
		ScaleAnimation scaleAnim = new ScaleAnimation(1.0f, 0f, 1.0f, 1.0f,
				Animation.RELATIVE_TO_PARENT, 0.5f,
				Animation.RELATIVE_TO_PARENT, 0);
		if (inter != null) {
			scaleAnim.setInterpolator(inter);
		}
		scaleAnim.setFillAfter(true);
		scaleAnim.setDuration(animDuration);
		return scaleAnim;

	}

	public static Animation ScaleToBigVerticalIn(Interpolator inter) {
		ScaleAnimation scaleAnim = new ScaleAnimation(1.0f, 1.0f, 0f, 1.0f,
				Animation.RELATIVE_TO_PARENT, 0f, Animation.RELATIVE_TO_PARENT,
				0.5f);
		if (inter != null) {
			scaleAnim.setInterpolator(inter);
		}
		scaleAnim.setFillAfter(true);
		scaleAnim.setDuration(animDuration);
		return scaleAnim;

	}

	public static Animation ScaleToBigVerticalOut(Interpolator inter) {
		ScaleAnimation scaleAnim = new ScaleAnimation(1.0f, 1.0f, 1.0f, 0f,
				Animation.RELATIVE_TO_PARENT, 0f, Animation.RELATIVE_TO_PARENT,
				0.5f);
		if (inter != null) {
			scaleAnim.setInterpolator(inter);
		}
		scaleAnim.setFillAfter(true);
		scaleAnim.setDuration(animDuration);
		return scaleAnim;

	}

	public static Animation ShakeMode(Interpolator inter, Integer shakeCount) {
		TranslateAnimation transAnim = new TranslateAnimation(Animation.RELATIVE_TO_PARENT, -0.1f,
				Animation.RELATIVE_TO_PARENT, 0.1f,
				Animation.RELATIVE_TO_PARENT, 0, Animation.RELATIVE_TO_PARENT,
				0);
		if (shakeCount == null) {
			transAnim.setRepeatCount(1);
		} else {
			transAnim.setRepeatCount(shakeCount);
		}
		transAnim.setDuration(400);
		if (inter != null) {
			transAnim.setInterpolator(inter);
		} else {
			transAnim.setInterpolator(BaseEffects.getBounceEffect());
		}
		return transAnim;
	}

	/************************************************************************/

	public static Animation SlideFromRight(Interpolator inter) {
		TranslateAnimation transAnim = new TranslateAnimation(Animation.RELATIVE_TO_PARENT, 1.0f,
				Animation.RELATIVE_TO_PARENT, 0, Animation.RELATIVE_TO_PARENT,
				0, Animation.RELATIVE_TO_PARENT, 0);
		transAnim.setFillAfter(true);
		transAnim.setDuration(animDuration);
		if (inter != null) {
			transAnim.setInterpolator(inter);
		}
		return transAnim;
	}

	public static Animation SlideToRight(Interpolator inter) {
		TranslateAnimation transAnim = new TranslateAnimation(Animation.RELATIVE_TO_PARENT, 0,
				Animation.RELATIVE_TO_PARENT, 1.0f,
				Animation.RELATIVE_TO_PARENT, 0, Animation.RELATIVE_TO_PARENT,
				0f);
		transAnim.setFillAfter(true);
		transAnim.setDuration(animDuration);
		if (inter != null) {
			transAnim.setInterpolator(inter);
		}

		return transAnim;
	}
}
