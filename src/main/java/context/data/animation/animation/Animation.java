package context.data.animation.animation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Animation {

	private List<Keyframe> keyframes;

	public Animation() {
		keyframes = new ArrayList<>();
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
