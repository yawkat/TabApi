package at.yawk.pinglist;

/**
 * @author Jonas Konrad (yawkat)
 */
public class BakedPingList {
    private final PingListEntry.SplitPingListEntry[] entries;

    BakedPingList(PingListEntry.SplitPingListEntry[] entries) { this.entries = entries; }

    PingListEntry.SplitPingListEntry[] getEntries() {
        return entries;
    }
}
