#version 330 core

uniform sampler2D diffuse;

in vec3 pass_normal;
in vec2 pass_uv;

out vec4 color;

void main()
{
	color = vec4(texture(diffuse, pass_uv).rgb, 1.0);
}