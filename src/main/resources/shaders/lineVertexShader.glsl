#version 330 core
layout (location = 0) in vec3 vertexPos;
layout (location = 1) in vec2 aTexCoord;

uniform mat4 transform;

void main() {
    gl_Position = transform * vec4(vertexPos, 1.0);
}