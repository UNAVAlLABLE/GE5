package ge5;

import java.util.HashSet;
import java.util.Set;

public abstract class UI implements tickable{

	protected static Set<UI> orphans = new HashSet<UI>();

	protected int x;
	protected int y;
	protected int xOffset = 0;
	protected int yOffset = 0;
	protected int width;
	protected int height;

	protected boolean isVisible;
	protected boolean isFocusable;
	protected boolean isFocused;

	protected UI parent;
	protected Set<UI> children = new HashSet<UI>();

	protected abstract void render();

	protected UI getParent(){

		return parent;

	}

	protected Set<UI> getChildren(){

		return children;

	}

	protected void setParent(UI newParent){

		parent.orphanChild(this);
		if(newParent != null)
			newParent.adoptChild(this);

	}

	protected void orphanChild(UI ... children){

		for (UI child: children)

			if(this.children.remove(child)){

				child.parent = null;
				orphans.add(child);

				// Adjust positions to world space
				child.x += child.xOffset;
				child.y += child.yOffset;
				child.xOffset = 0;
				child.yOffset = 0;

			}

	}

	protected void adoptChild(UI ... children){

		for (UI child: children){

			// Remove it from its parent's list
			if(orphans.remove(child) == false)
				child.parent.children.remove(child);

			// Adjust positions to new local space
			child.x -= xOffset + x;
			child.y -= yOffset + y;
			child.xOffset = xOffset + x;
			child.yOffset = yOffset + y;

			child.parent = this;
			this.children.add(child);

		}

	}

	public static void renderAll(){

		for (UI child: orphans){
			child.renderChildren();
			child.render();
		}

	}

	protected void renderChildren(){

		for (UI child: children){

			child.renderChildren();
			render();

		}

	}

}
