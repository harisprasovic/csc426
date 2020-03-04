package csc426.interp;

public class Loop implements StackEntry {
	public String id;
	public int index;
	public int limit;
	public Position position;

	public boolean isPosition() {
		return false;
	}

	public boolean isLoop() {
		return true;
	}
}
