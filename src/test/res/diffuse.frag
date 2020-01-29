#version 330 core

const vec3 lightDir = vec3(0.12039, 0.963, 0.24077);

uniform sampler2D diffuse;

in vec3 pass_normal;
in vec2 pass_uv;

out vec4 color;

void main()
{
    vec3 col = texture(diffuse, pass_uv).rgb;
    col *= dot(pass_normal, lightDir) * 0.15 + 0.85;
	color = vec4(col, 1.0);
}