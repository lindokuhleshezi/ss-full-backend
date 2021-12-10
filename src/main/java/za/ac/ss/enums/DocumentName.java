package za.ac.ss.enums;

public enum DocumentName {
	
	RAF_DOCUMENT("slabbert");

	private final String documentName;

	DocumentName(String documentName) {
		this.documentName = documentName;
	}

	public String getDocumentName() {
		return documentName;
	}
	
}
