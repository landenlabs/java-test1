
import java.net.Inet4Address;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.*;

import com.google.android.collect.Lists;
import com.google.android.collect.Maps;
import okhttp3.Dns;

import static java.util.stream.Collectors.toList;

public class TestDns {


    public static class UsageException extends RuntimeException {
        public UsageException(String msg) {
            super(msg);
        }
    }

    public static class DnsSelector implements Dns {

        public enum Mode {
            SYSTEM,
            IPV6_FIRST,
            IPV4_FIRST,
            IPV6_ONLY,
            IPV4_ONLY
        }

        private Map<String, List<InetAddress>> overrides = Maps.newHashMap();

        private Mode mode;

        public DnsSelector(Mode mode) {
            this.mode = mode;
        }

        public static Dns byName(String ipMode) {
            Mode selectedMode;
            switch (ipMode) {
                case "ipv6":
                    selectedMode = Mode.IPV6_FIRST;
                    break;
                case "ipv4":
                    selectedMode = Mode.IPV4_FIRST;
                    break;
                case "ipv6only":
                    selectedMode = Mode.IPV6_ONLY;
                    break;
                case "ipv4only":
                    selectedMode = Mode.IPV4_ONLY;
                    break;
                default:
                    selectedMode = Mode.SYSTEM;
                    break;
            }

            return new DnsSelector(selectedMode);
        }

        @Override public List<InetAddress> lookup(String hostname) throws UnknownHostException {
            List<InetAddress> addresses = overrides.get(hostname.toLowerCase());

            if (addresses != null) {
                return addresses;
            }

            addresses = Dns.SYSTEM.lookup(hostname);

            switch (mode) {
                case IPV6_FIRST:
                    addresses.sort(Comparator.comparing(Inet4Address.class::isInstance));
                    break;
                case IPV4_FIRST:
                    addresses.sort(Comparator.comparing(Inet4Address.class::isInstance).reversed());
                    break;
                case IPV6_ONLY:
                    addresses = addresses.stream().filter(Inet6Address.class::isInstance).collect(toList());
                    break;
                case IPV4_ONLY:
                    addresses = addresses.stream().filter(Inet4Address.class::isInstance).collect(toList());
                    break;
            }

            return addresses;
        }

        public void addOverride(String hostname, InetAddress address) {
            overrides.put(hostname.toLowerCase(), Lists.newArrayList(address));
        }
    }


    public static class DnsOverride implements Dns {
        private final Dns dns;
        private Map<String, String> overrides = Maps.newHashMap();

        public DnsOverride(Dns dns) {
            this.dns = dns;
        }

        private void put(String host, String target) {
            overrides.put(host, target);
        }

        @Override public List<InetAddress> lookup(String s) throws UnknownHostException {
            String override = overrides.get(s);

            if (override != null) {
                return Arrays.asList(InetAddress.getByName(override));
            }

            return dns.lookup(s);
        }

        public static DnsOverride build(Dns dns, List<String> resolveStrings) {
            DnsOverride dnsOverride = new DnsOverride(dns);

            for (String resolveString : resolveStrings) {
                String[] parts = resolveString.split(":");

                if (parts.length != 2) {
                    throw new UsageException("Invalid resolve string '" + resolveString + "'");
                }

                dnsOverride.put(parts[0], parts[1]);
            }

            return dnsOverride;
        }
    }


    public static void test1() {

        Dns dns = new DnsSelector(DnsSelector.Mode.IPV4_FIRST);

        ArrayList<String> hostList =new ArrayList<>();
        hostList.add("api.weather.com:alternateName");
        Dns alterateNameOrIp = DnsOverride.build(dns, hostList);

        try {
            List<InetAddress> addresses = dns.lookup("api.weather.com");
            int idx = 0;
            for (InetAddress address : addresses) {
                System.out.printf("%3d: %s\n", idx++, address.getHostAddress());
            }
        } catch (UnknownHostException ex) {
            System.err.println("Unknown host " + ex);
        }
    }
}
