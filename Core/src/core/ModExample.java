package core;

import com.maceswinger.items.Condition;
import com.maceswinger.items.Material;
import com.maceswinger.mods.Mod;
import com.maceswinger.mods.ModInfo;
@ModInfo
public class ModExample extends Mod {

	public static final Material STEEL = new Material(102, "Steel", 40);
	public static final Condition MIGHTYFINE = new Condition(102, "Mighy Fine", 30);


	public void init() {
		//registerMaterial(STEEL);
		//registerCondition(MIGHTYFINE);
	}

	@Override
	public void info() {
		name = "Example Mod";
		desc = "how lovely";

	}
}