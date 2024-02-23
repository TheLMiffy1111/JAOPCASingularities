package thelm.jaopca.singularities.compat.eternalsingularity;

import java.lang.reflect.Field;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import singulariteam.eternalsingularity.proxy.CommonProxy;
import thelm.jaopca.api.oredict.IOredictModule;
import thelm.jaopca.api.oredict.JAOPCAOredictModule;
import thelm.jaopca.singularities.compat.avaritia.items.JAOPCASingularityItem;

@JAOPCAOredictModule(modDependencies = "eternalsingularity")
public class EternalSingularityOredictModule implements IOredictModule {

	private static final Logger LOGGER = LogManager.getLogger();

	@Override
	public String getName() {
		return "eternalsingularity";
	}

	@Override
	public void register() {
		try {
			Field classSetField = CommonProxy.class.getDeclaredField("classSet");
			classSetField.setAccessible(true);
			Set<Class<?>> classSet = (Set<Class<?>>)classSetField.get(null);
			classSet.add(JAOPCASingularityItem.class);
		}
		catch(Exception e) {
			LOGGER.warn("Unable to add JAOPCA singularities to Eternal Singularity", e);
		}
	}
}
