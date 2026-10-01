package com.starskyvisuals.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;

import com.starskyvisuals.client.module.HitParticles;
import com.starskyvisuals.client.module.CriticalEffect;

public class StarSkyVisualsClient implements ClientModInitializer {

    private Entity lastTarget = null;
    private long lastAttackTime = 0;

    @Override
    public void onInitializeClient() {
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (client.player == null || client.world == null) return;

            // Определяем цель: сущность под прицелом в радиусе 4 блоков
            Entity target = getTargetUnderCrosshair(client);
            if (target == null) {
                lastTarget = null;
                return;
            }

            // Проверяем: игрок атакует (нажата ЛКМ) и цель сменилась/новая атака
            if (client.options.attackKey.isPressed() && target != lastTarget) {
                HitParticles.onHit(target);
                if (client.player.fallDistance > 0.0f && !client.player.isOnGround()) {
                    CriticalEffect.onCrit(target);
                }
                lastTarget = target;
                lastAttackTime = System.currentTimeMillis();
            }
        });
    }

    private Entity getTargetUnderCrosshair(MinecraftClient client) {
        if (client.crosshairTarget == null) return null;
        if (!(client.crosshairTarget instanceof net.minecraft.util.hit.EntityHitResult ehr)) return null;
        Entity e = ehr.getEntity();
        if (e == client.player) return null;
        if (!(e instanceof LivingEntity)) return null;
        return e;
    }
}
