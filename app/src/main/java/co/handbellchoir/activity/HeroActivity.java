package co.handbellchoir.activity;

import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;

import butterknife.Bind;
import co.handbellchoir.R;
import co.handbellchoir.enums.Score;

/**
 * Created by Leonardo on 11/8/15.
 */
public class HeroActivity extends SensorActivity {

    @Bind(R.id.bar)
    View bar;
    @Bind(R.id.effect)
    View effect;

    public void showEffect(@NonNull Score score) {

        bar.setBackgroundColor(ContextCompat.getColor(this, score.getColor()));
        effect.setBackgroundResource(score.getGradient());

        effect.setVisibility(View.VISIBLE);
        Animation anim = new ScaleAnimation(
                1f, 1f,
                0f, 1f,
                Animation.RELATIVE_TO_SELF, 0f,
                Animation.RELATIVE_TO_SELF, 1f);
        anim.setInterpolator(new AccelerateDecelerateInterpolator());
        anim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                Animation anim = new ScaleAnimation(
                        1f, 1f,
                        1f, 0f,
                        Animation.RELATIVE_TO_SELF, 0f,
                        Animation.RELATIVE_TO_SELF, 1f);
                anim.setInterpolator(new AccelerateDecelerateInterpolator());
                anim.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {
                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        effect.setVisibility(View.INVISIBLE);
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {
                    }
                });
                anim.setDuration(250);
                effect.startAnimation(anim);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });
        anim.setDuration(250);
        effect.startAnimation(anim);
    }
}
