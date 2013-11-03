package ppg.vitavermis.render;

public class Sprite {
	final public String name;
	final SpriteModel model;
	final PositionProvider posRef;

	Sprite(String name, SpriteModel model, PositionProvider posRef) {	
		this.name = name;
		this.model = model;
		this.posRef = posRef;
	}
}
