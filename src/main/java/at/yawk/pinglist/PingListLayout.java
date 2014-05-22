package at.yawk.pinglist;


/**
 * @author Yawkat
 */
public interface PingListLayout {
    /**
     * Any elements of this array may be <code>null</code>. This should be interpreted as an empty {@link String}.
     */
    PingListEntry[] getContent();

    void add(PingListEntry entry, Object argument);

    void set(int index, PingListEntry entry, Object argument);

    void remove(PingListEntry entry);
}
