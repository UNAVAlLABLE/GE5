package ge5.engine;


public interface tickable {
	
	void init();

	void start();

	void tick(int skips);

}
