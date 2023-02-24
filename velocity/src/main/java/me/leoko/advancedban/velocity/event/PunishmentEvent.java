package me.leoko.advancedban.velocity.event;

import me.leoko.advancedban.utils.Punishment;

/**
 * Event fired when a punishment is created
 */
public class PunishmentEvent {

    private final Punishment punishment;
    private final boolean silent;

    public PunishmentEvent(Punishment punishment) {
        this(punishment, false);
    }

    public PunishmentEvent(Punishment punishment, boolean silent) {
        this.punishment = punishment;
        this.silent = silent;
    }

    public Punishment getPunishment() {
        return this.punishment;
    }

    public boolean isSilent() {
        return silent;
    }
}
