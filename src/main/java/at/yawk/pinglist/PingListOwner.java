package at.yawk.pinglist;

import javax.annotation.Nullable;

/**
 * @author Jonas Konrad (yawkat)
 */
public interface PingListOwner {
    @Nullable
    PingListEntry.SplitPingListEntry[] getPingListContent();

    void setPingListContent(PingListEntry.SplitPingListEntry[] content);
}
