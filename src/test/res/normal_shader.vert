#version 330

uniform mat4 mvp;

layout(location = 0) in vec3 pos;
layout(location = 1) in vec3 normal;
layout(location = 2) in vec2 uv;

out vec3 normal;

void main()
{
	gl_Position = mvp * vec4(pos, 1.0);
	normal = normal;
}