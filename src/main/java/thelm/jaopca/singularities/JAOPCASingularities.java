package thelm.jaopca.singularities;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.ModMetadata;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(
		modid = JAOPCASingularities.MOD_ID,
		name = JAOPCASingularities.NAME,
		version = JAOPCASingularities.VERSION,
		dependencies = "required-after:avaritia;required-before:jaopca@[1.12.2-2.2.5.64,)"
		)
public class JAOPCASingularities {
	public static final String MOD_ID = "jaopcasingularities";
	public static final String NAME = "JAOPCASingularities";
	public static final String VERSION = "1.12.2-2.2.0.8";
	@Instance(JAOPCASingularities.MOD_ID)
	public static JAOPCASingularities core;
	@SidedProxy(serverSide="thelm.jaopca.singularities.CommonProxy",clientSide="thelm.jaopca.singularities.ClientProxy")
	public static CommonProxy proxy;
	public static ModMetadata metadata;

	@EventHandler
	public void firstMovement(FMLPreInitializationEvent event) {
		metadata = event.getModMetadata();
		metadata.autogenerated = false;
		metadata.version = VERSION;
		metadata.credits = "Idea taken from AOBD Singularities by RCXCrafter";
		metadata.authorList.add("TheLMiffy1111");
		metadata.description = "A mod that aims to add singularities for more ores to Avaritia.";

		ModuleAvaritia.register();
	}
}
