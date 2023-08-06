package com.fameless.servermanager.rank;

import org.bukkit.ChatColor;

public enum Rank {

    OWNER("\uE001 " + ChatColor.DARK_RED,'a'),
    ADMIN("\uE002 " + ChatColor.RED,'b'),
    DEVELOPER("\uE003 " + ChatColor.AQUA, 'c'),
    MODERATOR("\uE004 " + ChatColor.GREEN,'d'),
    MVP( "\uE005 " + ChatColor.AQUA,'e'),
    VIP("\uE006 " + ChatColor.GOLD,'f'),
    MEMBER("\uE007 " + ChatColor.GRAY,'g'),
    GUEST("\uE008 " + ChatColor.DARK_GRAY,'h');

    private final String display;
    private final char oderSymbol;

    Rank(String display, char oderSymbol) {
        this.oderSymbol = oderSymbol;
        this.display = display;
    }

    public String getDisplay() { return display; }
    public char getOrderSymbol() { return oderSymbol; }
}
