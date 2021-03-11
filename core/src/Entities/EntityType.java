package Entities;

import World.GameMap;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.reflect.ClassReflection;
import com.badlogic.gdx.utils.reflect.ReflectionException;

import java.util.HashMap;

@SuppressWarnings("rawtypes")
public enum EntityType {

    PLAYER("player", 14,Player.class, 32, 40);


    private String id;
    private Class loaderClass;
    private int width, height;
    private float weight;

    EntityType(String id, int width, Class loaderClass,  int height, float weight) {
        this.id = id;
        this.loaderClass = loaderClass;
        this.width = width;
        this.height = height;
        this.weight = weight;
    }

    public String getId() {
        return id;
    }
    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public float getWeight() {
        return weight;
    }

    public static Entity createEntityUsingSnapshot(EntitySnapshot entitySnapshot, GameMap map){
        EntityType type = entityTypes.get(entitySnapshot.type);
        try{
            @SuppressWarnings("unchecked")
            Entity entity = (Entity) ClassReflection.newInstance(type.loaderClass);
            entity.create(entitySnapshot, type, map);
            return entity;
        }catch (ReflectionException e){
            Gdx.app.error("Entity Loader", "Could not load entity of type "+ type.id);
            return null;
        }

    }

    private static HashMap<String, EntityType> entityTypes;

    static{
        entityTypes = new HashMap<String, EntityType>();
        for(EntityType type : EntityType.values()){
            entityTypes.put(type.id, type);
        }
    }
}
