package com.binomed.andengine;

import org.anddev.andengine.entity.modifier.MoveModifier;
import org.anddev.andengine.entity.modifier.PathModifier.Path;
import org.anddev.andengine.entity.sprite.AnimatedSprite;
import org.anddev.andengine.input.touch.TouchEvent;
import org.anddev.andengine.opengl.texture.region.TiledTextureRegion;
import org.anddev.andengine.opengl.vertex.RectangleVertexBuffer;
import org.anddev.andengine.util.modifier.ease.EaseLinear;
import org.anddev.andengine.util.modifier.ease.IEaseFunction;

public class Peg extends AnimatedSprite {
	
	private boolean onRectangle;
	private MovePeg callBack;

	 private static final IEaseFunction[][] EASEFUNCTIONS = new IEaseFunction[][] {
         new IEaseFunction[] { EaseLinear.getInstance(),
                         EaseLinear.getInstance(), EaseLinear.getInstance() },
          };
	
	
	public Peg(float pX, float pY, float pTileWidth, float pTileHeight, TiledTextureRegion pTiledTextureRegion, RectangleVertexBuffer pRectangleVertexBuffer,MovePeg callBack) {
		super(pX, pY, pTileWidth, pTileHeight, pTiledTextureRegion, pRectangleVertexBuffer);
		this.callBack =callBack;
	}

	public Peg(float pX, float pY, float pTileWidth, float pTileHeight, TiledTextureRegion pTiledTextureRegion,MovePeg callBack) {
		super(pX, pY, pTileWidth, pTileHeight, pTiledTextureRegion);
		this.callBack =callBack;
	}

	public Peg(float pX, float pY, TiledTextureRegion pTiledTextureRegion, RectangleVertexBuffer pRectangleVertexBuffer,MovePeg callBack) {
		super(pX, pY, pTiledTextureRegion, pRectangleVertexBuffer);
		this.callBack =callBack;
	}

	public Peg(float pX, float pY, TiledTextureRegion pTiledTextureRegion,MovePeg callBack) {
		super(pX, pY, pTiledTextureRegion);
		this.callBack =callBack;
	}

	
	
	public boolean isOnRectangle() {
		return onRectangle;
	}

	public void setOnRectangle(boolean onRectangle) {
		this.onRectangle = onRectangle;
	}

	@Override
	public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {
		if (pSceneTouchEvent.isActionMove()){
		this.setPosition(pSceneTouchEvent.getX() - this.getWidth() / 2, (pSceneTouchEvent.getY() - this.getHeight() / 2)-50);
		}else if(pSceneTouchEvent.isActionUp()){
			if (onRectangle){
				callBack.animatePeg(this, MonActiviteInteraction.CAMERA_LARGEUR/2, MonActiviteInteraction.CAMERA_HAUTEUR-50);
			}else{
				callBack.animatePeg(this, 50, 50);
				
			}
		}
		return true;
	}
	
	

}
