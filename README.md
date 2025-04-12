# Mention Edit Text 
[![](https://jitpack.io/v/idewaputuwiprah/mention-edit-text.svg)](https://jitpack.io/#idewaputuwiprah/mention-edit-text)

An Android library that extends the standard EditText to support @mentions, just like in popular social media apps. It makes it easy to detect, highlight, and manage mentions within your app’s text input fields.

## Getting Started
You can download a jar from GitHub's [releases page][1].

Or use `Gradle` by:

1. Add the JitPack repository to your build file:
```gradle
repositories {
  maven { url 'https://jitpack.io' }
}
```
2. Add the dependency
```gradle
dependencies {
  implementation 'com.github.idewaputuwiprah:mention-edit-text:0.2'
}
```

## Overview
This is how the library works:

### 1. User Input Detection  
The user types text in `MentionEditText` widget. It automatically detects when the user types the @ character.

### 2. Suggestions Based on Trigger
After the @ character, the library listens for the following text and fetches/display suggestions dynamically based on the typed content. You can use Android's `RecyclerView` to show the suggestion list.

### 3. User Selection
The user taps on one of the suggestions from the suggestion list. 

### 4. Insert & Display
The selected suggestion is inserted into `MentionEditText` widget, replacing the @ trigger and associated text.

## How To Use

### 1. Insert `MentionEditText` widget into your layout  
```xml
<com.adit.library.MentionEditText
  android:layout_width="match_parent"
  android:layout_height="wrap_content"
  app:mentionColor="@color/some_color"/>
```
You can customize the mention color using `app:mentionColor` attribute.  

  
### 2. Define `RecyclerView` widget in your layout  
This widget will be used to display the suggestion list. You can see the sample layout [here][2]

  
### 3. Define `RecyclerViewAdapter` and attach it to your `RecyclerView`  
You need to extend `Mention` interface for your items. You can check the sample [here][3]

  
### 4. Implement keyword listener  
You need to implement [listener][4] that will fetch the suggestion list either localy or remotely.
```kotlin
edMention.setOnMentionTriggeredListener { keywords->
  // fetch your suggestion list using the keywords provided
}
```

  
### 5. Show the suggestion to `MentionEditText` widget  
To show the suggestion in the widget, you can use the following method:
```kotlin
edMention.setMention(item) // item is an object that extend Mention interface
```

  
### 6. Get mention objects  
To get mentions object, you can use `getMentions()` method from `MentionEditText` widget:
```kotlin
edMention.getMentions()
```
This will return list of [MentionSpan][5] objects  
  
**You can check the implementation sample [here][6]**

## License
    Copyright 2025 I Dewa Putu Wiprah Adwityam

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.

[1]: https://github.com/idewaputuwiprah/mention-edit-text/releases
[2]: https://github.com/idewaputuwiprah/mention-edit-text/blob/master/app/src/main/res/layout/activity_main.xml
[3]: https://github.com/idewaputuwiprah/mention-edit-text/blob/master/app/src/main/java/com/adit/mentionedittext/MainAdapter.kt
[4]: https://github.com/idewaputuwiprah/mention-edit-text/blob/master/app/src/main/java/com/adit/mentionedittext/MainActivity.kt#L39
[5]: https://github.com/idewaputuwiprah/mention-edit-text/blob/master/library/src/main/java/com/adit/library/MentionSpan.kt
[6]: https://github.com/idewaputuwiprah/mention-edit-text/blob/master/app/src/main/java/com/adit/mentionedittext/MainActivity.kt
