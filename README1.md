# ISE5783_0879_6954
A computer graphics project that made in the second year in JCT.

This project is used to teach some fondamental and more advenced topic in Software Engineering.

This project is the Pratical course to the Intro to Software Engineering course
in this project we use java 17 to create and implement a raytracing engin
using some fundamental design pattern.

Authors
---

- [@Daniel](https://github.com/dattali18/)
- [@Itzick](https://github.com/itzickw/)

Examples
---

## renders of cornel-box 400x400

### base render

<img width="300" src="https://github.com/dattali18/ISE5783_0879_6954/blob/main/images/picture%20improvement/base%20photo.png">

### with [anti-aliasing](https://en.wikipedia.org/wiki/Anti-aliasing)

<img width="300" src="https://github.com/dattali18/ISE5783_0879_6954/blob/main/images/picture%20improvement/anti-aliasing.png">

### with [soft-shadow](https://en.wikipedia.org/wiki/Shadow_mapping)

<img width="300" src="https://github.com/dattali18/ISE5783_0879_6954/blob/main/images/picture%20improvement/soft-shasow.png">

### with [anti-aliasing](https://en.wikipedia.org/wiki/Anti-aliasing) & [soft-shadow](https://en.wikipedia.org/wiki/Shadow_mapping)

<img width="300" src="https://github.com/dattali18/ISE5783_0879_6954/blob/main/images/picture%20improvement/soft-shdow%20%26%20anti-aliasing.png">

### Glossy Sourface

<img width="300" src="https://github.com/dattali18/ISE5783_0879_6954/blob/main/images/cornel/glossy4.png">

### Blurry Glass

<img width="300" src="https://github.com/dattali18/ISE5783_0879_6954/blob/main/images/cornel/glass18.png">

Picture Improvement
---

- [Anti-Aliasing](https://en.wikipedia.org/wiki/Anti-aliasing) (with variable number of alising rays)
- [Soft-Shadow](https://en.wikipedia.org/wiki/Shadow_mapping) (with vaeriable number of shadow ray and light length/radius)
- [Depth-Of-Field](https://en.wikipedia.org/wiki/Depth_of_field) (with variable number of foacl rays and lens radius)
- [Glossy Sourfaces](https://en.wikipedia.org/wiki/Gloss_(optics)) (with variable randomness of rays)
- Diffused (Blurry) Glass (with variable randomness of rays)

Design Patterns
---


here some of the pattern you can find in the project:

- **Builder** pattern in the Scene class
- **Composit** pattern in the Geomtries class

and more!

3D Obejcts
---

here some of the 3D object suported in our project:

- Cylinder
- Tube
- Plane
- Polygon
- Triangle
- Sphere
- Cube (and all [Cuboid](https://en.wikipedia.org/wiki/Cuboid))

Lights
---

heres some fo the different light supported in our project:

- Ambient Light
- Directional Light
- Point Light
- Spot Light (with an option to set the beam angle)

Camera
---

our camera support:

- changing View Plane (width, height, distance)
- chaging the view angle
  - rolling 
  - yawning
  - pitching
- camera movment
  - Up and Down (according to the camera up vector)
  - Forward and Backward (according to the camera to vector)
  - Right and Left (according to the cemara right vector)
 

here is a link to a wikipedia article explaining the diffrent view angle **[here](https://en.wikipedia.org/wiki/Aircraft_principal_axes)**

