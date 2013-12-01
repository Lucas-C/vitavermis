package ppg.vitavermis.physics;

import java.util.Arrays;

import org.jbox2d.collision.shapes.CircleShape;
import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.collision.shapes.Shape;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;

import ppg.vitavermis.physics.ItemModel.Builder;

/*
 * Immutable characteristics shared by multiple physical items : shape, dimensions, friction, weight...
 * We use the builder pattern to create objects :
 *   ItemModel model = ItemModel.newDynamicItemModel("AppleModel").asCircle(0.5f).withFriction(0.4f).build();
 * 
 */
public class ItemModel {

	final public String name;
	final BodyDef bodyDef; // The 'position' attribute should NEVER be used outside the PhysicsMgr
	final FixtureDef fixtureDef;
	
	ItemModel(Builder builder) {
		BodyDef bodyDef = new BodyDef();
		bodyDef.type = builder.bodyType;
		
		FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.shape = builder.shape;
		fixtureDef.density = builder.density;
		fixtureDef.friction = builder.friction;
		fixtureDef.restitution = builder.restitution;
		
		this.name			= builder.name;
		this.bodyDef		= bodyDef;
		this.fixtureDef		= fixtureDef;
	}
	
	
	public final static Builder newDynamicItemModel(String modelName) {
		return new Builder(modelName, BodyType.DYNAMIC);
	}
	
	public final static Builder newStaticItemModel(String modelName) {
		return new Builder(modelName, BodyType.STATIC);
	}
	
	public static class Builder {
	    // Required
	    private final String name;
	    private final BodyType bodyType;

	    // Optional (default values copied from FixtureDef definition)
	    private float density = 0f; // in kg/m^2
	    private float friction = 0.2f; // in [0,1]
	    private float restitution = 0f; // elasticity, in [0,1]
	    private Shape shape = null;
	    
	    public Builder asCircle(float radius) {
			assert radius > 0;
	    	CircleShape circle = new CircleShape();
	    	circle.m_radius = radius;
	    	this.shape = circle;
	    	return this;
	    }

		public Builder asBox(float halfwidth, float halfheight) {
			assert halfwidth > 0 && halfheight > 0;
			PolygonShape polygon = new PolygonShape();
			polygon.setAsBox(halfwidth, halfheight);
	    	this.shape = polygon;
	        return this;
		}

		public Builder withDensity(float density) {
	    	this.density = density;
	    	return this;
	    }
	    
	    public Builder withFriction(float friction) {
	    	this.friction = friction;
	    	return this;
	    }
	    
	    public Builder withRestitution(float restitution) {
	    	this.restitution = restitution;
	    	return this;
	    }
	    
	    Builder(String name, BodyType bodyType) {
	    	this.name = name;
	    	this.bodyType = bodyType;
	    }
	    
	    public ItemModel build() {
	    	return new ItemModel(this);
	    }
	}
}
