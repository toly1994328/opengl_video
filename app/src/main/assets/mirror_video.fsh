#version 300 es
#extension GL_OES_EGL_image_external_essl3 : require
precision highp float;

in vec2 texCoo2Frag;
out vec4 outColor;

layout (location = 4) uniform samplerExternalOES sTexture;

void main() {
    vec2 pos = texCoo2Frag;
    if (pos.x > 0.5) {
        pos.x = 1.0 - pos.x;
    }
    outColor = texture(sTexture, pos);
}