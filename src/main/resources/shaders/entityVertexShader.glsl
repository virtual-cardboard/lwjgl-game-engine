#version 430 core

layout (location = 0) in vec3 vertexPosition;
layout (location = 1) in vec2 vertexTextureCoordinates;
layout (location = 2) in vec3 vertexNormal;

out vec3 fragmentPosition;
out vec2 fragmentTextureCoordinates;
out vec3 fragmentNormal;

uniform mat4 modelMatrix;
uniform mat4 viewMatrix;
uniform mat4 projectionMatrix;

void main() {

	mat4 modelViewMatrix = viewMatrix * modelMatrix;
	vec4 viewPosition = modelViewMatrix * vec4(vertexPosition, 1.0);
	 
	fragmentPosition = viewPosition.xyz;
	fragmentNormal = vec3(modelViewMatrix * vec4(vertexNormal, 0.0));
	fragmentTextureCoordinates = vertexTextureCoordinates;
	
	gl_Position = projectionMatrix * viewPosition;
	
}