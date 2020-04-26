package pleasepleasepleasepleaspleasdontoverwhelmyourself.mainboi;

import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import pleasepleasepleasepleaspleasdontoverwhelmyourself.mainboi.capabilities.CapabilitiesCore;
import pleasepleasepleasepleaspleasdontoverwhelmyourself.mainboi.genetics.GeneticsCore;

import java.util.Objects;


public final class MainBoi extends JavaPlugin {
    private static MainBoi instance;

    @Override
    public void onEnable() {
        instance = this;

        CapabilitiesCore capabilitiesCore = new CapabilitiesCore();

        // Registering event listeners.
        PluginManager pluginManager = getServer().getPluginManager();
        pluginManager.registerEvents(capabilitiesCore, this);
        pluginManager.registerEvents(new GeneticsCore(), this);

        // Registering command executors
        PluginCommand capabilitiesCommand = Objects.requireNonNull(getCommand("capabilities"));
        capabilitiesCommand.setExecutor(capabilitiesCore);
        capabilitiesCommand.setTabCompleter(capabilitiesCore);

        GeneticsCore.onEnable();
        CapabilitiesCore.onEnable();

        // Starts onTick function.
        new BukkitRunnable() {@Override public void run() {
                onTick();
        }}.runTaskTimer(this, 0, 1);
    }

    /**
     * Base runnable for the plugin.
     */
    private static void onTick() {
        CapabilitiesCore.tickCapabilities();
    }

    @Override
    public void onDisable() {

    }



    /**
     * Returns the plugin's instance, mainly used for BukkitRunnable.
     *
     * @return The plugin's instance
     */
    public static MainBoi getInstance() {
        return instance;
    }
}
