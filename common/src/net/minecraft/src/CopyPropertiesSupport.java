package net.minecraft.src;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

public class CopyPropertiesSupport<E> {

	private static List<Field> getInstanceFields(Class cls) {
		List<Field> result = new ArrayList<Field>();
		if (cls.getSuperclass() != null) {
			result.addAll(getInstanceFields(cls.getSuperclass()));
		}

		for (Field field : cls.getDeclaredFields()) {
			int mod = field.getModifiers();
			if (!Modifier.isFinal(mod) && !Modifier.isStatic(mod)) {
				field.setAccessible(true);
				result.add(field);
			}
		}

		return result;
	}

	private final List<Field> fList;

	public CopyPropertiesSupport(Class<E> cls) {
		fList = getInstanceFields(cls);
	}

	public void copyProperties(E src, E dist) {
		for (Field field : fList) {
			try {
				Object o = field.get(src);
				field.set(dist, o);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
