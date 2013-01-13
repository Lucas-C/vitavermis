package ot.physics;

import java.util.ArrayList;

import ot.Entity;


// Only handle gravity, ground vertical limit & ground friction for now

public class PhysicsMgr {
	PhyMgrParams		m_params		= null;
	ArrayList<Entity>	m_Entities		= null;
	ArrayList<Obstacle>	m_Obstacles		= null;
	
	public void init(PhyMgrParams _params) {
		m_params	= _params;
		m_Entities	= new ArrayList<Entity>(m_params.baseEntitiesNb);
		m_Obstacles	= new ArrayList<Obstacle>(m_params.baseObstaclesNb);
	}
	
	public void add(Obstacle _Obstacle) {
		m_Obstacles.add(_Obstacle);
	}

	public void remove(Obstacle _Obstacle) {
		m_Obstacles.remove(_Obstacle);
	}

	public void add(Entity _Entity) {
		m_Entities.add(_Entity);
	}

	public void remove(Entity _Entity) {
		m_Entities.remove(_Entity);
	}

	public void update(int _delta) {
		float frictionSlowFactor = (float) Math.pow(m_params.frictionCoeff, _delta);
		for (Entity item : m_Entities) {
			/** Handling X **/
			item.x += item.speedX * _delta;
			
			/** Handling Y **/			
			// First integration : gravity
			item.speedY += m_params.gravity;
			
			// Second integration : speed
			final float yBottom = item.y + item.halfHeight;
			float newYBottom = yBottom + item.speedY * _delta;
			boolean contact = false;
						
			// Ground test
			if (newYBottom >= m_params.groundHeight) {
				newYBottom = m_params.groundHeight;
				contact = true;
			} else {
				// Obstacles test : COSTLY !
				for (Obstacle obstacle : m_Obstacles) {
					if (item.x >= obstacle.x - obstacle.halfHeight
					&&	item.x <= obstacle.x + obstacle.halfHeight
					&&	yBottom <= obstacle.y
					&&	newYBottom > obstacle.y) {
						System.out.println("Obstacle");
						newYBottom = obstacle.y;
						contact = true;					
						break;
					}
				}
			}
			
			item.y = newYBottom - item.halfHeight;
			if (contact) {
				item.speedY = 0;
				item.isTouchingGround = true;				
			}

			// Friction
			if (item.isTouchingGround) {
				item.speedX *= frictionSlowFactor;
			}
		}
	}
}
