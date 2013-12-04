package ppg.vitavermis.render;

import java.util.ArrayList;
import java.util.List;

import org.jbox2d.callbacks.DebugDraw;
import org.jbox2d.common.Color3f;
import org.jbox2d.common.OBBViewportTransform;
import org.jbox2d.common.Transform;
import org.jbox2d.common.Vec2;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Circle;
import org.newdawn.slick.geom.Line;
import org.newdawn.slick.geom.Polygon;
import org.newdawn.slick.geom.Shape;

//TODO: Do not ignore Colors
//TODO: Use a pool of Lines like in DebugDrawJ2D to improve perfs
public class DebugDrawPPG extends DebugDraw {
	class RenderString {
		String str; float x, y;
		public RenderString(String str, float x, float y) {
			this.str = str; this.x = x; this.y = y;
		}
	}
	
	private final static boolean INVERT_Y = true;
	private static float convertCoord(float coord) {
		return RenderMgr.SCALE / 2 + coord * RenderMgr.SCALE;
	}

	private List<Shape> debugShapes;
	private List<RenderString> debugStrings;
	private boolean debugRenderedSinceLastAdd = false;
    
	public DebugDrawPPG(RenderMgr renderMgr) {
	    super(new OBBViewportTransform());
		viewportTransform.setYFlip(INVERT_Y);
		this.debugShapes = new ArrayList<Shape>();
		this.debugStrings = new ArrayList<RenderString>();
    }

	void renderDebugShapes(Graphics graphics, long timeSinceStart) {
		//graphics.setColor(Color.white);
		//graphics.setLineWidth(1);
		for (Shape shape : debugShapes) {
			graphics.draw(shape);			
		}
		for (RenderString renderStr : debugStrings) {
			graphics.drawString(renderStr.str, renderStr.x, renderStr.y);			
		}
		graphics.drawString("TIME: " + timeSinceStart / 1000f, 0, 50);
		debugRenderedSinceLastAdd = true;
	}
	
	private void preCheckDebugReset() {
		if (debugRenderedSinceLastAdd) {
			debugShapes.clear();
			debugStrings.clear();
			debugRenderedSinceLastAdd = false;
		}
	}
	
	@Override
	public void drawSegment(Vec2 p1, Vec2 p2, Color3f color) {
		Line segment = new Line(convertCoord(p1.x), convertCoord(p1.y), convertCoord(p2.x), convertCoord(p2.y));
		preCheckDebugReset();
		debugShapes.add(segment);
	}

	@Override
	public void drawSolidCircle(Vec2 center, float radius, Vec2 axis, Color3f color) {
		Circle solidCircle = new Circle(convertCoord(center.x), convertCoord(center.y), radius * RenderMgr.SCALE);
		preCheckDebugReset();
		debugShapes.add(solidCircle);
	}

	@Override
	public void drawSolidPolygon(Vec2[] vertices, int vertexCount, Color3f color) {
		float[] scaledVertices = new float[2 * vertexCount];
		for (int i = 0; i < vertexCount; i++) {
			scaledVertices[2 * i] =  convertCoord(vertices[i].x);
			scaledVertices[2 * i + 1] =  convertCoord(vertices[i].y);
		}
		Polygon solidPolygon = new Polygon(scaledVertices);
		preCheckDebugReset();
		debugShapes.add(solidPolygon);
	}

	@Override
	public void drawString(float x, float y, String s, Color3f color) {
		if (INVERT_Y) {
			y = -y;
		}
		RenderString renderStr = new RenderString(s, convertCoord(x), convertCoord(y));
		preCheckDebugReset();
		debugStrings.add(renderStr);
	}

	@Override
	public void drawCircle(Vec2 center, float radius, Color3f color) {
		Circle circle = new Circle(convertCoord(center.x), convertCoord(center.y), radius * RenderMgr.SCALE);
		preCheckDebugReset();
		debugShapes.add(circle);
	}

	@Override
	public void drawPoint(Vec2 argPoint, float argRadiusOnScreen, Color3f argColor) {
		System.out.println("UNIMPLEMENTED drawPoint()");
	}

	@Override
	public void drawTransform(Transform xf) {
		System.out.println("UNIMPLEMENTED drawTransform: " + xf);
	}
}