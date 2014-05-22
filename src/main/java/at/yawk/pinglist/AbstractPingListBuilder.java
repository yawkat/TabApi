package at.yawk.pinglist;

import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;

/**
 * @author Yawkat
 */
public abstract class AbstractPingListBuilder<P> implements PingConstants {
    /**
     * Array of {@link #PING_LIST_SIZE} {@link String}s that are unique, composed of two minecraft-invisible characters,
     * with a length below or equal to {@link #UNIQUE_SUFFIX_BITS}.
     */
    private static final String[] UNIQUE_NAMES = new String[PING_LIST_SIZE];

    static {
        Iterator<String> iterator = new WhitespaceIterable(UNIQUE_SUFFIX_BITS, ' ', '\u0A00').iterator();
        for (int i = 0; i < PING_LIST_SIZE; i++) {
            assert iterator != null;
            assert iterator.hasNext();
            UNIQUE_NAMES[i] = iterator.next();
            assert UNIQUE_NAMES[i].length() <= UNIQUE_SUFFIX_BITS;
        }
    }

    public BakedPingList bakeLayout(PingListLayout layout) {
        PingListEntry.SplitPingListEntry[] entries = new PingListEntry.SplitPingListEntry[PING_LIST_SIZE];
        PingListEntry[] content = layout.getContent();
        if (content != null) {
            for (int i = 0; i < PING_LIST_SIZE; i++) {
                if (content[i] != null) { entries[i] = content[i].split(); }
            }
        }
        return new BakedPingList(entries);
    }

    public void set(P player, PingListLayout layout) {
        set(player, bakeLayout(layout));
    }

    public void set(P player, BakedPingList baked) {
        PingListEntry.SplitPingListEntry[] newEntries = baked.getEntries();
        assert newEntries.length == PING_LIST_SIZE;
        PingListEntry.SplitPingListEntry[] oldEntries = toPingListOwner(player).getPingListContent();
        assert oldEntries != null;
        assert oldEntries.length == PING_LIST_SIZE;

        // first entry index where the NAME changed or -1 if none
        int firstValueChanged = -1;
        for (int i = 0; i < PING_LIST_SIZE; i++) {
            if (newEntries[i] == null) {
                // newEntries[i] might be null, use empty one instead
                newEntries[i] = PingListEntry.SplitPingListEntry.EMPTY;
            }
            if (oldEntries[i] == null) {
                // this can happen when the ping list is reset
                oldEntries[i] = PingListEntry.SplitPingListEntry.EMPTY;
            }
            // true if the prefix of the message has changed (first 16 characters)
            boolean prefixChanged = !oldEntries[i].getPrefix().equals(newEntries[i].getPrefix());
            // true if the name of the message has changed (other characters)
            boolean valueChanged = !oldEntries[i].getName().equals(newEntries[i].getName());
            if (valueChanged) {
                // name change detected, we need to remove and readd all items after this one
                if (firstValueChanged == -1) {
                    firstValueChanged = i;
                }
                if (!newEntries[i].getName().isEmpty()) {
                    // send a group-add packet so the prefix is visible
                    sendTeamInfo(player,
                                 3,
                                 "p." + i,
                                 null,
                                 Collections.singleton(newEntries[i].getName() + UNIQUE_NAMES[i]));
                }
            }
            if (prefixChanged) {
                // prefix has changed, update team prefix
                sendTeamInfo(player, 2, "p." + i, newEntries[i].getPrefix(), null);
            }
            if (firstValueChanged != -1) {
                // a name before this item (or this item) changed, we need to clear all these
                sendPlayerInfo(player, oldEntries[i].getName() + UNIQUE_NAMES[i], false); // remove
            }
        }
        if (firstValueChanged != -1) {
            // an item name was changed, we need to readd all entries after it, including the entry
            for (int i = firstValueChanged; i < PING_LIST_SIZE; i++) {
                PingListEntry.SplitPingListEntry entry = newEntries[i];
                sendPlayerInfo(player, entry.getName() + UNIQUE_NAMES[i], true); // add
            }
        }
        // mark as flushed
        toPingListOwner(player).setPingListContent(newEntries);
    }

    protected abstract void sendPlayerInfo(P target, String entry, boolean add);

    protected abstract void sendTeamInfo(P target,
                                         int action,
                                         String teamId,
                                         String newPrefix,
                                         Collection<String> newMembers);

    public void init(P player) {
        PingListEntry.SplitPingListEntry[] c = new PingListEntry.SplitPingListEntry[PING_LIST_SIZE];
        // init
        for (int i = 0; i < PING_LIST_SIZE; i++) {
            // push the empty item into the ping list so we only need to change team prefix later
            sendPlayerInfo(player, UNIQUE_NAMES[i], true); // add

            // create a new team with no prefix and the unique name at this index as the only member
            sendTeamInfo(player, 0, "p." + i, null, Collections.singleton(UNIQUE_NAMES[i]));

            // fill the array
            c[i] = PingListEntry.SplitPingListEntry.EMPTY;
        }
        toPingListOwner(player).setPingListContent(c);
    }

    protected abstract PingListOwner toPingListOwner(P player);
}
