package ge5;

import java.util.List;

public abstract class GameInterface {

	protected int x;
	protected int y;
	protected int width;
	protected int height;

	protected boolean isVisible;
	protected boolean isFocusable;
	protected boolean isFocused;

	protected static List<GameInterface> orphans;

	protected GameInterface parent;
	protected List<GameInterface> children;

	protected GameInterface getParent(){

		return parent;

	}

	protected List<GameInterface> getChildren(){

		return children;

	}

	protected void setParent(GameInterface newParent){

		parent.orphanChild(this);
		newParent.adoptChild(this);

	}

	protected void orphanChild(GameInterface ... gi){

		for (GameInterface i: gi) {
			children.remove(i);
			i.parent = null;
			orphans.add(i);
		}

	}

	protected void adoptChild(GameInterface ... gi){

		for (GameInterface i: gi) {
			children.add(i);
			i.parent = this;
		}

	}

}
