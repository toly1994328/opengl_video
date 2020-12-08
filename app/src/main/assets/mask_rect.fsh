#version 300 es
#extension GL_OES_EGL_image_external_essl3 : require
precision highp float;

in vec2 texCoo2Frag;
out vec4 outColor;

layout (location = 4) uniform samplerExternalOES sTexture;

void main(){
    float rate= 2264.0 / 1080.0;
    float cellX= 1.0;
    float cellY= 1.0;
    float count = 80.0;

    vec2 pos = texCoo2Frag;
    pos.x = pos.x*count;
    pos.y = pos.y*count/rate;

    pos = vec2(floor(pos.x/cellX)*cellX/count, floor(pos.y/cellY)*cellY/(count/rate))+ 0.5/count*vec2(cellX, cellY);
    outColor = texture(sTexture, pos);
}

