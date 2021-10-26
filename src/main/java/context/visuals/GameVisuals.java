package context.visuals;

import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glClearColor;

import common.loader.GameLoader;
import context.ContextPart;
import context.data.GameData;
import context.visuals.gui.RootGui;
import context.visuals.renderer.GameRenderer;

/**
 * A bundle part that displays visuals based on data from {@link GameData}.
 * 
 * @author Jay
 *
 */
public abstract class GameVisuals extends ContextPart {

	/**
	 * The {@link RootGui} to which all GUIs will be children of.
	 */
	private RootGui rootGui = new RootGui(0, 0);
	private GameLoader loader;

	public RootGui rootGui() {
		return rootGui;
	}

	/**
	 * Uses {@link GameRenderer}s to render the game. This is automatically called
	 * every frame.
	 */
	public abstract void render();

	protected final void background(float r, float g, float b, float a) {
		glClearColor(r, g, b, a);
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
	}

	public final void doInit(GameLoader loader) {
		this.loader = loader;
		init();
	}
//
//	/**
//	 * Creates a {@link VertexArrayObject}. Should not be called outside of the
//	 * rendering thread.
//	 * 
//	 * @param eboBuilder  the {@link ElementBufferObjectBuilder} that will create
//	 *                    the VAO's EBO
//	 * @param vboBuilders the {@link VertexBufferObjectBuilder
//	 *                    VertexBufferObjectBuilders} that will create the VAO's
//	 *                    VBOs
//	 * @return the linked <code>VertexArrayObject</code>
//	 */
//	protected final VertexArrayObject createVertexArrayObject(ElementBufferObjectBuilder eboBuilder, VertexBufferObjectBuilder... vboBuilders) {
//		try {
//			ElementBufferObject ebo = glFactory.createUsingBuilder(eboBuilder).get();
//			VertexBufferObject[] vbos = new VertexBufferObject[vboBuilders.length];
//			for (int i = 0; i < vboBuilders.length; i++) {
//				vbos[i] = glFactory.createUsingBuilder(vboBuilders[i]).get();
//			}
//			return glFactory.createUsingBuilder(new VertexArrayObjectBuilder(context().glContext(), ebo, vbos)).get();
//		} catch (Exception e) {
//			throw new RuntimeException(e);
//		}
//	}

	protected final GameLoader loader() {
		return loader;
	}

}
