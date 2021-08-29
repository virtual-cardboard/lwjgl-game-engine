package context.data.animation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Animation {

	private List<Keyframe> keyframes;

	public Animation(Keyframe... keyframes) {
		this.keyframes = new ArrayList<>(Arrays.asList(keyframes));
	}

	public Animation(List<Keyframe> keyframes) {
		this.keyframes = keyframes;
	}

	public void addKeyframe(Keyframe keyframe) {
		int index = Collections.binarySearch(keyframes, keyframe);
		if (index >= 0) {
			throw new RuntimeException("Keyframe already exists at time " + keyframe.getTime());
		} else {
			keyframes.add(-index - 1, keyframe);
		}
	}

	public List<Keyframe> getKeyframes() {
		return keyframes;
	}

}
