package org.lo.d.commons.reflections;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Set;

import com.google.common.collect.Sets;

public class ReflectionSupport {

	public interface MethodFilter {
		enum State {
			INCLUDE,
			EXCLUDE;
		}

		State doFilter(Method method);
	}

	public interface FieldFilter {
		enum State {
			INCLUDE,
			EXCLUDE;
		}

		State doFilter(Field field);
	}

	public interface Worker {
		void work(Class<?> cls);
	}
	private static class DefaultFieldFilter implements FieldFilter {

		@Override
		public State doFilter(Field field) {
			return State.INCLUDE;
		}
	}

	private static class DefaultMethodFilter implements MethodFilter {
		@Override
		public State doFilter(Method method) {
			return State.INCLUDE;
		}
	}
	public static Field[] getFilteredRecursiveFields(Class<?> cls, final FieldFilter filter) {
		final Set<Field> fields = Sets.newLinkedHashSet();

		recursiveProcess(cls, new Worker() {
			@Override
			public void work(Class<?> cls) {
				for (Field field : cls.getDeclaredFields()) {
					for (Field f : fields) {
						if (f.getName().equals(field.getName())) {
							continue;
						}
					}
					if (filter.doFilter(field) == FieldFilter.State.INCLUDE) {
						fields.add(field);
					}
				}
			}
		});

		return fields.toArray(new Field[] {});
	}

	public static Field[] getAllRecursiveFields(Class<?> cls) {
		return getFilteredRecursiveFields(cls, new DefaultFieldFilter());
	}

	public static Method[] getAllRecursiveMethods(Class<?> cls) {
		return getFilteredRecursiveMethods(cls, new DefaultMethodFilter());
	}

	public static Method[] getFilteredRecursiveMethods(Class<?> cls, final MethodFilter filter) {
		final Set<Method> methods = Sets.newLinkedHashSet();
		recursiveProcess(cls, new Worker() {
			@Override
			public void work(Class<?> cls) {
				for (Method method : cls.getDeclaredMethods()) {
					for (Method m : methods) {
						if (m.getName().equals(method.getName())) {
							continue;
						}
					}
					if (filter.doFilter(method) == MethodFilter.State.INCLUDE) {
						methods.add(method);
					}
				}
			}
		});
		return methods.toArray(new Method[] {});
	}

	public static void recursiveProcess(Class<?> cls, Worker worker) {
		if (cls == null) {
			return;
		}
		worker.work(cls);
		recursiveProcess(cls.getSuperclass(), worker);
	}
}
