package common.loader.loadtask;

import java.util.concurrent.CountDownLatch;

import common.loader.GLLoadTask;
import context.GLContext;
import context.visuals.lwjgl.ElementBufferObject;

public final class ElementBufferObjectLoadTask extends GLLoadTask<ElementBufferObject> {

	private int[] indices;

	public ElementBufferObjectLoadTask(int[] indices) {
		this(new CountDownLatch(1), indices);
	}

	public ElementBufferObjectLoadTask(CountDownLatch countDownLatch, int[] indices) {
		super(countDownLatch);
		this.indices = indices;
	}

	@Override
	protected ElementBufferObject loadGL(GLContext glContext) {
		ElementBufferObject ebo = new ElementBufferObject(indices);
		ebo.generateId();
		ebo.loadData(glContext);
		return ebo;
	}

}
