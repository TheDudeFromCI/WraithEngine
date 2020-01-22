#version 330

uniform mat4 mvp;

layout(location = 0) in vec3 pos;
layout(location = 1) in vec3 normal;
layout(location = 4) in vec2 uv;

out vec3 pass_normal;
out vec2 pass_uv;

void main()
{
	gl_Position = mvp * vec4(pos, 1.0);

	pass_normal = normal;
	pass_uv = uv;
}