package ppg.vitavermis.maincharacter;

import ppg.vitavermis.entity.EntityModel;
import ppg.vitavermis.entity.EntityVisPhy;
import ppg.vitavermis.entity.moveable.MoveableBehaviour;
import ppg.vitavermis.entity.moveable.MoveableEntity;
import ppg.vitavermis.physics.ItemState;
import ppg.vitavermis.render.Sprite;

public class MainCharacter extends EntityVisPhy implements MoveableEntity {
	public final static float MAX_SPEED = 5;
	public final static float FAST_SPEED_CHANGE_FACTOR = 0.1f;
	public final static float AUTO_IDLE_FRAMES_COUNT = 60;

	public MainCharacter(EntityModel model, ItemState itemPhy, Sprite sprite) {
		super(model, itemPhy, sprite);
		addBehaviour(new MoveableBehaviour(this, MAX_SPEED, FAST_SPEED_CHANGE_FACTOR, AUTO_IDLE_FRAMES_COUNT));
	}
}