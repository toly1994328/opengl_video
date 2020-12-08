#version 300 es
#extension GL_OES_EGL_image_external_essl3 : require
precision highp float;

in vec2 texCoo2Frag;
out vec4 outColor;

layout (location = 4) uniform samplerExternalOES sTexture;

void main(){
    float arg = 1.5;

    vec4 color= texture(sTexture, texCoo2Frag);

    float r = color.r;
    float g = color.g;
    float b = color.b;

    b = sqrt(b)*arg;
    if (b>1.0) b = 1.0;

    outColor = vec4(r, g, b, 1.0);
}
