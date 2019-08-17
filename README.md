# SlideToDelete
Not a RecyclerView, Just a View<br>

<img src="gifs/preview.gif" width="250"/>

## Quick Start
1. Copy [this file](https://github.com/MinSeungHyun/SlideToDelete/blob/master/app/src/main/java/com/seunghyun/dragtodelete/SlideToDeleteTouchListener.kt) to your project.
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

3. kotlin
```kotlin
deletedTV.setOnTouchListener(SlideToDeleteTouchListener(container, text))
```

That's it!
