package fr.delaria.core.utils;

import fr.delaria.core.Core;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class EssentialsHomeConvertor {

    private static final File essentialsFolder = new File(Core.get().getDataFolder().getParentFile(), "Essentials/userdata");
    private static final Yaml yaml = new Yaml();

    private EssentialsHomeConvertor() {}

    public static Map<String, Location> convertHomes(Player player) throws IOException {
        File file = new File(essentialsFolder, player.getUniqueId() + ".yml");
        if(!file.exists()) return new HashMap<>();
        Path path = file.toPath();
        Map<String, Location> map = new HashMap<>();
        Map<String, Object> yml = yaml.load(Files.readString(path));
        Map<String, Object> homes = (Map<String, Object>) yml.get("homes");
        for (Map.Entry<String, Object> entry : homes.entrySet()) {
            String name = entry.getKey();
            Map<String, Object> home = (Map<String, Object>) entry.getValue();
            Location loc = new Location(Bukkit.getWorld((String) home.get("world-name")), ((Number) home.get("x")).doubleValue(), ((Number) home.get("y")).doubleValue(), ((Number) home.get("z")).doubleValue(), ((Number) home.get("yaw")).floatValue(), ((Number) home.get("pitch")).floatValue());
            map.put(name, loc);
        }
        return map;
    }
}
