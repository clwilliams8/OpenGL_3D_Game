# OpenGL_3D_Game
READ ME:

After pulling this project down, you will have to set your Run Configurations for whatever IDE you are using to Application
or Java Application, set the main class to engineTester.MainGameLoop, set the working directory, in my case it is
C:\Users\colto\IdeaProjects\OpenGL_3D_Game. You also need to go to your Project Struture or Project settings and navigate to
Libraries and include everything in the lib and natives folder with in the project. Include everything in 
C:\Users\colto\IdeaProjects\OpenGL_3D_Game\OpenGL_3D_Game\lib\jars , and everything in
C:\Users\colto\IdeaProjects\OpenGL_3D_Game\OpenGL_3D_Game\lib\natives . 
...........................................................................

If you pull this project down and get errors for "FileNotFound Exception Thown" it is probably because it is trying to read the
textures in the resource folder or files in the source folder and can't find the folder.

For example, view the TerrainShader class in the Shaders Module and the first two lines you will see are:

private static final String VERTEX_FILE = "OpenGL_3D_Game/src/shaders/terrainVertexShader.txt";
private static final String FRAGMENT_FILE = "OpenGL_3D_Game/src/shaders/terrainFragmentShader.txt";

If you the FileNotFound error from this, you need to change it to: 

private static final String VERTEX_FILE = "src/shaders/terrainVertexShader.txt";
private static final String FRAGMENT_FILE = "src/shaders/terrainFragmentShader.txt";

You will have to change every instance like this. Instances are found in TerrainShader(line 13 and 14), 
StaticShader(line 13 and 14, and Loader(line 42).



If you have any problems running the project after that, please contact me, colton@coltonlwilliams.com
