#version 330 core
layout (location = 0) in vec3 vertexPos;

out vec4 fragmentPos;
uniform mat4 transform;

void main() {
    fragmentPos = transform * vec4(vertexPos, 1.0);
}