#version 300 es
layout (location = 0) in vec4 vPosition;//顶点位置
layout (location = 1) in vec4 vTexCoord;//纹理坐标
layout (location = 2) uniform mat4 uMatrix;
layout (location = 3) uniform mat4 uSTMatrix;

out vec2 texCoo2Frag;

void main() {
    texCoo2Frag = (uSTMatrix * vTexCoord).xy;
    gl_Position = uMatrix*vPosition;
}
