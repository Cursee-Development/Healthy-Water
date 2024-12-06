package com.cursee.healthy_water.mixin;

import net.minecraft.world.entity.Attackable;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.alchemy.Potions;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntity.class)
public class FabricLivingEntityMixin {

    @Shadow protected ItemStack useItem;

    @Inject(method = "completeUsingItem", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/LivingEntity;triggerItemUseEffects(Lnet/minecraft/world/item/ItemStack;I)V"))
    public void inject_$_onCompleteUsingItem(CallbackInfo ci) {

        LivingEntity instance = (LivingEntity) (Object) this;

        if (!(instance instanceof Player player)) return;

        ItemStack usedItemStack = this.useItem.copy();

        if (usedItemStack.getItem() == Items.POTION && PotionUtils.getPotion(usedItemStack) == Potions.WATER) {
            player.getFoodData().eat(1, 0.5F);
            player.heal(1.0F);
        }
    }
}
