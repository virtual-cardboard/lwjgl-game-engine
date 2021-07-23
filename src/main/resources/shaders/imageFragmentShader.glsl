#version 330 core
out vec4 FragColor;

in vec2 TexCoord;

uniform sampler2D textureSampler;

void main()
{
    //FragColor = texture(textureSampler, TexCoord);
    FragColor = vec4(0.8, 0.2, 0.2, 1.0);
}