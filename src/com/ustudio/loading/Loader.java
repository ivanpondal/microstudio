package com.ustudio.loading;

import org.anddev.andengine.entity.Entity;
import org.anddev.andengine.entity.primitive.Rectangle;
import org.anddev.andengine.entity.text.ChangeableText;
import org.anddev.andengine.opengl.font.Font;
import org.anddev.andengine.opengl.font.FontFactory;
import org.anddev.andengine.opengl.texture.Texture;
import org.anddev.andengine.opengl.texture.TextureOptions;
import org.anddev.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.anddev.andengine.util.HorizontalAlign;

import android.graphics.Color;
import android.util.DisplayMetrics;
import android.util.Log;

import com.ustudio.main.MainActivity;

public class Loader extends Entity{
	static int CAMERA_WIDTH;
	static int CAMERA_HEIGHT;
	
	private float mWidth;
	private float mHeight;
	private float mPBWidth;
	private float mPBHeight;
	private float mPB_x;
	private float mPB_y;
	private float mFontSize;
	
	private Rectangle mProgress;
	
	private ChangeableText mText;
	private ChangeableText mPercent;
	
	private Texture mFontTexture;
	
	private Font mFont;
	
	public Loader(int w, int h) {
		
		CAMERA_WIDTH = w;
		CAMERA_HEIGHT = h;
		
		loadSizes();
		loadTextures();
		drawForm();
		drawProgressBar();
		drawText();
	}
	
	private void loadSizes()
	{
		this.mWidth=CAMERA_WIDTH*0.85f;
		this.mHeight=CAMERA_HEIGHT*0.19f;
		this.mPBWidth=this.mWidth*0.85f;
		this.mPBHeight=this.mHeight*0.4f;
		this.mFontSize=CAMERA_HEIGHT/27.5f;
	}
	
	//PRIVATE
	
	private void loadTextures()
	{
		FontFactory.setAssetBasePath("fonts/");
		
		Log.d("Piano","height: "+CAMERA_HEIGHT);
		
		this.mFontTexture = new BitmapTextureAtlas(256, 256,TextureOptions.BILINEAR);

		this.mFont = FontFactory.createFromAsset((BitmapTextureAtlas) this.mFontTexture,MainActivity.getInstance().getApplicationContext(), "cambria.ttf", this.mFontSize, true,Color.WHITE);
		
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
	
	private void drawProgressBar()
	{
		float barX,barY;
		float perX,perY;
		
		this.mPercent = new ChangeableText(0, 0, this.mFont,"x",HorizontalAlign.CENTER,4);
		
		Rectangle tmpBG=new Rectangle(0,0,this.mPBWidth,this.mPBHeight);
		
		this.mProgress=new Rectangle(0,0,0,this.mPBHeight);
		
		this.mPB_x=this.mWidth/2-(this.mPBWidth/2);
		this.mPB_y=this.mPBHeight*1.25f;
		
		perX=this.mPB_x+(this.mPBWidth-this.mPercent.getWidth())/2;
		perY=this.mPB_y+(this.mPBHeight-this.mPercent.getHeight())/2;
		
		tmpBG.setColor(0, 0, 0);
		tmpBG.setPosition(this.mPB_x, this.mPB_y);
		
		this.mProgress.setAlpha(0.5f);
		this.mProgress.setPosition(this.mPB_x, this.mPB_y);
		
		this.mPercent.setPosition(perX, perY);
		
		this.attachChild(tmpBG);
		this.attachChild(this.mProgress);
		this.attachChild(this.mPercent);
	}
	
	private void drawText()
	{	
		this.mText = new ChangeableText(0, 0, this.mFont,"x",HorizontalAlign.CENTER,40);
		this.mText.setColor(0, 0, 0);
		this.mText.setPosition(this.mWidth/2-(this.mText.getWidth()/2), this.mText.getHeight()/2);

		this.attachChild(this.mText);
	}	
	
	//PUBLIC
	public void updateText(String s)
	{
		this.mText.setText(s);
		this.mText.setPosition(this.mWidth/2-(this.mText.getWidth()/2), this.mText.getY());
	}
	
	public void updatePercent(String s)
	{
		this.mPercent.setText(s);
		this.mPercent.setPosition(this.mPB_x+(this.mPBWidth-this.mPercent.getWidth())/2, this.mPercent.getY());
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
	
	public void setProgress(Rectangle p)
	{
		this.mProgress=p;
	}
	
	public void setPercent(ChangeableText p)
	{
		this.mPercent=p;
	}
	
	//GET
	public float getPBWidth()
	{
		return this.mPBWidth;
	}
	
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
	
	public Rectangle getProgress()
	{
		return this.mProgress;
	}
	
	public ChangeableText getPercent()
	{
		return this.mPercent;
	}
}
