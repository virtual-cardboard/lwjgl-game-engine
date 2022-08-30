#version 330 core

in vec3 fragmentPosition;
in vec2 fragmentTextureCoordinates;
in vec3 fragmentNormal;

layout (location = 0) out vec4 fragmentColour;

struct DirectionalLight {
    vec3 direction;
    vec3 ambient;
    vec3 diffuse;
    vec3 specular;
    float strength;
};

struct PointLight {
    vec3 position;
    vec3 constants;
    vec3 ambient;
    vec3 diffuse;
    vec3 specular;
    float strength;
};

struct SpotLight {
    vec3 position;
    vec3 direction;
    float angle;
    vec3 ambient;
    vec3 diffuse;
    vec3 specular;
    float strength;
};

struct Material {
    sampler2D diffuse;
    sampler2D specular;
};

const int MAX_LIGHTS = 3;

uniform Material material;
uniform DirectionalLight directionalLights[MAX_LIGHTS];
uniform PointLight pointLights[MAX_LIGHTS];
uniform SpotLight spotLights[MAX_LIGHTS];
uniform mat4 viewMatrix;

vec3 calculateDirectionalLight(DirectionalLight light, vec3 textureColour, vec3 specularMaterial, vec3 normal);
vec3 calculatePointLight(PointLight light, vec3 textureColour, vec3 specularMaterial, vec3 normal);
vec3 calculateSpotLight(SpotLight light, vec3 normal, vec3 viewDirection);

void main() {

    vec3 normal = normalize(fragmentNormal);
    vec3 textureColour = vec3(texture(material.diffuse, fragmentTextureCoordinates));
    vec3 specularMaterial = vec3(texture(material.specular, fragmentTextureCoordinates));
    vec3 lightingColour = vec3(0.0);
    for (int i = 0; i < MAX_LIGHTS; i++) {
        lightingColour += calculateDirectionalLight(directionalLights[i], textureColour, specularMaterial, normal);
        lightingColour += calculatePointLight(pointLights[i], textureColour, specularMaterial, normal);
    }
    fragmentColour = vec4(lightingColour, 1.0);

}

vec3 calculateDirectionalLight(DirectionalLight light, vec3 textureColour, vec3 specularMaterial, vec3 normal) {
    vec3 toLightVector = normalize(vec3(viewMatrix * vec4(-light.direction, 0.0)));
    float diffuseFactor = max(dot(normal, toLightVector), 0.0);
    vec3 reflectedLightVector = reflect(-toLightVector, normal);
    float specularFactor = pow(max(dot(normalize(-fragmentPosition), reflectedLightVector), 0.0), 16);
    vec3 ambient  = light.ambient * textureColour;
    vec3 diffuse  = light.diffuse * diffuseFactor * textureColour;
    vec3 specular = light.specular * specularFactor * specularMaterial;
    return light.strength * (ambient + diffuse + specular);
}

vec3 calculatePointLight(PointLight light, vec3 textureColour, vec3 specularMaterial, vec3 normal) {
    vec3 lightPosition = vec3(viewMatrix * vec4(light.position, 1.0));
    vec3 toLightVector = lightPosition - fragmentPosition;
    vec3 toLightDirection = normalize(toLightVector);
    float distance = length(toLightVector);
    float diffuseFactor = max(dot(normal, toLightDirection), 0.0);
    vec3 reflectedLightVector = reflect(-toLightDirection, normal);
    float specularFactor = pow(max(dot(normalize(-fragmentPosition), reflectedLightVector), 0.0), 16);
    float attenuation = 1.0 / (light.constants.z + light.constants.y * distance + light.constants.x * (distance * distance));
    vec3 ambient  = light.ambient * textureColour;
    vec3 diffuse  = light.diffuse * diffuseFactor * textureColour;
    vec3 specular = light.specular * specularFactor * specularMaterial;
    return attenuation * light.strength * (ambient + diffuse + specular);
}
