package org.lo.d.commons.gui;

import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;

import com.google.common.collect.Lists;

public class ContainerTab extends Container {

	private final List<Container> tabContainers = Lists.newArrayList();

	public boolean add(Container e) {
		return tabContainers.add(e);
	}

	@Override
	public boolean canInteractWith(EntityPlayer entityplayer) {
		for (Container container : tabContainers) {
			if (!container.canInteractWith(entityplayer)) {
				return false;
			}
		}
		return true;
	}

}
