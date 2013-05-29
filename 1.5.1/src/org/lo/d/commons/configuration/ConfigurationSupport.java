package org.lo.d.commons.configuration;

import java.lang.annotation.Annotation;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

import net.minecraftforge.common.Configuration;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;

public class ConfigurationSupport {

	@Retention(RetentionPolicy.RUNTIME)
	@Target({
			ElementType.METHOD, ElementType.FIELD
	})
	public @interface BlockIdConfig {
		int defaultValue();

		String name();
	}

	@Retention(RetentionPolicy.RUNTIME)
	@Target({
			ElementType.METHOD, ElementType.FIELD
	})
	public @interface BooleanArrayConfig {
		boolean[] defaultValue();

		String name();
	}

	@Retention(RetentionPolicy.RUNTIME)
	@Target({
			ElementType.METHOD, ElementType.FIELD
	})
	public @interface BooleanConfig {
		boolean defaultValue();

		String name();
	}

	@Retention(RetentionPolicy.RUNTIME)
	@Target({
		ElementType.TYPE
	})
	public @interface ConfigurationMod {
	}

	@Retention(RetentionPolicy.RUNTIME)
	@Target({
			ElementType.METHOD, ElementType.FIELD
	})
	public @interface DoubleArrayConfig {
		double[] defaultValue();

		String name();
	}

	@Retention(RetentionPolicy.RUNTIME)
	@Target({
			ElementType.METHOD, ElementType.FIELD
	})
	public @interface DoubleConfig {
		double defaultValue();

		String name();
	}

	@Retention(RetentionPolicy.RUNTIME)
	@Target({
			ElementType.METHOD, ElementType.FIELD
	})
	public @interface IntArrayConfig {
		int[] defaultValue();

		String name();
	}

	@Retention(RetentionPolicy.RUNTIME)
	@Target({
			ElementType.METHOD, ElementType.FIELD
	})
	public @interface IntConfig {
		int defaultValue();

		String name();
	}

	@Retention(RetentionPolicy.RUNTIME)
	@Target({
			ElementType.METHOD, ElementType.FIELD
	})
	public @interface ItemIdConfig {
		int defaultValue();

		String name();
	}

	@Retention(RetentionPolicy.RUNTIME)
	@Target({
			ElementType.METHOD, ElementType.FIELD
	})
	public @interface StringArrayConfig {
		String[] defaultValue();

		String name();
	}

	@Retention(RetentionPolicy.RUNTIME)
	@Target({
			ElementType.METHOD, ElementType.FIELD
	})
	public @interface StringConfig {
		String defaultValue();

		String name();
	}

	private static abstract class AbstractProcessor<AN extends Annotation, T> implements Processor<AN, T> {
		@Override
		public void injectValue(Configuration config, Field field) throws IllegalArgumentException,
				IllegalAccessException {
			AN configAnnotation = getAnnotation(field);

			String key = getName(configAnnotation);
			T defaultValue = getDefaultValue(configAnnotation);

			T value = getValue(config, key, defaultValue);

			field.setAccessible(true);
			field.set(null, value);
		}

		@Override
		public void injectValue(Configuration config, Method method) throws IllegalArgumentException,
				IllegalAccessException, InvocationTargetException {
			AN configAnnotation = getAnnotation(method);

			String key = getName(configAnnotation);
			T defaultValue = getDefaultValue(configAnnotation);

			T value = getValue(config, key, defaultValue);

			method.setAccessible(true);
			method.invoke(null, (Object[]) value);
		}

		@Override
		public boolean isAnnotated(Field field) {
			return getAnnotation(field) != null;
		}

		@Override
		public boolean isAnnotated(Method method) {
			return getAnnotation(method) != null;
		}

		protected abstract AN getAnnotation(Field field);

		protected AN getAnnotation(Field field, Class<AN> annoType) {
			if (!Modifier.isStatic(field.getModifiers())) {
				return null;
			}
			return field.getAnnotation(annoType);
		}

		protected abstract AN getAnnotation(Method method);

		protected AN getAnnotation(Method method, Class<T> paramType, Class<AN> annoType) {
			if (!Modifier.isStatic(method.getModifiers())) {
				return null;
			}
			Class<?>[] paramTypes = method.getParameterTypes();
			if (paramTypes == null || paramTypes.length != 1) {
				return null;
			}
			if (!paramType.isAssignableFrom(paramTypes[0])) {
				return null;
			}

			return method.getAnnotation(annoType);
		}

		protected abstract T getDefaultValue(AN configAnnotation);

		protected abstract String getName(AN configAnnotation);

		protected abstract T getValue(Configuration config, String key, T defaultValue);
	}

	private static class BlockIdConfigProcessor extends AbstractProcessor<BlockIdConfig, Integer> {

		@Override
		protected BlockIdConfig getAnnotation(Field field) {
			return getAnnotation(field, BlockIdConfig.class);
		}

		@Override
		protected BlockIdConfig getAnnotation(Method method) {
			return getAnnotation(method, Integer.class, BlockIdConfig.class);
		}

		@Override
		protected Integer getDefaultValue(BlockIdConfig configAnnotation) {
			return configAnnotation.defaultValue();
		}

		@Override
		protected String getName(BlockIdConfig configAnnotation) {
			return configAnnotation.name();
		}

		@Override
		protected Integer getValue(Configuration config, String key, Integer defaultValue) {
			return config.getBlock(key, defaultValue).getInt(defaultValue);
		}
	}

	private static class BooleanArrayConfigProcessor extends AbstractProcessor<BooleanArrayConfig, boolean[]> {

		@Override
		protected BooleanArrayConfig getAnnotation(Field field) {
			return getAnnotation(field, BooleanArrayConfig.class);
		}

		@Override
		protected BooleanArrayConfig getAnnotation(Method method) {
			return getAnnotation(method, boolean[].class, BooleanArrayConfig.class);
		}

		@Override
		protected boolean[] getDefaultValue(BooleanArrayConfig configAnnotation) {
			return configAnnotation.defaultValue();
		}

		@Override
		protected String getName(BooleanArrayConfig configAnnotation) {
			return configAnnotation.name();
		}

		@Override
		protected boolean[] getValue(Configuration config, String key, boolean[] defaultValue) {
			return config.get(Configuration.CATEGORY_GENERAL, key, defaultValue).getBooleanList();
		}
	}

	private static class BooleanConfigProcessor extends AbstractProcessor<BooleanConfig, Boolean> {

		@Override
		protected BooleanConfig getAnnotation(Field field) {
			return getAnnotation(field, BooleanConfig.class);
		}

		@Override
		protected BooleanConfig getAnnotation(Method method) {
			return getAnnotation(method, Boolean.class, BooleanConfig.class);
		}

		@Override
		protected Boolean getDefaultValue(BooleanConfig configAnnotation) {
			return configAnnotation.defaultValue();
		}

		@Override
		protected String getName(BooleanConfig configAnnotation) {
			return configAnnotation.name();
		}

		@Override
		protected Boolean getValue(Configuration config, String key, Boolean defaultValue) {
			return config.get(Configuration.CATEGORY_GENERAL, key, defaultValue).getBoolean(defaultValue);
		}
	}

	private static class DoubleArrayConfigProcessor extends AbstractProcessor<DoubleArrayConfig, double[]> {

		@Override
		protected DoubleArrayConfig getAnnotation(Field field) {
			return getAnnotation(field, DoubleArrayConfig.class);
		}

		@Override
		protected DoubleArrayConfig getAnnotation(Method method) {
			return getAnnotation(method, double[].class, DoubleArrayConfig.class);
		}

		@Override
		protected double[] getDefaultValue(DoubleArrayConfig configAnnotation) {
			return configAnnotation.defaultValue();
		}

		@Override
		protected String getName(DoubleArrayConfig configAnnotation) {
			return configAnnotation.name();
		}

		@Override
		protected double[] getValue(Configuration config, String key, double[] defaultValue) {
			return config.get(Configuration.CATEGORY_GENERAL, key, defaultValue).getDoubleList();
		}
	}

	private static class DoubleConfigProcessor extends AbstractProcessor<DoubleConfig, Double> {

		@Override
		protected DoubleConfig getAnnotation(Field field) {
			return getAnnotation(field, DoubleConfig.class);
		}

		@Override
		protected DoubleConfig getAnnotation(Method method) {
			return getAnnotation(method, Double.class, DoubleConfig.class);
		}

		@Override
		protected Double getDefaultValue(DoubleConfig configAnnotation) {
			return configAnnotation.defaultValue();
		}

		@Override
		protected String getName(DoubleConfig configAnnotation) {
			return configAnnotation.name();
		}

		@Override
		protected Double getValue(Configuration config, String key, Double defaultValue) {
			return config.get(Configuration.CATEGORY_GENERAL, key, defaultValue).getDouble(defaultValue);
		}
	}

	private static class IntArrayConfigProcessor extends AbstractProcessor<IntArrayConfig, int[]> {

		@Override
		protected IntArrayConfig getAnnotation(Field field) {
			return getAnnotation(field, IntArrayConfig.class);
		}

		@Override
		protected IntArrayConfig getAnnotation(Method method) {
			return getAnnotation(method, int[].class, IntArrayConfig.class);
		}

		@Override
		protected int[] getDefaultValue(IntArrayConfig configAnnotation) {
			return configAnnotation.defaultValue();
		}

		@Override
		protected String getName(IntArrayConfig configAnnotation) {
			return configAnnotation.name();
		}

		@Override
		protected int[] getValue(Configuration config, String key, int[] defaultValue) {
			return config.get(Configuration.CATEGORY_GENERAL, key, defaultValue).getIntList();
		}
	}

	private static class IntConfigProcessor extends AbstractProcessor<IntConfig, Integer> {

		@Override
		protected IntConfig getAnnotation(Field field) {
			return getAnnotation(field, IntConfig.class);
		}

		@Override
		protected IntConfig getAnnotation(Method method) {
			return getAnnotation(method, Integer.class, IntConfig.class);
		}

		@Override
		protected Integer getDefaultValue(IntConfig configAnnotation) {
			return configAnnotation.defaultValue();
		}

		@Override
		protected String getName(IntConfig configAnnotation) {
			return configAnnotation.name();
		}

		@Override
		protected Integer getValue(Configuration config, String key, Integer defaultValue) {
			return config.get(Configuration.CATEGORY_GENERAL, key, defaultValue).getInt(defaultValue);
		}
	}

	private static class ItemIdConfigProcessor extends AbstractProcessor<ItemIdConfig, Integer> {

		@Override
		protected ItemIdConfig getAnnotation(Field field) {
			return getAnnotation(field, ItemIdConfig.class);
		}

		@Override
		protected ItemIdConfig getAnnotation(Method method) {
			return getAnnotation(method, Integer.class, ItemIdConfig.class);
		}

		@Override
		protected Integer getDefaultValue(ItemIdConfig configAnnotation) {
			return configAnnotation.defaultValue();
		}

		@Override
		protected String getName(ItemIdConfig configAnnotation) {
			return configAnnotation.name();
		}

		@Override
		protected Integer getValue(Configuration config, String key, Integer defaultValue) {
			return config.getItem(key, defaultValue).getInt(defaultValue);
		}
	}

	private interface Processor<AN, T> {
		void injectValue(Configuration config, Field field) throws IllegalArgumentException, IllegalAccessException;

		void injectValue(Configuration config, Method method) throws IllegalArgumentException, IllegalAccessException,
				InvocationTargetException;

		boolean isAnnotated(Field field);

		boolean isAnnotated(Method method);
	}

	private static class StringArrayConfigProcessor extends AbstractProcessor<StringArrayConfig, String[]> {

		@Override
		protected StringArrayConfig getAnnotation(Field field) {
			return getAnnotation(field, StringArrayConfig.class);
		}

		@Override
		protected StringArrayConfig getAnnotation(Method method) {
			return getAnnotation(method, String[].class, StringArrayConfig.class);
		}

		@Override
		protected String[] getDefaultValue(StringArrayConfig configAnnotation) {
			return configAnnotation.defaultValue();
		}

		@Override
		protected String getName(StringArrayConfig configAnnotation) {
			return configAnnotation.name();
		}

		@Override
		protected String[] getValue(Configuration config, String key, String[] defaultValue) {
			return config.get(Configuration.CATEGORY_GENERAL, key, defaultValue).getStringList();
		}

	}

	private static class StringConfigProcessor extends AbstractProcessor<StringConfig, String> {
		@Override
		protected StringConfig getAnnotation(Field field) {
			return getAnnotation(field, StringConfig.class);
		}

		@Override
		protected StringConfig getAnnotation(Method method) {
			return getAnnotation(method, String.class, StringConfig.class);
		}

		@Override
		protected String getDefaultValue(StringConfig configAnnotation) {
			return configAnnotation.defaultValue();
		}

		@Override
		protected String getName(StringConfig configAnnotation) {
			return configAnnotation.name();
		}

		@Override
		protected String getValue(Configuration config, String key, String defaultValue) {
			return config.get(Configuration.CATEGORY_GENERAL, key, defaultValue).getString();
		}
	}

	private static final Processor<?, ?>[] PROCESSORS = {
			new BlockIdConfigProcessor(),
			new ItemIdConfigProcessor(),
			new BooleanConfigProcessor(),
			new BooleanArrayConfigProcessor(),
			new IntConfigProcessor(),
			new IntArrayConfigProcessor(),
			new DoubleConfigProcessor(),
			new DoubleArrayConfigProcessor(),
			new StringConfigProcessor(),
			new StringArrayConfigProcessor(),
	};

	public static void load(Class<?> modClass, FMLPreInitializationEvent event) throws IllegalArgumentException,
			IllegalAccessException, InvocationTargetException {
		ConfigurationMod configurationMod = modClass.getAnnotation(ConfigurationMod.class);
		if (configurationMod == null) {
			return;
		}

		Configuration config = new Configuration(event.getSuggestedConfigurationFile());
		config.load();

		for (Processor<?, ?> processor : PROCESSORS) {
			for (Field field : modClass.getDeclaredFields()) {
				if (!processor.isAnnotated(field)) {
					continue;
				}
				processor.injectValue(config, field);
			}
			for (Method method : modClass.getDeclaredMethods()) {
				if (!processor.isAnnotated(method)) {
					continue;
				}
				processor.injectValue(config, method);
			}
		}

		config.save();

	}
}
