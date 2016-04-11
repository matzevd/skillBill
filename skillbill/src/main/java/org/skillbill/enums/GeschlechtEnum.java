package org.skillbill.enums;

public enum GeschlechtEnum {
	
	WEIBLICH ("weiblich"),
	MÄNNLICH ("männlich");
	
	private final String text;

    /**
     * @param text
     */
    private GeschlechtEnum(final String text) {
        this.text = text;
    }

    /* (non-Javadoc)
     * @see java.lang.Enum#toString()
     */
    @Override
    public String toString() {
        return text;
    }


}
