package net.minecraft.src;

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;

import javax.imageio.ImageIO;

import net.minecraft.client.Minecraft;

public class RenderLittleMaidEx extends RenderLittleMaid {
	private static interface Factory<T> {
		boolean check(Class cls);

		boolean catchException(String className);

		T createInstance(Class cls) throws Throwable;
	}

	private static class ExtraEntry {
		private Class baseModelClass;
		private Class childEntityClass;
		private String textureName;
		private int color;

		private ExtraEntry(Class baseModelClass, Class childEntityClass,
				String textureName, int color) {
			super();
			this.baseModelClass = baseModelClass;
			this.childEntityClass = childEntityClass;
			this.textureName = textureName;
			this.color = color;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime
					* result
					+ ((baseModelClass == null) ? 0 : baseModelClass.hashCode());
			result = prime
					* result
					+ ((childEntityClass == null) ? 0 : childEntityClass
							.hashCode());
			result = prime * result + color;
			result = prime * result
					+ ((textureName == null) ? 0 : textureName.hashCode());
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			ExtraEntry other = (ExtraEntry) obj;
			if (baseModelClass == null) {
				if (other.baseModelClass != null)
					return false;
			} else if (!baseModelClass.equals(other.baseModelClass))
				return false;
			if (childEntityClass == null) {
				if (other.childEntityClass != null)
					return false;
			} else if (!childEntityClass.equals(other.childEntityClass))
				return false;
			if (color != other.color)
				return false;
			if (textureName == null) {
				if (other.textureName != null)
					return false;
			} else if (!textureName.equals(other.textureName))
				return false;
			return true;
		}

	}

	public static final Map<ExtraEntry, ModelLittleMaid> exModelMap = new HashMap<ExtraEntry, ModelLittleMaid>();
	public static final Map<ExtraEntry, ILittleMaidExRenderHelper> exRenderMap = new HashMap<ExtraEntry, ILittleMaidExRenderHelper>();

	public static final Map<ExtraEntry, ModelBase> childModelMap = new HashMap<ExtraEntry, ModelBase>();
	public static final Map<ExtraEntry, String> childModelTextureMap = new HashMap<ExtraEntry, String>();

	public static final Map<Class, RenderHasRenderer> childRenderMap = new HashMap<Class, RenderHasRenderer>();

	private String currentTextureName;
	private int currentTextureColor;

	public RenderLittleMaidEx(ModelLittleMaid modellittlemaid, float f) {
		super(modellittlemaid, f);
	}

	@Override
	public void doRenderLitlleMaid(EntityLittleMaid entitylittlemaid, double d,
			double d1, double d2, float f, float f1) {
		currentTextureName = entitylittlemaid.textureName;
		currentTextureColor = entitylittlemaid.getMaidColor();
		ILittleMaidExRenderHelper helper = getExRenderHelper(this);
		helper.doRenderLitlleMaid(this, entitylittlemaid, d, d1, d2, f, f1);
	}

	protected void do_doRenderLitlleMaid(EntityLittleMaid entitylittlemaid,
			double d, double d1, double d2, float f, float f1) {
		EntityLittleMaidEx exMaid = (EntityLittleMaidEx) entitylittlemaid;

		ModelLittleMaid prevModel = modelMain;
		ModelLittleMaid model = getExModel(exMaid);
		mainModel = modelBipedMain = modelMain = model == null ? modelBasicOrig[0]
				: model;
		super.doRenderLitlleMaid(entitylittlemaid, d, d1, d2, f, f1);
		mainModel = modelBipedMain = modelMain = prevModel;

		Minecraft mc = ModLoader.getMinecraftInstance();
		for (Entity child : exMaid.getChildEntitySet()) {
			if (child instanceof EntityHasRenderer) {
				EntityHasRenderer e = (EntityHasRenderer) child;
				float f2 = e.renderYawOffset;
				float f3 = e.rotationYaw;
				float f4 = e.rotationPitch;
				if (mc.currentScreen instanceof GuiLittleMaidInventory) {
					e.renderYawOffset = entitylittlemaid.renderYawOffset;
					e.rotationYaw = entitylittlemaid.rotationYaw;
					e.rotationPitch = entitylittlemaid.rotationPitch;
					RenderHelper.enableStandardItemLighting();
				}
				renderEntityWithPosYaw(child, d, d1, d2, f, f1);
				e.renderYawOffset = f2;
				e.rotationYaw = f3;
				e.rotationPitch = f4;
			} else if (child instanceof EntityLiving) {
				EntityLiving e = (EntityLiving) child;
				float f2 = e.renderYawOffset;
				float f3 = e.rotationYaw;
				float f4 = e.rotationPitch;
				if (mc.currentScreen instanceof GuiLittleMaidInventory) {
					e.renderYawOffset = entitylittlemaid.renderYawOffset;
					e.rotationYaw = entitylittlemaid.rotationYaw;
					e.rotationPitch = entitylittlemaid.rotationPitch;
					RenderHelper.enableStandardItemLighting();
				}
				renderEntityWithPosYaw(child, d, d1, d2, f, f1);
				e.renderYawOffset = f2;
				e.rotationYaw = f3;
				e.rotationPitch = f4;
			} else {
				float f3 = child.rotationYaw;
				float f4 = child.rotationPitch;
				if (mc.currentScreen instanceof GuiLittleMaidInventory) {
					child.rotationYaw = entitylittlemaid.rotationYaw;
					child.rotationPitch = entitylittlemaid.rotationPitch;
					RenderHelper.enableStandardItemLighting();
				}
				renderEntityWithPosYaw(child, d, d1, d2, f, f1);
				child.rotationYaw = f3;
				child.rotationPitch = f4;
			}
		}

		currentTextureName = null;
	}

	public RenderHasRenderer getEntityClassRenderObject(Class par1Class) {
		RenderHasRenderer render = childRenderMap.get(par1Class);

		if (render == null && par1Class != (net.minecraft.src.Entity.class)) {
			render = getEntityClassRenderObject(par1Class.getSuperclass());
			childRenderMap.put(par1Class, render);
		}

		return render;
	}

	public RenderHasRenderer getEntityRenderObject(Entity par1Entity) {
		return getEntityClassRenderObject(par1Entity.getClass());
	}

	/**
	 * Renders the specified entity with the passed in position, yaw, and
	 * partialTickTime. Args: entity, x, y, z, yaw, partialTickTime
	 */
	public void renderEntityWithPosYaw(Entity par1Entity, double par2,
			double par4, double par6, float par8, float par9) {
		RenderHasRenderer render = getEntityRenderObject(par1Entity);

		if (render != null) {
			ModelBase model = getChildModel(par1Entity);
			if (model != null) {
				render.mainModel = model;
				render.renderManager = renderManager;
				render.doRender(par1Entity, par2, par4, par6, par8, par9);
			}
		}
	}

	public String getExClassSuffix() {
		return null;
	}

	public ModelLittleMaid getDefaultExModel() {
		return null;
	}

	public ILittleMaidExRenderHelper getExRenerHelperClass() {
		return null;
	}

	public String getChildEntityModelSuffix(Entity par1Entity) {
		return null;
	}

	public ModelBase getChildEntityModelClass(Entity par1Entity) {
		return null;
	}

	private ModelLittleMaid getExModel(EntityLittleMaidEx exMaid) {
		ExtraEntry entry = new ExtraEntry(mainModel.getClass(),
				exMaid.getClass(), currentTextureName, currentTextureColor);
		return getExModel(entry);
	}

	public ModelLittleMaid getExModel(EntityLittleMaidExChild<?> par1Entity,
			EntityLittleMaidEx parent) {
		ModelLittleMaid model = parent.textureModel[0];
		model = model != null ? model : modelBasicOrig[0];
		ExtraEntry entry = new ExtraEntry(model.getClass(), parent.getClass(),
				parent.textureName, parent.getMaidColor());
		if (exModelMap.containsKey(entry)) {
			return exModelMap.get(entry);
		} else {
			return null;
		}
	}

	protected ModelLittleMaid getExModel(ExtraEntry entry) {
		if (exModelMap.containsKey(entry)) {
			return exModelMap.get(entry);
		}
		String suffix = getExClassSuffix();
		final ModelLittleMaid defaultModel = getDefaultExModel();
		if (suffix == null || defaultModel == null) {
			return null;
		}
		Factory<ModelLittleMaid> factory = new Factory<ModelLittleMaid>() {
			@Override
			public boolean catchException(String className) {
				Object[] params = new Object[] {
						defaultModel.getClass().getSimpleName(), className, };
				ModLoader.getLogger().log(Level.INFO,
						"ExModel as {0} class not found from {1}", params);
				return false;
			}

			@Override
			public boolean check(Class cls) {
				return defaultModel.getClass().isAssignableFrom(cls)
						&& ModelLittleMaid.class.isAssignableFrom(cls);
			}

			@Override
			public ModelLittleMaid createInstance(Class cls) throws Throwable {
				Constructor constructor = cls.getConstructor();
				return (ModelLittleMaid) constructor.newInstance();
			}
		};
		ModelLittleMaid model = createClassInstance("_Ex_" + suffix, factory);

		if (model == null) {
			model = defaultModel;
		}

		if (model != null) {
			exModelMap.put(entry, model);
		}
		return model;
	}

	protected ILittleMaidExRenderHelper getExRenderHelper(
			RenderLittleMaidEx render) {
		ExtraEntry entry = new ExtraEntry(mainModel.getClass(),
				render.getClass(), currentTextureName, currentTextureColor);
		if (exRenderMap.containsKey(entry)) {
			return exRenderMap.get(entry);
		}
		String suffix = getExClassSuffix();
		final ILittleMaidExRenderHelper defaultHelper = getExRenerHelperClass();
		if (suffix == null || defaultHelper == null) {
			return null;
		}
		Factory<ILittleMaidExRenderHelper> factory = new Factory<ILittleMaidExRenderHelper>() {
			@Override
			public boolean catchException(String className) {
				Object[] params = new Object[] {
						defaultHelper.getClass().getSimpleName(), className, };
				ModLoader.getLogger().log(Level.INFO,
						"ExRenderHelper as {0} class not found from {1}",
						params);
				return false;
			}

			@Override
			public boolean check(Class cls) {
				return defaultHelper.getClass().isAssignableFrom(cls)
						&& ILittleMaidExRenderHelper.class
								.isAssignableFrom(cls);
			}

			@Override
			public ILittleMaidExRenderHelper createInstance(Class cls)
					throws Throwable {
				Constructor constructor = cls.getConstructor(new Class[] {});
				return (ILittleMaidExRenderHelper) constructor.newInstance();
			}
		};
		ILittleMaidExRenderHelper helper = createClassInstance("_ExRender_"
				+ suffix, factory);

		if (helper == null) {
			helper = defaultHelper;
		}

		if (helper != null) {
			exRenderMap.put(entry, helper);
		}
		return helper;
	}

	private ModelBase getChildModel(Entity par1Entity) {
		ExtraEntry entry = new ExtraEntry(mainModel.getClass(),
				par1Entity.getClass(), currentTextureName, currentTextureColor);
		return getChildModel(par1Entity, entry);
	}

	public ModelBase getChildModel(EntityLittleMaidExChild<?> par1Entity,
			EntityLittleMaidEx parent) {
		ModelLittleMaid model = getExModel(par1Entity, parent);
		if (model == null) {
			return null;
		}
		ExtraEntry entry = new ExtraEntry(model.getClass(),
				par1Entity.getClass(), parent.textureName,
				parent.getMaidColor());
		if (childModelMap.containsKey(entry)) {
			return childModelMap.get(entry);
		}
		return null;
	}

	protected ModelBase getChildModel(Entity par1Entity, ExtraEntry entry) {
		if (childModelMap.containsKey(entry)) {
			return childModelMap.get(entry);
		}
		String suffix = getChildEntityModelSuffix(par1Entity);
		final ModelBase defaultModel = getChildEntityModelClass(par1Entity);
		if (suffix == null || defaultModel == null) {
			return null;
		}
		Factory<ModelBase> factory = new Factory<ModelBase>() {
			@Override
			public boolean catchException(String className) {
				Object[] params = new Object[] {
						defaultModel.getClass().getSimpleName(), className, };
				ModLoader.getLogger().log(Level.INFO,
						"ExModel as {0} class not found from {1}", params);
				return false;
			}

			@Override
			public boolean check(Class cls) {
				return defaultModel.getClass().isAssignableFrom(cls)
						&& ModelBase.class.isAssignableFrom(cls);
			}

			@Override
			public ModelBase createInstance(Class cls) throws Throwable {
				Constructor constructor = cls.getConstructor();
				return (ModelBase) constructor.newInstance();
			}
		};
		ModelBase model = createClassInstance("_Ex_" + suffix, factory);

		if (model == null) {
			model = defaultModel;
		}

		if (model != null) {
			childModelMap.put(entry, model);
		}
		return model;
	}

	public static String getChildModelTexture(Class parentModelCls,
			String parentTextureName, int parentTextureColor, Class cls,
			String defaultTexture) {
		ExtraEntry entry = new ExtraEntry(parentModelCls, cls,
				parentTextureName, parentTextureColor);
		String textureName = childModelTextureMap.get(entry);
		if (textureName == null) {
			ModelBase model = childModelMap.get(entry);
			if (model != null) {
				textureName = getChildModelTexture(entry.textureName + "_"
						+ entry.color, model, defaultTexture);
				if (textureName == null) {
					textureName = getChildModelTexture(entry.textureName,
							model, defaultTexture);
					if (textureName == null) {
						textureName = defaultTexture;
					}
				}
				childModelTextureMap.put(entry, textureName);
			} else {
				textureName = defaultTexture;
			}
		}
		return textureName;
	}

	protected static String getChildModelTexture(String textureName,
			ModelBase model, String defaultTexture) {
		String loadTextureName = null;
		String fileName = null;
		InputStream is = null;
		try {
			String dir = defaultTexture.replaceAll("/[^/]*$", "/");
			fileName = dir + textureName + "_Ex_"
					+ model.getClass().getSimpleName() + ".png";
			is = TexturePackBase.class.getResourceAsStream(fileName);
			ImageIO.read(is);
			loadTextureName = fileName;
		} catch (Exception e) {
			Object[] params = new Object[] {
					model.getClass().getSimpleName(), fileName, };
			ModLoader.getLogger().log(Level.INFO,
					"ExChildTexture as {0} png not found from {1}", params);
		} finally {
			closeStream(is);
		}

		return loadTextureName;
	}

	private static void closeStream(Closeable closeable) {
		if (closeable != null) {
			try {
				closeable.close();
			} catch (IOException e) {
			}
		}
	}

	private <T> T createClassInstance(String suffix, Factory<T> factory) {
		T result = createClassInstanceByTextureAndColor(suffix, factory);
		if (result == null) {
			result = createClassInstanceByTexture(suffix, factory);
			if (result == null) {
				result = createClassInstanceDefault(suffix, factory);
			}
		}
		return result;
	}

	private <T> T createClassInstanceDefault(String suffix, Factory<T> factory) {
		return doCreateClassInstance(mainModel.getClass().getSimpleName()
				+ suffix, factory);

	}

	private <T> T createClassInstanceByTextureAndColor(String suffix,
			Factory<T> factory) {
		return doCreateClassInstance("Texture_" + currentTextureName + "_"
				+ currentTextureColor + suffix, factory);

	}

	private <T> T createClassInstanceByTexture(String suffix, Factory<T> factory) {
		return doCreateClassInstance("Texture_" + currentTextureName + suffix,
				factory);

	}

	private <T> T doCreateClassInstance(String name, Factory<T> factory) {
		T result = null;
		String className = name;
		try {
			ClassLoader classloader = (net.minecraft.client.Minecraft.class)
					.getClassLoader();
			Package package1 = (net.minecraft.src.ModLoader.class).getPackage();
			Class class1;

			if (package1 != null) {
				className = (new StringBuilder(String.valueOf(package1
						.getName()))).append(".").append(className).toString();
				class1 = classloader.loadClass(className);
			} else {
				class1 = Class.forName(className);
			}

			if (factory.check(class1)) {
				result = factory.createInstance(class1);
			}

		} catch (ClassNotFoundException e) {
			if (factory.catchException(className)) {
			}
		} catch (Throwable th) {
			th.printStackTrace();
		}
		return result;
	}
}
