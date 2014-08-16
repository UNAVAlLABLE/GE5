// Just a basic UI element to serve as an example

package ge5;

public class Box extends UI {

	int color = 0xffcccccc;

	public Box(int x, int y, int width, int height) {

		orphans.add(this);
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;

	}

	@Override
	public void start() {

	}

	@Override
	public void tick(int skips) {

	}

	@Override
	protected void render() {

	}

}
