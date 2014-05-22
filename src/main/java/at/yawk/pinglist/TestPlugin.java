package at.yawk.pinglist;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * @author Yawkat
 */
public class TestPlugin extends JavaPlugin implements Listener {
    private AbstractPingListBuilder<Player> builder;

    @Override
    public void onEnable() {
        builder = new BukkitPingListBuilder(this);

        getServer().getPluginManager().registerEvents(this, this);
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        builder.init(event.getPlayer());
    }

    @EventHandler
    public void onChat(final AsyncPlayerChatEvent evt) {
        getServer().getScheduler().runTask(this, new Runnable() {
            @Override
            public void run() {
                PingListLayout layout = new SectionPingList();
                layout.add(PingListEntry.EMPTY, SectionPingList.CENTER); // left margin
                layout.add(new PingListEntry(evt.getMessage()), SectionPingList.CENTER);
                builder.set(evt.getPlayer(), layout);
            }
        });
    }
}
