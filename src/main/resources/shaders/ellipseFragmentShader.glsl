#version 330 core
in vec4 fragmentPos;
out vec4 fragmentColor;

uniform vec2 centerPos;
uniform vec2 dimensions;
uniform vec4 innerColour;
uniform vec4 outerColour;
uniform float outerWidth;

void main() {
	float xDiff = fragmentPos.x - centerPos.x;
	float yDiff = fragmentPos.y - centerPos.y;
	float halfWidth = dimensions.x * 0.5f;
	float halfHeight = dimensions.y * 0.5f;
    if (xDiff * xDiff / ((halfWidth - outerWidth) * (halfWidth - outerWidth)) +
    	yDiff * yDiff / ((halfHeight - outerWidth) * (halfHeight - outerWidth)) <= 1) {
    	fragmentColor = innerColour;
    } else if (xDiff * xDiff / (halfWidth * halfWidth) +
    	yDiff * yDiff / (halfHeight * halfHeight) <= 1) {
    	fragmentColor = outerColour;
    } else {
	    fragmentColor = vec4(0.1f, 0.5f, 0.7f, 0.1f);
    }
}