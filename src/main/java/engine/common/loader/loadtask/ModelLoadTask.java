package engine.common.loader.loadtask;

import static org.lwjgl.assimp.Assimp.aiTextureType_DIFFUSE;

import java.io.IOException;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;

import context.GLContext;
import context.visuals.lwjgl.ElementBufferObject;
import context.visuals.lwjgl.Material;
import context.visuals.lwjgl.Mesh;
import context.visuals.lwjgl.Model;
import context.visuals.lwjgl.VertexArrayObject;
import context.visuals.lwjgl.VertexBufferObject;
import engine.common.loader.GLLoadTask;
import org.lwjgl.assimp.AIMaterial;
import org.lwjgl.assimp.AIMesh;
import org.lwjgl.assimp.AIScene;
import org.lwjgl.assimp.AIString;
import org.lwjgl.assimp.AIVector3D;
import org.lwjgl.assimp.Assimp;

public class ModelLoadTask implements GLLoadTask<Model> {

	private final String path;

	public ModelLoadTask(String path) {
		this.path = path;
	}

	@Override
	public Model loadGL(GLContext glContext) throws IOException {
		AIScene scene = Assimp.aiImportFile(path, Assimp.aiProcess_Triangulate | Assimp.aiProcess_FlipUVs);
		if (scene == null) {
			throw new IOException("Failed to load model " + path);
		}
		int meshCount = scene.mNumMeshes();
		if (meshCount == 0) {
			throw new IOException("Failed to load model " + path + ": 0 meshes found");
		}

		List<Material> materials = new ArrayList<>();

		for (int i = 0; i < scene.mNumMaterials(); i++) {
			AIMaterial material = AIMaterial.create(scene.mMaterials().get(i));
			AIString path = AIString.calloc();
			Assimp.aiGetMaterialTexture(material, aiTextureType_DIFFUSE, 0, path, (IntBuffer) null, null, null, null, null, null);
			String texturePath = path.dataString();
			// TODO: Load/get textures and create materials
			System.out.println("LOL imagine having textures in your 3D models: " + texturePath);
			path.free();
			material.free();
		}
		scene.close();

		// Create meshes
		List<Mesh> meshes = new ArrayList<>();

		for (int i = 0; i < scene.mNumMeshes(); i++) {
			try (AIMesh mesh = AIMesh.create(scene.mMeshes().get(i))) {
				meshes.add(createMesh(glContext, mesh, materials));
			}
		}

		return new Model(meshes, materials);
	}

	private Mesh createMesh(GLContext glContext, AIMesh mesh, List<Material> materials) {
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

		// Attach the vertices VBO
		VertexBufferObject verticesVBO = new VertexBufferObject(vertices, 3);
		verticesVBO.genID();
		verticesVBO.loadData(glContext);
		vao.attachVBO(verticesVBO);

		// mTextureCoords and mNormals might be null, so we need to check for that
		if (mTextureCoords != null) {
			for (int i = 0; i < numVertices; i++) {
				AIVector3D uvCoord = mTextureCoords.get(i);
				uvCoords[i * 2] = uvCoord.x();
				uvCoords[i * 2 + 1] = uvCoord.y();
			}
			// Attach the uv coordinates VBO
			VertexBufferObject uvCoordsVBO = new VertexBufferObject(uvCoords, 2);
			uvCoordsVBO.genID();
			uvCoordsVBO.loadData(glContext);
			vao.attachVBO(uvCoordsVBO);
		}
		if (mNormals != null) {
			for (int i = 0; i < numVertices; i++) {
				AIVector3D normal = mNormals.get(i);
				normals[i * 3] = normal.x();
				normals[i * 3 + 1] = normal.y();
				normals[i * 3 + 2] = normal.z();
			}
			// Attach the normals VBO
			VertexBufferObject normalsVBO = new VertexBufferObject(normals, 3);
			normalsVBO.genID();
			normalsVBO.loadData(glContext);
			vao.attachVBO(normalsVBO);
		}

		// Read the EBO data from the mesh
		int numFaces = mesh.mNumFaces();
		int[] indices = new int[numFaces * 3];
		for (int i = 0; i < numFaces; i++) {
			indices[i * 3] = mesh.mFaces().get(i).mIndices().get(0);
			indices[i * 3 + 1] = mesh.mFaces().get(i).mIndices().get(1);
			indices[i * 3 + 2] = mesh.mFaces().get(i).mIndices().get(2);
		}
		ElementBufferObject ebo = new ElementBufferObject(indices);
		ebo.genID();
		ebo.loadData(glContext);
		vao.setEbo(ebo);

		Material material = materials.get(mesh.mMaterialIndex());

		return new Mesh(vao, material);
	}

}
