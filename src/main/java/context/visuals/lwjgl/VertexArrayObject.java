package context.visuals.lwjgl;

import static org.lwjgl.opengl.GL11.GL_TRIANGLES;
import static org.lwjgl.opengl.GL11.GL_UNSIGNED_INT;
import static org.lwjgl.opengl.GL11.glDrawElements;
import static org.lwjgl.opengl.GL30.glBindVertexArray;
import static org.lwjgl.opengl.GL30.glDeleteVertexArrays;
import static org.lwjgl.opengl.GL30.glGenVertexArrays;
import static org.lwjgl.opengl.GL31.glDrawElementsInstanced;

import java.util.ArrayList;
import java.util.List;

import context.GLContext;
import context.ResourcePack;
import org.lwjgl.assimp.AIMesh;
import org.lwjgl.assimp.AIVector3D;

/**
 * An object that contains data about a group of vertices in OpenGL. Learn more
 * about them here: <a href=
 * "https://stackoverflow.com/questions/11821336/what-are-vertex-array-objects">
 * https://stackoverflow.com/questions/11821336/what-are-vertex-array-objects</a>
 *
 * @author Jay
 * @see VertexBufferObject
 * @see ElementBufferObject
 */
public class VertexArrayObject extends GLContainerObject {

	private int id;
	private List<VertexBufferObject> vbos = new ArrayList<>();
	private ElementBufferObject ebo;

	public static VertexArrayObject createFromMesh(AIMesh mesh, List<Material> materials) {
		// Create the VAO
		VertexArrayObject vao = new VertexArrayObject();
		vao.genID();

		// Read the VBO data from the mesh
		int numVertices = mesh.mNumVertices();
		float[] vertices = new float[numVertices * 3];
		float[] uvCoords = new float[numVertices * 2];
		float[] normals = new float[numVertices * 3];

		AIVector3D.Buffer mVertices = mesh.mVertices();
		AIVector3D.Buffer mTextureCoords = mesh.mTextureCoords(0);
		AIVector3D.Buffer mNormals = mesh.mNormals();
		for (int i = 0; i < numVertices; i++) {
			AIVector3D vertex = mVertices.get(i);
			vertices[i * 3] = vertex.x();
			vertices[i * 3 + 1] = vertex.y();
			vertices[i * 3 + 2] = vertex.z();
		}
		vao.attachVBO(new VertexBufferObject(vertices, 3));
		if (mTextureCoords != null) {
			for (int i = 0; i < numVertices; i++) {
				AIVector3D textureCoord = mTextureCoords.get(i);
				uvCoords[i * 2] = textureCoord.x();
				uvCoords[i * 2 + 1] = textureCoord.y();
			}
			// Attach the UV coordinates to the VAO
			vao.attachVBO(new VertexBufferObject(uvCoords, 2));
		}
		if (mNormals != null) {
			for (int i = 0; i < numVertices; i++) {
				AIVector3D normal = mNormals.get(i);
				normals[i * 3] = normal.x();
				normals[i * 3 + 1] = normal.y();
				normals[i * 3 + 2] = normal.z();
			}
			// Attach the normals to the VAO
			vao.attachVBO(new VertexBufferObject(normals, 3));
		}

		// Read the EBO data from the mesh
		int numFaces = mesh.mNumFaces();
		int[] indices = new int[numFaces * 3];
		for (int i = 0; i < numFaces; i++) {
			indices[i * 3] = mesh.mFaces().get(i).mIndices().get(0);
			indices[i * 3 + 1] = mesh.mFaces().get(i).mIndices().get(1);
			indices[i * 3 + 2] = mesh.mFaces().get(i).mIndices().get(2);
		}
		vao.setEbo(new ElementBufferObject(indices));
		return vao;
	}

	public void draw(GLContext glContext) {
		bind(glContext);
		ebo.bind(glContext);
		glDrawElements(GL_TRIANGLES, ebo.size(), GL_UNSIGNED_INT, 0);
	}

	public void drawInstanced(GLContext glContext, int times) {
		bind(glContext);
		ebo.bind(glContext);
		glDrawElementsInstanced(GL_TRIANGLES, ebo.size(), GL_UNSIGNED_INT, 0, times);
	}

	public void delete() {
		glDeleteVertexArrays(id);
	}

	/**
	 * Tells OpenGL to enable the attached VBOs. Should only be called once after
	 * attaching all necessary VBOs.
	 *
	 * @see VertexBufferObject
	 */
	public void enableVertexAttribPointers(GLContext glContext) {
		bind(glContext);
		for (int i = 0; i < vbos.size(); i++) {
			vbos.get(i).enableVertexAttribPointer(glContext, i);
		}
	}

	protected void bind(GLContext glContext) {
		verifyInitialized();
		if (glContext.vaoID == id) {
			return;
		}
		glBindVertexArray(id);
		glContext.vaoID = id;
	}

	public void attachVBO(VertexBufferObject vbo) {
		vbos.add(vbo);
	}

	public void genID() {
		this.id = glGenVertexArrays();
		initialize();
	}

	public void setEbo(ElementBufferObject ebo) {
		this.ebo = ebo;
	}

	@Override
	public void putInto(String name, ResourcePack resourcePack) {
		resourcePack.putVAO(name, this);
	}

}
