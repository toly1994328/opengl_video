#version 300 es
#extension GL_OES_EGL_image_external_essl3 : require
precision highp float;

in vec2 texCoo2Frag;
out vec4 outColor;

layout (location = 4) uniform samplerExternalOES sTexture;

void main(){
    float rate= 2264.0 / 1080.0;
    float cellX= 3.0;
    float cellY= 3.0;
    float rowCount=300.0;

    vec2 sizeFmt=vec2(rowCount, rowCount/rate);
    vec2 sizeMsk=vec2(cellX, cellY);
    vec2 posFmt = vec2(texCoo2Frag.x*sizeFmt.x, texCoo2Frag.y*sizeFmt.y);
    vec2 posMsk = vec2(floor(posFmt.x/sizeMsk.x)*sizeMsk.x, floor(posFmt.y/sizeMsk.y)*sizeMsk.y)+ 0.5*sizeMsk;

    float del = length(posMsk - posFmt);

    vec2 UVMosaic = vec2(posMsk.x/sizeFmt.x, posMsk.y/sizeFmt.y);

    vec4 result;
    if (del< cellX/2.0)
    result = texture(sTexture, UVMosaic);
    else
    result = vec4(1.0,1.0,1.0,0.0);
    outColor = result;
}

