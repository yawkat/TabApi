package at.yawk.pinglist;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Yawkat
 */
public class SectionPingList extends PingListLayout {
    public static final String TOP_LEFT = "topleft";
    public static final String TOP_CENTER = "topcenter";
    public static final String TOP_RIGHT = "topright";
    public static final String CENTER = "center";
    public static final String BOTTOM_LEFT = "bottomleft";
    public static final String BOTTOM_CENTER = "bottomcenter";
    public static final String BOTTOM_RIGHT = "bottomright";

    private final List<PingListEntry> topLeft = new ArrayList<>();
    private final List<PingListEntry> topCenter = new ArrayList<>();
    private final List<PingListEntry> topRight = new ArrayList<>();
    private final List<PingListEntry> center = new ArrayList<>();
    private final List<PingListEntry> bottomLeft = new ArrayList<>();
    private final List<PingListEntry> bottomCenter = new ArrayList<>();
    private final List<PingListEntry> bottomRight = new ArrayList<>();


    @Override
    public PingListEntry[] getContent() {
        PingListEntry[] content = new PingListEntry[60];
        int topOffset = max(10, topLeft.size(), topCenter.size(), topRight.size());
        {
            int i = 0;
            for (PingListEntry p : topLeft) {
                content[(i++ * 3)] = p;
            }
        }
        {
            int i = 0;
            for (PingListEntry p : topCenter) {
                content[i++ * 3 + 1] = p;
            }
        }
        {
            int i = 0;
            for (PingListEntry p : topRight) {
                content[i++ * 3 + 2] = p;
            }
        }
        {
            int i = 0;
            for (PingListEntry p : bottomLeft) {
                content[57 + i-- * 3] = p;
            }
        }
        {
            int i = 0;
            for (PingListEntry p : bottomCenter) {
                content[58 + i-- * 3] = p;
            }
        }
        {
            int i = 0;
            for (PingListEntry p : bottomRight) {
                content[59 + i-- * 3] = p;
            }
        }
        {
            int i = 0;
            for (PingListEntry p : center) {
                content[i++ + topOffset * 3] = p;
            }
        }
        return content;
    }

    @Override
    public void add(PingListEntry entry, Object argument) {
        String position = ((String) argument).toLowerCase();
        addToList(getSection(position), entry, getLimit(position));
    }

    @Override
    public void set(int index, PingListEntry entry, Object argument) {
        String position = ((String) argument).toLowerCase();
        if (index >= getLimit(position)) { return; }
        List<PingListEntry> sec = getSection(position);
        while (sec.size() <= index) { sec.add(null); }
        sec.set(index, entry);
    }

    private List<PingListEntry> getSection(String id) {
        switch (id) {
        case TOP_LEFT:
            return topLeft;
        case TOP_CENTER:
            return topCenter;
        case TOP_RIGHT:
            return topRight;
        case CENTER:
            return center;
        case BOTTOM_LEFT:
            return bottomLeft;
        case BOTTOM_CENTER:
            return bottomCenter;
        case BOTTOM_RIGHT:
            return bottomRight;
        default:
            throw new IllegalArgumentException("Invalid position: " + id);
        }
    }

    private int getLimit(String id) {
        return id.equals(CENTER) ? PingConstants.PING_LIST_SIZE : 20;
    }

    @Override
    public void remove(PingListEntry entry) {
        topLeft.remove(entry);
        topCenter.remove(entry);
        topRight.remove(entry);
        center.remove(entry);
        bottomLeft.remove(entry);
        bottomCenter.remove(entry);
        bottomRight.remove(entry);
    }

    private static int max(int... items) {
        int max = Integer.MIN_VALUE;
        for (int i : items) {
            max = Math.max(max, i);
        }
        return max;
    }

    private static <T> void addToList(List<T> list, T element, int maximumListLength) {
        if (list.size() < maximumListLength) {
            list.add(element);
        }
    }
}
