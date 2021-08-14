#version 330 core
layout (location = 0) in vec3 vertexPos;
layout (location = 1) in vec2 aTexCoord;

out vec2 fragmentPos;
uniform mat4 matrix4f;

void main() {
    gl_Position = matrix4f * vec4(vertexPos, 1.0);
    fragmentPos = gl_Position.xy;
}