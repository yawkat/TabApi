package at.yawk.pinglist;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.WeakHashMap;
import javax.annotation.Nullable;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.protocol.packet.PlayerListItem;
import net.md_5.bungee.protocol.packet.Team;

/**
 * @author Yawkat
 */
public class BungeePingListBuilder extends AbstractPingListBuilder<ProxiedPlayer> {
    private final Map<ProxiedPlayer, SimplePingListOwner> players = Collections.synchronizedMap(new WeakHashMap<ProxiedPlayer, SimplePingListOwner>());

    @Override
    protected void sendPlayerInfo(ProxiedPlayer target, String entry, boolean add) {
        target.unsafe().sendPacket(new PlayerListItem(entry, add, (short) 0));
    }

    @Override
    protected void sendTeamInfo(ProxiedPlayer target,
                                int action,
                                String teamId,
                                @Nullable String newPrefix,
                                @Nullable Collection<String> newMembers) {
        Team packet = new Team(teamId);

        packet.setMode((byte) action);
        packet.setDisplayName("");
        packet.setPrefix(newPrefix == null ? "" : newPrefix);
        packet.setSuffix("");
        if (newMembers != null) { packet.setPlayers(newMembers.toArray(new String[newMembers.size()])); }

        target.unsafe().sendPacket(packet);
    }

    @Override
    protected synchronized PingListOwner toPingListOwner(ProxiedPlayer player) {
        if (!players.containsKey(player)) { players.put(player, new SimplePingListOwner()); }
        return players.get(player);
    }
}
