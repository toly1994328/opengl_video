#version 300 es
#extension GL_OES_EGL_image_external_essl3 : require
precision highp float;

in vec2 texCoo2Frag;
out vec4 outColor;

layout (location = 4) uniform samplerExternalOES sTexture;


layout (location = 5) uniform float uProgress;

void main() {

    //周期
    float duration = 0.7;
    //放大的最大比例
    float maxScale = 1.1;
    //颜色偏移值
    float offset = 0.02;

    //当前时间在整个周期中的进度,在0~1
    float progress = mod(uProgress, duration) / duration; // 0~1
    //具体的偏移量
    vec2 offsetCoords = vec2(offset, offset) * progress;
    //图层放大缩小的比例
    float scale = 1.0 + (maxScale - 1.0) * progress;

    //获取缩放之后实际纹理坐标
    vec2 ScaleTextureCoords = vec2(0.5, 0.5) + (texCoo2Frag - vec2(0.5, 0.5)) / scale;

    //设置缩放之后的纹理坐标和经过具体的颜色偏移坐标
    //三组分别代表RGB不同方向的纹理像素值
    vec4 maskR = texture(sTexture, ScaleTextureCoords + offsetCoords);
    vec4 maskB = texture(sTexture, ScaleTextureCoords - offsetCoords);
    vec4 mask = texture(sTexture, ScaleTextureCoords);

    //根据不同的纹理坐标值得到经过颜色偏移之后的颜色
    outColor = vec4(maskR.r, mask.g, maskB.b, mask.a);

}
