#version 300 es
#extension GL_OES_EGL_image_external_essl3 : require
precision highp float;

in vec2 texCoo2Frag;
out vec4 outColor;

layout (location = 4) uniform samplerExternalOES sTexture;


layout (location = 5) uniform float uProgress;

const float PI = 3.14159265;

const float uD = 80.0;
const float uR = 1.0;


void main()
{
  float rate= 2264.0 / 1080.0;
  ivec2 ires = ivec2(128, 128);
  float res = float(ires.s);
  //周期
  float duration = 3.0;
  vec2 st = texCoo2Frag;
  float radius = res * uR;
  //进度
  float progress = mod(uProgress, duration) / duration; // 0~1
  vec2 xy = res * st;

  vec2 dxy = xy - vec2(res/2., res/2.);
  float r = length(dxy);

  //(1.0 - r/Radius);
  float beta = atan(dxy.y, dxy.x) + radians(uD) * 2.0 * (-(r/radius)*(r/radius) + 1.0);

  vec2 xy1 = xy;
  if(r<=radius)
  {
    xy1 = res/2. + r*vec2(cos(beta), sin(beta))*progress;

  }

  st = xy1/res;

  vec3 irgb = texture(sTexture, st).rgb;

  outColor = vec4( irgb, 1.0 );
}
