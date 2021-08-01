#version 330 core
in vec4 fragmentPos;
out vec4 fragmentColor;

uniform float x;
uniform float y;
uniform float width;
uniform float height;
uniform vec4 colour;

void main() {
	float xDiff = fragmentPos.x - x;
	float yDiff = fragmentPos.y - y;
	float halfWidth = width * 0.5f;
	float halfHeight = height * 0.5f;
    if (xDiff * xDiff / (halfWidth * halfWidth) +
    	yDiff * yDiff / (halfHeight * halfHeight) <= 1) {
    	fragmentColor = colour;
    } else {
	    fragmentColor = vec4(0, 0, 0, 0);
    }
}