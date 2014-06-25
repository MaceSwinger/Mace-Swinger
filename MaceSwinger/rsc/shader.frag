uniform vec2 lightLocation;
uniform vec3 lightColor;
uniform float lightRadius;

void main() {
	float distance = length(lightLocation - gl_FragCoord.xy);
	float attenuation = lightRadius / (distance/2.00);
	vec4 color = vec4(attenuation, attenuation, attenuation, attenuation*attenuation*attenuation) * vec4(lightColor, 1);

	gl_FragColor = color;
}