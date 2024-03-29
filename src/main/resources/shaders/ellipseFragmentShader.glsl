#version 330 core
in vec2 fragmentPos;
out vec4 fragColor;

// The following floats are in NORMALIZED DEVICE COORDINATES
uniform float x;
uniform float y;
uniform float width;
uniform float height;
uniform vec4 fill;

void main() {
	float xDiff = fragmentPos.x - x;
	float yDiff = fragmentPos.y - y;
	float halfWidth = width * 0.5f;
	float halfHeight = height * 0.5f;
	float dist = xDiff * xDiff / (halfWidth * halfWidth) + yDiff * yDiff / (halfHeight * halfHeight);
	float delta = fwidth(dist);
	fragColor = fill;
	fragColor.a *= 1 - smoothstep(1 - delta, 1, dist);
}