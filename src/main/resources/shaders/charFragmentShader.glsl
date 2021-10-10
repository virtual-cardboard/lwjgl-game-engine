#version 330 core
layout (location = 1) in vec2 textureCoordinate;

out vec4 fragColor;

uniform sampler2D textureSampler;
uniform float texWidth, texHeight;
uniform float width, height;
uniform float x, y;

void main() {
	mat4 texCoordMatrix4f = mat4(1 / texWidth, 0, 0, x / texWidth,
								 0, 1 / texHeight, 0, y / texHeight,
								 0, 0, 1, 0,
								 0, 0, 0, 1);
    fragColor = texture(textureSampler, texCoordMatrix4f * textureCoordinate);
}