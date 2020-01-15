package se.melsom.model.shooter;

import se.melsom.model.ShooterDatabase;

public class Unit {
	private ShooterDatabase parent;
	private String id;
	private String name;
	private String groupID;
	
	public Unit(ShooterDatabase parent, String id, String name, String groupID) {
		this.parent = parent;
		this.id = id;
		this.name = name;
		this.groupID = groupID;
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

	public String getGroupID() {
		return groupID;
	}

	public void setGroupID(String groupID) {
		this.groupID = groupID;
		
		parent.setDirty(true);
	}
}
