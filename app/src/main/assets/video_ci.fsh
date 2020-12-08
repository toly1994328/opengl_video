#version 300 es
#extension GL_OES_EGL_image_external_essl3 : require
precision highp float;

in vec2 texCoo2Frag;
out vec4 outColor;

layout (location = 4) uniform samplerExternalOES sTexture;

// 进度值
layout (location = 5) uniform float uProgress;

const float PI = 3.14159265;
const float uD = 80.0;
const float uR = 0.5;

//这个函数式c中获取随机数的
float rand(float n) {
  //返回fract(x)的x小数部分
  return fract(sin(n) * 43758.5453123);
}

void main (void) {
  //最大抖动
  float maxJitter = 0.2;
  float duration = 0.4;
  //红色颜色偏移量
  float colorROffset = 0.01;
  //蓝色颜色偏移量
  float colorBOffset = -0.025;

  //当前周期的时间
  float time = mod(uProgress, duration * 2.0);
  //当前振幅0.0 ~ 1.0
  float amplitude = max(sin(uProgress * (PI / duration)), 0.0);

  // 当前坐标的y值获取随机偏移值  -1~1
  float jitter = rand(texCoo2Frag.y) * 2.0 - 1.0;
  //判断当前的坐标是否需要偏移
  bool needOffset = abs(jitter) < maxJitter * amplitude;

  //获取纹理x值，根据是否大于某一个阀值来判断到底在x方向应该偏移多少
  float textureX = texCoo2Frag.x + (needOffset ? jitter : (jitter * amplitude * 0.006));
  //x轴方向进行撕裂之后的纹理坐标
  vec2 textureCoords = vec2(textureX, texCoo2Frag.y);

  //颜色偏移3组颜色
  vec4 mask = texture(sTexture, textureCoords);
  vec4 maskR = texture(sTexture, textureCoords + vec2(colorROffset * amplitude, 0.0));
  vec4 maskB = texture(sTexture, textureCoords + vec2(colorBOffset * amplitude, 0.0));

  //最终根据三组不同的纹理坐标值来获取最终的颜色
  outColor = vec4(maskR.r, mask.g, maskB.b, mask.a);
}