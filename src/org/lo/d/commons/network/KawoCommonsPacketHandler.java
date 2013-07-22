package org.lo.d.commons.network;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;

import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet250CustomPayload;

import org.lo.d.commons.reflections.ReflectionSupport;

import com.google.common.collect.Maps;

import cpw.mods.fml.common.network.IPacketHandler;
import cpw.mods.fml.common.network.Player;

public class KawoCommonsPacketHandler implements IPacketHandler, ReflectionSupport.Worker {

	@Retention(RetentionPolicy.RUNTIME)
	@Target({ ElementType.TYPE })
	public @interface KawoCommonsCustomPacketHandler {
		@Retention(RetentionPolicy.RUNTIME)
		@Target({ ElementType.METHOD })
		public @interface HandleMethod {
		}

		String value();
	}

	private static class PacketHandlerInvoker {
		private final Method m;
		private final Object o;

		public PacketHandlerInvoker(Method m, Object o) {
			this.m = m;
			this.o = o;
		}

		public void invokeHandleMethod(INetworkManager manager, Packet250CustomPayload packet, Player player) {
			try {
				m.invoke(o, manager, packet, player);
			} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
				e.printStackTrace();
			}
		}
	}

	private static final Map<String, PacketHandlerInvoker> map = Maps.newHashMap();

	@Override
	public void onPacketData(INetworkManager manager, Packet250CustomPayload packet, Player player) {
		if (!map.containsKey(packet.channel)) {
			return;
		}
		map.get(packet.channel).invokeHandleMethod(manager, packet, player);
	}

	@Override
	public void work(Class<?> cls) {
		KawoCommonsCustomPacketHandler typeAnno = cls.getAnnotation(KawoCommonsCustomPacketHandler.class);
		if (typeAnno == null) {
			return;
		}

		Constructor<?> c;
		try {
			c = cls.getConstructor(new Class<?>[] {});
			if (c == null) {
				return;
			}

			for (Method method : ReflectionSupport.getAllRecursiveMethods(cls)) {
				if (!validateMethod(method)) {
					continue;
				}

				KawoCommonsCustomPacketHandler.HandleMethod methodAnno = method
						.getAnnotation(KawoCommonsCustomPacketHandler.HandleMethod.class);
				if (methodAnno == null) {
					continue;
				}

				map.put(typeAnno.value(), new PacketHandlerInvoker(method, c.newInstance(new Object[] {})));
			}
		} catch (NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException
				| IllegalArgumentException | InvocationTargetException e) {
			e.printStackTrace();
			return;
		}
	}

	private boolean validateMethod(Method method) {
		Class<?>[] paramTypes = method.getParameterTypes();
		if (paramTypes.length != 3) {
			return false;
		}
		int i = 0;
		for (Class<?> c : new Class<?>[] { INetworkManager.class, Packet250CustomPayload.class, Player.class }) {
			if (!c.isAssignableFrom(paramTypes[i])) {
				return false;
			}
			i++;
		}
		return true;
	}

}
