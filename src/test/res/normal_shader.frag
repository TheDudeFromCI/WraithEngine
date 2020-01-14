#version 330 core

in vec3 v_normal;

out vec4 color;

void main()
{
	color = vec4(v_normal * 0.5 + 0.5, 1.0);
}