package engineTester;
 
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
 
import models.RawModel;
import models.TexturedModel;
import objConverter.ModelData;
import objConverter.OBJFileLoader;

import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector3f;
 
import renderEngine.DisplayManager;
import renderEngine.Loader;
import renderEngine.MasterRenderer;
import renderEngine.OBJLoader;
import terrains.Terrain;
import textures.ModelTexture;
import entities.Camera;
import entities.Entity;
import entities.Light;
 
public class MainGameLoop {
 
    public static void main(String[] args) {
 
        DisplayManager.createDisplay();
        Loader loader = new Loader();
         
         
        ModelData treeModel = OBJFileLoader.loadOBJ("tree");
        ModelData grassModel = OBJFileLoader.loadOBJ("grassModel");
        ModelData fernModel = OBJFileLoader.loadOBJ("fern");

        RawModel tree = loader.loadToVAO(treeModel.getVertices(), 
        		treeModel.getTextureCoords(), 
        		treeModel.getNormals(), 
        		treeModel.getIndices());
        
        RawModel grass = loader.loadToVAO(grassModel.getVertices(), 
        		grassModel.getTextureCoords(), 
        		grassModel.getNormals(), 
        		grassModel.getIndices());
        
        RawModel fern = loader.loadToVAO(fernModel.getVertices(), 
        		fernModel.getTextureCoords(), 
        		fernModel.getNormals(), 
        		fernModel.getIndices());
         
        TexturedModel treeTexture = new TexturedModel(tree,new ModelTexture(loader.loadTexture("tree")));
        TexturedModel grassTexture = new TexturedModel(grass,new ModelTexture(loader.loadTexture("grassTexture")));
        grassTexture.getTexture().setHasTransparency(true);
        grassTexture.getTexture().setUseFakeLighting(true);
        TexturedModel fernTexture = new TexturedModel(fern,new ModelTexture(loader.loadTexture("fern")));
        fernTexture.getTexture().setHasTransparency(true);

        
         
        List<Entity> entities = new ArrayList<Entity>();
        Random random = new Random();
        for(int i=0;i<300;i++){
            entities.add(new Entity(treeTexture, new Vector3f(random.nextFloat()*800 - 400,0,random.nextFloat() * -600),0,0,0,3));
        }
        
        for(int i=0;i<100;i++){
            entities.add(new Entity(grassTexture, new Vector3f(random.nextFloat()*800 - 400,0,random.nextFloat() * -600),0,0,0,3));
        }
        
        for(int i=0;i<100;i++){
            entities.add(new Entity(fernTexture, new Vector3f(random.nextFloat()*800 - 400,0,random.nextFloat() * -600),0,0,0,3));
        }
        
        
        Light light = new Light(new Vector3f(20000,20000,2000),new Vector3f(1,1,1));
         
        Terrain terrain = new Terrain(0,-1,loader,new ModelTexture(loader.loadTexture("grass")));
        Terrain terrain2 = new Terrain(-1,-1,loader,new ModelTexture(loader.loadTexture("grass")));
         
        Camera camera = new Camera();   
        MasterRenderer renderer = new MasterRenderer();
         
        while(!Display.isCloseRequested()){
            camera.move();
             
            renderer.processTerrain(terrain);
            renderer.processTerrain(terrain2);
            for(Entity entity:entities){
                renderer.processEntity(entity);
            }
            renderer.render(light, camera);
            DisplayManager.updateDisplay();
        }
 
        renderer.cleanUp();
        loader.cleanUp();
        DisplayManager.closeDisplay();
 
    }
 
}