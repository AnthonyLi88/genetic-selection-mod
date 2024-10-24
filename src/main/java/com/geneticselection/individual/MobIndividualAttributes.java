package com.geneticselection.individual;

import com.geneticselection.attributes.GlobalAttributesManager;
import com.geneticselection.attributes.MobAttributes;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerEntityEvents;
import net.minecraft.entity.Entity;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.passive.CowEntity;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class MobIndividualAttributes {
    private static final Map<Entity, MobAttributes> individualAttributes = new HashMap<>();

    public static void register() {
        ServerEntityEvents.ENTITY_LOAD.register(MobIndividualAttributes::onEntityLoad);
        ServerEntityEvents.ENTITY_UNLOAD.register(MobIndividualAttributes::onEntityUnload);
    }

    private static void onEntityLoad(Entity entity, net.minecraft.world.World world) {
        if (entity instanceof CowEntity cow) {
            MobAttributes attributes = initializeAttributes(cow);
            individualAttributes.put(cow, attributes);
            applyAttributes(cow, attributes);
        }
    }

    private static void onEntityUnload(Entity entity, net.minecraft.world.World world) {
        if (entity instanceof CowEntity) {
            individualAttributes.remove(entity);
        }
    }

    private static MobAttributes initializeAttributes(CowEntity cow) {
        MobAttributes global = GlobalAttributesManager.getAttributes(cow.getType());
        double speed = global.getMovementSpeed() * (0.9 + Math.random() * 0.2);
        double health = global.getMaxHealth() * (0.9 + Math.random() * 0.2);
        return new MobAttributes(speed, health);
    }

    private static void applyAttributes(CowEntity cow, MobAttributes attributes) {
        Objects.requireNonNull(cow.getAttributeInstance(EntityAttributes.GENERIC_MOVEMENT_SPEED))
                .setBaseValue(attributes.getMovementSpeed());
        Objects.requireNonNull(cow.getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH))
                .setBaseValue(attributes.getMaxHealth());
        cow.setHealth((float) attributes.getMaxHealth());
    }

    public static MobAttributes getAttributes(Entity entity) {
        return individualAttributes.getOrDefault(entity, GlobalAttributesManager.getAttributes(entity.getType()));
    }

    public static void setAttributes(Entity entity, MobAttributes attributes) {
        individualAttributes.put(entity, attributes);
        applyAttributes((CowEntity) entity, attributes);
    }
}