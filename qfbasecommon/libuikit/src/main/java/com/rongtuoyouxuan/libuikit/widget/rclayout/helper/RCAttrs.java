/*
 * Copyright 2018 GcsSloop
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * Last modified 2018-04-16 13:11:58
 *
 * GitHub: https://github.com/GcsSloop
 * WeiBo: http://weibo.com/GcsSloop
 * WebSite: http://www.gcssloop.com
 */

package com.rongtuoyouxuan.libuikit.widget.rclayout.helper;

public interface RCAttrs {
    void setRadius(int radius);

    boolean isClipBackground();

    void setClipBackground(boolean clipBackground);

    boolean isRoundAsCircle();

    void setRoundAsCircle(boolean roundAsCircle);

    float getTopLeftRadius();

    void setTopLeftRadius(int topLeftRadius);

    float getTopRightRadius();

    void setTopRightRadius(int topRightRadius);

    float getBottomLeftRadius();

    void setBottomLeftRadius(int bottomLeftRadius);

    float getBottomRightRadius();

    void setBottomRightRadius(int bottomRightRadius);

    int getStrokeWidth();

    void setStrokeWidth(int strokeWidth);

    int getStrokeColor();

    void setStrokeColor(int strokeColor);
}