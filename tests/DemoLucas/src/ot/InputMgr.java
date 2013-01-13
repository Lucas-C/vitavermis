package ot;

import org.newdawn.slick.Input;

import ot.physics.PhysicsMgr;



public class InputMgr {

	Player		m_player	= null;
	PhysicsMgr	m_physics	= null;
	
	InputMgr(Player _player, PhysicsMgr _physics) {
		m_player	= _player;
		m_physics	= _physics;
	}
	
	void update(Input _input, int _delta) {
		boolean moveLeft = _input.isKeyDown(Input.KEY_Q); 
		boolean moveRight = _input.isKeyDown(Input.KEY_D); 
		if (moveLeft && !moveRight) {
			m_player.speedX = -m_player.runSpeedX;
			m_player.setDir(Character.DIR_LEFT);
		} else if (!moveLeft && moveRight) {
			m_player.speedX = m_player.runSpeedX;
			m_player.setDir(Character.DIR_RIGHT);
		} else {
			m_player.speedX = 0;
		}

		if (_input.isKeyDown(Input.KEY_Z) && !m_player.isJumping()) {
			m_player.speedY = m_player.jumpSpeed;
		}
	}
	
}
