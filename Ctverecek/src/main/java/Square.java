import org.joml.Matrix4f;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL33;
import org.lwjgl.system.MemoryUtil;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
public class Square {
    public static int uniformMatrixLocation;

    private final int[] indices = {
            0, 1, 3,
            1, 2, 3
    };
    public static int textureId;
    public int squareVaoId;
    public int squareVboId;
    public Matrix4f matrix;
    public FloatBuffer matrixFloatBuffer;
    public float[] gfindicies;
    private float[] vertices;
    private int squareEboId;
    private int squareColorId;
    private float x;
    private float y;
    private float size;
    public float fr = 0;
    private int run;
    public FloatBuffer tb = BufferUtils.createFloatBuffer(8);
    public Square(float x, float y, float size) {
        this.x = x;
        this.y = y;
        this.size = size;
        matrix = new Matrix4f().identity();
        matrixFloatBuffer = BufferUtils.createFloatBuffer(16);
        float[] colors = {
                1f, 1f, 1f, 1f,
                1f, 1f, 1f, 1f,
                1f, 1f, 1f, 1f,
                1f, 1f, 1f, 1f,
        };
        gfindicies = new float[]{
                1.0f, 0.0f,
                1.0f, 1.0f,
                0.0f, 1.0f,
                0.0f, 0.0f
        };
        float[] vertices = {
                x + size, y, 0.0f,
                x + size, y - size, 0.0f,
                x, y - size, 0.0f,
                x, y, 0.0f,
        };
        this.vertices = vertices;
        squareVaoId = GL33.glGenVertexArrays();
        squareEboId = GL33.glGenBuffers();
        squareVboId = GL33.glGenBuffers();
        squareColorId = GL33.glGenBuffers();
        textureId = GL33.glGenBuffers();

        cbrLoad();
        uniformMatrixLocation = GL33.glGetUniformLocation(Shaders.shaderProgramId, "matrix");

        GL33.glBindVertexArray(squareVaoId);
        GL33.glBindBuffer(GL33.GL_ELEMENT_ARRAY_BUFFER, squareEboId);
        IntBuffer ib = BufferUtils.createIntBuffer(indices.length)
                .put(indices)
                .flip();

        GL33.glBufferData(GL33.GL_ELEMENT_ARRAY_BUFFER, ib, GL33.GL_STATIC_DRAW);
        GL33.glBindBuffer(GL33.GL_ARRAY_BUFFER, squareColorId);
        FloatBuffer cfb = BufferUtils.createFloatBuffer(colors.length).put(colors).flip();
        GL33.glBufferData(GL33.GL_ARRAY_BUFFER, cfb, GL33.GL_STATIC_DRAW);
        GL33.glVertexAttribPointer(1, 4, GL33.GL_FLOAT, false, 0, 0);
        GL33.glEnableVertexAttribArray(1);
        MemoryUtil.memFree(cfb);
        GL33.glBindBuffer(GL33.GL_ARRAY_BUFFER, squareVboId);
        FloatBuffer fb = BufferUtils.createFloatBuffer(vertices.length)
                .put(vertices)
                .flip();

        GL33.glBufferData(GL33.GL_ARRAY_BUFFER, fb, GL33.GL_STATIC_DRAW);
        GL33.glVertexAttribPointer(0, 3, GL33.GL_FLOAT, false, 0, 0);
        GL33.glEnableVertexAttribArray(0);
        GL33.glUseProgram(Shaders.shaderProgramId);
        matrix.get(matrixFloatBuffer);
        GL33.glUniformMatrix4fv(uniformMatrixLocation, false, matrixFloatBuffer);
        MemoryUtil.memFree(fb);
        MemoryUtil.memFree(ib);
        GL33.glBindBuffer(GL33.GL_ARRAY_BUFFER, textureId);
        tb.put(gfindicies).flip();
        GL33.glBufferData(GL33.GL_ARRAY_BUFFER, tb, GL33.GL_STATIC_DRAW);
        GL33.glVertexAttribPointer(2, 2, GL33.GL_FLOAT, false, 0, 0);
        GL33.glEnableVertexAttribArray(2);
    }

    public void render() {
        matrix.get(matrixFloatBuffer);
        GL33.glUniformMatrix4fv(uniformMatrixLocation, false, matrixFloatBuffer);
        GL33.glUseProgram(Shaders.shaderProgramId);
        GL33.glBindVertexArray(squareVaoId);
        GL33.glDrawElements(GL33.GL_TRIANGLES, indices.length, GL33.GL_UNSIGNED_INT, 0);
    }

    public void update(long window) {
        run = (int) fr % 6;
        if (run == 0) {
            gfindicies = new float[]{
                    0.16f, 0.0f,
                    0.16f, 1f,
                    0.0f, 1f,
                    0.0f, 0.0f,
            };
        } else if (run == 1) {
            gfindicies = new float[]{
                    0.32f, 0.0f,
                    0.32f, 1f,
                    0.16f, 1f,
                    0.16f, 0.0f,
            };
        } else if (run == 2) {
            gfindicies = new float[]{
                    0.48f, 0.0f,
                    0.48f, 1f,
                    0.32f, 1f,
                    0.32f, 0.0f,
            };
        } else if (run == 3) {
            gfindicies = new float[]{
                    0.64f, 0.0f,
                    0.64f, 1f,
                    0.48f, 1f,
                    0.48f, 0.0f,
            };
        } else if (run == 4) {
            gfindicies = new float[]{
                    0.80f, 0.0f,
                    0.80f, 1f,
                    0.64f, 1f,
                    0.64f, 0.0f,
            };
        } else if (run == 5) {
            gfindicies = new float[]{
                    0.96f, 0.0f,
                    0.96f, 1f,
                    0.8f, 1f,
                    0.8f, 0f,
            };
        }
        fr += 0.1;
        tb.clear().put(gfindicies).flip();
        GL33.glBindBuffer(GL33.GL_ARRAY_BUFFER, textureId);
        GL33.glBufferData(GL33.GL_ARRAY_BUFFER, tb, GL33.GL_STATIC_DRAW);
        GL33.glVertexAttribPointer(2, 2, GL33.GL_FLOAT, false, 0, 0);
        GL33.glEnableVertexAttribArray(2);
    }

    private void cbrLoad() {
        Loadimgs.lod();
        }

    public float getY() {
        return y;
    }

    public float getSize() {
        return size;
    }


}