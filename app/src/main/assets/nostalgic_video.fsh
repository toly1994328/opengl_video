#version 300 es
#extension GL_OES_EGL_image_external_essl3 : require
precision highp float;

in vec2 texCoo2Frag;
out vec4 outColor;

layout (location = 4) uniform samplerExternalOES sTexture;

void main() {
    vec4 color = texture(sTexture, texCoo2Frag);
    float r = color.r;
    float g = color.g;
    float b = color.b;

    r = 0.393* r + 0.769 * g + 0.189* b;
    g = 0.349 * r + 0.686 * g + 0.168 * b;
    b = 0.272 * r + 0.534 * g + 0.131 * b;
    outColor = vec4(r, g, b, 1.0);
}