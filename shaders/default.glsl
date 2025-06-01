#type vertex
#version 330 core

layout (location = 0) in vec3 pos;
layout (location = 1) in vec4 colour;

out vec4 fragColour;

void main()
{
    fragColour = colour;
    gl_Position = vec4(pos, 1.0);
}

#type fragment
#version 330 core

in vec4 fragColour;

out vec4 colour;

void main()
{
    colour = fragColour;
}