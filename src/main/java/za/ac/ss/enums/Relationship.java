package za.ac.ss.enums;

public enum Relationship {
	
	SPOUSE("Spouse"), SON("Son"), DAUGTHER("Daugther"), AUNT("Aunt"), FATHER("Father"), MOTHER("Mother"), OTHER("Other");

	private final String name;

	Relationship(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}
}
