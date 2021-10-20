package common.loader.loadtask;

import java.util.concurrent.CountDownLatch;

import common.loader.GLLoadTask;
import context.GLContext;
import context.visuals.lwjgl.ElementBufferObject;

public final class ElementBufferObjectLoadTask extends GLLoadTask<ElementBufferObject> {

	private GLContext context;
	private int[] indices;

	public ElementBufferObjectLoadTask(GLContext context, int[] indices) {
		this(new CountDownLatch(1), context, indices);
	}

	public ElementBufferObjectLoadTask(CountDownLatch countDownLatch, GLContext context, int[] indices) {
		super(countDownLatch);
		this.context = context;
		this.indices = indices;
	}

	@Override
	protected ElementBufferObject loadGL() {
		ElementBufferObject ebo = new ElementBufferObject(context, indices);
		ebo.generateId();
		ebo.loadData();
		return ebo;
	}

}
