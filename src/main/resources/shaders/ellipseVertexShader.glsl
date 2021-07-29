#version 330 core
layout (location = 0) in vec3 vertexPos;
layout (location = 1) in vec2 aTexCoord;

out vec4 fragmentPos;
uniform mat4 transform;

void main() {
    gl_Position = transform * vec4(vertexPos, 1.0);
    fragmentPos = gl_Position;
}