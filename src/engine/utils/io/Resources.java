package engine.utils.io;

import java.util.HashMap;

import engine.utils.ThreedUtils;
import engine.utils.Lambda.Func1;
import engine.graphics.Image;
import engine.threed.Mesh;

//TODO: refactor this class to be more generic and less specific to meshes and images
/*
 * Example Functionality:
 * Resources r = new Resources();
 * r.load("mesh", "path/to/mesh.obj");
 * r.load("image", "path/to/image.png");
 * r.get("mesh");
 * r.get("image");
 * r.remove("mesh");
 * r.remove("image");
 * r.clear();
 * r.load("mesh", "path/to/mesh.obj");
 * r.set("mesh", meshObject);
 * r.addLoader("mesh", ThreedUtils::MeshFromObjFile);
 * r.addLoader("image", Image::fromFile);
 * r.forEach((name, resource) -> System.out.println(name + " " + resource));
 */

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
