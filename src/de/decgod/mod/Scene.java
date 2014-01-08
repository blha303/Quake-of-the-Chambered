package de.decgod.mod;

import java.util.HashMap;
import java.util.Vector;

import com.mojang.escape.Game;
import com.mojang.escape.entities.BatBossEntity;
import com.mojang.escape.entities.BatEntity;
import com.mojang.escape.entities.BossOgre;
import com.mojang.escape.entities.BoulderEntity;
import com.mojang.escape.entities.EyeBossEntity;
import com.mojang.escape.entities.EyeEntity;
import com.mojang.escape.entities.GhostBossEntity;
import com.mojang.escape.entities.GhostEntity;
import com.mojang.escape.entities.Medikit;
import com.mojang.escape.entities.OgreEntity;
import com.mojang.escape.entities.Player;
import com.mojang.escape.level.Level;

public class Scene {

	private Level level;
	private Game game;
	private Player player;

	private HashMap<String, String> entityMap;

	private Scene() {
		loadEntities();
	}

	public static void registerComp(Level level, Game game, Player player) {

		if (instance == null) {
			instance = new Scene();
		}

		instance.level = level;
		instance.game = game;
		instance.player = player;

	}

	void loadEntities() {
		if (entityMap == null) {

			entityMap = new HashMap<String, String>();

			/* Enemys */
			entityMap.put("Bat", BatEntity.class.getName());
			entityMap.put("BatBoss", BatBossEntity.class.getName());
			entityMap.put("Ogre", OgreEntity.class.getName());
			entityMap.put("OgreBoss", BossOgre.class.getName());
			entityMap.put("Eye", EyeEntity.class.getName());
			entityMap.put("EyeBoss", EyeBossEntity.class.getName());
			entityMap.put("Ghost", GhostEntity.class.getName());
			entityMap.put("GhostBoss", GhostBossEntity.class.getName());
			entityMap.put("Medikit", Medikit.class.getName());

			/* Items */
			entityMap.put("Boulder", BoulderEntity.class.getName());
		}
	}

	public HashMap<String, String> getEntities() {
		return entityMap;
	}

	public static Scene getInstance() {
		return instance;
	}

	private static Scene instance = null;

	public Level getLevel() {
		return level;
	}

	public Game getGame() {
		return game;
	}

	public Player getPlayer() {
		return player;
	}
	

}