package org.skillbill.enums;

public enum StandortEnum {
	
	DÜSSELDORF("Düsseldorf"), 
	MÜNSTER("Münster" ), 
	LEVERKUSEN("Leverkusen"), 
	RATTINGEN("Rattingen"), 
	DORTMUND( "Dortmund" ), 
	KÖLN("Köln" ), 
	HAMBURG(  "Hamburg"),
	BERLIN ("Berlin");

	
	private final String standort;

    /**
     * @param standort
     */
    private StandortEnum(final String standort) {
        this.standort = standort;
    }

    /* (non-Javadoc)
     * @see java.lang.Enum#toString()
     */
    @Override
    public String toString() {
        return standort;
    }

}
