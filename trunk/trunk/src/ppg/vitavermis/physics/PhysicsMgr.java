package ppg.vitavermis.physics;

import java.util.Arrays;

import org.jbox2d.callbacks.DebugDraw;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.World;
 
public class PhysicsMgr {
	/* From Box2D manual:
	[On keeping an invariable time step] A variable time step produces variable results,
	which makes it difficult to debug. So don't tie the time step to your frame rate.
	[...]
	There are two phases in the constraint solver: a velocity phase and a position phase.
	In the velocity phase the solver computes the impulses necessary for the bodies to move correctly.
	In the position phase the solver adjusts the positions of the bodies to reduce overlap and joint detachment.
	Each phase has its own iteration count. In addition, the position phase may exit iterations early
	if the errors are small.

	The suggested iteration count for Box2D is 8 for velocity and 3 for position.
	You can tune this number to your liking, just keep in mind that this has a trade-off between speed and accuracy.
	Using fewer iterations increases performance but accuracy suffers.
	Likewise, using more iterations decreases performance but improves the quality of your simulation. 
	*/
	private final static float BOX2D_TIME_STEP_SECONDS = 1.0f / 60.0f; // 60Hz
	private final static float BOX2D_TIME_STEP_MS = BOX2D_TIME_STEP_SECONDS * 1000;
	private final static int BOX2D_VELOCITY_ITERATIONS = 6;
	private final static int BOX2D_POSITION_ITERATIONS = 2;
	
	private World box2dWorld;
	private float time_since_last_update_ms = 0;
	
	
	public PhysicsMgr() {
		Vec2 gravity = new Vec2(0.0f, 10.0f);
		this.box2dWorld = new World(gravity);
	}
	
	public void init(DebugDraw debugDraw)  {
		this.box2dWorld.setDebugDraw(debugDraw);
		// SEE DebugDrawJ2D for an example of implementation using Java2D
	}

	public void update(int delta_ms) {
		this.time_since_last_update_ms += delta_ms;
		while (this.time_since_last_update_ms > BOX2D_TIME_STEP_MS) {
			this.time_since_last_update_ms -= BOX2D_TIME_STEP_MS;
			this.box2dWorld.step(BOX2D_TIME_STEP_SECONDS, BOX2D_VELOCITY_ITERATIONS, BOX2D_POSITION_ITERATIONS);
			//printBodies(this.box2dWorld);
			this.box2dWorld.drawDebugData();
		}
	}
	
	private static void printBodies(World w) {
		Body body = w.getBodyList();
		do {
			System.out.println("Body 1st fixture:" + body.getFixtureList().m_shape);			
			body = body.getNext();
		} while (body != null);
	}
	
	// TODO: deleting a body can be more tricky, due to sync issues :
	// best solution seems to 'flag' them somehow and batch process removals during the update()
	public final ItemState createItem(String itemName, ItemModel model, Vec2 position) {
		model.bodyDef.position.set(position);
		Body body = this.box2dWorld.createBody(model.bodyDef);
        body.createFixture(model.fixtureDef);
        return new ItemState(itemName, model, body);
	}
}
