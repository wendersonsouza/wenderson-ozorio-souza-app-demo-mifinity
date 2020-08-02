package ie.wenderson.ozorio.app.demo.entity;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape=JsonFormat.Shape.OBJECT)
public enum Role {
	
	AD("Administrator"),
	OP("Operator");

	private String description;
	private String name;

	private Role(String description) {
		this.name = name();
		this.description = description;
	}

	public String getDescription() {
		return description;
	}
	
	public String getName() {
		return name;
	}
	
	public static Role getByName(String name) {
		Role Role = null;
		for(Role value : Role.values()) {
			if(value.getName().equalsIgnoreCase(name)) {
				Role = value;
				break;
			}
		}
		return Role;
	}


}
