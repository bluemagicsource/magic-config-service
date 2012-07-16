package org.bluemagic.config.service.dao;
/* HISTORICAL PROPERTIES OVERVIEW
 * Same layout as the PROPERTIES table, anytime there is an update or deletion in the PROPERTIES table
 * the value that is deleted or the value before the update is copied to the HISTORICAL_PROPERTIES table.
 * HISTORICAL_PROPERTIES only gets changed when a "property" is deleted or the "value" field of the property
 * is changed. The ID's for the properties within HISTORICAL_PROPERTIES are not "unique".
 * fin
*/

import org.bluemagic.config.service.dao.impl.helper.CompletePropertyDto;
import org.bluemagic.config.service.dao.impl.helper.PropertyDto;
import java.lang.String; 

public interface HistoricalPropertiesDao {

    public boolean insertHistoricalProperty(String historicalPropertyKey);

    public String getHistoricalPropertyValue(String historicalPropertyKey, String user);

	public PropertyDto getHistoricalProperty(String historicalPropertyKey);

	public CompletePropertyDto getCompleteHistoricalProperty(String historicalPropertyKey);
}
