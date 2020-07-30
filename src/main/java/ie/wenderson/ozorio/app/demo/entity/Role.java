package ie.wenderson.ozorio.app.demo.entity;

public enum Role {
	
	AD("Admin"),
	OP("Operator");

	private String description;

	private Role(String description) {
		this.description = description;
	}

	public String getDescription() {
		return description;
	}
	
	public static Role getByDescription(String description) {
		Role Role = null;
		for(Role value : Role.values()) {
			if(value.getDescription().equalsIgnoreCase(description)) {
				Role = value;
				break;
			}
		}
		return Role;
	}

}
