package common.loader.loadtask;

import common.loader.GLContainerObjectLoadTask;
import context.GLContext;
import context.visuals.lwjgl.FrameBufferObject;

public class FrameBufferObjectLoadTask extends GLContainerObjectLoadTask<FrameBufferObject> {

	@Override
	protected FrameBufferObject loadGL(GLContext glContext) {
		FrameBufferObject fbo = new FrameBufferObject();
		fbo.genID();
		return fbo;
	}

}
