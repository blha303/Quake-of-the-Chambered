package de.decgod.bashcommands;

import java.lang.reflect.Constructor;
import java.util.HashMap;

import com.mojang.escape.entities.Entity;

import de.decgod.bashcommandimpl.AbstractAction;
import de.decgod.mod.InGameLogger;
import de.decgod.mod.Scene;

public class Spawn extends AbstractAction {

	@Override
	public String getDescripton() {
		return "Spawn - <monster,x,z> - spawns <monster> @ <x,z>";
	}

	@Override
	public void doAction(HashMap<String, String> hm) {
		if (hm.size() == 0) {
			for (String e : Scene.getInstance().getEntities().keySet()) {
				InGameLogger.getInstance().addMessage(e);
			}
		}
		if (hm.size() == 3 && hm.get("key_2").matches("\\d+")
				&& hm.get("key_3").matches("\\d+")) {

			String classname = Scene.getInstance().getEntities()
					.get(hm.get("key_1"));

			if (classname != null) {

				Double x = new Double(Double.parseDouble(hm.get("key_2")));
				Double z =  new Double(Double.parseDouble(hm.get("key_3")));
				Entity entity = null;
				Constructor entitiyConstructor;

				try {
					entitiyConstructor = Class.forName(classname)
							.getConstructor(
									new Class[] { double.class, double.class });
					entity = (Entity) entitiyConstructor
							.newInstance(new Object[] { x, z });

				} catch (Exception e) {
					System.out.println(e);
					int xxx = x.intValue();
					int zzz = z.intValue();
					try {
						entitiyConstructor = Class.forName(classname)
								.getConstructor(
										new Class[] { int.class, int.class });
						entity = (Entity) entitiyConstructor
								.newInstance(new Object[] { xxx, zzz });
					} catch (Exception e2) {
						System.out.println("?");
					}
				}

				if (entity != null)
					Scene.getInstance().getLevel().addEntity(entity);
					InGameLogger.getInstance().addMessage(entity.name + " spawned!");
			}
			
			classname = null;
			
		}
	}

}
