/*
 * Open Parties and Claims - adds chunk claims and player parties to Minecraft
 * Copyright (C) 2024, Xaero <xaero1996@gmail.com> and contributors
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of version 3 of the GNU Lesser General Public License
 * (LGPL-3.0-only) as published by the Free Software Foundation.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received copies of the GNU Lesser General Public License
 * and the GNU General Public License along with this program.
 * If not, see <https://www.gnu.org/licenses/>.
 */

package xaero.pac.common.mixin;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.enchantment.EnchantedItemInUse;
import net.minecraft.world.item.enchantment.effects.ReplaceDisk;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import xaero.pac.common.server.core.ServerCore;

@Mixin(value = ReplaceDisk.class, priority = 1000001)
public class MixinReplaceDisk {

	@Inject(method = "apply", at = @At("HEAD"), cancellable = true)
	public void onApplyHead(ServerLevel level, int i, EnchantedItemInUse enchantedItemInUse, Entity entity, Vec3 pos, CallbackInfo ci){
		if(ServerCore.captureEnchantmentEffectLevel(level, entity))
			ci.cancel();
	}

	@ModifyVariable(method = "apply", at = @At(value = "INVOKE_ASSIGN", target = "Lnet/minecraft/core/BlockPos;offset(Lnet/minecraft/core/Vec3i;)Lnet/minecraft/core/BlockPos;", ordinal = 0))
	public BlockPos captureLevel(BlockPos actual, ServerLevel level, int i, EnchantedItemInUse enchantedItemInUse, Entity entity, Vec3 pos){
		return ServerCore.replaceEnchantmentEffectBlockPosDisk(actual, level, entity, i, (ReplaceDisk)(Object)this);
	}

	@Inject(method = "apply", at = @At("RETURN"))
	public void onApplyReturn(ServerLevel serverLevel, int i, EnchantedItemInUse enchantedItemInUse, Entity entity, Vec3 vec3, CallbackInfo ci){
		ServerCore.postEnchantmentEffectOnDisk();
	}

}
