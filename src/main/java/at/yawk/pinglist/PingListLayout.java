package at.yawk.pinglist;


/**
 * @author Yawkat
 */
public abstract class PingListLayout {
    /**
     * Any elements of this array may be <code>null</code>. This should be interpreted as an empty {@link String}.
     */
    public abstract PingListEntry[] getContent();

    public abstract void add(PingListEntry entry, Object argument);

    public abstract void set(int index, PingListEntry entry, Object argument);

    public abstract void remove(PingListEntry entry);
}
