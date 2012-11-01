package com.ustudio.loading;

import org.anddev.andengine.entity.Entity;
import org.anddev.andengine.entity.primitive.Rectangle;
import org.anddev.andengine.entity.scene.Scene;
import org.anddev.andengine.entity.sprite.Sprite;
import org.anddev.andengine.entity.text.ChangeableText;
import org.anddev.andengine.entity.text.Text;
import org.anddev.andengine.input.touch.TouchEvent;
import org.anddev.andengine.opengl.font.Font;
import org.anddev.andengine.opengl.font.FontFactory;
import org.anddev.andengine.opengl.texture.Texture;
import org.anddev.andengine.opengl.texture.TextureOptions;
import org.anddev.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.anddev.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.anddev.andengine.opengl.texture.region.TextureRegion;
import org.anddev.andengine.util.HorizontalAlign;

import android.graphics.Color;
import android.util.Log;

import com.ustudio.main.MainActivity;

public class Loader extends Entity{
	static int CAMERA_WIDTH;
	static int CAMERA_HEIGHT;
	
	private float mWidth;
	private float mHeight;
	
	private ChangeableText mText;
	
	private Texture mFontTexture;
	
	private Font mFont;
	
	public Loader(int w, int h, String t) {
		
		CAMERA_WIDTH = w;
		CAMERA_HEIGHT = h;
		
		loadSizes();
		loadTextures();
		drawForm();
		drawText(t);
	}
	
	private void loadSizes()
	{
		this.mWidth=CAMERA_WIDTH*0.85f;
		this.mHeight=CAMERA_HEIGHT*0.19f;
	}
	
	private void loadTextures()
	{
		FontFactory.setAssetBasePath("fonts/");
		
		this.mFontTexture = new BitmapTextureAtlas(256, 256,TextureOptions.BILINEAR_PREMULTIPLYALPHA);

		this.mFont = FontFactory.createFromAsset((BitmapTextureAtlas) this.mFontTexture,MainActivity.getInstance().getApplicationContext(), "georgia.ttf", 16, true,Color.BLACK);
		
		MainActivity.getInstance().getEngine().getTextureManager().loadTexture(this.mFontTexture);
		MainActivity.getInstance().getEngine().getFontManager().loadFont(this.mFont);
	}
	
	private void drawForm()
	{
		Rectangle tmpBG=new Rectangle(0,0,CAMERA_WIDTH,CAMERA_HEIGHT);
		
		Rectangle tmpForm=new Rectangle(0,0,this.mWidth,this.mHeight);
		
		tmpBG.setColor(0, 0, 0);
		tmpBG.setAlpha(0.5f);
		tmpBG.setPosition((CAMERA_WIDTH-this.mWidth)/-2,(CAMERA_HEIGHT-this.mHeight)/-2);
		
		this.attachChild(tmpBG);
		this.attachChild(tmpForm);
	}
	
	private void drawText(String s)
	{		
		this.mText = new ChangeableText(0, 0, this.mFont,s);
		this.mText.setPosition(this.mWidth/2-(this.mText.getWidth()/2), this.mText.getHeight()/2);

		this.attachChild(this.mText);
	}	
	
	//SET
	public void setWidth(float w)
	{
		this.mWidth=w;
	}
	
	public void setHeight(float h)
	{
		this.mHeight=h;
	}
	
	public void setText(ChangeableText t)
	{
		this.mText=t;
	}
	
	
	//GET
	public float getWidth()
	{
		return this.mWidth;
	}
	
	public float getHeight()
	{
		return this.mHeight;
	}
	
	public ChangeableText getText()
	{
		return this.mText;
	}
}
