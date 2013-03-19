package com.me.recursion;

import com.badlogic.gdx.math.Rectangle;

public class Block extends Rectangle{
	
	public int color, xc, yc;
	

	public int x, y;

	
	public Block(int x, int y, int width, int height, int xc, int yc) {
		this.x = x;
		this.y = y;
		this.set(x, y, width, height);
		this.xc = xc;
		this.yc = yc;
		color = (int) Math.abs((Math.random()*10)-5);
	}
	
	public int getColor() {
		return color;
	}


	public void setColor(int color) {
		this.color = color;
	}


}
