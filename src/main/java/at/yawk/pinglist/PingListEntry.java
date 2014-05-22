package at.yawk.pinglist;

/**
 * @author Yawkat
 */
public final class PingListEntry implements PingConstants {
    public static final PingListEntry EMPTY = new PingListEntry("");

    private final String translated;

    public PingListEntry(String translated) {
        this.translated = translated;
    }


    public SplitPingListEntry split() {
        String prefix;
        String name;

        if (translated.length() <= MAXIMUM_PREFIX_LENGTH) {
            prefix = translated;
            name = "";
        } else {
            if (translated.charAt(MAXIMUM_PREFIX_LENGTH - 1) == '\247') { // don't split colors apart
                prefix = translated.substring(0, MAXIMUM_PREFIX_LENGTH - 1);
                name = translated.substring(MAXIMUM_PREFIX_LENGTH - 1);
            } else {
                prefix = translated.substring(0, MAXIMUM_PREFIX_LENGTH);
                name = translated.substring(MAXIMUM_PREFIX_LENGTH);
            }
        }

        return new SplitPingListEntry(prefix, name);
    }


    public String getTranslated() {return this.translated;}

    @Override
    public boolean equals(Object o) {
        if (this == o) { return true; }
        if (o == null || getClass() != o.getClass()) { return false; }

        PingListEntry that = (PingListEntry) o;

        if (!translated.equals(that.translated)) { return false; }

        return true;
    }

    @Override
    public int hashCode() {
        return translated.hashCode();
    }

    public static class SplitPingListEntry {
        public static final SplitPingListEntry EMPTY = new SplitPingListEntry("", "");

        private final String prefix;
        private final String name;

        private SplitPingListEntry(String prefix, String name) {
            this.prefix = prefix;
            this.name = name;
        }

        public String getPrefix() {return this.prefix;}

        public String getName() {return this.name;}

        @Override
        public boolean equals(Object o) {
            if (this == o) { return true; }
            if (o == null || getClass() != o.getClass()) { return false; }

            SplitPingListEntry that = (SplitPingListEntry) o;

            if (!name.equals(that.name)) { return false; }
            if (!prefix.equals(that.prefix)) { return false; }

            return true;
        }

        @Override
        public int hashCode() {
            int result = prefix.hashCode();
            result = 31 * result + name.hashCode();
            return result;
        }
    }
}
