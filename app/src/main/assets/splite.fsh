#version 300 es
#extension GL_OES_EGL_image_external_essl3 : require
precision highp float;

in vec2 texCoo2Frag;
out vec4 outColor;

layout (location = 4) uniform samplerExternalOES sTexture;

void main(){
    vec2 pos = texCoo2Frag.xy;
    vec4 result;

    if (pos.x <= 0.5 && pos.y<= 0.5){ //左上
        pos.x = pos.x * 2.0;
        pos.y = pos.y * 2.0;
        vec4 color = texture(sTexture, pos);
        result = vec4(color.g, color.g, color.g, 1.0);
    } else if (pos.x > 0.5 && pos.y< 0.5){ //右上
        pos.x = (pos.x - 0.5) * 2.0;
        pos.y = (pos.y) * 2.0;
        vec4 color= texture(sTexture, pos);
        float arg = 1.5;
        float r = color.r;
        float g = color.g;
        float b = color.b;
        b = sqrt(b)*arg;
        if (b>1.0) b = 1.0;
        result = vec4(r, g, b, 1.0);
    } else if (pos.y> 0.5 && pos.x < 0.5) { //左下
        pos.y = (pos.y - 0.5) * 2.0;
        pos.x = pos.x * 2.0;
        vec4 color= texture(sTexture, pos);
        float r = color.r;
        float g = color.g;
        float b = color.b;
        r = 0.393* r + 0.769 * g + 0.189* b;
        g = 0.349 * r + 0.686 * g + 0.168 * b;
        b = 0.272 * r + 0.534 * g + 0.131 * b;
        result = vec4(r, g, b, 1.0);
    } else if (pos.y> 0.5 && pos.x > 0.5){ //右下
        pos.y = (pos.y - 0.5) * 2.0;
        pos.x = (pos.x - 0.5) * 2.0;
        vec4 color= texture(sTexture, pos);
        float r = color.r;
        float g = color.g;
        float b = color.b;
        b = 0.393* r + 0.769 * g + 0.189* b;
        g = 0.349 * r + 0.686 * g + 0.168 * b;
        r = 0.272 * r + 0.534 * g + 0.131 * b;
        result = vec4(r, g, b, 1.0);
    }
    outColor = result;
}