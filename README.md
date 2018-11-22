# OpenGL_3D_Game
Using LWJGL to create a 3D world

NOTE: WHEN YOU CLONE THIS PROJECT YOU WILL ABSOUTELY HAVE TO CHANGE A FEW LINES OF CODE TO GET IT TO RUN!!!
You will have to just press Run/Play button and you're first error will be in MainGameLoop when it tries to load textures. The reason
for the error is everyones computer will have a different path to the resource folder. I have them set up as global variables in the shaders
called " VERTEX_FILE = VERTEX_FILE = "\\Users\\colto\\Desktop\\OpenGlNew\\OpenGL_3D_Game\\OpenGL_3D_Game\\src\\shaders\\terrainVertexShader.txt";
And you will have to change the path to match your computer. There is like 6 lines kinda like this you gotta change and it works fine.
Another one for example: texture = TextureLoader.getTexture("PNG",
                    new FileInputStream("\\Users\\colto\\Desktop\\OpenGlNew\\OpenGL_3D_Game\\OpenGL_3D_Game\\res\\" + fileName + ".png"));
Just keep pressing play and find them all and fix them and you're good to go :)
