# SlideToDelete
![GitHub release](https://img.shields.io/github/release/MinSeungHyun/SlideToDelete?color=red)
![GitHub Release Date](https://img.shields.io/github/release-date/MinSeungHyun/SlideToDelete?color=orange)
![GitHub last commit](https://img.shields.io/github/last-commit/MinSeungHyun/SlideToDelete?color=yellow)
[![](https://jitpack.io/v/MinSeungHyun/SlideToDelete.svg)](https://jitpack.io/#MinSeungHyun/SlideToDelete)
[![GitHub license](https://img.shields.io/github/license/MinSeungHyun/SlideToDelete?color=blue)](https://github.com/MinSeungHyun/SlideToDelete/blob/master/LICENSE)
<br>
Not a RecyclerView, Just a View<br>

<img src="gifs/preview.gif" width="250"/>

## Quick Start
1. Add this in your `build.gradle (app)` at the end of project
```gradle
allprojects {
	repositories {
		maven { url 'https://jitpack.io' }
	}
}

dependencies {
	implementation 'com.github.MinSeungHyun:SlideToDelete:v1.0'
}
```
2. In your xml
```xml
<RelativeLayout
    android:id="@+id/container"
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    android:layout_marginBottom="16dp"
    android:gravity="center"
    tools:ignore="HardcodedText">

    <TextView
        android:id="@+id/deletedTV"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:layout_alignBottom="@+id/text"
        android:layout_alignParentTop="true"
        android:background="#FF2E2E"
        android:gravity="center"
        android:text="deleted"
        android:textColor="@android:color/white"
        android:textSize="18sp"
        android:textStyle="bold" />

    <TextView
        android:id="@+id/text"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:background="#69BEFF"
        android:gravity="center"
        android:padding="16dp"
        android:text="Slide to delete"
        android:textColor="@android:color/black" />
</RelativeLayout>
```

3. code
```kotlin
deletedTV.setOnTouchListener(SlideToDeleteTouchListener(container, text))
```

That's it!
