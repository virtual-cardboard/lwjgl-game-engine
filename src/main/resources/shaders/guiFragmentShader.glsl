#version 330 core

//in vec2 fragmentTextureCoordinates;

layout (location = 0) out vec4 fragmentColour;

//uniform sampler2D textureSampler;
uniform vec4 fill;

void main() {
	
	//fragmentColour = texture(textureSampler, fragmentTextureCoordinates);
	fragmentColour = fill;
    
}