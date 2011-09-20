package com.binomed.andengine;

import org.anddev.andengine.entity.sprite.AnimatedSprite;

public interface MovePeg {
	void animatePeg(final AnimatedSprite sprite, final float y, final float x);

	void pegMove(final AnimatedSprite sprite);
}
