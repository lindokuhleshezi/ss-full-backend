package za.ac.ss.enums;

public enum DocumentProcess {
	
	APPROVED("Approved"), REJECTED("Rejected"), PENDING("Pending");
	
	private final String name;

	DocumentProcess(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}
}
