package org.skillbill.enums;


/**
 * Diese Klasse enthält ein Enum
 * Dieses Enum ist für die Anzeige der Geschlechter, damit nicht extra im Sinne der 3. Normalform eine Tabelle für Standorte eingeführt werden muss
 */
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
