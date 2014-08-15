package ge5;

import java.util.Set;

public abstract class GameInterface implements tickable{

	protected int x;
	protected int y;
	protected int xOffset = 0;
	protected int yOffset = 0;
	protected int width;
	protected int height;

	protected boolean isVisible;
	protected boolean isFocusable;
	protected boolean isFocused;

	protected static Set<GameInterface> orphans;

	protected GameInterface parent;
	protected Set<GameInterface> children;

	protected abstract void render();

	protected GameInterface getParent(){

		return parent;

	}

	protected Set<GameInterface> getChildren(){

		return children;

	}

	protected void setParent(GameInterface newParent){

		parent.orphanChild(this);

		if(newParent != null)
			newParent.adoptChild(this);

	}

	protected void orphanChild(GameInterface ... gi){
		for (GameInterface i: gi)

			if(children.remove(i)){

				i.parent = null;
				orphans.add(i);

				// Adjust positions to world space
				i.x = i.xOffset + i.x;
				i.y = i.yOffset + i.y;
				i.xOffset = 0;
				i.yOffset = 0;

			}

	}

	protected void adoptChild(GameInterface ... gi){
		for (GameInterface i: gi){

			// Remove it from its parent's list
			if(orphans.remove(i) == false)
				i.parent.children.remove(i);

			// Adjust positions to new local space
			i.x -= xOffset + x;
			i.y -= yOffset + y;
			i.xOffset = xOffset + x;
			i.yOffset = yOffset + y;

			i.parent = this;
			children.add(i);

		}
	}

}
