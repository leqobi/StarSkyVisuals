package com.starskyvisuals.client.module;

import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.Entity;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.math.Vec3d;

public class CriticalEffect {

    public static boolean enabled = true;

    public static void onCrit(Entity target) {
        if (!enabled || target == null) return;

        MinecraftClient mc = MinecraftClient.getInstance();
        if (mc.world == null) return;

        Vec3d pos = target.getPos();
        double x = pos.x;
        double y = pos.y + target.getHeight() * 0.7;
        double z = pos.z;

        for (int i = 0; i < 12; i++) {
            double dx = (Math.random() - 0.5) * 0.8;
            double dy = (Math.random() - 0.5) * 0.8;
            double dz = (Math.random() - 0.5) * 0.8;
            mc.world.addParticle(ParticleTypes.ENCHANTED_HIT, x, y, z, dx, dy, dz);
        }
    }
}
