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
import terrains.Terrain;
import textures.ModelTexture;
import textures.TerrainTexture;
import textures.TerrainTexturePack;
import entities.Camera;
import entities.Entity;
import entities.Light;
import entities.Player;

public class MainGameLoop {

    public static void main(String[] args) {

        DisplayManager.createDisplay();
        Loader loader = new Loader();

        //***************TERRAIN TEXTURE************* test//

        TerrainTexture backgroundTexture = new TerrainTexture(loader.loadTexture("grassy"));
        TerrainTexture rTexture = new TerrainTexture(loader.loadTexture("dirt"));
        TerrainTexture gTexture = new TerrainTexture(loader.loadTexture("pinkFlowers"));
        TerrainTexture bTexture = new TerrainTexture(loader.loadTexture("path"));

        TerrainTexturePack texturePack = new TerrainTexturePack(backgroundTexture, rTexture, gTexture, bTexture);
        TerrainTexture blendMap = new TerrainTexture(loader.loadTexture("blendMap"));

        //***************TERRAIN TEXTURE*************//

        ModelData playerModel = OBJFileLoader.loadOBJ("person");
        ModelData treeModel = OBJFileLoader.loadOBJ("tree");
        ModelData lowPolyTreeModel = OBJFileLoader.loadOBJ("lowPolyTree");
        ModelData fernModel = OBJFileLoader.loadOBJ("fern");


        RawModel person = loader.loadToVAO(playerModel.getVertices(),
                playerModel.getTextureCoords(),
                playerModel.getNormals(),
                playerModel.getIndices());

        RawModel tree = loader.loadToVAO(treeModel.getVertices(),
                treeModel.getTextureCoords(),
                treeModel.getNormals(),
                treeModel.getIndices());

        RawModel lowPolyTree = loader.loadToVAO(lowPolyTreeModel.getVertices(),
                lowPolyTreeModel.getTextureCoords(),
                lowPolyTreeModel.getNormals(),
                lowPolyTreeModel.getIndices());

        RawModel fern = loader.loadToVAO(fernModel.getVertices(),
                fernModel.getTextureCoords(),
                fernModel.getNormals(),
                fernModel.getIndices());

        TexturedModel playerTexture = new TexturedModel(person, new ModelTexture(loader.loadTexture("playerTexture")));
        TexturedModel treeTexture = new TexturedModel(tree, new ModelTexture(loader.loadTexture("tree")));
        TexturedModel lowPolyTreeTexture = new TexturedModel(lowPolyTree, new ModelTexture(loader.loadTexture("lowPolyTree")));
        lowPolyTreeTexture.getTexture().setHasTransparency(true);
        lowPolyTreeTexture.getTexture().setUseFakeLighting(true);
        TexturedModel fernTexture = new TexturedModel(fern, new ModelTexture(loader.loadTexture("fern")));
        fernTexture.getTexture().setHasTransparency(true);

        Terrain terrain1 = new Terrain(0, -1, loader, texturePack, blendMap, "heightmap");
        Terrain terrain2 = new Terrain(-1, -1, loader, texturePack, blendMap, "heightmap");

        ArrayList<Terrain> terrains = new ArrayList<>();
        terrains.add(terrain1);
        terrains.add(terrain2);

        List<Entity> entities = new ArrayList<Entity>();
        Random random = new Random();
        for (Terrain terrain : terrains) {

            for (int i = 0; i < 500; i++) {
                if (i % 3 == 0) {
                    //float x = random.nextFloat() * 800 - 400;
                    //float z = random.nextFloat() * -600;
                    //float x = random.nextInt((int) terrain.getSize() ) + terrain.getX();
                    //float z = random.nextInt((int) terrain.getSize() ) + terrain.getZ();﻿
                    float x = random.nextInt((int) terrain.getSize() ) + terrain.getX();
                    float z = random.nextInt((int) terrain.getSize() ) + terrain.getZ();
                    float y = terrain.getHeightOfTerrain(x, z);
                    entities.add(new Entity(treeTexture, new Vector3f(x, y, z), 0, random.nextFloat() * 360, 0, 3.9f));
                }

                if (i % 5 == 0) {
                    //float x = random.nextFloat() * 800 - 400;
                    //float z = random.nextFloat() * -600;
                    //float x = random.nextInt((int) terrain.getSize() ) + terrain.getX();
                    //float z = random.nextInt((int) terrain.getSize() ) + terrain.getZ();﻿
                    float x = random.nextInt((int) terrain.getSize() ) + terrain.getX();
                    float z = random.nextInt((int) terrain.getSize() ) + terrain.getZ();
                    float y = terrain.getHeightOfTerrain(x, z);
                    entities.add(new Entity(lowPolyTreeTexture, new Vector3f(x, y, z), 0, random.nextFloat() * 360, 0, 1.5f));
                }

                if (i % 10 == 0) {
                    //float x = random.nextFloat() * 800 - 400;
                    //float z = random.nextFloat() * -600;
                    //float x = random.nextInt((int) terrain.getSize() ) + terrain.getX();
                    //float z = random.nextInt((int) terrain.getSize() ) + terrain.getZ();﻿
                    float x = random.nextInt((int) terrain.getSize() ) + terrain.getX();
                    float z = random.nextInt((int) terrain.getSize() ) + terrain.getZ();
                    float y = terrain.getHeightOfTerrain(x, z);
                    entities.add(new Entity(fernTexture, new Vector3f(x, y, z), 0, random.nextFloat() * 360, 0, 1.9f));
                }

            }
        }


        Light light = new Light(new Vector3f(20000, 20000, 2000), new Vector3f(1, 1, 1));

        MasterRenderer renderer = new MasterRenderer();

        Player player = new Player(playerTexture, new Vector3f(100, 0, -50), 0, 180, 0, 0.6f);
        Camera camera = new Camera(player);

        while (!Display.isCloseRequested()) {
                camera.move();
            for(Terrain terrain: terrains) {
                if (terrain.getX() <= player.getPosition().x) {
                    if (terrain.getX() + terrain.getSize() > player.getPosition().x) {
                        if (terrain.getZ() <= player.getPosition().z) {
                            if (terrain.getZ() + terrain.getSize() > player.getPosition().z) {

                                player.move(terrain);
                            }
                        }
                    }
                }
            }
                renderer.processEntity(player);
            for(Terrain terrainList: terrains) {
                renderer.processTerrain(terrainList);
                //renderer.processTerrain(terrain2);
                for (Entity entity : entities) {
                    renderer.processEntity(entity);
                }
            }

            renderer.render(light, camera);
            DisplayManager.updateDisplay();
        }

        renderer.cleanUp();
        loader.cleanUp();
        DisplayManager.closeDisplay();

    }

}