package edu.sc.seis.seisFile.stationxml;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;

public class Site {

    public Site(String town, String county, String state, String country) {
        super();
        this.town = town;
        this.county = county;
        this.state = state;
        this.country = country;
    }

    public Site(XMLEventReader reader) throws XMLStreamException, StationXMLException {
        StartElement startE = StaxUtil.expectStartElement(StationXMLTagNames.SITE, reader);
        while (reader.hasNext()) {
            XMLEvent e = reader.peek();
            if (e.isStartElement()) {
                String elName = e.asStartElement().getName().getLocalPart();
                if (elName.equals(StationXMLTagNames.TOWN)) {
                    town = StaxUtil.pullText(reader, StationXMLTagNames.TOWN);
                } else if (elName.equals(StationXMLTagNames.COUNTY)) {
                    county = StaxUtil.pullText(reader, StationXMLTagNames.COUNTY);
                } else if (elName.equals(StationXMLTagNames.STATE)) {
                    state = StaxUtil.pullText(reader, StationXMLTagNames.STATE);
                } else if (elName.equals(StationXMLTagNames.COUNTRY)) {
                    country = StaxUtil.pullText(reader, StationXMLTagNames.COUNTRY);
                } else {
                    StaxUtil.skipToMatchingEnd(reader);
                }
            } else if (e.isEndElement()) {
                reader.nextEvent();
                return;
            } else {
                e = reader.nextEvent();
            }
        }
    }

    public String getTown() {
        return town;
    }

    public void setTown(String town) {
        this.town = town;
    }

    public String getCounty() {
        return county;
    }

    public void setCounty(String county) {
        this.county = county;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    String town, county, state, country;
}
