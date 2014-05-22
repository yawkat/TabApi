package at.yawk.pinglist;

import com.comphenix.packetwrapper.WrapperPlayServerPlayerInfo;
import com.comphenix.packetwrapper.WrapperPlayServerScoreboardTeam;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.events.PacketEvent;
import com.google.common.base.Equivalence;
import java.util.*;
import javax.annotation.Nullable;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

/**
 * @author Yawkat
 */
public class BukkitPingListBuilder extends AbstractPingListBuilder<Player> {
    private final Map<Player, SimplePingListOwner> players = Collections.synchronizedMap(new WeakHashMap<Player, SimplePingListOwner>());
    private final Set<Object> letThrough = Collections.synchronizedSet(new HashSet<Object>());

    public BukkitPingListBuilder(Plugin plugin) {
        ProtocolLibrary.getProtocolManager()
                       .addPacketListener(new PacketAdapter(plugin, WrapperPlayServerPlayerInfo.TYPE) {
                           @Override
                           public void onPacketSending(PacketEvent event) {
                               // filter other ping list packets
                               if (!letThrough.remove(event.getPacket().getHandle())) {
                                   event.setCancelled(true);
                               }
                           }
                       });
    }

    @Override
    protected void sendPlayerInfo(Player target, String entry, boolean add) {
        WrapperPlayServerPlayerInfo wrapper = new WrapperPlayServerPlayerInfo();
        wrapper.setPlayerName(entry);
        wrapper.setOnline(add);
        letThrough.add(wrapper.getHandle().getHandle());
        wrapper.sendPacket(target);
    }

    @Override
    protected void sendTeamInfo(Player target,
                                int action,
                                String teamId,
                                @Nullable String newPrefix,
                                @Nullable Collection<String> newMembers) {
        WrapperPlayServerScoreboardTeam wrapper = new WrapperPlayServerScoreboardTeam();
        wrapper.setPacketMode((byte) action);
        wrapper.setTeamName(teamId);
        if (newMembers != null) { wrapper.setPlayers(newMembers); }
        if (newPrefix != null) { wrapper.setTeamPrefix(newPrefix); }
        wrapper.sendPacket(target);
    }

    @Override
    protected synchronized PingListOwner toPingListOwner(Player player) {
        if (!players.containsKey(player)) { players.put(player, new SimplePingListOwner()); }
        return players.get(player);
    }
}
