package thelm.jaopca.singularities;

import net.minecraftforge.fml.common.Mod;

@Mod(
		modid = JAOPCASingularities.MOD_ID,
		name = JAOPCASingularities.NAME,
		version = JAOPCASingularities.VERSION,
		dependencies = JAOPCASingularities.DEPENDENCIES
		)
public class JAOPCASingularities {

	public static final String MOD_ID = "jaopcasingularities";
	public static final String NAME = "JAOPCASingularities";
	public static final String VERSION = "1.12.2-0@VERSION@";
	public static final String DEPENDENCIES = "required-after:avaritia;required-before:jaopca@[1.12.2-2.3.2,);";
}
