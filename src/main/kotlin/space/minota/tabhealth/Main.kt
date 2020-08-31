package space.minota.tabhealth

import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer
import org.bukkit.plugin.java.JavaPlugin
import org.bukkit.scoreboard.DisplaySlot
import org.bukkit.scoreboard.Objective
import org.bukkit.scoreboard.Scoreboard
import kotlin.math.floor


class Main : JavaPlugin() {

    override fun onEnable() {
        val manager = Bukkit.getScoreboardManager()
        val board: Scoreboard = manager.mainScoreboard
        val name: Objective
        val tab: Objective
        name = if (board.getObjective("HealthNamePL") == null) {
            board.registerNewObjective("HealthNamePL", "dummy")
        } else {
            board.getObjective("HealthNamePL")
        }
        tab = if (board.getObjective("HealthTabPL") == null) {
            board.registerNewObjective("HealthTabPL", "dummy")
        } else {
            board.getObjective("HealthTabPL")
        }
        name.displaySlot = DisplaySlot.BELOW_NAME
        name.displayName = ChatColor.RED.toString() + "❤"
        tab.displaySlot = DisplaySlot.PLAYER_LIST
        Bukkit.getScheduler().runTaskTimer(this, {
            for (player in Bukkit.getOnlinePlayers()) {
                val health = floor(player.health / 20 * 100 + ((player as CraftPlayer).handle.absorptionHearts / 2) * 10).toInt()
                name.getScore(player.name).score = health
                tab.getScore(player.name).score = health
            }
        }, 1L, 1L)
    }

}