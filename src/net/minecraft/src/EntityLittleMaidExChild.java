package net.minecraft.src;

public abstract class EntityLittleMaidExChild<M extends EntityLittleMaidEx>
		extends EntityHasRenderer {
	protected M parent;

	public EntityLittleMaidExChild(World par1World, M parent) {
		super(par1World);
		this.parent = parent;
	}

	@Override
	public String getTexture() {
		ModelLittleMaid modelLittleMaid = parent.textureModel[0];
		Class parentModelClass;
		if (modelLittleMaid != null) {
			parentModelClass = modelLittleMaid.getClass();
		} else {
			parentModelClass = ModelLittleMaid.class;
		}
		return RenderLittleMaidEx.getChildModelTexture(parentModelClass,
				parent.textureName, parent.getMaidColor(), getClass(),
				super.getTexture());
	}

}
