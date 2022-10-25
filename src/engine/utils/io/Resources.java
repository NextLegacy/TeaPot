package engine.utils.io;

import java.util.HashMap;

import engine.threed.Mesh;
import engine.utils.ThreedUtils;
import engine.utils.Lambda.Func1;
import engine.utils.graphics.Image;

public final class Resources 
{
    private Resources() { }

    public static final ResourceCollection<Mesh> meshes = new ResourceCollection<>(ThreedUtils::MeshFromObjFile);
    public static final ResourceCollection<Image> images = new ResourceCollection<>(Image::fromFile);

    public static class ResourceCollection<T>
    {
        private final HashMap<String, T> map;
        private final Func1<T, String> loader;

        ResourceCollection(Func1<T, String> loader) 
        {
            this.loader = loader;

            map = new HashMap<>(); 
        }

        public void load(String name, String path)
        {
            map.put(name, loader.get(path));
        }

        public T get(String name)
        {
            return map.get(name);
        }
    }
}
