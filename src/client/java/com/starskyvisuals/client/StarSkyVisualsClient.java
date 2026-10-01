package com.starskyvisuals.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.event.player.AttackEntityCallback;
import net.minecraft.util.ActionResult;

import com.starskyvisuals.client.module.HitParticles;
import com.starskyvisuals.client.module.CriticalEffect;

public class StarSkyVisualsClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        AttackEntityCallback.EVENT.register((player, world, hand, entity, hitResult) -> {
            if (player == null || world == null || entity == null) return ActionResult.PASS;

            HitParticles.onHit(entity);

            // Крит: игрок в падении
            if (player.fallDistance > 0.0f && !player.isOnGround()) {
                CriticalEffect.onCrit(entity);
            }

            return ActionResult.PASS;
        });
    }
}
