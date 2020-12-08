#version 300 es
#extension GL_OES_EGL_image_external_essl3 : require
precision highp float;

in vec2 texCoo2Frag;
out vec4 outColor;

layout (location = 4) uniform samplerExternalOES sTexture;

void main() {
    vec3 color = texture(sTexture, texCoo2Frag).rgb;
    float threshold = 0.7;//阈值
    float mean = (color.r + color.g + color.b) / 3.0;
    color.r = color.g = mean >= threshold ? 1.0 : 0.0;
    outColor = vec4(color.rg, 1, 1);
}
