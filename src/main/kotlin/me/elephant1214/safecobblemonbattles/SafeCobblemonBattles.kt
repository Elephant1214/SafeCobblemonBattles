package me.elephant1214.safecobblemonbattles

import com.cobblemon.mod.common.Cobblemon
import net.fabricmc.api.ModInitializer
import net.fabricmc.fabric.api.entity.event.v1.ServerLivingEntityEvents
import net.minecraft.server.network.ServerPlayerEntity

object SafeCobblemonBattles : ModInitializer {
    override fun onInitialize() {
        ServerLivingEntityEvents.ALLOW_DAMAGE.register { entity, _, _ ->
            if (!entity.world.isClient && entity is ServerPlayerEntity) {
                if (Cobblemon.battleRegistry.getBattleByParticipatingPlayer(entity) != null) return@register false
            }

            return@register true
        }
    }
}