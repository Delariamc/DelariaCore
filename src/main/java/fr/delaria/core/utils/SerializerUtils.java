package fr.delaria.core.utils;

import com.google.gson.*;
import org.bukkit.Bukkit;
import org.bukkit.Location;

import java.lang.reflect.Type;

public class SerializerUtils {

    public static class LocationSerializer implements JsonSerializer<Location>, JsonDeserializer<Location> {

        @Override
        public JsonElement serialize(Location location, Type type, JsonSerializationContext ctx) {
            JsonObject object = new JsonObject();
            object.addProperty("world", location.getWorld().getName());
            object.addProperty("x", location.getX());
            object.addProperty("pitch", location.getPitch());
            object.addProperty("y", location.getY());
            object.addProperty("z", location.getZ());
            object.addProperty("yaw", location.getYaw());
            return object;
        }

        @Override
        public Location deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext ctx) throws JsonParseException {
            JsonObject object = jsonElement.getAsJsonObject();
            return new Location(
                    Bukkit.getWorld(object.get("world").getAsString()),
                    object.get("x").getAsDouble(),
                    object.get("y").getAsDouble(),
                    object.get("z").getAsDouble(),
                    object.get("yaw").getAsFloat(),
                    object.get("pitch").getAsFloat());
        }
    }

}
