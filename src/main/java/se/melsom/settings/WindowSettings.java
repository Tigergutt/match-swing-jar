package se.melsom.settings;

public class WindowSettings {
	private ApplicationSettings parent;
	private String name;
	private int x;
	private int y;
	private int width;
	private int height;
	private boolean isVisible;

	public WindowSettings(String name, int x, int y, int width, int height, boolean isVisible) {
		this.name = name;
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.isVisible = isVisible;
	}
	
	public void setParent(ApplicationSettings parent) {
		this.parent = parent;
	}
	
	public String getName() {
		return name;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
		
		if (parent != null) {
			parent.setDirty(true);
		}
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
		
		if (parent != null) {
			parent.setDirty(true);
		}
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
		
		if (parent != null) {
			parent.setDirty(true);
		}
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
		
		if (parent != null) {
			parent.setDirty(true);
		}
	}

	public boolean isVisible() {
		return isVisible;
	}

	public void setVisible(boolean isVisible) {
		this.isVisible = isVisible;
		
		if (parent != null) {
			parent.setDirty(true);
		}
	}

}
