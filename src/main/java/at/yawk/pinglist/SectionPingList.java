package at.yawk.pinglist;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Yawkat
 */
public class SectionPingList implements PingListLayout {
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
        for (int i = 0; i < topLeft.size(); i++) {
            content[(i++ * 3)] = topLeft.get(i);
        }
        for (int i = 0; i < topCenter.size(); i++) {
            content[i++ * 3 + 1] = topCenter.get(i);
        }
        for (int i = 0; i < topRight.size(); i++) {
            content[i++ * 3 + 2] = topRight.get(i);
        }
        for (int i = 0; i < bottomLeft.size(); i++) {
            content[57 + i-- * 3] = bottomLeft.get(i);
        }
        for (int i = 0; i < bottomCenter.size(); i++) {
            content[58 + i-- * 3] = bottomCenter.get(i);
        }
        for (int i = 0; i < bottomRight.size(); i++) {
            content[59 + i-- * 3] = bottomRight.get(i);
        }
        for (int i = 0; i < center.size(); i++) {
            content[i++ + topOffset * 3] = center.get(i);
        }
        return content;
    }

    @Override
    public void add(PingListEntry entry, Object argument) {
        String position = sanitize(argument);
        addToList(getSection(position), entry, getLimit(position));
    }

    @Override
    public void set(int index, PingListEntry entry, Object argument) {
        String position = sanitize(argument);
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

    @Override
    public void clear(Object section) {
        getSection(sanitize(section)).clear();
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

    private static String sanitize(Object sectionSpec) {
        return ((String) sectionSpec).toLowerCase();
    }
}
