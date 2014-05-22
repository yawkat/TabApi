package at.yawk.pinglist;

/**
 * @author Yawkat
 */
class SimplePingListOwner implements PingListOwner, PingConstants {
    private PingListEntry.SplitPingListEntry[] pingListContent = null; // initialized by init() method in Builder

    @Override
    public PingListEntry.SplitPingListEntry[] getPingListContent() {
        return pingListContent;
    }

    @Override
    public void setPingListContent(PingListEntry.SplitPingListEntry[] content) {
        this.pingListContent = content;
    }
}
