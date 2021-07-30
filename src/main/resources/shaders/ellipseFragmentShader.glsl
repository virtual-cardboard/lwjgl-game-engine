#version 330 core
in vec4 fragmentPos;
out vec4 fragmentColor;

uniform vec2 centerPos;
uniform vec2 dimensions;
uniform vec4 innerColour;
uniform vec4 outerColour;
uniform float outerWidthX;
uniform float outerWidthY;

void main() {
	float xDiff = fragmentPos.x - centerPos.x;
	float yDiff = fragmentPos.y - centerPos.y;
	float halfWidth = dimensions.x * 0.5f;
	float halfHeight = dimensions.y * 0.5f;
    if (xDiff * xDiff / ((halfWidth - outerWidthX) * (halfWidth - outerWidthX)) +
    	yDiff * yDiff / ((halfHeight - outerWidthY) * (halfHeight - outerWidthY)) <= 1) {
    	fragmentColor = innerColour;
    } else if (xDiff * xDiff / (halfWidth * halfWidth) +
    	yDiff * yDiff / (halfHeight * halfHeight) <= 1) {
    	fragmentColor = outerColour;
    } else {
	    fragmentColor = vec4(0, 0, 0, 0);
    }
}