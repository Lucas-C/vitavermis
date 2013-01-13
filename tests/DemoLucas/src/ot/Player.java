package ot;

public class Player extends Character {
	float runSpeedX = 0.5f;
	float jumpSpeed = -0.8f;
	
	boolean isJumping() {
		return Math.abs(speedY) > 0.0001;
	}
}
