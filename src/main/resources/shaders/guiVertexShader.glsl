#version 330 core

layout (location = 0) in vec2 vertexPosition;

out vec2 fragmentTextureCoordinates;

uniform mat4 modelMatrix;

void main(){

	fragmentTextureCoordinates = vec2(vertexPosition.x, - vertexPosition.y);
	gl_Position = modelMatrix * vec4(vertexPosition, 0.0, 1.0);

}