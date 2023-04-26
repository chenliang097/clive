/*
 * This is the source code of Telegram for Android v. 3.x.x
 * It is licensed under GNU GPL v. 2 or later.
 * You should have received a copy of the license in this archive (see LICENSE).
 * Copyright Nikolai Kudashov, 2013-2016.
 */

package com.rongtuoyouxuan.chatlive.permission;

import android.app.Activity;
import android.app.Application;
import android.app.KeyguardManager;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;

import java.util.concurrent.CopyOnWriteArrayList;

public class ForegroundDetector implements Application.ActivityLifecycleCallbacks {
  private static final String TAG = "SplashWakeBoss";
  private int refs;
  private boolean wasInBackground = true;
  private long enterBackgroundTime = 0;
  private CopyOnWriteArrayList<Listener> listeners = new CopyOnWriteArrayList<>();
  public ForegroundDetector(Application application) {
    application.registerActivityLifecycleCallbacks(this);
  }

  public static boolean isScreenLocked(Context context) {
    try {
      KeyguardManager myKM = (KeyguardManager) context.getSystemService(Context.KEYGUARD_SERVICE);
      return myKM.inKeyguardRestrictedInputMode();
    } catch (Exception e) {
      e.printStackTrace();
    }
    return true;
  }

  public boolean isForeground() {
    return refs > 0;
  }

  public boolean isBackground() {
    return refs == 0;
  }

  public void addListener(Listener listener) {
    listeners.add(listener);
  }

  public void removeListener(Listener listener) {
    listeners.remove(listener);
  }

  @Override
  public void onActivityStarted(Activity activity) {
    if (++refs == 1) {
      if (System.currentTimeMillis() - enterBackgroundTime < 200) {
        wasInBackground = false;
      }
      for (Listener listener : listeners) {
        try {
          listener.onBecameForeground(activity);
        } catch (Exception e) {
          e.printStackTrace();
        }
      }
    }
//    Log.d(TAG, "onActivityStarted() called <----------" + activity.getClass().getSimpleName());
  }

  public boolean isWasInBackground(boolean reset) {
    if (reset && Build.VERSION.SDK_INT >= 21
        && (System.currentTimeMillis() - enterBackgroundTime < 200)) {
      wasInBackground = false;
    }
    return wasInBackground;
  }

  public void resetBackgroundVar() {
    wasInBackground = false;
  }

  @Override
  public void onActivityStopped(Activity activity) {
    if (--refs == 0) {
      enterBackgroundTime = System.currentTimeMillis();
      wasInBackground = true;
      for (Listener listener : listeners) {
        try {
          listener.onBecameBackground(activity);
        } catch (Exception e) {
          e.printStackTrace();
        }
      }
    }
//    Log.d(TAG, "onActivityStopped() called <----------" + activity.getClass().getSimpleName());
  }

  @Override
  public void onActivityCreated(Activity activity, Bundle savedInstanceState) {}

  @Override
  public void onActivityResumed(Activity activity) {
//    Log.d(TAG, "onActivityResumed() called <----------" + activity.getClass().getSimpleName());
  }

  @Override
  public void onActivityPaused(Activity activity) {
//    Log.d(TAG, "onActivityPaused() called <----------" + activity.getClass().getSimpleName());
  }

  @Override
  public void onActivitySaveInstanceState(Activity activity, Bundle outState) {}

  @Override
  public void onActivityDestroyed(Activity activity) {}

  public interface Listener {
    void onBecameForeground(Activity act);

    void onBecameBackground(Activity act);
  }
}
