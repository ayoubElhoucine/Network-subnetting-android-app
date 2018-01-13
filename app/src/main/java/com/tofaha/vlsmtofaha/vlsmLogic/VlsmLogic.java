package com.tofaha.vlsmtofaha.vlsmLogic;

import com.tofaha.vlsmtofaha.model.Subnet;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class VlsmLogic {

    static class C04171 implements Comparator<Entry<String, Integer>> {
        C04171() {
        }

        public int compare(Entry<String, Integer> o1, Entry<String, Integer> o2) {
            return ((Integer) o2.getValue()).compareTo((Integer) o1.getValue());
        }
    }

    public static List<Subnet> calcVLSM(String majorNetwork, Map<String, Integer> subnets) {
        Map<String, Integer> sortedSubnets = sortMap(subnets);
        List<Subnet> output = new ArrayList();
        int currentIp = findFirstIp(majorNetwork);
        for (String key : sortedSubnets.keySet()) {
            Subnet subnet = new Subnet();
            subnet.name = key;
            subnet.address = convertIpToQuartet(currentIp);
            int neededSize = ((Integer) sortedSubnets.get(key)).intValue();
            subnet.neededSize = neededSize;
            int mask = calcMask(neededSize);
            subnet.mask = "/" + mask;
            subnet.decMask = toDecMask(mask);
            int allocatedSize = findUsableHosts(mask);
            subnet.allocatedSize = allocatedSize;
            subnet.broadcast = convertIpToQuartet((currentIp + allocatedSize) + 1);
            String firstUsableHost = convertIpToQuartet(currentIp + 1);
            subnet.range = firstUsableHost + " // " + convertIpToQuartet(currentIp + allocatedSize);
            output.add(subnet);
            currentIp += allocatedSize + 2;
        }
        return output;
    }

    public static Map<String, Integer> sortMap(Map<String, Integer> map) {
        List<Entry<String, Integer>> entries = new ArrayList(map.entrySet());
        Collections.sort(entries, new C04171());
        Map<String, Integer> sortedMap = new LinkedHashMap();
        for (Entry<String, Integer> entry : entries) {
            sortedMap.put(entry.getKey(), entry.getValue());
        }
        return sortedMap;
    }

    public static int convertQuartetToBinaryString(String ipAddress) {
        String[] ip = ipAddress.split("\\.|/");
        int octet1 = Integer.parseInt(ip[0]);
        int octet2 = Integer.parseInt(ip[1]);
        int octet3 = Integer.parseInt(ip[2]);
        return (((((octet1 << 8) + octet2) << 8) + octet3) << 8) + Integer.parseInt(ip[3]);
    }

    public static String convertIpToQuartet(int ipAddress) {
        return ((ipAddress >> 24) & 255) + "." + ((ipAddress >> 16) & 255) + "." + ((ipAddress >> 8) & 255) + "." + (ipAddress & 255);
    }

    public static int findFirstIp(String majorNetwork) {
        return convertQuartetToBinaryString(majorNetwork);
    }

    public static int calcMask(int neededSize) {

        if (neededSize % 2 != 0)
            neededSize += 1 ;

        int highestBit = Integer.highestOneBit(neededSize);
        int position = (int) (Math.log(highestBit) / Math.log(2));
        return Integer.SIZE - (position + 1);   // +1 because position starts with 0
    }

    public static int findUsableHosts(int mask) {
        return (int) Math.pow(2, Integer.SIZE - mask) - 2;
    }

    public static String toDecMask(int mask) {
        if (mask == 0) {
            return "0.0.0.0";
        }
        return convertIpToQuartet(-1 << (32 - mask));
    }
}
