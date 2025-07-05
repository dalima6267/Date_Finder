
# 📌 Objective

**DateFinder** is an Android app that extracts and speaks aloud the most relevant date from a user-selected image, completely offline using OCR (Tesseract) and Text-to-Speech.

---

# 🏧 Architecture Overview

### 🔹 Pattern Used: MVVM (Simplified)

Though this app is small, we maintain modularity by separating:

- **MainActivity** – UI interaction and event handling  
- **OCRProcessor** – OCR logic using Tess-Two  
- **DateExtractor** – Regex-based date extraction  
- **TTSManager** – Offline Text-to-Speech  

### 🔹 Dependencies

| Tool        | Purpose                         |
|-------------|--------------------------------|
| Tess-Two    | Offline OCR engine             |
| Regex       | Detect date strings from text  |
| Android TTS | Speak extracted date           |
| Bitmap APIs | Pre-process selected image     |

---

# ⚙️ Core Algorithm Details

1. 📷 **Image Selection**  
User selects an image from the gallery using `ActivityResultContracts.GetContent()`.  
The image is converted to a Bitmap.

2. 🧠 **Image Preprocessing** (Optional but Recommended)  
Bitmap is scaled and converted to grayscale for better OCR accuracy.

3. 🔍 **Offline OCR (Tesseract via Tess-Two)**

```kotlin
val tessBaseAPI = TessBaseAPI()
tessBaseAPI.init(dataPath, "eng")
tessBaseAPI.setImage(bitmap)
val text = tessBaseAPI.utF8Text
```

4. 🗕 **Date Extraction via Regex**

```kotlin
val datePatterns = listOf(
    "\b\d{1,2}[/-]\d{1,2}[/-]\d{2,4}\b",    // 12/10/2023
    "\b\d{4}[/-]\d{1,2}[/-]\d{1,2}\b",      // 2023-10-12
    "\b\d{1,2}\s+[A-Za-z]{3,9}\s+\d{4}\b", // 12 October 2023
    "\b[A-Za-z]{3,9}\s+\d{1,2},\s+\d{4}\b" // October 12, 2023
)
```

5. 🔊 **Text-to-Speech**  
The detected date is spoken via Android's TTS engine.  
If no date is found, it says "No date detected".

---

# 💻 Setup Instructions

### 1. ✅ Environment Requirements

- Android Studio: Chipmunk+ or later  
- Min SDK: 24+  
- Languages: Kotlin only  
- No Internet permission required  

### 2. 🔧 Steps to Run

- Clone or download the project into Android Studio.  
- In `build.gradle (app)`, ensure:

```gradle
implementation 'com.rmtheis:tess-two:9.1.0'
```

- Add required folders:  
  Create: `assets/tessdata/`  
  Place: `eng.traineddata` inside it (download from [tessdata repo](https://github.com/tesseract-ocr/tessdata))  
- Run the app on a physical/emulator device with SDK 24+

---

# 🧪 Testing Instructions

### ✅ Manual Test Cases

| Test Case               | Steps                        | Expected Output             |
|-------------------------|------------------------------|----------------------------|
| Select image with clear date | Choose image like a bill with 12/10/2023 | Date is detected and spoken |
| Select image with no date | Choose logo or scenery image  | "No date detected" is spoken |
| Select image with month names | e.g., "October 3, 2024"     | Date is extracted and announced |
| Use blurry/small text image | Choose unclear or low-res image | May fail or misdetect        |
| Test offline             | Turn off WiFi and mobile data | App still works without network |

---

# 🔍 Developer Debugging

Add:

```kotlin
Log.d("OCR_RESULT", text)
```

in `OCRProcessor` to verify raw OCR output.

---

# 🔐 Permissions Used

```xml
<uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE"/>
```

---

# 📆 Folder Structure

```
DateFinder/
├── MainActivity.kt
├── OCRProcessor.kt
├── DateExtractor.kt
├── TTSManager.kt
├── res/
│   ├── layout/activity_main.xml
│   └── values/strings.xml, colors.xml
├── assets/
│   └── tessdata/
│       └── eng.traineddata
```

---

#Screenshots
 <img src="https://github.com/user-attachments/assets/4c6333b0-42b7-4ef4-a214-cdcd41cd8003" alt="Splash Screen" width="300" height="600">
 <img src="https://github.com/user-attachments/assets/7436efcc-847b-4412-945b-89345383e27e" alt="Splash Screen" width="300" height="600">

#Demo Video

https://github.com/user-attachments/assets/e0c98d08-cf21-49e5-a28b-bb6a2a3b24e1
