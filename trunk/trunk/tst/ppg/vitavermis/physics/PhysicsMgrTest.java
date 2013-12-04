package ppg.vitavermis.physics;

import static java.lang.String.format;
import static java.lang.Math.abs;

import org.jbox2d.common.Vec2;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

import ppg.vitavermis.physics.PhysicsMgr;

public class PhysicsMgrTest {
	private PhysicsMgr physics_mgr;

	@Before
	public final void setUp() {
		this.physics_mgr = new PhysicsMgr();
		this.physics_mgr.init(null);
	}
	
	private final void setGround() {
		ItemModel groundModel = ItemModel.newStaticItemModel("GroundModel").asBox(10f, 0.01f).build();
		this.physics_mgr.createItem("Ground", groundModel, new Vec2(0, 0));
	}
	
	/*
	 * An 1kg apple of radius 0.05 is falling from position (0, 0) during 1 second
	 */
	@Test
	public final void appleFallingTest() {
		ItemModel appleModel = ItemModel.newDynamicItemModel("AppleModel").asCircle(0.05f).build();
		ItemState apple = this.physics_mgr.createItem("Apple", appleModel, new Vec2(0, 0));
		
		// All Box2D objects seem to default their mass to 1kg
		float expected_mass_in_kg = 1f;
		float apple_mass_in_kg = apple.getMassInKg();
		assertTrue(format("apple_mass=%.2f", apple_mass_in_kg), abs(expected_mass_in_kg - apple_mass_in_kg) < 0.01);

		int delta_ms = 1000; // 1 second
		this.physics_mgr.update(delta_ms);
		
		// An object of mass 1kg falling from a height of 1m during 1s will cover 4.92m
		float expected_pos_y = -4.92f;
		float apple_pos_y = apple.getPos().y;
		assertTrue(format("apple_pos_y=%.2f", apple_pos_y), (abs(expected_pos_y - apple_pos_y) < 0.01));
	}
	
	/*
	 * An 1kg apple of radius 0.05 is falling from position (0, 2) during 1 second to the ground at y=0
	 */
	@Test
	public final void appleFallingOnGroundTest() {
		this.setGround();
		
		ItemModel appleModel = ItemModel.newDynamicItemModel("AppleModel").asCircle(0.05f).build();
		ItemState apple = this.physics_mgr.createItem("Apple", appleModel, new Vec2(0, 2));
		
		int delta_ms = 1000; // 1 second
		this.physics_mgr.update(delta_ms);
		
		// The apple with stop on the ground, at an horizontal position equal to ground half-height + apple radius.
		float expected_pos_y = 0.01f + 0.05f;
		float apple_pos_y = apple.getPos().y;
		assertTrue(format("apple_pos_y=%.2f", apple_pos_y), (abs(expected_pos_y - apple_pos_y) < 0.01));
	}
}