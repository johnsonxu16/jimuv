<div align="center">

# 🎬 JimuV - 积木视频

**创作视频就像拼接积木一样简单！**

*Creating videos is as easy as piecing together building blocks!*

[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)
[![Java](https://img.shields.io/badge/Java-1.8+-orange.svg)](https://www.oracle.com/java/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-2.7.0-brightgreen.svg)](https://spring.io/projects/spring-boot)
[![JavaCV](https://img.shields.io/badge/JavaCV-1.5.9-blue.svg)](https://github.com/bytedeco/javacv)
[![FFmpeg](https://img.shields.io/badge/FFmpeg-Powered-red.svg)](https://ffmpeg.org/)

[**🇨🇳 中文**](README.md) | **🇺🇸 English**

</div>

---

## 💡 Design Philosophy

> Sculptors can already "see" their work in their minds before they start carving;  
> Painters know how their canvas will look before they pick up their brush.  
> **Video creation should be the same.**

Have you ever experienced this:
- *"Play a video for the first 5 seconds, switch scenes at 6 seconds, change background music at 3 seconds..."*
- Everything is clear and precise, but you get stuck cutting and editing on the timeline...

### 🧩 JimuV Brings Video Creation Back to Essence

**Just one JSON configuration** to describe your creative ideas:
- What content to play at what time
- How to display visual effects
- What music and text to add

**Leave the rest to JimuV**: automatically splice visuals, sync audio, and adjust timing.
Like building blocks, **clean, accurate, and precise**.

### 🚀 More Than Just an Editing Tool

**JimuV = Creative Ideas Translator**

Create videos through writing, turning thoughts into productivity.

---

## 📖 Product Overview

JimuV is an intelligent video creation platform **based on REST API**. Through simple API calls, it intelligently assembles various materials like text, audio, images, and videos to automatically generate high-quality video content.

### ✨ Core Features

| Feature | Description |
|---------|-------------|
| 🚀 **One-Click Generation** | Complete complex video production with a single API call |
| 📝 **JSON Driven** | Render a complete video with one JSON configuration |
| 🎨 **Multi-Material Fusion** | Free combination of text, audio, image, and video elements |
| 🔧 **Ready to Use** | Rich built-in default rules, zero configuration quick start |
| ⚡ **High Performance** | Asynchronous processing architecture supporting high concurrency |
| 🎭 **Rich Effects** | Built-in various visual effects and animation systems |

---

## 🚀 Quick Start

### 📋 Requirements

| Component | Version |
|-----------|---------|
| **Java** | 1.8+ |
| **Maven** | 3.6+ |

> 💡 **Zero Dependency Deployment**: Project integrates JavaCV with built-in FFmpeg functionality, no additional components needed!

### 🛠️ Deployment Steps

```bash
# 1. Clone the project
git clone https://github.com/johnsonxu16/jimuv.git
cd jimuv

# 2. Build
mvn clean install

# 3. Start service
cd jimuv-api
mvn spring-boot:run
```

### 🌐 Access Services

| Service | URL |
|---------|-----|
| **API Documentation** | http://localhost:8080/doc.html |

<div align="center">

![JimuV API Documentation Interface](doc/image/img1.png)

</div>

---

## 📋 API Usage Guide

<div align="center">

### 💎 Core Concept
**One JSON = One Video**

</div>

### 🎬 Getting Started

**Zero Configuration**: Send an empty object to experience (built-in rich default processing logic)

```json
{}
```

### 🏗️ Architecture Design

JimuV uses a unified API interface `/video/create` to complete video creation. JSON configuration contains two core parts:

| Configuration | Function | Description |
|---------------|----------|-------------|
| **videoInit** | Global Settings | Video dimensions, duration, background, quality, etc. |
| **videoBuild** | Content Elements | Video clips, images, audio, text and their effects |

### 📝 Complete Configuration Example

```json
{
  "videoInit": {
    "width": 1920,
    "height": 1080,
    "duration": 10,
    "backgroundColor": "#000000",
    "frameRate": 25,
    "name": "My Creation"
  },
  "videoBuild": [
    {
      "type": "element",
      "elementUrl": "https://example.com/video.mp4",
      "effect": {
        "time": { "startTime": 0, "endTime": 5 },
        "overlay": { "location": 5 },
        "scale": { "scaleWidth": 960, "scaleHeight": 540 }
      }
    },
    {
      "type": "text",
      "textContent": "Welcome to JimuV",
      "effect": {
        "time": { "startTime": 1, "endTime": 6 },
        "overlay": { "location": 2 },
        "text": { "fontSize": 48, "fontColor": "#FFFFFF" }
      }
    }
  ]
}
```

---

## 🔧 Parameter Reference

### 🎯 videoInit (Global Configuration)

| Parameter | Type | Required | Default | Description |
|-----------|------|----------|---------|-------------|
| `width` | integer | ✅ | - | Video width (pixels) |
| `height` | integer | ✅ | - | Video height (pixels) |
| `duration` | number | ✅ | - | Total video duration (seconds) |
| `backgroundColor` | string | ❌ | "#000000" | Background color (hex) |
| `backgroundImage` | string | ❌ | - | Background image URL |
| `frameRate` | integer | ❌ | 25 | Video frame rate |
| `crf` | integer | ❌ | 23 | Video quality (0-51, lower = higher quality) |
| `name` | string | ❌ | - | Task name |
| `id` | string | ❌ | - | Task unique identifier |
| `debug` | boolean | ❌ | false | Debug mode |
| `thumbnailTime` | number | ❌ | 0 | Thumbnail capture time (seconds) |

### 🎭 videoBuild (Element Configuration)

Each video element contains the following basic attributes:

| Parameter | Type | Description | Applicable Types |
|-----------|------|-------------|------------------|
| `type` | string | Element type | `text` / `audio` / `element` |
| `elementUrl` | string | Media file URL | `element` (video/image) |
| `audioUrl` | string | Audio file URL | `audio` |
| `textContent` | string | Text content | `text` |
| `effect` | object | Effect configuration | All types |

---

## 🎨 Effects System

### Effect Scope

| Effect Category | Applicable Elements | Function Description |
|-----------------|-------------------|---------------------|
| ⏰ **Time Control** | `text` / `audio` / `element` | Control element playback time |
| 📍 **Position Control** | `text` / `element` | Control element position on screen |
| 🎭 **Visual Effects** | `element` | Scale, crop, rotate, blur and other visual processing |
| ✍️ **Text Styling** | `text` | Font, color, border, background and other text attributes |
| 🎬 **Animation Effects** | `text` / `element` | Entry and exit animations |
| 🔊 **Audio Control** | `audio` | Volume, fade in/out and other audio processing |

### ⏰ Time Control (Universal)

```json
"time": {
  "startTime": 0,    // Start time (seconds)
  "endTime": 5       // End time (seconds)
}
```

### 📍 Position Control (Visual Elements)

```json
"overlay": {
  "location": 5,     // Grid position: 1-9
  "x": 100,          // Custom X coordinate
  "y": 50            // Custom Y coordinate
}
```

**Grid Position Map**:
```
1 Top-Left    2 Top-Center    3 Top-Right
4 Middle-Left 5 Center        6 Middle-Right  
7 Bottom-Left 8 Bottom-Center 9 Bottom-Right
```

### 🎭 Visual Effects (Media Elements)

#### 📏 Size and Cropping

```json
"scale": {           // Scaling
  "scaleWidth": 640,
  "scaleHeight": 360
},
"crop": {            // Cropping
  "cropX": 0,         // Crop start X coordinate
  "cropY": 0,         // Crop start Y coordinate  
  "cropWidth": 1280,  // Crop width
  "cropHeight": 720   // Crop height
}
```

#### 🔄 Transform Effects

```json
"rotate": {          // Rotation
  "rotation": 45,     // Rotation angle
  "isKeep": true      // Whether to keep rotating
},
"flip": {            // Flip
  "horizontalFlip": true,  // Horizontal flip
  "verticalFlip": false    // Vertical flip
}
```

#### 🌟 Visual Enhancement

```json
"fade": {            // Fade in/out
  "fadeInTime": 1,
  "fadeOutTime": 1
},
"transparent": {     // Transparency
  "transparency": 0.5
},
"gblur": {           // Gaussian blur
  "sigma": 2
},
"radius": {          // Border radius
  "radian": 10
}
```

### ✍️ Text Styling (Text Elements)

```json
"text": {
  "fontSize": 48,              // Font size
  "fontColor": "#FFFFFF",      // Font color
  "fontColorAlpha": 1.0,       // Font transparency
  "fontFile": "",              // Custom font file URL
  
  // Border settings
  "borderColor": "#000000",    // Border color
  "borderColorAlpha": 1.0,     // Border transparency
  "borderWidth": "2",          // Border width
  
  // Background settings
  "box": 1,                    // Show background: 0=no/1=yes
  "boxColor": "#000000",       // Background color
  "boxColorAlpha": 0.8,        // Background transparency
  "boxBorderWidth": 10         // Background padding
}
```

### 🎬 Animation Effects (Visual Elements)

```json
"move": {
  "in": {                    // Entry animation
    "moveDirection": 1,      // Direction: 1=left/2=right/3=up/4=down
    "moveSpeed": 100,        // Speed: pixels/second
    "moveTime": 2            // Animation duration: seconds
  },
  "out": {                   // Exit animation
    "moveDirection": 2,
    "moveSpeed": 150,
    "moveTime": 1.5
  }
}
```

### 🔊 Audio Control (Audio Elements)

```json
"audio": {
  "volume": 0.8,           // Volume: 0-1
  "volumeFadeIn": 2,       // Fade in time: seconds
  "volumeFadeOut": 2       // Fade out time: seconds
}
```

---

## 📖 Practical Examples

### 🎯 Example 1: Simple Text Video

```json
{
  "videoInit": { 
    "width": 1280, 
    "height": 720, 
    "duration": 5 
  },
  "videoBuild": [
    {
      "type": "text",
      "textContent": "Hello JimuV!",
      "effect": {
        "text": { 
          "fontSize": 64, 
          "fontColor": "#FF0000" 
        },
        "overlay": { "location": 5 },
        "time": { "startTime": 0, "endTime": 5 }
      }
    }
  ]
}
```

### 🎬 Example 2: Complex Animation Effects

```json
{
  "videoInit": { 
    "width": 1920, 
    "height": 1080, 
    "duration": 8 
  },
  "videoBuild": [
    {
      "type": "element",
      "elementUrl": "https://example.com/image.jpg",
      "effect": {
        "scale": { "scaleWidth": 1280, "scaleHeight": 720 },
        "overlay": { "location": 5 },
        "move": { 
          "in": { 
            "moveDirection": 1, 
            "moveSpeed": 200, 
            "moveTime": 2 
          } 
        },
        "time": { "startTime": 0, "endTime": 6 }
      }
    },
    {
      "type": "text", 
      "textContent": "Hello JimuV!",
      "effect": {
        "text": { 
          "fontSize": 48, 
          "fontColor": "#FFFFFF", 
          "box": 1, 
          "boxColor": "#000000" 
        },
        "overlay": { "location": 5 },
        "move": { 
          "in": { 
            "moveDirection": 3, 
            "moveSpeed": 100, 
            "moveTime": 1 
          } 
        },
        "time": { "startTime": 2, "endTime": 8 }
      }
    }
  ]
}
```

### 📡 API Call

```bash
curl -X POST "http://localhost:8080/video/create" \
     -H "Content-Type: application/json" \
     -d '{
       "videoInit": {
         "width": 1280,
         "height": 720,
         "duration": 10
       }
     }'
```

---

## 🙏 Acknowledgments

Thanks to the following excellent open source projects for providing strong support to JimuV:

| Project | Description |
|---------|-------------|
| [FFmpeg](https://ffmpeg.org/) | 🎥 Industry-leading multimedia processing framework, providing core video processing capabilities |
| [Spring Boot](https://spring.io/projects/spring-boot) | 🚀 Elegant Java application development framework |
| [JavaCV](https://github.com/bytedeco/javacv) | 📹 Powerful computer vision and multimedia processing library |
| [Knife4j](https://doc.xiaominfo.com/) | 📚 Beautiful and easy-to-use API documentation generation tool |

---

<div align="center">

## 📄 License

This project is based on [MIT License](LICENSE)

</div> 