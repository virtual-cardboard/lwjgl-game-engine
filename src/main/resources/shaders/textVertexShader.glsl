#version 330 core
layout (location = 0) in vec3 vertexPosition;
layout (location = 1) in vec2 textureCoordinate;

out vec2 texCoord;

uniform mat4 matrix4f;

void main() {
    gl_Position = matrix4f * vec4(vertexPosition, 1.0);
    texCoord = textureCoordinate;
}