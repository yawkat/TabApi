package at.yawk.pinglist;

/**
 * @author Jonas Konrad (yawkat)
 */
public interface PingConstants {
    /**
     * Size of the tab list.
     */
    static final int PING_LIST_SIZE = 60;
    /**
     * Amount of bits required for uniqueness of tab list entries ( <code>log2({@link #PING_LIST_SIZE})</code>)
     */
    static final int UNIQUE_SUFFIX_BITS = 5;
    /**
     * Maximum amount of characters allowed for scoreboard team prefixes.
     */
    static final int MAXIMUM_PREFIX_LENGTH = 16;
    /**
     * Maximum amount of characters allowed for usernames.
     */
    static final int MAXIMUM_NAME_LENGTH = 16;
    /**
     * Total of {@link #MAXIMUM_PREFIX_LENGTH} and {@link #MAXIMUM_NAME_LENGTH}, the amount of characters that could -
     * in theory - be used for ping list entries.
     */
    static final int MAXIMUM_ENTRY_LENGTH = MAXIMUM_PREFIX_LENGTH + MAXIMUM_NAME_LENGTH;
    /**
     * {@link #MAXIMUM_ENTRY_LENGTH} substracted by {@link #UNIQUE_SUFFIX_BITS}, maximum ping list message length with
     * ensured uniqueness.
     */
    static final int MAXIMUM_MESSAGE_LENGTH = MAXIMUM_ENTRY_LENGTH - UNIQUE_SUFFIX_BITS;
}
