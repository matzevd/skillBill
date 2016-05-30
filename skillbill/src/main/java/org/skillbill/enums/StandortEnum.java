package org.skillbill.enums;

public enum StandortEnum {
	
	DUESSELDORF("Duesseldorf"), 
	MUENSTER("Muenster" ), 
	LEVERKUSEN("Leverkusen"), 
	RATTINGEN("Rattingen"), 
	DORTMUND( "Dortmund" ), 
	KOELN("Koeln" ), 
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
