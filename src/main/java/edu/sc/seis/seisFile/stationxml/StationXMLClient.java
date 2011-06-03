package edu.sc.seis.seisFile.stationxml;

import java.io.IOException;
import java.net.URL;
import java.util.List;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.XMLEvent;

public class StationXMLClient {

    public static void main(String[] args) throws XMLStreamException, StationXMLException, IOException {
        if (args.length != 2 || ! args[0].equals("-u")) {
            System.out.println("Usage: stationxmlclient -u url");
            System.out.println("       stationxmlclient http://www.iris.edu/ws/station/query?net=IU&sta=SNZO&chan=BHZ&level=chan");
            return;
        }
        URL url = new URL(args[1]);
        XMLInputFactory factory = XMLInputFactory.newInstance();
        XMLEventReader r = factory.createXMLEventReader(url.toString(), url.openStream());
        XMLEvent e = r.peek();
        while (!e.isStartElement()) {
            e = r.nextEvent(); // eat this one
            e = r.peek(); // peek at the next
        }
        System.out.println("StaMessage");
        StaMessage staMessage = new StaMessage(r);
        System.out.println("Source: " + staMessage.getSource());
        System.out.println("Sender: " + staMessage.getSender());
        System.out.println("Module: " + staMessage.getModule());
        System.out.println("SentDate: " + staMessage.getSentDate());
        NetworkIterator it = staMessage.getNetworks();
        while (it.hasNext()) {
            Network n = it.next();
            System.out.println("Network: " +n.getNetCode()+" "+n.getDescription()+" "+n.getStartDate()+" "+n.getEndDate()); 
            StationIterator sit = n.getStations();
            while (sit.hasNext()) {
                Station s = sit.next();
                System.out.println("Station: " + s.getNetCode() + "." + s.getStaCode() + " "
                        + s.getStationEpochs().size());
                List<StationEpoch> staEpochs = s.getStationEpochs();
                for (StationEpoch stationEpoch : staEpochs) {
                    System.out.println("Station Epoch: " + s.getNetCode() + "." + s.getStaCode()
                                       + "  " + stationEpoch.getStartDate() + " to " + stationEpoch.getEndDate());
                    List<Channel> chanList = stationEpoch.getChannelList();
                    for (Channel channel : chanList) {
                        List<Epoch> chanEpochList = channel.getChanEpochList();
                        for (Epoch epoch : chanEpochList) {
                            System.out.println("Channel Epoch: " + channel.getLocCode() + "." + channel.getChanCode()
                                    + "  " + epoch.getStartDate() + " to " + epoch.getEndDate());
                        }
                    }
                }
            }
        }
        System.out.println("Done station iterate");
    }
}