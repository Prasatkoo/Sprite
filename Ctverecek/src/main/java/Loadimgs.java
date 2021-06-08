import org.lwjgl.opengl.GL33;
import org.lwjgl.stb.STBImage;
import org.lwjgl.system.MemoryStack;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;

public class Loadimgs {
    public static void lod() {
        MemoryStack stack = MemoryStack.stackPush();
        IntBuffer wide = stack.mallocInt(1);
        IntBuffer height = stack.mallocInt(1);
        IntBuffer comp = stack.mallocInt(1);
        ByteBuffer img = STBImage.stbi_load("Cyborg_run.png", wide, height, comp, 3);

        if (img != null) {
            img.flip();
            GL33.glBindTexture(GL33.GL_TEXTURE_2D, Square.textureId);
            GL33.glTexImage2D(GL33.GL_TEXTURE_2D, 0, GL33.GL_RGB, wide.get(), height.get(), 0, GL33.GL_RGB, GL33.GL_UNSIGNED_BYTE, img);
            GL33.glGenerateMipmap(GL33.GL_TEXTURE_2D);

        }
    }
}
