# SlideToDelete
[![](https://jitpack.io/v/MinSeungHyun/SlideToDelete.svg)](https://jitpack.io/#MinSeungHyun/SlideToDelete)<br>
Not a RecyclerView, Just a View<br>

<img src="gifs/preview.gif" width="250"/>

## Quick Start
1. Add it in your `build.gradle (app)` at the end of project
```xml
allprojects {
	repositories {
		...
		maven { url 'https://jitpack.io' }
	}
}

...

dependencies {
        implementation 'com.github.MinSeungHyun:SlideToDelete:Tag'
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
