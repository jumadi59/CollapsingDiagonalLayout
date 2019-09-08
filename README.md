## collapsingdiagonallayout

### Support
Support from Android 4.4 KitKat / Minimum API 19

### Installing
Add repository in build.gradle 

```gradle
repositories {
    maven { url "https://jitpack.io" }
}
```
And add dependencies 

```gradle
dependencies {
    implementation 'com.github.jumadi59:collapsingdiagonallayout:0.0.1'
}
```

### Sample Code

Add in code AppBarLayout
```xml
<com.cumacoding.collapsingdiagonal.CollapsingDiagonalLayout
            android:id="@+id/collapsing_diagonal"
            android:layout_width="match_parent"
            android:layout_height="250dp"
            app:di_position="bottom_left"
            app:di_overlap="90dp"
            app:titleEnabled="false"
            app:statusBarScrim="@color/colorPrimary"
            android:fitsSystemWindows="true"
            app:contentScrim="?attr/colorPrimary"
            app:layout_scrollFlags="scroll|exitUntilCollapsed"
            app:toolbarId="@+id/toolbar">
          
</com.cumacoding.collapsingdiagonal.CollapsingDiagonalLayout>
```
Code Behavior
```xml
<com.google.android.material.floatingactionbutton.FloatingActionButton
        android:id="@+id/fab"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_marginEnd="8dp"
        android:layout_marginTop="10dp"
        android:layout_gravity="end"
        app:isTrigger="true"
        app:CollapsingId="@id/collapsing_diagonal"
        app:layout_behavior="@string/collapsing_diagonal_layout_behavior"
        app:srcCompat="@android:drawable/ic_dialog_email" />
```
