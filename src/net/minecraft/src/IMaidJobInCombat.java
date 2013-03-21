package net.minecraft.src;

public interface IMaidJobInCombat extends IMaidJob {
	public EntityLiving getCombatEnemy();

	public void inCombat(EntityLiving enemy);

	public void inPeace();
}
