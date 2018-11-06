import java.util.*;

public class IPtoCIDR {
    private long ipToLong(String strIP) { // 255.0.0.7
        long[] ip = new long[4];
        String[] ipSec = strIP.split("\\.");
        for (int k = 0; k < 4; k++) {
            ip[k] = Long.valueOf(ipSec[k]); //255 0 0 7
        }
        return (ip[0] << 24) + (ip[1] << 16) + (ip[2] << 8) + ip[3]; // 11111111 000000000 00000000 00000011
    }
    private String longToIP(long longIP) {  // 11111111 000000000 00000000 00000011
        StringBuffer sb = new StringBuffer("");
        sb.append(String.valueOf(longIP >>> 24)); //11111111
        sb.append(".");
        //下一行，16进制 0x是开头，实际数00FFFFFF (F是15) 二进制表示00000000 11111111 11111111 11111111
        sb.append(String.valueOf((longIP & 0x00FFFFFF) >>> 16)); //11111111.00000000
        sb.append(".");
        sb.append(String.valueOf((longIP & 0x0000FFFF) >>> 8)); //11111111.00000000.00000000
        sb.append(".");
        sb.append(String.valueOf(longIP & 0x000000FF)); ////11111111.00000000.00000000.000001
        return sb.toString();
    }
    public List<String> ipRange2Cidr(String startIp, int range) {
// check parameters
        String a = "";
        long start = ipToLong(startIp);
        long end = start + range - 1;
        List<String> res = new ArrayList<>();
        while (start <= end) {
// identify the location of first 1's from lower bit to higher bit of start IP
// e.g. 00000001.00000001.00000001.01101100, return 4 (100)
            long locOfFirstOne = start & (-start); // location of first one 比如4&-4得到 0100

            //正数的符号位用“0”，负数的符号位“1”; 符号位：sign bit
            //负数的表示：原码-》反码（除符号位，其他取反） —》补码(最后一位加1)
            // true(original) code -反码（ones' complement）-补码（two's complement)
            // -4: 1100 -> 1011 -> 1100
            // 比如 4 & -4 ： 0100 & 1100 = 0100 找到最后边的1 (更tricky：-4就是把4的码（包括符号）全取反，+1。所以只要&,就找到在哪变的，找到了最右边的1)
            // ocOfFirstOne = 0100 = 4，想知道1是倒数第几位，即0100（4）是2的几次方； loc= log2(4) = 2 倒数第2位
            int curMask = 32 - (int) (Math.log(locOfFirstOne) / Math.log(2)); // 32 - log2(locOfFirstOne)
// calculate how many IP addresses between the start and end
// e.g. between 1.1.1.111 and 1.1.1.120, there are 10 IP address
// 3 bits to represent 8 IPs, from 1.1.1.112 to 1.1.1.119 (119 -112 + 1 = 8)
            double currRange = Math.log(end - start + 1) / Math.log(2); //需要求的range范围是2的几次方
            int currRangeMask = 32 - (int) Math.floor(currRange);
// why max?
// if the currRangeMask is larger than curMask
// which means the numbers of IPs from start to end is smaller than mask range
// so we can't use as many as bits we want to mask the start IP to avoid exceed the end IP
// Otherwise, if currRangeMask is smaller than curMask, which means number of IPs is larger than mask range
// in this case we can use curMask to mask as many as IPs from start we want.
                    curMask = Math.max(currRangeMask, curMask);
// Add to results
            String ip = longToIP(start);
            res.add(ip + "/" + curMask);
// We have already included 2^(32 - curMask) numbers of IP into result
// So the next roundUp start must insert that number
            start += Math.pow(2, (32 - curMask));
        }
        return res;
    }

    public static void main(String[] args){
        IPtoCIDR i = new IPtoCIDR();
        long longip = Long.valueOf(427819008);
        System.out.println(i.longToIP(longip));
    }
}
