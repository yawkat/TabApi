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
        switch (position) {
        case TOP_LEFT:
            addToList(topLeft, entry, 20);
            break;
        case TOP_CENTER:
            addToList(topCenter, entry, 20);
            break;
        case TOP_RIGHT:
            addToList(topRight, entry, 20);
            break;
        case CENTER:
            addToList(center, entry, 30);
            break;
        case BOTTOM_LEFT:
            addToList(bottomLeft, entry, 20);
            break;
        case BOTTOM_CENTER:
            addToList(bottomCenter, entry, 20);
            break;
        case BOTTOM_RIGHT:
            addToList(bottomRight, entry, 20);
            break;
        default:
            throw new IllegalArgumentException("Invalid position: " + position);
        }
    }

    @Override
    public void set(int index, PingListEntry entry, Object argument) {
        String position = ((String) argument).toLowerCase();
        switch (position) {
        case TOP_LEFT:
            topLeft.set(index, entry);
            break;
        case TOP_CENTER:
            topCenter.set(index, entry);
            break;
        case TOP_RIGHT:
            topRight.set(index, entry);
            break;
        case CENTER:
            center.set(index, entry);
            break;
        case BOTTOM_LEFT:
            bottomLeft.set(index, entry);
            break;
        case BOTTOM_CENTER:
            bottomCenter.set(index, entry);
            break;
        case BOTTOM_RIGHT:
            bottomRight.set(index, entry);
            break;
        default:
            throw new IllegalArgumentException("Invalid position: " + position);
        }
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
