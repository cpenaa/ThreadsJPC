# JetPackComposeTemplate
Jetpack Compose Template

# ComposeTemplate - Proyecto Base Jetpack Compose

Este repositorio contiene un proyecto base de Jetpack Compose para Android, pensado como plantilla para nuevos proyectos. Incluye la configuracion minima para iniciar rapidamente con Compose, Material3 y estructura moderna de Android Studio.

---

## Estructura del proyecto

- app/ - Codigo fuente de la aplicacion
    - MainActivity.kt - Actividad principal usando Compose
    - ui/ - Componentes y pantallas de Compose (puedes crear subcarpetas para organizar tus UI)
- build.gradle.kts - Configuracion de Gradle con Compose y dependencias actualizadas
- libs.versions.toml - Dependencias centralizadas
- README.md y LICENSE - Informacion y licencia del proyecto

---

## Como usar esta plantilla

1. Haz click en Use this template en GitHub para crear un nuevo repositorio basado en este proyecto
2. Clona el nuevo repositorio a tu maquina:

   git clone https://github.com/tu-usuario/NuevoProyecto.git

3. Abre el proyecto en Android Studio
4. Sincroniza Gradle (Sync Project) para asegurarte que todas las dependencias se descarguen
5. Empieza a desarrollar tu aplicacion usando Jetpack Compose

---

## Configuraciones a actualizar al crear un nuevo proyecto

- **Nombre del proyecto interno en Android Studio**  
  `settings.gradle(.kts)` → `rootProject.name = "NuevoNombreDelProyecto"`

- **Application ID**  
  `app/build.gradle(.kts)` → `applicationId = "com.example.nuevoprojecto"`

- **Paquete en el Manifest**
  `app/src/main/AndroidManifest.xml` → `<manifest ...\n package="com.example.nuevoprojecto">`

- **Tema de la aplicación**  
  `AndroidManifest.xml` → `android:theme="@style/Theme.NuevoNombreDelProyecto"`  
  `res/values/themes.xml` y `res/values-night/themes.xml` → `<style name="Theme.NuevoNombreDelProyecto" parent="Theme.Material3.DayNight.NoActionBar">`

- **Package declarations en los archivos Kotlin**  
  Aplicar un refactor/rename
  Todos los archivos `.kt` → `package com.example.nuevoprojecto`

- **Opcional: launcher icons y recursos**  
  Revisar `res/mipmap-*` y `res/drawable-*` si se personalizan iconos o imágenes

---

## Dependencias incluidas

- Kotlin 1.9.x
- Jetpack Compose (Material3)
- AndroidX Core, Lifecycle y Activity Compose
- Configuracion de Gradle con Compose BOM
- Dependencias para tests y debugging

---

## Licencia

Este proyecto esta bajo la licencia MIT License. Puedes reutilizarlo libremente en tus propios proyectos