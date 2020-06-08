package pleasepleasepleasepleaspleasdontoverwhelmyourself.mainboi.capabilities;

import org.bukkit.ChatColor;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import pleasepleasepleasepleaspleasdontoverwhelmyourself.mainboi.helpers.LocalizedMessages;

/**
 * Represents a status effect, much like potion effects.
 *
 * duration - The duration of the effect in ticks.
 * amplifier - The amplifier of the status effect.
 * ambient - If true, status effects produce less, or less noticeable, particles.
 * particles - If true, particles are displayed while a status effect is active.
 * notify - If true, notifies players of the effect when they receive it, and notifies them when it is gone.
 *
 * The extra data is stored in the order that is shown above.
 */
public abstract class StatusEffectCapability extends Capability {
    protected int duration;
    protected byte amplifier;
    protected boolean ambient;
    protected boolean particles;
    protected boolean notify;

    public StatusEffectCapability(String extraData) {
        super(extraData);

        String[] splitExtraData = extraData.split(",");

        if (splitExtraData.length >= 1) {
            try {
                duration = Integer.parseInt(splitExtraData[0]);

            } catch (NumberFormatException ignored) {
                duration = 20;
            }

        } else
            duration = 20;

        if (splitExtraData.length >= 2) {
            try {
                amplifier = Byte.parseByte(splitExtraData[1]);

            } catch (NumberFormatException ignored) {
                amplifier = 0;
            }

        } else
            amplifier = 0;

        if (splitExtraData.length >= 3) {
            ambient = Boolean.parseBoolean(splitExtraData[2]);

        } else
            ambient = false;

        if (splitExtraData.length >= 4) {
            particles = Boolean.parseBoolean(splitExtraData[3]);

        } else
            particles = true;

        if (splitExtraData.length >= 5) {
            notify = Boolean.parseBoolean(splitExtraData[4]);

        } else
            notify = true;
    }

    @Override
    public String getExtraData() {
        return duration + "," + amplifier + "," + ambient + "," + particles + "," + notify;
    }

    @Override
    public boolean isVolatile() {
        return true;
    }



    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public byte getAmplifier() {
        return amplifier;
    }

    public void setAmplifier(byte amplifier) {
        this.amplifier = amplifier;
    }

    public boolean isAmbient() {
        return ambient;
    }

    public void setAmbient(boolean ambient) {
        this.ambient = ambient;
    }

    public boolean hasParticles() {
        return particles;
    }

    public void setParticles(boolean particles) {
        this.particles = particles;
    }

    public boolean willNotify() {
        return notify;
    }

    public void setNotify(boolean notify) {
        this.notify = notify;
    }

    /**
     * Returns the base name for an effect. (e.x.: effect.generic_effect.name).
     */
    public String getBaseName() {
        return null;
    }



    @Override
    public void runCapability(Entity entity) {
        duration -= 1;

        if (duration <= 0)
            CapabilitiesCore.revokeCapability(entity, this);
    }



    @Override
    public void onAssignment(Entity entity) {
        if (notify && entity instanceof Player) {
            String displayName = LocalizedMessages.getMessageFor(entity, getBaseName());

            entity.sendMessage(LocalizedMessages.getMessageFor(entity, "capability.statuseffect.notify_give")
                    .replaceAll("%s1", "" + ChatColor.YELLOW + ChatColor.ITALIC + (displayName == null ? getCapabilityName() : displayName) + (amplifier != 0 ? " " + String.valueOf(amplifier) : "") + ChatColor.RESET)
                    .replaceAll("%s2","" + ChatColor.YELLOW + ChatColor.ITALIC + Math.round(duration / 20.0) + ChatColor.RESET)
            );
        }
    }

    @Override
    public void onRevoke(Entity entity) {
        if (notify && entity instanceof Player) {
            String displayName = LocalizedMessages.getMessageFor(entity, getBaseName());

            entity.sendMessage(LocalizedMessages.getMessageFor(entity, "capability.statuseffect.notify_remove")
                    .replaceAll("%s", "" + ChatColor.YELLOW + ChatColor.ITALIC + (displayName == null ? getCapabilityName() : displayName) + (amplifier != 0 ? " " + String.valueOf(amplifier) : ""))
            );
        }
    }
}
