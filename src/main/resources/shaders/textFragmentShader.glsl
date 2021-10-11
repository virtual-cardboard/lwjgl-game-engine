#version 330 core
in vec2 texCoord;
out vec4 fragColor;

uniform sampler2D textureSampler;
uniform float texWidth, texHeight;
uniform float width, height;
uniform float x, y;
uniform vec4 colour;

void main() {
	vec2 newTexCoord = texCoord.xy;
	newTexCoord.x = (texCoord.x * width + x) / texWidth;
	newTexCoord.y = (texCoord.y * height + y) / texHeight;
    fragColor = texture(textureSampler, newTexCoord) * colour;
}