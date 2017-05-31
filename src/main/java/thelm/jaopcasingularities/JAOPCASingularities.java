package thelm.jaopcasingularities;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.ModMetadata;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(
		modid = JAOPCASingularities.MOD_ID,
		name = "JAOPCASingularities",
		version = JAOPCASingularities.VERSION,
		dependencies = "required-after:avaritia;required-before:jaopca@[1.10.2-1.0.10,)"
		)
public class JAOPCASingularities {
	public static final String MOD_ID = "jaopcasingularities";
	public static final String VERSION = "1.10.2-1.0.1";
	@Instance(JAOPCASingularities.MOD_ID)
	public static JAOPCASingularities core;
	@SidedProxy(serverSide="thelm.jaopcasingularities.CommonProxy",clientSide="thelm.jaopcasingularities.ClientProxy")
	public static CommonProxy proxy;
	public static ModMetadata metadata;
	
	@EventHandler
	public void firstMovement(FMLPreInitializationEvent event) {
		metadata = event.getModMetadata();
		metadata.autogenerated = false;
		metadata.modId = MOD_ID;
		metadata.version = VERSION;
		metadata.name = "Just A Ore Processing Compatibility Attempt: Singularities";
		metadata.credits = "Idea taken from AOBD Singularities by RCXCrafter";
		metadata.authorList.add("TheLMiffy1111");
		metadata.description = "A mod that aims to add singularities for more ores to avaritia.";

		ModuleAvaritia.register();
	}
}
