#version 300 es
layout (location = 0) in vec4 vPosition;//顶点位置
layout (location = 1) in vec4 vTexCoord;//纹理坐标
layout (location = 2) uniform mat4 uMatrix;
layout (location = 3) uniform mat4 uSTMatrix;
//当前的时间
layout (location = 5) uniform float uProgress;

out vec2 texCoo2Frag;
const float PI = 3.1415926;
void main() {
    
    //周期
    float duration = 0.6;
    //缩放的最大值
    float maxAmplitude = 0.3;

    //类似取余，表示当前周期中的时间值
    float time = mod(uProgress, duration);
    //根据周期中的位置，获取当前的放大值
    float amplitude = 1.0 + maxAmplitude * abs(sin(time * (PI / duration)));
    //当前顶点转化到屏幕坐标的位置
    gl_Position = uMatrix*vec4(vPosition.x * amplitude, vPosition.y * amplitude, vPosition.zw);
    texCoo2Frag = (uSTMatrix * vTexCoord).xy;
}
