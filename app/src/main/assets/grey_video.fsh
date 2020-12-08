#version 300 es
#extension GL_OES_EGL_image_external_essl3 : require
precision highp float;

in vec2 texCoo2Frag;
out vec4 outColor;

layout (location = 4) uniform samplerExternalOES sTexture;

void main() {
    vec4 color = texture(sTexture, texCoo2Frag);
    outColor = vec4(color.g, color.g, color.g, 1.0);
}