package ot;

import org.newdawn.slick.Image;

public class Character extends Entity {
	static final int DIR_LEFT	= 0;
	static final int DIR_RIGHT	= 1;
	//static final int DIR_UP	= 2;
	//static final int DIR_DOWN	= 3;
	
	private int		aimDirection	= DIR_LEFT;
	private Image	leftImg			= null;
	private Image	rightImg		= null;
	
	void setPairImg(Image _imgLeft, Image _imgRight) {
		leftImg = _imgLeft;
		rightImg = _imgRight;
		
		aimDirection = DIR_LEFT;
		img = leftImg;
	}
	
	void setDir(int _dir) {
		if (_dir == DIR_LEFT) {
			if (aimDirection != DIR_LEFT && leftImg != null) {
				aimDirection = DIR_LEFT;
				img = leftImg;
			}
			return;
		}
		if (_dir == DIR_RIGHT) {
			if (aimDirection != DIR_RIGHT && rightImg != null) {
				aimDirection = DIR_RIGHT;
				img = rightImg;
			}
			return;
		}
		assert false;
	}
}
