package com.example.myapplication;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import android.graphics.Color;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import androidx.core.view.WindowInsetsControllerCompat;
import android.animation.ValueAnimator;
import android.graphics.drawable.GradientDrawable;

public class SignupActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Fix status bar màu tối
        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(Color.TRANSPARENT);

        View decorView = window.getDecorView();
        decorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE |
                        View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        );

        // Icon status bar màu TRẮNG (phù hợp nền tối)
        WindowInsetsControllerCompat controller =
                new WindowInsetsControllerCompat(window, decorView);
        controller.setAppearanceLightStatusBars(false);
        // ✅ Thêm animated gradient vào đây
        androidx.appcompat.widget.AppCompatButton btnEnter = findViewById(R.id.signupBtn);

        ValueAnimator animator = ValueAnimator.ofFloat(0f, 1f);
        animator.setDuration(5000);
        animator.setRepeatCount(ValueAnimator.INFINITE);
        animator.setRepeatMode(ValueAnimator.REVERSE);
        animator.addUpdateListener(animation -> {
            float fraction = (float) animation.getAnimatedValue();
            int startColor = interpolateColor(0xFF8B5CF6, 0xFF06B6D4, fraction);
            int endColor = interpolateColor(0xFF06B6D4, 0xFF8B5CF6, fraction);
            GradientDrawable drawable = new GradientDrawable(
                    GradientDrawable.Orientation.LEFT_RIGHT,
                    new int[]{startColor, endColor}
            );
            drawable.setCornerRadius(26 * getResources().getDisplayMetrics().density);
            btnEnter.setBackground(drawable);
        });
        animator.start();
    }

    // ✅ Thêm hàm này bên ngoài onCreate, trong class MainActivity
    private int interpolateColor(int colorA, int colorB, float fraction) {
        int rA = (colorA >> 16) & 0xFF;
        int gA = (colorA >> 8) & 0xFF;
        int bA = colorA & 0xFF;

        int rB = (colorB >> 16) & 0xFF;
        int gB = (colorB >> 8) & 0xFF;
        int bB = colorB & 0xFF;

        int r = (int)(rA + (rB - rA) * fraction);
        int g = (int)(gA + (gB - gA) * fraction);
        int b = (int)(bA + (bB - bA) * fraction);

        return 0xFF000000 | (r << 16) | (g << 8) | b;
    }
}