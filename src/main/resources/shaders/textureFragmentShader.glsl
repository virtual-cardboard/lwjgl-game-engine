#version 330 core
out vec4 fragColor;

in vec2 texCoord;

uniform sampler2D textureSampler;
uniform vec4 diffuse = vec4(1, 1, 1, 1);

void main()
{
    fragColor = texture(textureSampler, texCoord) * diffuse;
    if (fragColor.a == 0) {
    	discard;
    }
}