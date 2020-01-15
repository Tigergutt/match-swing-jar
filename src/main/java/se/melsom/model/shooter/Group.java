package se.melsom.model.shooter;

import se.melsom.model.ShooterDatabase;

public class Group {
	private ShooterDatabase parent;
	private String id;
	private String shortName;
	private String name;

	public Group(ShooterDatabase parent, String id, String shortName, String name) {
		this.parent = parent;
		this.id = id;
		this.shortName = shortName;
		this.name = name;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
		
		parent.setDirty(true);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
		
		parent.setDirty(true);
	}

	public String getShortName() {
		return shortName;
	}

	public void setShortName(String shortName) {
		this.shortName = shortName;
		
		parent.setDirty(true);
	}

}
