/*
 * Open Parties and Claims - adds chunk claims and player parties to Minecraft
 * Copyright (C) 2022-2023, Xaero <xaero1996@gmail.com> and contributors
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

package xaero.pac.common;

import net.neoforged.fml.ModList;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import xaero.pac.OpenPartiesAndClaims;
import xaero.pac.OpenPartiesAndClaimsNeoForge;
import xaero.pac.common.event.CommonEventsNeoForge;
import xaero.pac.common.packet.PacketHandlerNeoForge;

public class LoadCommonNeoForge<L extends LoadCommon> {
	
	protected final OpenPartiesAndClaimsNeoForge modMain;
	protected final L loader;

	public LoadCommonNeoForge(OpenPartiesAndClaimsNeoForge modMain, L loader) {
		this.modMain = modMain;
		this.loader = loader;
		CommonEventsNeoForge commonEventsNeoForge = new CommonEventsNeoForge(modMain);
		NeoForge.EVENT_BUS.register(commonEventsNeoForge);
		modMain.setCommonEventsForge(commonEventsNeoForge);
	}

	public void loadCommon(final FMLCommonSetupEvent event) {
		modMain.getForgeConfigHelper().setModContainer(ModList.get().getModContainerById(OpenPartiesAndClaims.MOD_ID).get());
		loader.loadCommon();
	}

	public void onRegisterPayloadHandler(RegisterPayloadHandlersEvent event){
		PacketHandlerNeoForge.registerPayloadHandler(event);
	}

}