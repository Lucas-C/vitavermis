package ot;

import org.newdawn.slick.Image;

//TODO: split "config" attributes & "state" attributes

public class Entity {
	// Graphical attributes
	public Image	img					= null;
	public int		layerLvl			= 0; // Note[Lucas] : could be suppressed -> no need to be keeped at runtime
	
	public float	x					= 0;
	public float	y					= 0;
	
	// Physical attributes
	public float	halfHeight			= 0;

	public boolean	hasWeight			= true;
	public boolean	isTouchingGround	= false;
	public float	speedX				= 0;
	public float	speedY				= 0;

	public boolean	dbgDrawHeight		= false;
}
