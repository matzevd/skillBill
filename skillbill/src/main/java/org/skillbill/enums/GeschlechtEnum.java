package org.skillbill.enums;
/**
 * Diese Klasse enthält ein Enum
 * Dieses Enum ist für die Anzeige der Geschlechter, damit nicht extra im Sinne der 3. Normalform eine Tabelle für Geschlecht eingeführt werden muss
 */
public enum GeschlechtEnum {
	
	WEIBLICH ("weiblich"),
	MAENNLICH ("maennlich");
	
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
