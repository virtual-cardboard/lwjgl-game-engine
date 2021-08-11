#version 330 core
layout(origin_upper_left, pixel_center_integer) in vec4 gl_FragCoord;
out vec4 fragmentColor;

// The following floats are in PIXEL COORDINATES
uniform float x1;
uniform float y1;
uniform float x2;
uniform float y2;
uniform float halfWidth;
uniform vec4 colour;

float lengthSquared(float x1, float y1, float x2, float y2) {
	return (x2 - x1) * (x2 - x1) + (y2 - y1) * (y2 - y1);
}

float getDist(vec2 p, float x1, float y1, float x2, float y2) {
	// Return minimum distance between line segment [p1(x1, y1) to p2(x2, y2)] and point p
	float lenSquared = lengthSquared(x1, y1, x2, y2);
	if (lenSquared == 0.0) {
		return sqrt(lengthSquared(p.x, p.y, x1, y1));   // p1 == p2 case
	}
	// Consider the line extending the segment, parameterized as p1 + t (p2 - p1).
	// We find projection of point p onto the line. 
	// It falls where t = [(p-p1) . (p2-p1)] / |p2-p1|^2
	// We clamp t from [0,1] to handle points outside the segment vw.
	float dot = (p.x - x1) * (x2 - x1) + (p.y - y1) * (y2 - y1);
	float t = max(0, min(1, dot / lenSquared));
	float projectionX = x1 + t * (x2 - x1);
	float projectionY = y1 + t * (y2 - y1); // Projection falls on the segment
	return sqrt(lengthSquared(p.x, p.y, projectionX, projectionY));
}

void main() {
	float dist = getDist(gl_FragCoord.xy, x1, y1, x2, y2);
	fragmentColor = colour;
	float delta = fwidth(dist);
	fragmentColor.a *= 1 - smoothstep(halfWidth - 2 * delta, halfWidth, dist);
}
